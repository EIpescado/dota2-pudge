package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemButton;
import pers.yurwisher.dota2.pudge.system.mapper.SystemButtonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.service.ISystemButtonService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemButtonFo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemButtonVo;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-26 11:35:17
 * @description 按钮
 * @since V1.0.0
 */
@Service
public class SystemButtonServiceImpl extends BaseServiceImpl<SystemButtonMapper,SystemButton> implements ISystemButtonService{

    /**
     * 新增
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemButtonFo fo){
        SystemButton systemButton = new SystemButton();
        BeanUtils.copyProperties(fo,systemButton);
        baseMapper.insert(systemButton);
    }

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id,SystemButtonFo fo){
        SystemButton systemButton = baseMapper.selectById(id);
        Assert.notNull(systemButton);
        BeanUtils.copyProperties(fo,systemButton);
        baseMapper.updateById(systemButton);
    }


    /**
    * 详情
    * @param id 主键
    * @return SystemButtonVo
    */
    @Override
    public SystemButtonVo get(Long id){
        return baseMapper.get(id);
    }

    @Override
    public List<ButtonNode> getUserButtonNodes(Long userId) {
        return baseMapper.getUserButtonNodes(userId);
    }

    @Override
    public List<ButtonNode> getAllButtonNodes() {
        return baseMapper.getAllButtonNodes();
    }

}
