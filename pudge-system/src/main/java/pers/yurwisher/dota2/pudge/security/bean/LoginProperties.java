package pers.yurwisher.dota2.pudge.security.bean;

import lombok.Data;

/**
 * 登录配置
 * @author yq
 */
@Data
public class LoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean single = false;

    private LoginCode codeConfig;

}
