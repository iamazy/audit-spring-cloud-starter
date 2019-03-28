package com.iamazy.springcloud.audit.configuration.condition;

import com.iamazy.springcloud.audit.configuration.AuditConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author iamazy
 * @date 2019/1/12
 * @descrition
 **/
public class AuditEnabledCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        AuditConfigurationProperties auditProperties=getAuditProperties(context);
        if(!auditProperties.getEnabled()){
            return ConditionOutcome.noMatch("审计功能被禁用,因为audit.enabled=false!!!");
        }
        return ConditionOutcome.match();
    }

    private AuditConfigurationProperties getAuditProperties(ConditionContext context){
        AuditConfigurationProperties auditProperties=new AuditConfigurationProperties(context.getEnvironment());
        Binder.get(context.getEnvironment()).bind("audit", Bindable.ofInstance(auditProperties));
        return auditProperties;
    }
}
