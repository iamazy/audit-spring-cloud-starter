package com.iamazy.springcloud.audit.configuration;

import com.github.jknack.handlebars.internal.Files;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateSource;
import com.iamazy.springcloud.audit.layout.DefaultLayout;
import com.iamazy.springcloud.audit.layout.Layout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author iamazy
 * @date 2019/1/12
 * @descrition
 **/
@Slf4j
@Configuration
public class LayoutConfiguration {

    @Bean(name = "templateLayout")
    public Layout defaultLayout() throws IOException {
        Layout layout;
        try {
            File file = ResourceUtils.getFile("classpath:audit.hbs");
            TemplateSource source=new StringTemplateSource(file.getName(), Files.read(file, Charset.defaultCharset()));
            layout = new DefaultLayout(source);
        }catch (FileNotFoundException e){
            log.warn("未找到审计模板,将使用默认模板!!!");
            layout = new DefaultLayout();
        }
        return layout;
    }
}
