package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.enums.UserClientType;
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 2020年9月21日 10:50:20
 *
 * @author yq
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final SecurityProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;
    private final ISystemUserService systemUserService;
    private Map<UserClientType, List<SecurityProperties.ClientTokenConfig>> userTokenConfigMap;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
        //客户端token配置
        userTokenConfigMap = properties.getClientTokenConfigs().stream().collect(Collectors.groupingBy(SecurityProperties.ClientTokenConfig::getType));
    }

    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param subject subject
     * @return token
     */
    public String createToken(String subject) {
        return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                .setSubject(subject)
                .compact();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param username 用户名
     * @param token    请求凭证
     * @return 鉴权信息
     */
    Authentication getAuthentication(String username, String token) {
        CurrentUser currentUser = systemUserService.findUserByUsername(username);
        JwtUser principal = new JwtUser(currentUser);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    /**
     * 检查续期
     *
     * @param onlineKey        用户在线key
     * @param onlineExpireTime 用户在线过期时间
     */
    public void checkRenewal(String onlineKey, Long onlineExpireTime, UserClientType type) {
        // 判断是否续期token,计算token的过期时间
        long timeInt = onlineExpireTime != null ? onlineExpireTime.intValue() : 0;
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireDate = now.plusSeconds(timeInt);
        // 判断当前时间与过期时间的时间差
        Duration differ = Duration.between(expireDate, now);
        SecurityProperties.ClientTokenConfig clientTokenConfig = userTokenConfigMap.get(type).get(0);
        // 如果在续期检查的范围内，则续期
        if (differ.toMillis() <= clientTokenConfig.getDetectTimeMills()) {
            long renew = timeInt + clientTokenConfig.getRenewTimeMills();
            redisTemplate.expire(onlineKey, renew, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 从请求中获取token
     *
     * @param request 请求
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (StrUtil.isNotBlank(requestHeader) && requestHeader.startsWith(properties.getPrefix())) {
            return requestHeader.replace(properties.getPrefix(), "");
        } else {
            log.debug("非法Token：{}", requestHeader);
        }
        return null;
    }

    /**
     * 从请求中获取token -> 从token中获取 用户名::客户端类型key
     *
     * @param token 请求凭证
     * @return username
     */
    public String getSubjectFromToken(String token) {
        if (StrUtil.isNotBlank(token)) {
            //从token 获取载荷信息
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            return claims.getSubject();
        }
        return null;
    }
}
