package com.iamazy.springcloud.audit.layout;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateSource;
import com.iamazy.springcloud.audit.model.AuditEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

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
@Slf4j
@Data
public class DefaultLayout implements Layout {

    private static final long serialVersionUID = -6242848376637845333L;

    private static final String DEFAULT_TEMPLATE=
            "\n=========================={{ startTime }} Start ===================================\n"+
            "Uuid: {{ uuid }}\n" +
            "Actor: {{ actor }}\n" +
            "ServerIp: {{ serverIp }}\n" +
            "ClientIp: {{ clientIp }}\n" +
            "UserAgent: {{ userAgent }}\n"+
            "Url: {{ url }}\n"+
            "Action: {{ action }}\n"+
            "Class: {{ clazz }}\n"+
            "Method: {{ method }}\n"+
            "Args: {{#each args }} {{ this }} {{/each}}\n"+
            "Result: {{ result }}\n"+
            "Status: {{ status }}\n"+
            "=========================={{ endTime }} End =====================================\n";

    private TemplateSource source;

    private static Handlebars handlebars=new Handlebars();

    public DefaultLayout(){ }

    public DefaultLayout(TemplateSource source){
        this.source=source;
    }

    @Override
    public String format(AuditEvent event) {
        try {
            Template template;
            if(!Objects.isNull(source)){
                template=handlebars.compile(source);
            }else {
                template = handlebars.compileInline(DEFAULT_TEMPLATE);
            }
            return template.apply(event);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("审计模板解析出错!!!无法显示审计日志信息!!!");
            return StringUtils.EMPTY;
        }
    }
}
