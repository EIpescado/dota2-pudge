package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.base.BasePageQo;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;


/**
 * 切面定义 配置
 * @author yq
 */
@Component
@Aspect
public class AspectConfig {

    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    /**
     * 查询参数 转化aop
     */
    @Around("execution(public * *(pers.yurwisher.dota2.pudge.base.BasePageQo+))")
    public Object aroundPageQo(ProceedingJoinPoint pjp) throws Throwable {
        return around(pjp);
    }

    private Object around(ProceedingJoinPoint pjp) throws Throwable {
        BasePageQo qo = (BasePageQo) pjp.getArgs()[0];
        if (qo != null) {
            if (StrUtil.isEmpty(qo.getUsername())) {
                CurrentUser currentUser = JwtUser.current();
                qo.setUsername(currentUser.getUsername());
                qo.setUserId(currentUser.getId());
            }
        }
        return pjp.proceed();
    }

}
