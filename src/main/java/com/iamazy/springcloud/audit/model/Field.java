package com.iamazy.springcloud.audit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
 * @date 2019/1/11
 * @descrition
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field implements Serializable {


    private static final long serialVersionUID = -3924275304800140805L;

    private String name;

    private String value;

    private String type;

    public Field(String name, String value){
        this.name=name;
        this.value=value;
        this.type=value.getClass().getName();
    }

}
