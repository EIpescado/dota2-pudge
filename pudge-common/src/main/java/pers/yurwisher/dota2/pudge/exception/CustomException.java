package pers.yurwisher.dota2.pudge.exception;

import pers.yurwisher.dota2.pudge.enums.ICustomTipEnum;
import pers.yurwisher.dota2.pudge.wrapper.CustomTip;

/**
 * @author yq
 * @date 2019/05/21 11:34
 * @description 自定义异常
 * @since V1.0.0
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -4083494081772087464L;

    protected CustomTip tip;

    protected CustomTip tip() {
        return tip;
    }

    public CustomException(CustomTip customTip) {
        this.tip = customTip;
    }

    public CustomException(CustomTip customTip, Object...args) {
        this.tip = CustomTip.of(customTip.getCode(),String.format(customTip.getMsg(),args));
    }

    public CustomException(ICustomTipEnum customTipEnum){
        this(customTipEnum.tip());
    }

    public CustomException(ICustomTipEnum customTipEnum, Object...args){
        this(customTipEnum.tip(),args);
    }
}
