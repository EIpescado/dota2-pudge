package pers.yurwisher.dota2.pudge.security.service.impl;

import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.security.cache.OnlineUser;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;
import pers.yurwisher.dota2.pudge.system.service.CustomRedisCacheService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020/09/18 17:20
 * @description 在线用户service
 * @since V1.0.0
 */
@Service
public class OnlineUserServiceImpl implements IOnlineUserService {

    private final CustomRedisCacheService customRedisCacheService;

    public OnlineUserServiceImpl(CustomRedisCacheService customRedisCacheService) {
        this.customRedisCacheService = customRedisCacheService;
    }

    @Override
    public OnlineUser getOne(String username) {
        return customRedisCacheService.getCachePlus(CacheConstant.MaName.PC_ONLINE_USER,username);
    }

    @Override
    public OnlineUser getOneByType(String username, String type) {
        //todo
        return null;
    }

    @Override
    public void save(HttpServletRequest request, String token, JwtUser user) {
        //存入redis
        customRedisCacheService.setCachePlus(CacheConstant.MaName.PC_ONLINE_USER, user.getUsername(), () -> {
            OnlineUser onlineUser = new OnlineUser();
            CurrentUser currentUser = user.getUser();
            onlineUser.setUsername(currentUser.getUsername());
            onlineUser.setNickname(currentUser.getNickname());
            //加密token
            onlineUser.setToken(PudgeUtil.encrypt(token));
            onlineUser.setLoginDate(LocalDateTime.now());

            PudgeUtil.UserClientInfo clientInfo = PudgeUtil.getUserClientInfo(request);
            onlineUser.setIp(clientInfo.getIp());
            onlineUser.setAddress(clientInfo.getAddress());
            onlineUser.setBrowser(clientInfo.getBrowser());
            onlineUser.setType(clientInfo.getSystem());
            return onlineUser;
        });
    }
}
