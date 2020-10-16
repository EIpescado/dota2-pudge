package pers.yurwisher.dota2.pudge.security.service;

import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.security.cache.OnlineUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yq
 * @date 2020/09/18 17:20
 * @description 在线用户service
 * @since V1.0.0
 */
public interface IOnlineUserService {
    /**
     * 根据key获取当前在线用户
     * @param username 用户名
     * @return 在线用户信息
     */
    OnlineUser getOne(String username);

    /**
     * 根据key获取当前在线用户
     * @param username 用户名
     * @param type 客户端类型
     * @return 在线用户信息
     */
    OnlineUser getOneByType(String username,String type);

    /**
     * 保存在线信息到redis
     * @param request 请求
     * @param token 凭证
     * @param user 用户
     */
    void save(HttpServletRequest request, String token, JwtUser user);

}
