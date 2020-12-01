package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;


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

}
