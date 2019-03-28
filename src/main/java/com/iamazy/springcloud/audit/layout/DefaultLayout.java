package com.iamazy.springcloud.audit.layout;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateSource;
import com.iamazy.springcloud.audit.cons.CoreConstants;
import com.iamazy.springcloud.audit.model.AuditEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
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
            "Args: {{ fields }}\n"+
            "Result: {{ result }}\n"+
            "Status: {{ status }}\n"+
            "=========================={{ endTime }} End =====================================\n";

    private String dateFormat= CoreConstants.DEFAULT_DATE_FORMAT;

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
                handlebars.setPrettyPrint(true);
                handlebars.setCharset(Charset.forName("UTF-8"));
                handlebars.setStringParams(true);
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
