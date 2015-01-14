package com.topsem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来注释模块中的每个字段
 *
 * @author jiangfeng
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldDefine {

    String title();

    String group() default "默认组";

    int SN() default 0; //排序号

    int length() default 0;

    String remark() default "";

    String description() default "";

    boolean hidden() default false;

    boolean money() default false;

    boolean percent() default false;

    boolean isNameField() default false;

}
