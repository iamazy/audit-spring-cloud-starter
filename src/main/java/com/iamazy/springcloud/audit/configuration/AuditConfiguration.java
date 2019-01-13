package com.iamazy.springcloud.audit.configuration;

import com.iamazy.springcloud.audit.configuration.condition.AuditEnabledCondition;
import com.iamazy.springcloud.audit.interceptor.WebApiAuditInterceptor;
import com.iamazy.springcloud.audit.layout.Layout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

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
 * @date 2019/1/12
 * @descrition
 **/
@Slf4j
@Conditional(AuditEnabledCondition.class)
@Configuration
@EnableConfigurationProperties(AuditConfigurationProperties.class)
public class AuditConfiguration {

    @Resource(name = "templateLayout")
    private Layout layout;

    @Bean
    public WebApiAuditInterceptor webApiAuditInterceptor(){
        return new WebApiAuditInterceptor(layout);
    }
}
