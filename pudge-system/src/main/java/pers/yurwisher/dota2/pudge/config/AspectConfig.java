package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.yurwisher.dota2.pudge.annotation.AdminNotFilterByUserId;
import pers.yurwisher.dota2.pudge.base.BasePageQo;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.utils.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;


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

    private final ISystemLogService systemLogService;
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
            if (qo.getSize() > maxPageSize) {
                logger.info("分页查询超出最大单页数量");
                throw new SystemCustomException(SystemCustomTipEnum.QUERY_PAGE_SIZE_OVER_MAX);
            }
            if (StrUtil.isEmpty(qo.getUsername())) {
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                Method method = signature.getMethod();
                AdminNotFilterByUserId annotation = method.getAnnotation(AdminNotFilterByUserId.class);
                CurrentUser currentUser = JwtUser.current();
                //是否按用户ID过滤
                boolean filterByUserId ;
                //配置了注解
                if (annotation != null) {
                    //不按用户ID过滤 需满足 AdminNotFilterByUserId 为true 且是 admin
                    filterByUserId = !(annotation.value() && currentUser.isAdmin());
                }else{
                    //未配置注解则判断是否admin admin默认默认不按用户ID过滤数据
                    filterByUserId = !currentUser.isAdmin();
                }
                if (filterByUserId) {
                    qo.setUsername(currentUser.getUsername());
                    qo.setUserId(currentUser.getId());
                }
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
        HttpServletRequest request = RequestHolder.currentRequest();
        //异步保存日志
        systemLogService.saveLog(joinPoint,
                this.getUserClientInfo(request),
                this.getUserId(),
                timeCost,
                this.getUrlParams(request),
                this.getUrl(request)
        );
        return result;
    }

    /**
     * 异常增强
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        long timeCost = getTimeCost();
        HttpServletRequest request = RequestHolder.currentRequest();
        //异步保存日志
        systemLogService.saveErrorLog(joinPoint,
                this.getUserClientInfo(request),
                this.getUserId(),
                timeCost,
                throwable.getLocalizedMessage(),
                this.getUrlParams(request),
                this.getUrl(request)
        );
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

    /**
     * 获取客户请求客户端信息
     *
     * @param request 请求
     * @return 客户端信息
     */
    private PudgeUtil.UserClientInfo getUserClientInfo(HttpServletRequest request) {
        return PudgeUtil.getUserClientInfo(request);
    }

    /**
     * 获取请求参数 只可在异步保存日志前调用,request已失效
     *
     * @param request 请求
     * @return 请求参数, 即url上参数
     */
    private JSONObject getUrlParams(HttpServletRequest request) {
        // fix 异步可能导致request 已失效获取不到参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollectionUtil.isNotEmpty(parameterMap)) {
            JSONObject requestParams = new JSONObject();
            //修改为 key: value,即取string[]数组的第一个值
            parameterMap.forEach((k,v) -> requestParams.put(k,v != null && v.length > 0 ? v[0] : null));
            return requestParams;
        }
        return null;
    }

    /**
     * 获取请求地址
     *
     * @param request 请求
     * @return 请求地址
     */
    private String getUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
