package com.iamazy.springcloud.audit.configuration;

import com.iamazy.springcloud.audit.configuration.condition.AuditEnabledCondition;
import com.iamazy.springcloud.audit.layout.Layout;
import com.iamazy.springcloud.audit.interceptor.WebApiAuditInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
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

    @Resource(name="auditOutput")
    private IAuditOutput auditOutput;

    @Bean
    public WebApiAuditInterceptor webApiAuditInterceptor(){
        return new WebApiAuditInterceptor(auditOutput);
    }

    @Bean(name = "auditOutput")
    @ConditionalOnMissingBean
    public IAuditOutput auditOutput(){
        return new DefaultAuditOutput(layout);
    }
}
