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

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid;

    /**登录用户客户端类型*/
    private String type;
}
