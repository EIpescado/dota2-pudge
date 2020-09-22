package pers.yurwisher.dota2.pudge.system.service;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/22 09:59
 * @description 关系绑定
 * @since V1.0.0
 */
public interface IRelationService {

    /**
     * 用户绑定角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     */
    void userBindRoles(Long userId, List<Long> roleIds);

    /**
     * 角色绑定菜单
     * @param roleId 角色ID
     * @param menuIds 菜单ID集合
     */
    void roleBindMenus(Long roleId, List<Long> menuIds);
}
