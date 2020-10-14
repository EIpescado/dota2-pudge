package pers.yurwisher.dota2.pudge.validator;


import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import pers.yurwisher.dota2.pudge.validator.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yq
 * @date 2020/08/04 10:59
 * @description 手机号校验
 * @since V1.0.0
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private boolean nullable;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StrUtil.isEmpty(value)){
            return nullable;
        }else{
            return PhoneUtil.isMobile(value);
        }
    }
}
