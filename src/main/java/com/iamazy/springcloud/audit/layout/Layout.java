package com.iamazy.springcloud.audit.layout;

import com.iamazy.springcloud.audit.model.AuditEvent;

import java.io.Serializable;


/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
public interface Layout extends Serializable {

    String format(AuditEvent event);
}
