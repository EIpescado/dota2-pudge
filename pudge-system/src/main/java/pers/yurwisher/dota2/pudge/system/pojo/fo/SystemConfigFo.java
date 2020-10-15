package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "配置编码必填")
    private String code;
    @NotBlank(message = "配置描述必填")
    private String description;
    @NotBlank(message = "配置值必填")
    private String val;
}
