package pers.yurwisher.dota2.pudge.security.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security 配置
 * @author yq
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class SecurityProperties {

    /**
     * token 请求头名称
     */
    private String header;

    /**
     * token 令牌前缀
     */
    private String prefix;

    /**
     * 令牌编码密钥,建议88位以上base64
     */
    private String secret;

    /**
     * 令牌过期时间 单位分钟
     */
    private Long expireTime;

    /**
     * token 续期检查时间 分钟
     */
    private Long detectTime;

    /**
     * 续期时间 分钟
     */
    private Long renewTime;

    public String getPrefix() {
        return prefix + " ";
    }
}
