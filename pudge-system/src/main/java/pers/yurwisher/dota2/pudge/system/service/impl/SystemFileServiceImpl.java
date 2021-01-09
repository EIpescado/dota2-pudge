package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.enums.SystemConfigEnum;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemFileMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemConfigService;
import pers.yurwisher.dota2.pudge.system.service.ISystemFileService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class SystemFileServiceImpl extends BaseServiceImpl<SystemFileMapper, SystemFile> implements ISystemFileService {

    private final ISystemConfigService systemConfigService;

    /**
     * MD5实例
     */
    private static final MD5 MD5_INSTANCE = MD5.create();

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemFileTo> list(SystemFileQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    public List<SystemFile> upload(MultipartFile[] files, Integer fileTag, HttpServletRequest request) {
        if (ArrayUtil.isNotEmpty(files)) {
            logger.info("上传文件开始,数量[{}]",files.length);
            try {
                //文件存储根路径
                String root = systemConfigService.getValByCode(SystemConfigEnum.SYSTEM_FILE_ROOT_PATH);
                SystemFile entity;
                List<SystemFile> resultList = new ArrayList<>(files.length);
                for (MultipartFile file : files) {
                    logger.info("开始上传[{}]",file.getOriginalFilename());
                    //读取文件流, 并关闭流
                    byte[] fileBytes = IoUtil.readBytes(file.getInputStream());
                    //文件MD5
                    String fileMd5 = MD5_INSTANCE.digestHex(fileBytes);
                    //判断是否已经上传过
                    entity = super.getOneByFieldValueEq(SystemFile::getFileHash, fileMd5);
                    if (entity != null) {
                        logger.info("结束上传[{}],存在相同文件[{}]",file.getOriginalFilename(),fileMd5);
                        resultList.add(entity);
                        continue;
                    }
                    //文件类型
                    String fileType = PudgeUtil.getFileType(file.getOriginalFilename(), fileBytes);
                    //文件存储路径
                    String filePath = generateFileFinalSavePath(fileTag,fileType);
                    //保存文件基础信息到mysql
                    entity = this.saveEntity(file, fileType,fileTag, fileMd5,filePath);
                    //存储文件到目标地址
                    FileUtil.writeBytes(fileBytes,root + filePath);
                    logger.info("结束上传[{}]",file.getOriginalFilename());
                    resultList.add(entity);
                }
                logger.info("上传文件结束");
                return resultList;
            } catch (IOException e) {
                logger.info("上传文件异常: [{}]", e.getLocalizedMessage());
                throw new SystemCustomException(SystemCustomTipEnum.AUTH_CODE_NOT_EXIST_OR_EXPIRED);
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public SystemFile saveEntity(MultipartFile file, String fileType,Integer fileTag, String fileMd5,String filePath) {
        SystemFile entity = new SystemFile();
        //原始文件名称
        entity.setFileName(file.getOriginalFilename());
        //类型
        entity.setFileType(fileType);
        //大小
        entity.setFileSize(file.getSize());
        //文件标记 区分用途
        entity.setFileTag(fileTag);
        //文件MD5
        entity.setFileHash(fileMd5);
        //文件存储路径
        entity.setFilePath(filePath);
        //上传时间
        entity.setUploadDate(LocalDateTime.now());
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 生成上传文件的最终存储路径(不包含根路径,方便迁移)
     *
     * @param fileTag  文件标记,区分用途
     * @param fileType 文件
     * @return 存储路径
     */
    private String generateFileFinalSavePath(Integer fileTag, String fileType) {
        //根目录
        StrBuilder strBuilder = StrBuilder.create();
        //文件标记目录
        strBuilder.append(File.separator).append(fileTag);
        LocalDateTime now = LocalDateTime.now();
        //年 目录
        strBuilder.append(File.separator).append(now.getYear());
        //月 目录
        strBuilder.append(File.separator).append(now.getMonthValue());
        //日 目录
        strBuilder.append(File.separator).append(now.getDayOfMonth());
        //文件名称
        strBuilder.append(File.separator).append(IdUtil.fastSimpleUUID()).append(StrUtil.DOT).append(fileType);
        return strBuilder.toString();
    }
}
