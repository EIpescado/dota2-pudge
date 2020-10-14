package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemConfigTo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String description;
    private String val;
    private Long id;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
