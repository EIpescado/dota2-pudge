package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.entity.SystemRole;
import pers.yurwisher.dota2.pudge.system.mapper.SystemRoleMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemRoleFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemRoleQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemRoleTo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.service.CustomRedisCacheService;
import pers.yurwisher.dota2.pudge.system.service.IRelationService;
import pers.yurwisher.dota2.pudge.system.service.ISystemButtonService;
import pers.yurwisher.dota2.pudge.system.service.ISystemMenuService;
import pers.yurwisher.dota2.pudge.system.service.ISystemRoleService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements ISystemRoleService {

    private final ISystemMenuService menuService;
    private final ISystemButtonService buttonService;
    private final IRelationService relationService;
    private final CustomRedisCacheService customRedisCacheService;

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemRoleFo fo) {
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(fo, systemRole);
        baseMapper.insert(systemRole);
        this.deleteCache();
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemRoleFo fo) {
        SystemRole systemRole = baseMapper.selectById(id);
        Assert.notNull(systemRole);
        BeanUtils.copyProperties(fo, systemRole,"name");
        baseMapper.updateById(systemRole);
        this.deleteCache();
    }

    /**
     * 列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemRoleTo> list(SystemRoleQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
        this.deleteCache();
        //删除所有含有此角色的用户缓存信息 与 菜单缓存
        List<String> usernameList = relationService.getAllHaveThisRoleIdUsername(id);
        customRedisCacheService.batchDelete(CacheConstant.AnName.SYSTEM_USER_INFO,usernameList);
        customRedisCacheService.batchDelete(CacheConstant.MaName.SYSTEM_USER_TREE,usernameList);
    }

    @Override
    public List<String> getUserPermission(Long userId) {
        //获取用户所有菜单权限
        List<String> menuPermissions = menuService.getUserMenuPermission(userId);
        //获取用户所有按钮权限
        List<String> buttonPermissions = buttonService.getUserButtonPermission(userId);
        return CollectionUtil.unionAll(menuPermissions,buttonPermissions);
    }

    @Override
    public List<String> getUserRole(Long userId) {
        return baseMapper.getUserRole(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindMenuAndButton(Long roleId, List<MenuAndButtonTreeNode> nodes) {
        //删除角色所有已绑定节点
        if (CollectionUtil.isNotEmpty(nodes)) {
            Map<Boolean, List<MenuAndButtonTreeNode>> map = nodes.stream().collect(Collectors.groupingBy(MenuAndButtonTreeNode::getWhetherButton));
            //绑定菜单
            List<MenuAndButtonTreeNode> menus = map.get(Boolean.FALSE);
            if (CollectionUtil.isNotEmpty(menus)) {
                relationService.roleBindMenus(roleId, menus.stream().map(MenuAndButtonTreeNode::getId).collect(Collectors.toList()));
            }
            //绑定按钮
            List<MenuAndButtonTreeNode> buttons = map.get(Boolean.TRUE);
            if (CollectionUtil.isNotEmpty(buttons)) {
                relationService.roleBindButtons(roleId, buttons.stream().map(MenuAndButtonTreeNode::getId).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public List<String> singleRoleMenuAndButton(Long roleId) {
        List<Long> menuIds = relationService.singleRoleMenu(roleId);
        List<Long> buttonIds = relationService.singleRoleButton(roleId);
        if (CollectionUtil.isNotEmpty(menuIds)) {
            if (CollectionUtil.isNotEmpty(buttonIds)) {
                menuIds.addAll(buttonIds);
            }
            //fastJson 即使配置全局对Long的序列化 ToStringSerializer , 序列化 List<Long> 使用的 ListSerializer 未对Long做处理 依旧返回Long
            return menuIds.stream().map(Object::toString).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Selector<Long>> select() {
       return customRedisCacheService.cacheRound(CacheConstant.Key.ROLE_SELECT,() -> baseMapper.select());
    }

    private void deleteCache(){
        //删除缓存
        customRedisCacheService.deleteCache(CacheConstant.Key.ROLE_SELECT);
    }
}
