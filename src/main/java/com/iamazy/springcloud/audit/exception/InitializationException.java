package com.iamazy.springcloud.audit.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@Slf4j
public class InitializationException extends RuntimeException {

    private static final long serialVersionUID = 7878936140829656430L;

    public InitializationException(String message){
        super(message);
        log.error("Failed to initialize:"+message);
    }

    public InitializationException(String message,Throwable cause){
        super(message);
        log.error("Failed to initialize:"+message,cause);
    }
}
