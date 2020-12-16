package pers.yurwisher.dota2.pudge.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yq
 * @date 2020/12/16 17:32
 * @description 字典值 转 描述
 * @since V1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DictValueToName {

    @AliasFor("dictType")
    String value() default "";

    @AliasFor("value")
    String dictType() default "";
}
