package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.enums.SystemConfigEnum;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemConfig;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemConfigMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemConfigFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemConfigQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemConfigTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemConfigService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置
 * @since V1.0.0
 */
@Service
@CacheConfig(cacheNames = CacheConstant.AnName.SYSTEM_CONFIG)
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigService {

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemConfigFo fo) {
        if (super.haveFieldValueEq(SystemConfig::getCode, fo.getCode())) {
            //配置编码已存在
            throw new SystemCustomException(SystemCustomTipEnum.CONFIG_CODE_EXISTED);
        }
        SystemConfig systemConfig = new SystemConfig();
        BeanUtils.copyProperties(fo, systemConfig);
        baseMapper.insert(systemConfig);
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#fo.code")
    public void update(Long id, SystemConfigFo fo) {
        SystemConfig systemConfig = baseMapper.selectById(id);
        Assert.notNull(systemConfig);
        BeanUtils.copyProperties(fo, systemConfig, "code");
        baseMapper.updateById(systemConfig);
    }

    /**
     * 列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemConfigTo> list(SystemConfigQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    @Cacheable(key = "#configEnum.name()", unless = "#result == null || #result.length() == 0")
    public String getValByCode(SystemConfigEnum configEnum) {
        SystemConfig config = super.getOneByFieldValueEq(SystemConfig::getCode, configEnum.name());
        if (config != null) {
            return config.getVal();
        }
        return null;
    }


}
