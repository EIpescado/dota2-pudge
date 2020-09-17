package pers.yurwisher.pudge.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author yq
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemUser extends BaseEntity {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后密码重置时间
     */
    private LocalDateTime lastPasswordResetDate;

    /**
     * 头像
     */
    private String avatar;

}
