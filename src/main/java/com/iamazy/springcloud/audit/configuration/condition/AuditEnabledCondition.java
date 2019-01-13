package com.iamazy.springcloud.audit.configuration.condition;

import com.iamazy.springcloud.audit.configuration.AuditConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

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
