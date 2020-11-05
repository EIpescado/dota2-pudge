package pers.yurwisher.dota2.pudge.enums;

import pers.yurwisher.dota2.pudge.wrapper.CustomTip;

/**
 * @author yq
 * @date 2020/09/18 09:54
 * @description 系统服务 自定义提示枚举
 * @since V1.0.0
 */
public enum SystemCustomTipEnum implements ICustomTipEnum {
    AUTH_USERNAME_OR_PASSWORD_ERROR(10001,"账号或密码错误"),
    AUTH_USERNAME_NOT_ENABLED(10002,"账号未激活"),
    AUTH_NOR_RIGHT(10003,"无权操作"),
    AUTH_LOGIN_EXPIRED(10004,"登录已过期"),
    AUTH_CODE_NOT_EXIST_OR_EXPIRED(10005,"验证码已过期"),
    AUTH_CODE_ERROR(10006,"验证码错误"),
    USERNAME_EXISTED(10007,"用户名已被使用"),
    PHONE_EXISTED(10008,"手机已注册"),

    MENU_I_FRAME_PATH_PREFIX_ERROR(10009,"iFrame菜单路由地址必须以http/https开头"),
    MENU_PID_NOT_ID(10010,"上级不能为自己"),
    MENU_COMPONENT_NOT_BE_NULL(10011,"菜单component不可为空"),

    CONFIG_CODE_EXISTED(10012,"配置编码已存在"),

    DICT_FIXED_NOT_CHANGE(10013,"固定字典不可修改"),
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
