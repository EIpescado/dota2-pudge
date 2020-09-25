package pers.yurwisher.dota2.pudge.validator.annotation;


import pers.yurwisher.dota2.pudge.validator.DataInValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yq
 * @date 2020/08/04 13:46
 * @description 数据在指定集合内
 * @since V1.0.0
 */
@Constraint(validatedBy = {DataInValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataIn {

    String message();

    boolean nullable() default false;

    String[] dataList() ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
