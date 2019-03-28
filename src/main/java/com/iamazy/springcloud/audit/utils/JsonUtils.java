package com.iamazy.springcloud.audit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iamazy.springcloud.audit.cons.CoreConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author iamazy
 * @date 2018/12/12
 **/
public class JsonUtils {



    private JsonUtils(){}

    public static String object2Json(Object object){
        try {
            return CoreConstants.OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    public static boolean isJsonValid(Object o) {
        try {
            CoreConstants.OBJECT_MAPPER.readTree(o.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static <T> T o2o(Object fromValue,Class<T> toValueType){
        return CoreConstants.OBJECT_MAPPER.convertValue(fromValue,toValueType);
    }
}
