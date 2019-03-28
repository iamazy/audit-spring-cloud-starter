package com.iamazy.springcloud.audit.annotation;

import java.lang.annotation.*;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
public @interface IgnoreAudit {
}
