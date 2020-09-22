package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pers.yurwisher.dota2.pudge.annotation.AnonymousAccess;
import pers.yurwisher.dota2.pudge.security.JwtAccessDeniedHandler;
import pers.yurwisher.dota2.pudge.security.JwtAuthenticationEntryPoint;
import pers.yurwisher.dota2.pudge.security.TokenConfigurer;
import pers.yurwisher.dota2.pudge.security.TokenProvider;
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yq
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final SecurityProperties properties;
    private final IOnlineUserService onlineUserService;
    private final String ALL = "ALL";

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        // 获取匿名标记
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 静态资源等等
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/webSocket/**").permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 文件
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
                // GET
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethod.GET.name()).toArray(new String[0])).permitAll()
                // POST
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethod.POST.name()).toArray(new String[0])).permitAll()
                // PUT
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethod.PUT.name()).toArray(new String[0])).permitAll()
                // PATCH
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethod.PATCH.name()).toArray(new String[0])).permitAll()
                // DELETE
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethod.DELETE.name()).toArray(new String[0])).permitAll()
                // 所有类型的接口都放行
                .antMatchers(anonymousUrls.get(ALL).toArray(new String[0])).permitAll()

                // 所有请求都需要认证
                .anyRequest().authenticated()
                .and().apply(securityConfigurerAdapter());
    }

    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<String, Set<String>>(6){
            private static final long serialVersionUID = -5791995712570156974L;

            {
                put(RequestMethod.GET.name(), new HashSet<>());
                put(RequestMethod.POST.name(), new HashSet<>());
                put(RequestMethod.PUT.name(), new HashSet<>());
                put(RequestMethod.PATCH.name(), new HashSet<>());
                put(RequestMethod.DELETE.name(), new HashSet<>());
                put(ALL, new HashSet<>());
            }
        };
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                //请求方式
                String requestMethod = CollectionUtil.isEmpty(requestMethods) ? ALL: requestMethods.get(0).name();
                //路径pattern
                Set<String> patterns = infoEntry.getKey().getPatternsCondition().getPatterns();
                if(CollectionUtil.isNotEmpty(patterns)){
                    anonymousUrls.get(requestMethod).addAll(patterns);
                }
            }
        }
        return anonymousUrls;
    }

    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(tokenProvider, properties, onlineUserService);
    }

}
