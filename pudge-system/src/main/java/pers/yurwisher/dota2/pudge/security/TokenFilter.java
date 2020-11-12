package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.enums.UserClientType;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求凭证过滤器
 *
 * @author yq 2020年10月16日 14:19:25
 */
public class TokenFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);


    private final TokenProvider tokenProvider;
    private final IOnlineUserService onlineUserService;
    /**subject中元素的数量 subject构成 username::clientType*/
    private static final int SUBJECT_ELEMENT_NUMBER = 2;

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
            //用户名::客户端类型 key,形如 yq::pc
            String subject = tokenProvider.getSubjectFromToken(token);
            if (StrUtil.isNotBlank(subject)) {
                // 用户是否已登录,存在key 且未过期
                Long onlineExpireTime = onlineUserService.getOnlineExpireTime(subject);
                if (onlineExpireTime != null) {
                    //从subject 获取用户名和用户客户端类型
                    String[] array = subject.split(PudgeUtil.DOUBLE_COLON);
                    //subject结构正确
                    if(array.length == SUBJECT_ELEMENT_NUMBER){
                        String username = array[1];
                        UserClientType type = UserClientType.valueOf(array[0]);
                        //存入用户信息
                        Authentication authentication = tokenProvider.getAuthentication(username, token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        // 如有必要,Token 续期
                        tokenProvider.checkRenewal(subject, onlineExpireTime,type);
                    }else{
                        PudgeUtil.responseJSON((HttpServletResponse) servletResponse, R.fail(SystemCustomTipEnum.AUTH_LOGIN_EXPIRED));
                        return;
                    }
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
