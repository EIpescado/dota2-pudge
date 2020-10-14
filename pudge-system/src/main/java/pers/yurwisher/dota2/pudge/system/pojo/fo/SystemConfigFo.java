package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemConfigFo implements Serializable {
    private static final long serialVersionUID = 6707993662531503550L;
    private String code;
    private String description;
    private String val;
}
