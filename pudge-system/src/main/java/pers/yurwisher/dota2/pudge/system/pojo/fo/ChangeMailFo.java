package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yq
 * @date 2018/11/16 09:38
 * @description 变更邮箱
 * @since V1.0.0
 */
@Data
public class ChangeMailFo implements Serializable {
    private static final long serialVersionUID = 984897616818417570L;

    @NotBlank(message = "新邮箱必填")
    private String mail;

    @NotBlank(message = "验证码必填")
    private String code;

    @NotBlank(message = "帐号密码必填")
    private String password;
}
