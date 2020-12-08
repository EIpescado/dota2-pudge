package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import io.netty.util.internal.ThrowableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.annotation.Log;
import pers.yurwisher.dota2.pudge.base.BasePageQo;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.utils.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 切面定义 配置
 * @author yq
 */
@Component
@Aspect
public class AspectConfig {

    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);
    private static final ThreadLocal<Long> CURRENT_TIME = new ThreadLocal<>();

    private  ISystemLogService systemLogService;

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
            if (StrUtil.isEmpty(qo.getUsername())) {
                CurrentUser currentUser = JwtUser.current();
                qo.setUsername(currentUser.getUsername());
                qo.setUserId(currentUser.getId());
            }
        }
        return pjp.proceed();
    }

    /**日志切点*/
    @Pointcut("@annotation(pers.yurwisher.dota2.pudge.annotation.Log)")
    public void logPointcut() {}

    /**日志环绕增强*/
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        CURRENT_TIME.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        long timeCost = getTimeCost();
        //异步保存日志
        systemLogService.saveLog(joinPoint,this.getUserClientInfo(),this.getUserId(),timeCost);
        return result;
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        long timeCost = getTimeCost();
        //异步保存日志
        systemLogService.saveErrorLog(joinPoint,this.getUserClientInfo(),this.getUserId(),timeCost,throwable.getLocalizedMessage());
    }

    private long getTimeCost(){
        long timeCost = System.currentTimeMillis() - CURRENT_TIME.get();
        CURRENT_TIME.remove();
        return timeCost;
    }

    private Long getUserId(){
        Long userId;
        try{
            userId = JwtUser.currentUserId();
        }catch (SystemCustomException e){
            userId = 1L;
        }
        return userId;
    }

    private PudgeUtil.UserClientInfo getUserClientInfo(){
        HttpServletRequest request = RequestHolder.currentRequest();
        return  PudgeUtil.getUserClientInfo(request);
    }

}
