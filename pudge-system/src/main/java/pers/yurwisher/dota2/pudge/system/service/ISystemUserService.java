package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import java.util.List;

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

    /**
     * 用户列表
     * @param qo 查询对象
     * @return 分页结果
     */
    PageR<SystemUserTo> list(SystemUserQo qo);
}
