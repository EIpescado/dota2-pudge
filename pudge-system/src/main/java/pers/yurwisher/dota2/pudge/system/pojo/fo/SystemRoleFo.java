package pers.yurwisher.dota2.pudge.system.pojo.fo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemRoleFo implements Serializable {
    private static final long serialVersionUID = -2787152552603004097L;
    private String name;
    private String description;
}
