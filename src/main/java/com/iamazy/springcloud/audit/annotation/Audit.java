package com.iamazy.springcloud.audit.annotation;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@InterceptorBinding
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface Audit {

    /**
     * 描述审计方法的行为
     * @return
     */
    @Nonbinding
    String action() default "action";

    /**
     * 定义审计日志的输出方式
     * @return
     */
    @Nonbinding
    String repository() default "console";
}
