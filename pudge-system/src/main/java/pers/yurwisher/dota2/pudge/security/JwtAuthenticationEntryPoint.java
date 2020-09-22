package pers.yurwisher.dota2.pudge.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author yq
 * @date 2019/07/15 15:17
 * @description security 无凭证访问需凭证资源时回调 解决匿名用户访问无权限资源时的异常
 * @since V1.0.0
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 6166431046638739236L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("ip:{},request {} ,security error:{}", PudgeUtil.getIp(request), request.getRequestURI(),e.getMessage());
        PudgeUtil.responseJSON(response, R.fail(SystemCustomTipEnum.NOR_RIGHT));
    }
}
