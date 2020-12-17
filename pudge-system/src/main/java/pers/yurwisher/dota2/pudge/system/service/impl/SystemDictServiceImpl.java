package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
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
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDictMapper, SystemDict> implements ISystemDictService {

    private final CustomRedisCacheService customRedisCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemDictFo fo) {
        SystemDict systemDict = new SystemDict();
        BeanUtils.copyProperties(fo, systemDict);
        baseMapper.insert(systemDict);
        this.deleteCache(fo.getTypeCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemDictFo fo) {
        SystemDict systemDict = baseMapper.selectById(id);
        Assert.notNull(systemDict);
        if (systemDict.getFixed()) {
            //固定字典不可修改
            throw new SystemCustomException(SystemCustomTipEnum.DICT_FIXED_NOT_CHANGE);
        }
        BeanUtils.copyProperties(fo, systemDict);
        baseMapper.updateById(systemDict);
        this.deleteCache(fo.getTypeCode());
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
        this.deleteCache(dict.getTypeCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTypeCode(String typeCode) {
        baseMapper.delete(Wrappers.<SystemDict>lambdaQuery().eq(SystemDict::getTypeCode, typeCode).eq(SystemDict::getEnabled, true));
        this.deleteCache(typeCode);
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.AnName.SYSTEM_DICT, key = "#dictType", unless = "#result == null || #result.size() == 0")
    public List<Selector<String>> select(String dictType) {
        return baseMapper.select(dictType);
    }

    @Override
    public String getNameByTypeAndVal(String dictType, String val) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(CacheConstant.MaName.SYSTEM_DICT_MAP, dictType);
        return customRedisCacheService.hashCacheRound(redisKey, val, () -> baseMapper.getNameByTypeAndVal(dictType, val));
    }

    /**
     * 删除指定字典类型的缓存
     *
     * @param typeCode 字典类型编码
     */
    private void deleteCache(String typeCode) {
        customRedisCacheService.deleteCachePlus(CacheConstant.AnName.SYSTEM_DICT, typeCode);
        customRedisCacheService.deleteCachePlus(CacheConstant.MaName.SYSTEM_DICT_MAP, typeCode);
    }

}
