package pers.yurwisher.dota2.pudge.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;

/**
 * 注册
 * @author yq
 */
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final IOnlineUserService onlineUserService;

    public TokenConfigurer(TokenProvider tokenProvider,IOnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.onlineUserService = onlineUserService;
    }

    @Override
    public void configure(HttpSecurity http) {
        TokenFilter customFilter = new TokenFilter(tokenProvider,onlineUserService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
