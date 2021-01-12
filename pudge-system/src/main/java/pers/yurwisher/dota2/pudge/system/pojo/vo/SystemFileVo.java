package pers.yurwisher.dota2.pudge.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemFileVo implements Serializable {
    private static final long serialVersionUID = 7243173454002280856L;
    private String fileName;
    private String fileType;
    private String filePath;
    private Integer fileTag;
    private String mimeType;
    private Long id;
}
