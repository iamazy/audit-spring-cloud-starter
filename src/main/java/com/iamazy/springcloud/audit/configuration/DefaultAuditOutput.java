package com.iamazy.springcloud.audit.configuration;

import com.iamazy.springcloud.audit.layout.Layout;
import com.iamazy.springcloud.audit.model.AuditEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DefaultAuditOutput implements IAuditOutput {


    private Layout layout;

    public DefaultAuditOutput(Layout layout){
        this.layout=layout;
    }

    @Override
    public void output(AuditEvent event) {
        log.info(layout.format(event));
    }
}
