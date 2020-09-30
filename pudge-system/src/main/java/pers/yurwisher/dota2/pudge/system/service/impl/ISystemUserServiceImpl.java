package pers.yurwisher.dota2.pudge.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheNameConstant;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemUserMapper;
import pers.yurwisher.dota2.pudge.system.service.ISystemRoleService;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/21 11:36
 * @description 系统用户service
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheNameConstant.SYSTEM_USER)
public class ISystemUserServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    private final ISystemRoleService systemRoleService;

    @Override
    @Cacheable(key = "'username:' + #username")
    public CurrentUser findUserByUsername(String username) {
        SystemUser user = getUserByUsername(username);
        if (user == null){
            //账号或密码错误
            throw new SystemCustomException(SystemCustomTipEnum.AUTH_USERNAME_OR_PASSWORD_ERROR);
        }
        if(!user.getEnabled()){
            //账号未激活
            throw new SystemCustomException(SystemCustomTipEnum.AUTH_USERNAME_NOT_ENABLED);
        }
        CurrentUser vo = new CurrentUser();
        BeanUtils.copyProperties(user,vo);
        //获取用户角色
        List<String> roles = systemRoleService.getUserRole(user.getId());
        vo.setRoles(roles);
        //获取用户权限
        List<String> permissions = systemRoleService.getUserPermission(user.getId());
        vo.setPermissions(permissions);
        return vo;
    }


    @Override
    public SystemUser getUserByUsername(String username) {
        return baseMapper.selectOne(super.buildLambdaQueryWrapper().eq(SystemUser::getUsername, username));
    }
}
