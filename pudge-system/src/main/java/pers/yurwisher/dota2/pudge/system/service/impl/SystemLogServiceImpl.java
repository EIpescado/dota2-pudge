package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void saveLog(ProceedingJoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost) throws Throwable {
        this.saveLog(joinPoint, userClientInfo, userId, timeCost, 1, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void saveErrorLog(JoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, String errorInfo) {
        this.saveLog(joinPoint, userClientInfo, userId, timeCost, 2, errorInfo);
    }

    public void saveLog(JoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, Integer type, String errorInfo) {
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

        //参数值
        log.setParams(JSON.toJSONString(joinPoint.getArgs()));
        //日志类型
        log.setType(type);
        log.setErrorInfo(errorInfo);
        logger.info("用户:[{}],[{}]", userId, log.getAction());
        baseMapper.insert(log);
    }

}
