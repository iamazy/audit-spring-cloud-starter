package com.iamazy.springcloud.audit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Copyright 2018-2019 iamazy Logic Ltd
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@Data
@NoArgsConstructor
public class AuditEvent implements Serializable {

    private static final long serialVersionUID = 8989334307700723014L;

    public String uuid;

    public String actor;

    public String serverIp;

    public String clientIp;

    /**
     * 若码猿填了@Audit的action,则为该值，如不填则为方法名
     */
    public String action;

    public String startTime;

    public String endTime;

    /**
     * 请求 成功:1 或 失败:0, 是Web Api时就是返回的状态码
     */
    public Integer status;

    private List<Field> fields;

    /**
     * 用户请求的Web Url
     */
    public String url;

    /**
     * 方法执行所在的类,而不是返回值的类
     */
    private Class<?> clazz;

    private Method method;

    private Object[] args;

    private Object result;

    private String userAgent;


    @Override
    public boolean equals(Object o) {
        return o instanceof AuditEvent&&EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hash(uuid, actor, serverIp, clientIp, action, startTime, endTime, status, fields, url, userAgent , clazz, method, result);
        result1 = 31 * result1 + Arrays.hashCode(args);
        return result1;
    }

    public static class Builder{
        private AuditEvent event;

        public Builder(){
            event=new AuditEvent();
        }

        public Builder clazz(Class<?> clazz){
            event.setClazz(clazz);
            return this;
        }

        public Builder args(Object[] args){
            event.setArgs(args);
            return this;
        }

        public Builder result(Object result){
            event.setResult(result);
            return this;
        }

        public Builder method(Method method){
            event.setMethod(method);
            return this;
        }

        public Builder startTime(String startTime){
            event.setStartTime(startTime);
            return this;
        }

        public Builder actor(String actor){
            event.setActor(actor);
            return this;
        }

        public Builder serverIp(String serverIp){
            event.setServerIp(serverIp);
            return this;
        }

        public Builder clientIp(String clientIp){
            event.setClientIp(clientIp);
            return this;
        }

        public Builder url(String url){
            event.setUrl(url);
            return this;
        }

        public Builder action(String action){
            event.setAction(action);
            return this;
        }

        public Builder endTime(String endTime){
            event.setEndTime(endTime);
            return this;
        }

        public Builder status(Integer status){
            event.setStatus(status);
            return this;
        }

        public Builder uuid(String uuid){
            event.setUuid(uuid);
            return this;
        }

        public Builder userAgent(String userAgent){
            event.setUserAgent(userAgent);
            return this;
        }


        public AuditEvent build(){
            return event;
        }
    }
}
