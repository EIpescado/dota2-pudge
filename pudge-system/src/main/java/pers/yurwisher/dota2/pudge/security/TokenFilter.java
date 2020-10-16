package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pers.yurwisher.dota2.pudge.security.cache.OnlineUser;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求凭证过滤器
 * @author yq 2020年10月16日 14:19:25
 */
public class TokenFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);


    private final TokenProvider tokenProvider;
    private final IOnlineUserService onlineUserService;

    public TokenFilter(TokenProvider tokenProvider, IOnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = tokenProvider.getToken(httpServletRequest);
        if (StrUtil.isNotBlank(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            if(StrUtil.isNotBlank(username))        {
                // 用户是否已登录
                OnlineUser onlineUser = onlineUserService.getOne(username);
                if (onlineUser != null) {
                    //存入用户信息
                    Authentication authentication = tokenProvider.getAuthentication(username,token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // 如有必要,Token 续期
                    tokenProvider.checkRenewal(username);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static void main(String[] args) {
        //生成加密token密钥
        System.out.println(Base64.encode(RandomUtil.randomString(128)));
    }
}
