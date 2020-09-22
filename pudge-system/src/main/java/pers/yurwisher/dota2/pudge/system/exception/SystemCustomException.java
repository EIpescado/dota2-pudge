package pers.yurwisher.dota2.pudge.system.exception;

import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.exception.CustomException;

/**
 * @author yq
 * @date 2020/09/21 12:04
 * @description 系统自定义异常
 * @since V1.0.0
 */
public class SystemCustomException extends CustomException {

    private static final long serialVersionUID = -6632920847550172761L;

    public SystemCustomException(SystemCustomTipEnum customTipEnum) {
        super(customTipEnum);
    }

    public SystemCustomException(SystemCustomTipEnum customTipEnum, Object... args) {
        super(customTipEnum, args);
    }
}
