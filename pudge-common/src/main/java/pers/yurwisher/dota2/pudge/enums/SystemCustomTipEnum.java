package pers.yurwisher.dota2.pudge.enums;

import pers.yurwisher.dota2.pudge.wrapper.CustomTip;

/**
 * @author yq
 * @date 2020/09/18 09:54
 * @description 系统服务 自定义提示枚举
 * @since V1.0.0
 */
public enum SystemCustomTipEnum implements ICustomTipEnum {
    USERNAME_OR_PASSWORD_ERROR(10001,"账号或密码错误"),
    USERNAME_NOT_ENABLED(10002,"账号未激活"),
    NOR_RIGHT(10003,"have no right to use"),
    LOGIN_EXPIRED(10004,"登录已过期"),
    ;

    private CustomTip tip;

    SystemCustomTipEnum(int code,String msg) {
        this.tip = CustomTip.of(code, msg);
    }

    @Override
    public CustomTip tip() {
        return tip;
    }
}
