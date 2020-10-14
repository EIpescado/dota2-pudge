package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemConfig;
import pers.yurwisher.dota2.pudge.system.mapper.SystemConfigMapper;
import pers.yurwisher.dota2.pudge.system.service.ISystemConfigService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemConfigFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemConfigQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemConfigTo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置
 * @since V1.0.0
 */
@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper,SystemConfig> implements ISystemConfigService{

    /**
     * 新增
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemConfigFo fo){
        SystemConfig systemConfig = new SystemConfig();
        BeanUtils.copyProperties(fo,systemConfig);
        baseMapper.insert(systemConfig);
    }

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id,SystemConfigFo fo){
        SystemConfig systemConfig = baseMapper.selectById(id);
        Assert.notNull(systemConfig);
        BeanUtils.copyProperties(fo,systemConfig);
        baseMapper.updateById(systemConfig);
    }

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemConfigTo> list(SystemConfigQo qo){
        return super.toPageR(baseMapper.list(super.toPage(qo),qo));
    }



     /**
     * 删除
     * @param id 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        baseMapper.deleteById(id);
    }
}
