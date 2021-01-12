package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.system.mapper.RelationMapper;
import pers.yurwisher.dota2.pudge.system.service.IRelationService;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/22 10:03
 * @description 关系service
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements IRelationService {

    private final RelationMapper relationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userBindRoles(Long userId, List<Long> roleIds) {
        if (userId != null) {
            //先删除所有已有角色
            relationMapper.deleteUserRoleRelationByUserId(userId);
            if (CollectionUtil.isNotEmpty(roleIds)) {
                //绑定新角色
                relationMapper.batchInsertUserRoleRelation(userId, roleIds);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleBindMenus(Long roleId, List<Long> menuIds) {
        if (roleId != null) {
            //先删除所有已绑定菜单
            relationMapper.deleteRoleMenuRelationByRoleId(roleId);
            if (CollectionUtil.isNotEmpty(menuIds)) {
                //绑定新菜单
                relationMapper.batchInsertRoleMenuRelation(roleId, menuIds);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleBindButtons(Long roleId, List<Long> buttonIds) {
        if (roleId != null) {
            //先删除所有已绑定按钮
            relationMapper.deleteRoleButtonRelationByRoleId(roleId);
            if (CollectionUtil.isNotEmpty(buttonIds)) {
                //绑定新按钮
                relationMapper.batchInsertRoleButtonRelation(roleId, buttonIds);
            }
        }
    }

    @Override
    public List<Long> singleRoleMenu(Long roleId) {
        return relationMapper.singleRoleMenu(roleId);
    }

    @Override
    public List<Long> singleRoleButton(Long roleId) {
        return relationMapper.singleRoleButton(roleId);
    }

    @Override
    public List<Long> getUserAlreadyBindRoleIds(Long userId) {
        return relationMapper.getUserAlreadyBindRoleIds(userId);
    }

    @Override
    public List<String> getAllHaveThisRoleIdUsername(Long roleId) {
        return relationMapper.getAllHaveThisRoleIdUsername(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void entityBindFiles(Long entityId, List<Long> fileIds) {
        if (entityId != null) {
            //先删除所有已绑定附件
            relationMapper.deleteEntityFileRelationByEntityId(entityId);
            if (CollectionUtil.isNotEmpty(fileIds)) {
                //重新绑定
                relationMapper.batchInsertEntityFileRelation(entityId, fileIds);
            }
        }
    }
}
