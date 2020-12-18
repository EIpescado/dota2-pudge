package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.base.BasePageQo;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.utils.RequestHolder;

import javax.servlet.http.HttpServletRequest;


/**
 * 切面定义 配置
 *
 * @author yq
 */
@Component
@Aspect
public class AspectConfig {

    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);
    private static final ThreadLocal<Long> CURRENT_TIME = new ThreadLocal<>();

    private ISystemLogService systemLogService;
    @Value("${query.max-page-size}")
    private Long maxPageSize;

    public AspectConfig(ISystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * 分页列表参数设置切面
     */
    @Around("execution(public * *(pers.yurwisher.dota2.pudge.base.BasePageQo+))")
    public Object aroundPageQo(ProceedingJoinPoint pjp) throws Throwable {
        BasePageQo qo = (BasePageQo) pjp.getArgs()[0];
        if (qo != null) {
            //限制分页查询最大单页数量
            if(qo.getSize() > maxPageSize){
                throw new SystemCustomException(SystemCustomTipEnum.QUERY_PAGE_SIZE_OVER_MAX);
            }
            if (StrUtil.isEmpty(qo.getUsername())) {
                CurrentUser currentUser = JwtUser.current();
                qo.setUsername(currentUser.getUsername());
                qo.setUserId(currentUser.getId());
            }
        }
        return pjp.proceed();
    }

    /**
     * 日志切点
     */
    @Pointcut("@annotation(pers.yurwisher.dota2.pudge.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 日志环绕增强
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        CURRENT_TIME.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        long timeCost = getTimeCost();
        //异步保存日志
        systemLogService.saveLog(joinPoint, this.getUserClientInfo(), this.getUserId(), timeCost);
        return result;
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        long timeCost = getTimeCost();
        //异步保存日志
        systemLogService.saveErrorLog(joinPoint, this.getUserClientInfo(), this.getUserId(), timeCost, throwable.getLocalizedMessage());
    }

    private long getTimeCost() {
        long timeCost = System.currentTimeMillis() - CURRENT_TIME.get();
        CURRENT_TIME.remove();
        return timeCost;
    }

    private Long getUserId() {
        Long userId;
        try {
            userId = JwtUser.currentUserId();
        } catch (SystemCustomException e) {
            userId = 1L;
        }
        return userId;
    }

    private PudgeUtil.UserClientInfo getUserClientInfo() {
        HttpServletRequest request = RequestHolder.currentRequest();
        return PudgeUtil.getUserClientInfo(request);
    }

}
