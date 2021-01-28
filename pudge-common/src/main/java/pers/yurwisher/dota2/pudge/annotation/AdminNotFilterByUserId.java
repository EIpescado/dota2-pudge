package pers.yurwisher.dota2.pudge.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 管理员查询 不按用户ID过滤数据
 * @author yq 2021年1月28日 15:20:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminNotFilterByUserId {

    boolean value() default true;
}
