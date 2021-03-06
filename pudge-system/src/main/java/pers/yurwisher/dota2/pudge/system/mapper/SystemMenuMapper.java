package pers.yurwisher.dota2.pudge.system.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Mapper
 * @since V1.0.0
 */
public interface SystemMenuMapper extends CommonMapper<SystemMenu> {

    /**
     * 获取用户菜单权限
     * @param userId 用户ID
     * @return 菜单权限
     */
    List<String> getUserMenuPermission(@Param("userId")Long userId);

    /**
     * 获取用户所有菜单TreeNode
     * @param userId 用户ID
     * @return nodes
     */
    List<MenuTreeNode> getUserMenuTreeNodes(@Param("userId")Long userId);

    /**
     * 所有菜单
     * @return 菜单集合
     */
    List<MenuTreeNode> getAllMenuTreeNodes();

    /**
     * 获取所有菜单和按钮
     * @return 菜单和按钮集合
     */
    List<MenuAndButtonTreeNode> getAllNodes();
}
