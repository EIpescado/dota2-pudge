package pers.yurwisher.dota2.pudge.security.cache;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020/09/18 17:09
 * @description 当前在线用户
 * @since V1.0.0
 */
@Data
public class OnlineUser {

    /**用户名*/
    private String username;
    /**名称*/
    private String nickname;
    /**IP*/
    private String ip;
    /**ip对应地址*/
    private String address;
    /**浏览器*/
    private String browser;
    /**登录时间*/
    private LocalDateTime loginDate;
    /**对应token*/
    private String token;

    /**在线类型:  pc, h5, 小程序, IOS, Android*/
    private String type;
}
