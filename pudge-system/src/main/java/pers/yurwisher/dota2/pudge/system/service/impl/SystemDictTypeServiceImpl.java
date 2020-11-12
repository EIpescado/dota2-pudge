package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemDictType;
import pers.yurwisher.dota2.pudge.system.mapper.SystemDictTypeMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictTypeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictTypeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTypeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictTypeVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictTypeService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class SystemDictTypeServiceImpl extends BaseServiceImpl<SystemDictTypeMapper, SystemDictType> implements ISystemDictTypeService {

    private final ISystemDictService systemDictService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemDictTypeFo fo) {
        SystemDictType systemDictType = new SystemDictType();
        BeanUtils.copyProperties(fo, systemDictType);
        baseMapper.insert(systemDictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemDictTypeFo fo) {
        SystemDictType systemDictType = baseMapper.selectById(id);
        Assert.notNull(systemDictType);
        BeanUtils.copyProperties(fo, systemDictType);
        baseMapper.updateById(systemDictType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemDictTypeTo> list(SystemDictTypeQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SystemDictType systemDictType = baseMapper.selectById(id);
        Assert.notNull(systemDictType);
        baseMapper.deleteById(id);
        //删除字典
        systemDictService.deleteByTypeCode(systemDictType.getCode());
    }

    @Override
    public SystemDictTypeVo get(Long id) {
        return baseMapper.get(id);
    }
}
