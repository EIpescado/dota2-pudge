package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import pers.yurwisher.dota2.pudge.annotation.Log;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.system.mapper.SystemLogMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志
 * @since V1.0.0
 */
@Service
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLogMapper, SystemLog> implements ISystemLogService {

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemLogTo> list(SystemLogQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageR<UserSystemLogTo> userLogList(SystemLogQo qo) {
        return super.toPageR(baseMapper.userLogList(super.toPage(qo), qo));
    }

    @Override
    public SystemLogVo get(Long id) {
        return baseMapper.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void saveLog(ProceedingJoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, Map<String, String[]> parameterMap,String url) throws Throwable {
        this.saveLog(joinPoint, userClientInfo, userId, timeCost, 1, null, parameterMap,url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void saveErrorLog(JoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, String errorInfo, Map<String, String[]> parameterMap,String url) {
        this.saveLog(joinPoint, userClientInfo, userId, timeCost, 2, errorInfo, parameterMap,url);
    }

    public void saveLog(JoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, Integer type, String errorInfo, Map<String, String[]> parameterMap,String url) {
        SystemLog log = new SystemLog();
        log.setTimeCost(timeCost);
        log.setUserId(userId);
        //IP及客户端信息
        log.setIp(userClientInfo.getIp());
        log.setAddress(userClientInfo.getAddress());
        log.setSystem(userClientInfo.getSystem());
        log.setBrowser(userClientInfo.getBrowser());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);
        //操作名称
        log.setAction(aopLog.value());
        // 方法
        String methodName = StrBuilder.create(joinPoint.getTarget().getClass().getSimpleName(), StrUtil.DOT, signature.getName()).toString();
        log.setMethod(methodName);

        //参数
        log.setParams(this.buildRequestParams(parameterMap,method,joinPoint.getArgs()));
        //日志类型
        log.setType(type);
        log.setErrorInfo(errorInfo);
        log.setUrl(url);
        logger.info("用户:[{}],[{}]", userId, log.getAction());
        baseMapper.insert(log);
    }

    /**
     * 将requestParam 和 requestBody 组合起来保存
     * @param parameterMap requestParam 参数
     * @return 参数
     */
    private String buildRequestParams(Map<String, String[]> parameterMap, Method method, Object[] args){
        Object requestBody = this.getRequestBody(method,args);
        if(CollectionUtil.isEmpty(parameterMap) && requestBody == null){
            return null;
        }
        JSONObject params = new JSONObject();
        if (requestBody != null) {
            params.put("requestBody",requestBody);
        }
        if (CollectionUtil.isNotEmpty(parameterMap)) {
            JSONObject requestParams = new JSONObject();
            //修改为 key: value,即取string[]数组的第一个值
            parameterMap.forEach((k,v) -> requestParams.put(k,v != null && v.length > 0 ? v[0] : null));
            params.put("requestParams",requestParams);
        }
        return params.toJSONString();
    }

    /**
     * 获取请求体
     *
     * @param method 方法
     * @param args   参数
     * @return requestBody
     */
    private Object getRequestBody(Method method, Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        Parameter[] parameters = method.getParameters();
        RequestBody requestBody;
        for (int i = 0, length = parameters.length; i < length; i++) {
            requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                return args[i];
            }
        }
        return null;
    }
}
