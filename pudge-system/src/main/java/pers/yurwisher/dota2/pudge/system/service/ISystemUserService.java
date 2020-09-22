package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;

/**
 * @author yq
 * @date 2020/09/21 11:36
 * @description 用户service
 * @since V1.0.0
 */
public interface ISystemUserService extends BaseService<SystemUser> {


    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    CurrentUser findUserByUsername(String username);

    /**
     * 根据账号获取用户
     * @param username 账号
     * @return 用户
     */
    SystemUser getUserByUsername(String username);
}
