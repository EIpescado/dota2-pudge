package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020/12/31 17:08
 * @description 系统文件
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemFile extends BaseEntity {
    private static final long serialVersionUID = -5074624960145901146L;

    /**原始文件名称*/
    private String fileName;
    /**文件MimeType*/
    private String mimeType;
    /**文件大小,单位字节(B)*/
    private Long fileSize;
    /**文件MD5*/
    private String fileHash;
    /**文件本地存储路径*/
    private String filePath;
    /**上传时间*/
    private LocalDateTime uploadDate;
    /**文件标记,区分用途,详见字典system_file_tag*/
    private Integer fileTag;
}
