package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
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
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 2020年9月21日 10:50:20
 * @author yq
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final SecurityProperties properties;
    private final RedisTemplate<String,Object> redisTemplate;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;
    private final ISystemUserService systemUserService;


    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     *  创建Token 设置永不过期，
     *  Token 的时间有效性转到Redis 维护
     * @param vo 用户信息
     * @return token
     */
    public String createToken(CurrentUser vo) {
        return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                .setSubject(vo.getUsername())
                .compact();
    }


    /**
     * 依据Token 获取鉴权信息
     * @param token token字符串
     * @return 鉴权信息
     */
    Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String username = claims.getSubject();
        CurrentUser currentUser = systemUserService.findUserByUsername(username);
        JwtUser principal = new JwtUser(currentUser);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    /**
     * 从token 获取载荷信息
     * @param token token字符串
     * @return 载荷信息
     */
    Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token) {
        String onlineKey = properties.getOnlineKey() + token;
        // 判断是否续期token,计算token的过期时间
        Long time = redisTemplate.getExpire(onlineKey);
        int timeInt = time != null ? time.intValue() : 0;
        Date now = new Date();
        //过期时间
        Date expireDate = DateUtil.offset(now, DateField.MILLISECOND, timeInt);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - now.getTime();
        // 如果在续期检查的范围内，则续期
        if (differ <= properties.getDetectTime()) {
            long renew = timeInt + properties.getRenewTime();
            redisTemplate.expire(onlineKey, renew, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 从请求中获取token
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
}
