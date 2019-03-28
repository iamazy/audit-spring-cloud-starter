package com.iamazy.springcloud.audit.configuration;

import com.iamazy.springcloud.audit.model.AuditEvent;

public interface IAuditOutput {

    default void output(AuditEvent event){}

}
