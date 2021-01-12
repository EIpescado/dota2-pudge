package pers.yurwisher.dota2.pudge.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/22 10:04
 * @description 关系
 * @since V1.0.0
 */
public interface RelationMapper {

    /**
     * 批量插入用户角色关系
     * @param userId 用户ID
     * @param roleIds 角色ID集合
     * @return 影响行数
     */
    Integer batchInsertUserRoleRelation(@Param("userId") Long userId,@Param("roleIds") List<Long> roleIds);

    /**
     * 删除指定用户所有绑定的角色
     * @param userId 用户ID
     * @return 影响行数
     */
    Integer deleteUserRoleRelationByUserId(@Param("userId")Long userId);

    /**
     * 删除指定角色已绑定菜单
     * @param roleId 角色ID
     * @return 影响行数
     */
    Integer deleteRoleMenuRelationByRoleId(@Param("roleId")Long roleId);

    /**
     * 批量插入角色菜单关系
     * @param roleId 角色ID
     * @param menuIds 菜单ID集合
     * @return 影响行数
     */
    Integer batchInsertRoleMenuRelation(@Param("roleId")Long roleId, @Param("menuIds")List<Long> menuIds);

    /**
     * 删除角色已绑定的按钮
     * @param roleId 角色ID
     * @return 影响行数
     */
    Integer deleteRoleButtonRelationByRoleId(@Param("roleId")Long roleId);

    /**
     * 批量插入角色按钮关系
     * @param roleId 角色ID
     * @param buttonIds 按钮ID集合
     * @return 影响行数
     */
    Integer batchInsertRoleButtonRelation(@Param("roleId")Long roleId,@Param("buttonIds") List<Long> buttonIds);

    /**
     * 单个角色菜单
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> singleRoleMenu(@Param("roleId")Long roleId);

    /**
     * 单个角色按钮
     * @param roleId 角色ID
     * @return 按钮集合
     */
    List<Long> singleRoleButton(@Param("roleId")Long roleId);

    /**
     * 获取用户已经绑定的所有角色ID
     * @param userId 用户ID
     * @return 角色ID集合
     */
    List<Long> getUserAlreadyBindRoleIds(@Param("userId")Long userId);

    /**
     * 获取已绑定指定角色的所有username
     * @param roleId 角色ID
     * @return username集合
     */
    List<String> getAllHaveThisRoleIdUsername(Long roleId);

    /**
     * 删除实体已绑定文件
     * @param entityId 实体ID
     * @return 影响行数
     */
    Integer deleteEntityFileRelationByEntityId(@Param("entityId")Long entityId);

    /**
     * 批量插入实体文件关系
     * @param entityId 实体ID
     * @param fileIds 按钮ID集合
     * @return 影响行数
     */
    Integer batchInsertEntityFileRelation(@Param("entityId")Long entityId,@Param("fileIds") List<Long> fileIds);
}
