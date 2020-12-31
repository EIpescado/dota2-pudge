package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemFileTo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String mimeType;
    private Long fileSize;
    private String fileHash;
    private String filePath;
    private LocalDateTime uploadDate;
    private Integer fileTag;
    private Long id;
    private Boolean enabled;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
