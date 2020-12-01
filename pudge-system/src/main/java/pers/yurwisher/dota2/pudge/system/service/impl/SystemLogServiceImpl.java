package pers.yurwisher.dota2.pudge.system.service.impl;

import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.system.mapper.SystemLogMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

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
}
