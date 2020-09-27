package pers.yurwisher.dota2.pudge.security.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yq
 * @date 2020/09/18 09:29
 * @description 用户登录表单
 * @since V1.0.0
 */
@Data
public class UserLoginForm {

    @NotBlank(message = "帐号不可为空")
    private String username;

    @NotBlank(message = "密码不可为空")
    private String password;

    private String code;

    private String uuid;
}
