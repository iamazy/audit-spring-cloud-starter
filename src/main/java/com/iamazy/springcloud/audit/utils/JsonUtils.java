package com.iamazy.springcloud.audit.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author iamazy
 * @date 2018/12/12
 **/
public class JsonUtils {

    private static ObjectMapper objectMapper=new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private JsonUtils(){}

    public static String object2Json(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    public static <T> T o2o(Object fromValue,Class<T> toValueType){
        return objectMapper.convertValue(fromValue,toValueType);
    }
}
