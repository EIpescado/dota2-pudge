package pers.yurwisher.dota2.pudge.security.bean;

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
         * 令牌过期时间 单位分钟
         */
        private Duration expireTime = Duration.ofHours(4);
        private Long expireTimeMills;

        /**
         * token 续期检查时间 分钟
         */
        private Duration detectTime = Duration.ofMinutes(30);
        private Long detectTimeMills;

        /**
         * 续期时间 分钟
         */
        private Duration renewTime = Duration.ofHours(2);
        private Long renewTimeMills;

        public ClientTokenConfig() {
            this.expireTimeMills = expireTime.toMillis();
            this.detectTimeMills = detectTime.toMillis();
            this.renewTimeMills = renewTime.toMillis();
        }

        public UserClientType getType() {
            return type;
        }

        public Duration getExpireTime() {
            return expireTime;
        }

        public Long getExpireTimeMills() {
            return expireTimeMills;
        }

        public Duration getDetectTime() {
            return detectTime;
        }

        public Long getDetectTimeMills() {
            return detectTimeMills;
        }

        public Duration getRenewTime() {
            return renewTime;
        }

        public Long getRenewTimeMills() {
            return renewTimeMills;
        }

        public void setType(UserClientType type) {
            this.type = type;
        }

        public void setExpireTime(Duration expireTime) {
            this.expireTime = expireTime;
        }

        public void setDetectTime(Duration detectTime) {
            this.detectTime = detectTime;
        }

        public void setRenewTime(Duration renewTime) {
            this.renewTime = renewTime;
        }
    }
}
