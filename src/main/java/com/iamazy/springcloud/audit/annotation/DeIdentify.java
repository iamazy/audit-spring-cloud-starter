package com.iamazy.springcloud.audit.annotation;

import java.lang.annotation.*;

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
 * @descrition 该注解用来隐藏敏感信息的一部分
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER,ElementType.FIELD})
public @interface DeIdentify {

    int left() default 0;

    int right() default 0;

    int fromLeft() default 0;

    int fromRight() default 0;
}
