package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemMenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;

import java.util.List;


/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
public interface ISystemMenuService extends BaseService<SystemMenu> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemMenuFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id, SystemMenuFo fo);

    /**
     * 获取用户所有菜单
     * @param userId 用户ID
     * @return 菜单集合
     */
    List<SystemMenu> findAllByUserId(Long userId);

    /**
     * 用户的菜单树
     * @return 菜单tree
     */
    List<MenuTreeNode> tree();

    /**
     * 完整菜单tree
     * @return 完整菜单tree
     */
    List<MenuAndButtonTreeNode> wholeTree();

    /**
     * 删除菜单和按钮
     * @param nodes 菜单和按钮
     */
    void delete(List<MenuAndButtonTreeNode> nodes);
}
