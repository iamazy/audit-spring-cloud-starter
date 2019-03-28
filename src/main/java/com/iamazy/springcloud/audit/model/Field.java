package com.iamazy.springcloud.audit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field implements Serializable {


    private static final long serialVersionUID = -3924275304800140805L;

    private String name;

    private byte[] value;

    private String type;

    public Field(String name, byte[] value){
        this.name=name;
        this.value=value;
        this.type=value.getClass().getName();
    }

}
