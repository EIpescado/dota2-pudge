package pers.yurwisher.dota2.pudge.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 用户
 * @author yq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemUser extends BaseEntity {

    private static final long serialVersionUID = 8411371755246793866L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;


    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 最后密码重置时间
     */
    private LocalDateTime lastPasswordResetDate;



}
