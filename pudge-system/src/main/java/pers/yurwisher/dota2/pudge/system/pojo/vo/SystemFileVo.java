package pers.yurwisher.dota2.pudge.system.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    private LocalDateTime uploadDate;
    private String uploadPerson;
    private Integer fileTag;


    @JSONField(serialize = false)
    private File file;
}
