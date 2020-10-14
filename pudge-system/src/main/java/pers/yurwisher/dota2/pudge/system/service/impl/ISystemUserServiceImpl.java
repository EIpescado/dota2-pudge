package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheNameConstant;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemUserMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemUserFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemUserVo;
import pers.yurwisher.dota2.pudge.system.service.IRelationService;
import pers.yurwisher.dota2.pudge.system.service.ISystemRoleService;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

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
    private final IRelationService relationService;

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

    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemUserTo> list(SystemUserQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemUserFo fo) {
        //判断用户名是否存在
        if(super.haveFieldValueEq(SystemUser::getUsername,fo.getUsername())){
            //用户名已被使用
            throw new SystemCustomException(SystemCustomTipEnum.USERNAME_EXISTED);
        }
        //判断手机号是否存在
        if(super.haveFieldValueEq(SystemUser::getPhone,fo.getPhone())){
            //用户名已被使用
            throw new SystemCustomException(SystemCustomTipEnum.PHONE_EXISTED);
        }
        SystemUser user = new SystemUser();
        BeanUtils.copyProperties(fo,user);
        //todo 后续取配置
        user.setPassword(PudgeUtil.encodePwd("123456"));
        baseMapper.insert(user);
        //绑定角色
        relationService.userBindRoles(user.getId(),fo.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemUserFo fo) {
        SystemUser user = baseMapper.selectById(id);
        Assert.notNull(user);
        SystemUser userByPhone = super.getOneByFieldValueEq(SystemUser::getPhone,fo.getPhone());
        //判断手机号是否存在
        if(userByPhone != null && !userByPhone.getId().equals(id)){
            //用户名已被使用
            throw new SystemCustomException(SystemCustomTipEnum.PHONE_EXISTED);
        }
        BeanUtils.copyProperties(fo,user);
        baseMapper.updateById(user);
        //绑定角色
        relationService.userBindRoles(user.getId(),fo.getRoleIds());
    }

    @Override
    public SystemUserVo get(Long id) {
        SystemUserVo vo = baseMapper.get(id);
        vo.setRoleIds(relationService.getUserAlreadyBindRoleIds(id));
        return vo;
    }
}
