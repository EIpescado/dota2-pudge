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

    /**
     * 角色绑定按钮
     * @param roleId 角色ID
     * @param buttonIds 按钮ID集合
     */
    void roleBindButtons(Long roleId, List<Long> buttonIds);

    /**
     * 单个角色所有菜单
     * @param roleId 角色ID
     * @return 所有菜单
     */
    List<Long> singleRoleMenu(Long roleId);

    /**
     * 单个角色所有按钮
     * @param roleId 角色ID
     * @return 所有按钮
     */
    List<Long> singleRoleButton(Long roleId);

    /**
     * 获取用户已经绑定的角色
     * @param userId 用户ID
     * @return 角色ID集合
     */
    List<Long> getUserAlreadyBindRoleIds(Long userId);

    /**
     * 获取所有拥有指定角色ID的用户名称
     * @param roleId  角色ID
     * @return username 集合
     */
    List<String> getAllHaveThisRoleIdUsername(Long roleId);

    /**
     * 实体绑定文件
     * @param entityId 实体ID
     * @param fileIds 文件ID集合
     */
    void entityBindFiles(Long entityId, List<Long> fileIds);

    /**
     * 批量获取实体关联的附件数量
     * @param entityIds 实体ID集合
     * @return 附件数量集合
     */
    List<Integer> batchGetEntityFileCount(List<Long> entityIds);
}
