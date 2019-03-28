package com.iamazy.springcloud.audit.annotation;

import java.lang.annotation.*;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition 该注解用来隐藏敏感信息的一部分
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER,ElementType.FIELD})
public @interface DeIdentify {

    int left() default 0;

    int right() default 0;

    int fromLeft() default 0;

    int fromRight() default 0;
}
