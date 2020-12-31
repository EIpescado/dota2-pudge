package pers.yurwisher.dota2.pudge.system.service.impl;

import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.mapper.SystemFileMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemFileService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件
 * @since V1.0.0
 */
@Service
public class SystemFileServiceImpl extends BaseServiceImpl<SystemFileMapper, SystemFile> implements ISystemFileService {

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemFileTo> list(SystemFileQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

}
