package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrBuilder;
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
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
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
    @Transactional(rollbackFor = Exception.class)
    public SystemFileUploadBack upload(MultipartFile file, Integer fileTag, HttpServletRequest request) {
        logger.info("开始上传[{}]", file.getOriginalFilename());
        //读取文件流, 并关闭流
        byte[] fileBytes;
        try {
            fileBytes = IoUtil.readBytes(file.getInputStream());
        } catch (IOException e) {
            logger.info("上传文件失败: [{}]", e.getLocalizedMessage());
            throw new SystemCustomException(SystemCustomTipEnum.FILE_UPLOAD_ERROR);
        }
        //文件MD5
        String fileMd5 = MD5_INSTANCE.digestHex(fileBytes);
        //判断是否已经上传过
        SystemFileUploadBack back = baseMapper.findByFileHash(fileMd5);
        if (back != null) {
            logger.info("结束上传[{}],存在相同文件[{}]", file.getOriginalFilename(), fileMd5);
            return back;
        }
        //文件类型
        String fileType = PudgeUtil.getFileType(file.getOriginalFilename(), fileBytes);
        //文件存储路径
        String filePath = generateFileFinalSavePath(fileTag, fileType);
        //文件存储根路径
        String root = systemConfigService.getValByCode(SystemConfigEnum.SYSTEM_FILE_ROOT_PATH);
        //存储文件到目标地址
        FileUtil.writeBytes(fileBytes, root + filePath);
        logger.info("文件[{}]上传结束", file.getOriginalFilename());
        //保存文件基础信息到mysql
        SystemFile entity = this.saveEntity(file, fileType, fileTag, fileMd5, filePath);
        logger.info("文件[{}]信息保存完毕", file.getOriginalFilename());
        return this.toBack(entity);
    }

    private SystemFileUploadBack toBack(SystemFile entity) {
        SystemFileUploadBack back = new SystemFileUploadBack();
        back.setFileName(entity.getFileName());
        back.setId(entity.getId());
        back.setHash(entity.getFileHash());
        return back;
    }

    private SystemFile saveEntity(MultipartFile file, String fileType, Integer fileTag, String fileMd5, String filePath) {
        SystemFile entity = new SystemFile();
        //原始文件名称
        entity.setFileName(file.getOriginalFilename());
        //类型
        entity.setFileType(fileType.toLowerCase());
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
