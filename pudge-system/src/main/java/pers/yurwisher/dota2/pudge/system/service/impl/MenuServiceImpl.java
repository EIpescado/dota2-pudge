package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.Menu;
import pers.yurwisher.dota2.pudge.system.mapper.MenuMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.MenuFo;
import pers.yurwisher.dota2.pudge.system.service.IMenuService;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper,Menu> implements IMenuService{

    /**
     * 新增
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MenuFo fo){
        Menu menu = new Menu();
        BeanUtils.copyProperties(fo,menu);
        baseMapper.insert(menu);
    }

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id,MenuFo fo){
        Menu menu = baseMapper.selectById(id);
        Assert.notNull(menu);
        BeanUtils.copyProperties(fo,menu);
        baseMapper.updateById(menu);
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

    @Override
    public List<Menu> findAllByUserId(Long userId) {
        return baseMapper.getUserMenus(userId);
    }
}
