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

    /**
     *密码前端会通过公钥加密, 解密私钥
     */
    private String passwordPrivateKey;

    private LoginCode codeConfig;

}
