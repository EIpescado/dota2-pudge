package pers.yurwisher.dota2.pudge.security.form;

import lombok.Data;
import pers.yurwisher.dota2.pudge.enums.UserClientType;

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

    /**用户客户端类型 默认PC*/
    private UserClientType type = UserClientType.PC;
}
