package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemDict;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemDictMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictVo;
import pers.yurwisher.dota2.pudge.system.service.CustomRedisCacheService;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheConstant.AnName.SYSTEM_DICT)
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDictMapper, SystemDict> implements ISystemDictService {

    private final CustomRedisCacheService customRedisCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#fo.getTypeCode()")
    public void create(SystemDictFo fo) {
        SystemDict systemDict = new SystemDict();
        BeanUtils.copyProperties(fo, systemDict);
        baseMapper.insert(systemDict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#fo.getTypeCode()")
    public void update(Long id, SystemDictFo fo) {
        SystemDict systemDict = baseMapper.selectById(id);
        Assert.notNull(systemDict);
        if (systemDict.getFixed()) {
            //固定字典不可修改
            throw new SystemCustomException(SystemCustomTipEnum.DICT_FIXED_NOT_CHANGE);
        }
        BeanUtils.copyProperties(fo, systemDict);
        baseMapper.updateById(systemDict);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemDictTo> list(SystemDictQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    public SystemDictVo get(Long id) {
        return baseMapper.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SystemDict dict = baseMapper.selectById(id);
        Assert.notNull(dict);
        baseMapper.deleteById(id);
        customRedisCacheService.deleteCachePlus(CacheConstant.AnName.SYSTEM_DICT,dict.getTypeCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTypeCode(String typeCode) {
        baseMapper.delete(Wrappers.<SystemDict>lambdaQuery().eq(SystemDict::getTypeCode,typeCode).eq(SystemDict::getEnabled,true));
        customRedisCacheService.deleteCachePlus(CacheConstant.AnName.SYSTEM_DICT,typeCode);
    }

    @Override
    @Cacheable(key = "#dictType", unless = "#result == null || #result.size() == 0")
    public List<Selector<String>> select(String dictType) {
        return baseMapper.select(dictType);
    }

}