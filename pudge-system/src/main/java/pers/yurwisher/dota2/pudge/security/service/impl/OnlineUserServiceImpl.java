package pers.yurwisher.dota2.pudge.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.security.cache.OnlineUser;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yq
 * @date 2020/09/18 17:20
 * @description  在线用户service
 * @since V1.0.0
 */
@Service
public class OnlineUserServiceImpl implements IOnlineUserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public OnlineUser getOne(String key) {
        return null;
    }

    @Override
    public void save(HttpServletRequest request, String token, JwtUser user) {

    }
}
