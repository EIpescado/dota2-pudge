package pers.yurwisher.dota2.pudge.enums;


import pers.yurwisher.dota2.pudge.wrapper.CustomTip;

/**
 * @author yq
 * @date 2019/05/21 11:28
 * @description 提示枚举
 * @since V1.0.0
 */
public enum CustomTipEnum implements ICustomTipEnum {
    SUCCESS(0,"success"),
    FAIL(1,"fail"),

    NOT_FOUND(404,"404,not found"),
    METHOD_NOT_ALLOWED(405,"405,method not allowed"),
    UNSUPPORTED_MEDIA_TYPE(415,"415,Unsupported Media Type"),
    ;

    private CustomTip tip;

    CustomTipEnum(int code,String msg) {
        this.tip = CustomTip.of(code, msg);
    }

    @Override
    public CustomTip tip() {
        return tip;
    }

}
