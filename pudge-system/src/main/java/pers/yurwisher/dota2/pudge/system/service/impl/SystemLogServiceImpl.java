package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        StringBuilder params = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        log.setParams(params.toString());
        //正常
        log.setType(1);
        logger.info("保持日志: [{}]", log.getAction());
        baseMapper.insert(log);
    }
}
