package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.enums.SystemConfigEnum;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemFileMapper;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemFileVo;
import pers.yurwisher.dota2.pudge.system.service.IRelationService;
import pers.yurwisher.dota2.pudge.system.service.ISystemConfigService;
import pers.yurwisher.dota2.pudge.system.service.ISystemFileService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    private final IRelationService relationService;

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
    public SystemFileUploadBack upload(MultipartFile file, Integer fileTag, String mimeType, HttpServletRequest request) {
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
        //上传人
        entity.setUserId(JwtUser.currentUserId());
        //mimeType
        entity.setMimeType(mimeType);
        baseMapper.insert(entity);
        logger.info("文件[{}]信息保存完毕", file.getOriginalFilename());
        return this.toBack(entity);
    }

    @Override
    public List<SystemFileVo> getEntityFiles(Long entityId) {
        return baseMapper.getEntityFiles(entityId);
    }

    @Override
    public void download(Long id, HttpServletResponse response) {
        SystemFile fileEntity = baseMapper.selectById(id);
        //文件不存在
        if (fileEntity == null) {
            throw new SystemCustomException(SystemCustomTipEnum.FILE_NOT_EXIST);
        }
        //文件存储根路径
        String root = systemConfigService.getValByCode(SystemConfigEnum.SYSTEM_FILE_ROOT_PATH);
        String fileName = PudgeUtil.urlEncode(fileEntity.getFileName());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        response.setContentType(fileEntity.getMimeType());
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(root + fileEntity.getFilePath());
            IoUtil.copyByNIO(inputStream, outputStream, 8192, new StreamProgress() {
                @Override
                public void start() {
                    logger.info("开始下载: [{}]", fileEntity.getFileName());
                }

                @Override
                public void progress(long l) {
                    logger.info("正在下载: [{}],已下载: [{}]", fileEntity.getFileName(), l);
                }

                @Override
                public void finish() {
                    logger.info("下载: [{}]完成", fileEntity.getFileName());
                }
            });
        } catch (IOException e) {
            logger.info("文件下载异常: [{}}]", e.getLocalizedMessage());
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
        }
    }

    @Override
    public void downloadZip(List<Long> ids, HttpServletResponse response) {
        if (CollectionUtil.isNotEmpty(ids)) {
            List<SystemFileVo> list = baseMapper.getSystemFiles(ids);
            if (CollectionUtil.isNotEmpty(list)) {
                //文件存储根路径
                String root = systemConfigService.getValByCode(SystemConfigEnum.SYSTEM_FILE_ROOT_PATH);
                ZipOutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    //生成zip文件名 规则第一个文件名 + 等 + 压缩包总文件个数 + 个文件.zip,形如 1.txt等3个文件
                    String zipFileName = PudgeUtil.urlEncode(StrBuilder.create(list.get(0).getFileName(),"等",Integer.toString(list.size()),"个文件.zip").toString());
                    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + zipFileName);
                    response.setContentType("application/x-zip-compressed");
                    outputStream = new ZipOutputStream(response.getOutputStream());
                    SystemFileVo currentFileVo;
                    for (int i = 0; i < list.size(); i++) {
                        currentFileVo = list.get(i);
                        //压缩包中的文件名
                        outputStream.putNextEntry(new ZipEntry(currentFileVo.getFileName()));
                        //写到
                        inputStream = new FileInputStream(FileUtil.file(root, currentFileVo.getFilePath()));
                        IoUtil.copy(inputStream, outputStream);
                        IoUtil.close(inputStream);
                        outputStream.closeEntry();
                    }
                } catch (IOException e) {
                    logger.info("打包ZIP批量下载异常: [{}]", e.getLocalizedMessage());
                } finally {
                    IoUtil.close(inputStream);
                    IoUtil.close(outputStream);
                }
            }
        }
    }

    private SystemFileUploadBack toBack(SystemFile entity) {
        SystemFileUploadBack back = new SystemFileUploadBack();
        back.setFileName(entity.getFileName());
        back.setId(entity.getId());
        back.setHash(entity.getFileHash());
        return back;
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

    public static void main(String[] args) {
        ZipUtil.zip(FileUtil.file("d:/ccc.zip"), false, FileUtil.file("d:/logs"));
    }
}
