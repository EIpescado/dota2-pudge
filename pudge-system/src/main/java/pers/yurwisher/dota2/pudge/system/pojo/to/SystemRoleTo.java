package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemRoleTo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Integer level;
    private Long id;
    private Boolean enabled;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
