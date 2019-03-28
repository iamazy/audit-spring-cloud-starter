package com.iamazy.springcloud.audit.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

/**
 * @author iamazy
 * @date 2019/1/12
 * @descrition
 **/
@Data
@ConfigurationProperties(prefix = "audit",ignoreUnknownFields = false)
public class AuditConfigurationProperties {

    private Boolean enabled=false;

    private final Environment environment;

    public AuditConfigurationProperties(Environment environment){
        Assert.notNull(environment, "Environment must not be null");
        this.environment = environment;
    }
}
