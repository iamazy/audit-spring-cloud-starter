package com.iamazy.springcloud.audit.utils;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
public class ObjectUtils {

    public static boolean isPrimitive(Object object) {
        return object instanceof String || object instanceof Number || object instanceof Boolean
                || object instanceof Character;
    }
}
