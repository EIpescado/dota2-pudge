package pers.yurwisher.dota2.pudge.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决认证过的用户访问无权限资源时的异常
 * @author yq
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
      log.info("ip:{},request {} ,security error:{}", PudgeUtil.getIp(request), request.getRequestURI(),e.getMessage());
      PudgeUtil.responseJSON(response, R.fail(SystemCustomTipEnum.NOR_RIGHT));
   }
}
