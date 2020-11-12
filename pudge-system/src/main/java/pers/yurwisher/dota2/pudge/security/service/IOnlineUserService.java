package pers.yurwisher.dota2.pudge.security.service;

import pers.yurwisher.dota2.pudge.enums.UserClientType;
import pers.yurwisher.dota2.pudge.security.JwtUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yq
 * @date 2020/09/18 17:20
 * @description 在线用户service
 * @since V1.0.0
 */
public interface IOnlineUserService {

    /**
     * 用户在线状态过期时间
     *
     * @param subject 用户::客户端类型 key
     * @return time 单位s
     */
    Long getOnlineExpireTime(String subject);

    /**
     * 保存在线信息到redis
     *
     * @param request 请求
     * @param token   凭证
     * @param user    用户
     * @param type, 用户客户端类型
     */
    void save(HttpServletRequest request, String token, JwtUser user, UserClientType type);

}
