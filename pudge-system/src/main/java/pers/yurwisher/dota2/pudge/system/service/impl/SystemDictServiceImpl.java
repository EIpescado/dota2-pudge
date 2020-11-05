package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemDict;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemDictMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典
 * @since V1.0.0
 */
@Service
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDictMapper,SystemDict> implements ISystemDictService{

    /**
     * 新增
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemDictFo fo){
        SystemDict systemDict = new SystemDict();
        BeanUtils.copyProperties(fo,systemDict);
        baseMapper.insert(systemDict);
    }

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id,SystemDictFo fo){
        SystemDict systemDict = baseMapper.selectById(id);
        Assert.notNull(systemDict);
        if(systemDict.getFixed()){
            //固定字典不可修改
            throw new SystemCustomException(SystemCustomTipEnum.DICT_FIXED_NOT_CHANGE);
        }
        BeanUtils.copyProperties(fo,systemDict);
        baseMapper.updateById(systemDict);
    }

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemDictTo> list(SystemDictQo qo){
        return super.toPageR(baseMapper.list(super.toPage(qo),qo));
    }


    /**
    * 详情
    * @param id 主键
    * @return SystemDictVo
    */
    @Override
    public SystemDictVo get(Long id){
        return baseMapper.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

}
