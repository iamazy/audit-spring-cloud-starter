package com.iamazy.springcloud.audit.interceptor;

import com.iamazy.springcloud.audit.annotation.DeIdentify;
import com.iamazy.springcloud.audit.annotation.IgnoreAudit;
import com.iamazy.springcloud.audit.annotation.DeIdentifyUtils;
import com.iamazy.springcloud.audit.cons.CoreConstants;
import com.iamazy.springcloud.audit.cons.LoginUserInfoHolder;
import com.iamazy.springcloud.audit.layout.Layout;
import com.iamazy.springcloud.audit.model.AuditEvent;
import com.iamazy.springcloud.audit.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
@Aspect
public class WebApiAuditInterceptor {

    private Layout layout;

    public WebApiAuditInterceptor(Layout layout) {
        this.layout = layout;
    }

    private AuditEvent.Builder builder = new AuditEvent.Builder();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);

    private MethodSignature signature;

    @Pointcut("execution(public * *(..))&&@annotation(com.iamazy.springcloud.audit.annotation.Audit)")
    public void auditPointCut() {
    }

    @Before("auditPointCut()")
    public void beforeExecute(JoinPoint joinPoint) {
        builder.startTime(dateFormat.format(System.currentTimeMillis()));
        signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取切面函数的参数列表
        builder.method(method)
                .action(method.getName())
                .clazz(signature.getDeclaringType());
    }

    @Around("auditPointCut()")
    public Object aroundExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String username = LoginUserInfoHolder.getLoginUserInfo().getUsername();
            builder.actor(username)
                    .uuid(UUID.nameUUIDFromBytes((username + "_" + System.currentTimeMillis()).getBytes()).toString());
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(ExceptionUtils.getStackTrace(throwable));
            builder.status(HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw throwable;
        }

    }

    @AfterReturning(returning = "result", pointcut = "auditPointCut()")
    public void afterExecute(JoinPoint joinPoint, Object result) {
        builder.endTime(dateFormat.format(System.currentTimeMillis()));

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if (response != null) {
            builder.userAgent(request.getHeader("User-Agent"))
                    .clientIp(SpringUtils.getRemoteIp(request))
                    .status(response.getStatus())
                    .url("[" + request.getMethod() + "]" + request.getRequestURL().toString());
        }

        //获取切面函数结果
        builder.result(result.toString());
        try {
            builder.serverIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            builder.serverIp("Unknown Host");
        }

        final Parameter[] parameters = signature.getMethod().getParameters();
        final Object[] args = joinPoint.getArgs();
        List<Object> argsList = new ArrayList<>(0);
        for (int i = 0; i < parameters.length; i++) {
            final IgnoreAudit ignoreAudit = parameters[i].getAnnotation(IgnoreAudit.class);
            if (ignoreAudit != null) {
                continue;
            }
            final DeIdentify deIdentify = parameters[i].getAnnotation(DeIdentify.class);
            if (deIdentify == null) {
                continue;
            }
            if (parameters[i].getType().getName().equals(String.class.getCanonicalName())) {
                args[i] = DeIdentifyUtils.deIdentify(args[i].toString(), deIdentify.left(), deIdentify.right(), deIdentify.fromLeft(), deIdentify.fromRight());
            }
            argsList.add(args[i]);
        }
        builder.args(argsList.toArray());
        AuditEvent auditEvent = builder.build();
        final IgnoreAudit ignoreAudit = signature.getMethod().getAnnotation(IgnoreAudit.class);
        if (ignoreAudit == null) {
            log.info(layout.format(auditEvent));
        }
    }

}
