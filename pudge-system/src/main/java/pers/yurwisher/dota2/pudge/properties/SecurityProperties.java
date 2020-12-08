package pers.yurwisher.dota2.pudge.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.enums.UserClientType;

import java.time.Duration;
import java.util.List;

/**
 * Security 配置
 *
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
     * 不同客户端 token配置
     */
    private List<ClientTokenConfig> clientTokenConfigs;

    public String getPrefix() {
        return prefix + " ";
    }

    public static class ClientTokenConfig {

        /**
         * 客户端类型
         */
        private UserClientType type;

        /**
         * 令牌过期时间
         */
        private Duration expireTime;

        /**
         * token 续期检查时间
         */
        private Duration detectTime;
        private Long detectTimeSeconds;

        /**
         * 续期时间
         */
        private Duration renewTime;
        private Long renewTimeSeconds;

        public UserClientType getType() {
            return type;
        }

        public Duration getExpireTime() {
            return expireTime;
        }

        public void setType(UserClientType type) {
            this.type = type;
        }

        public void setExpireTime(Duration expireTime) {
            this.expireTime = expireTime;
        }

        public void setDetectTime(Duration detectTime) {
            this.detectTime = detectTime;
            this.detectTimeSeconds = detectTime.getSeconds();
        }

        public void setRenewTime(Duration renewTime) {
            this.renewTime = renewTime;
            this.renewTimeSeconds = renewTime.getSeconds();
        }

        public Duration getDetectTime() {
            return detectTime;
        }

        public Long getDetectTimeSeconds() {
            return detectTimeSeconds;
        }

        public Duration getRenewTime() {
            return renewTime;
        }

        public Long getRenewTimeSeconds() {
            return renewTimeSeconds;
        }
    }
}
