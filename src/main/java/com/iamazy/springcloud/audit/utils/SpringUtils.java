package com.iamazy.springcloud.audit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author pokerzeus
 * @date 2017
 */
@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法。设置上下文环境
     *
     * @param applicationContext 上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取对象Bean
     *
     * @param name Bean名称
     * @return Bean对象
     * @throws BeansException Bean异常
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取远程访问路径参数
     *
     * @return 来访路径
     */
    public static String getRequestContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String context = /*request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + */request.getServletPath();
        if (request.getQueryString() != null) {
            context += "?" + request.getQueryString();
        }
        return context;
    }

    /**
     * 获取URL Decoded之后的远程访问路径参数
     *
     * @return 来访路径
     */
    public static String getRequestContextDecoded() {
        String context = getRequestContext();
        try {
            return URLDecoder.decode(context, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("解码访问URL地址出错！" + context);
            return context;
        }
    }

    /**
     * 获取来访客户端IP。当在Controller中调用该方法时，RequestContextHolder已经被自动注入绑定到了调用者的线程上，可以直接获取到其IP
     *
     * @return 来访IP地址
     */
    private static final String UNKNOWN ="unknown";
    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIp = request.getHeader("x-forwarded-for");
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("X-Real-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        }
        if (remoteIp == null || remoteIp.isEmpty() || UNKNOWN.equalsIgnoreCase(remoteIp)) {
            remoteIp = request.getRemoteHost();
        }
        return remoteIp;
    }


}

