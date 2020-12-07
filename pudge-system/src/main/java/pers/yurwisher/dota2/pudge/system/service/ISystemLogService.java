package pers.yurwisher.dota2.pudge.system.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import javax.servlet.http.HttpServletRequest;


/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志
 * @since V1.0.0
 */
public interface ISystemLogService extends BaseService<SystemLog> {


    /**
     * 列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemLogTo> list(SystemLogQo qo);

    /**
     * 用户操作日志列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<UserSystemLogTo> userLogList(SystemLogQo qo);


    /**
     * 详情
     *
     * @param id 主键
     * @return SystemLogVo
     */
    SystemLogVo get(Long id);

    /**
     * 保存日志
     * @param joinPoint 切面
     * @param userClientInfo 请求客户端信息
     * @param userId 用户ID
     * @param timeCost 接口耗时
     * @throws Throwable 异常
     */
    void saveLog(ProceedingJoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost) throws Throwable ;

    /**
     * 保存异常日志
     * @param joinPoint 切面
     * @param userClientInfo 请求客户端信息
     * @param userId 用户ID
     * @param timeCost 接口耗时
     * @param errorInfo 异常信息
     */
    void saveErrorLog(JoinPoint joinPoint, PudgeUtil.UserClientInfo userClientInfo, Long userId, long timeCost, String errorInfo);

}
