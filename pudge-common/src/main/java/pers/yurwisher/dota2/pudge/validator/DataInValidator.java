package pers.yurwisher.dota2.pudge.validator;


import cn.hutool.core.util.StrUtil;
import pers.yurwisher.dota2.pudge.validator.annotation.DataIn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yq
 * @date 2020/08/04 10:59
 * @description 数据在指定数组中
 * @since V1.0.0
 */
public class DataInValidator implements ConstraintValidator<DataIn, Object> {

    private boolean nullable;
    private String[] dataList;

    @Override
    public void initialize(DataIn constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
        this.dataList = constraintAnnotation.dataList();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null || StrUtil.isEmpty(value.toString())){
            return nullable;
        }else{
            for (int i = 0; i < dataList.length; i++) {
                if(dataList[i].equals(value.toString())){
                    return true;
                }
            }
            return false;
        }
    }
}
