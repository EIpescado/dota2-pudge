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
            try {
                //文件存储根路径
                String root = systemConfigService.getValByCode(SystemConfigEnum.SYSTEM_FILE_ROOT_PATH);
                SystemFile entity;
                List<SystemFile> resultList = new ArrayList<>(files.length);
                for (MultipartFile file : files) {
                    //读取文件流, 并关闭流
                    byte[] fileBytes = IoUtil.readBytes(file.getInputStream());
                    //文件MD5
                    String fileMd5 = MD5_INSTANCE.digestHex(fileBytes);
                    //判断是否已经上传过
                    entity = super.getOneByFieldValueEq(SystemFile::getFileHash, fileMd5);
                    if (entity != null) {
                        resultList.add(entity);
                        continue;
                    }
                    entity = this.saveEntity(file, fileBytes, root, fileTag, fileMd5);
                    resultList.add(entity);
                }
                return resultList;
            } catch (IOException e) {
                logger.info("上传文件异常: [{}]", e.getLocalizedMessage());
                throw new SystemCustomException(SystemCustomTipEnum.AUTH_CODE_NOT_EXIST_OR_EXPIRED);
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public SystemFile saveEntity(MultipartFile file, byte[] fileBytes, String root, Integer fileTag, String fileMd5) {
        SystemFile entity = new SystemFile();
        //原始文件名称
        entity.setFileName(file.getOriginalFilename());
        String fileType = PudgeUtil.getFileType(file.getOriginalFilename(), fileBytes);
        //类型
        entity.setFileType(fileType);
        //大小
        entity.setFileSize(file.getSize());
        //文件标记 区分用途
        entity.setFileTag(fileTag);
        //文件MD5
        entity.setFileHash(fileMd5);
        //目标文件地址
        FileUtil.writeBytes(fileBytes, this.generateFinalFileFullPath(root, fileTag, fileType));
        baseMapper.insert(entity);
        return entity;
    }

    /**
     * 生成上传文件的最终存储全路径
     *
     * @param root     文件存储根路径
     * @param fileTag  文件标记,区分用途
     * @param fileType 文件
     * @return 文件全路径
     */
    private String generateFinalFileFullPath(String root, Integer fileTag, String fileType) {
        //根目录
        StrBuilder strBuilder = StrBuilder.create(root);
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
