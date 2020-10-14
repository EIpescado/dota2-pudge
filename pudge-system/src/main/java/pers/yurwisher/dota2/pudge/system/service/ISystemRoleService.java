package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemRole;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemRoleFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemRoleQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemRoleTo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;


/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色
 * @since V1.0.0
 */
public interface ISystemRoleService extends BaseService<SystemRole> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemRoleFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemRoleFo fo);

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemRoleTo> list(SystemRoleQo qo);

     /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 获取用户权限
     * @param userId 用户ID
     * @return 权限
     */
    List<String> getUserPermission(Long userId);

    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 角色代码集合
     */
    List<String> getUserRole(Long userId);

    /**
     * 角色绑定菜单和按钮
     * @param roleId 角色ID
     * @param nodes 节点集合
     */
    void bindMenuAndButton(Long roleId,List<MenuAndButtonTreeNode> nodes);

    /**
     * 单个角色已经绑定的菜单和按钮
     * @param roleId 角色ID
     * @return 菜单和按钮ID集合
     */
    List<String> singleRoleMenuAndButton(Long roleId);

    /**
     * 角色下拉框
     * @return 角色集合
     */
    List<Selector<Long>> select();
}
