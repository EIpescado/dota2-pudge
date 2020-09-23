package pers.yurwisher.dota2.pudge.security.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.security.cache.OnlineUser;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020/09/18 17:20
 * @description  在线用户service
 * @since V1.0.0
 */
@Service
public class OnlineUserServiceImpl implements IOnlineUserService {

    private final RedisTemplate<String,Object> redisTemplate;
    private final SecurityProperties properties;

    public OnlineUserServiceImpl(RedisTemplate<String, Object> redisTemplate, SecurityProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public OnlineUser getOne(String key) {
        return (OnlineUser) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void save(HttpServletRequest request, String token, JwtUser user) {
        OnlineUser onlineUser = new OnlineUser();
        CurrentUser currentUser = user.getUser();
        onlineUser.setUsername(currentUser.getUsername());
        onlineUser.setNickName(currentUser.getNickName());
        //加密token
        onlineUser.setToken(PudgeUtil.encrypt(token));
        onlineUser.setLoginDate(LocalDateTime.now());

        PudgeUtil.UserClientInfo clientInfo = PudgeUtil.getUserClientInfo(request);
        onlineUser.setIp(clientInfo.getIp());
        onlineUser.setAddress(clientInfo.getAddress());
        onlineUser.setBrowser(clientInfo.getBrowser());
        onlineUser.setType(clientInfo.getSystem());
        //存入redis
        redisTemplate.opsForValue().set(properties.getOnlineKey() + token, onlineUser);
    }
}
