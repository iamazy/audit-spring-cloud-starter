package com.iamazy.springcloud.audit.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iamazy.springcloud.audit.annotation.DeIdentify;
import com.iamazy.springcloud.audit.annotation.DeIdentifyUtils;
import com.iamazy.springcloud.audit.annotation.IgnoreAudit;
import com.iamazy.springcloud.audit.configuration.IAuditOutput;
import com.iamazy.springcloud.audit.model.AuditEvent;
import com.iamazy.springcloud.audit.model.Field;
import com.iamazy.springcloud.audit.utils.SpringUtils;
import com.iamazy.springcloud.audit.cons.CoreConstants;
import com.iamazy.springcloud.audit.cons.LoginUserInfoHolder;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author iamazy
 * @date 2019/1/11
 * @descrition
 **/
@Slf4j
@Aspect
public class WebApiAuditInterceptor {


    private IAuditOutput auditOutput;

    public WebApiAuditInterceptor(IAuditOutput auditOutput) {
        this.auditOutput = auditOutput;
    }

    private AuditEvent.Builder builder = new AuditEvent.Builder();

    private MethodSignature signature;

    @Pointcut("(execution(public * *(..))&&@annotation(com.iamazy.springcloud.audit.annotation.Audit))||@within(com.iamazy.springcloud.audit.annotation.Audit)")
    public void auditPointCut() {
    }

    @Before("auditPointCut()")
    public void beforeExecute(JoinPoint joinPoint) {
        builder.startTime(System.currentTimeMillis());
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
    public void afterExecute(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        builder.endTime(System.currentTimeMillis());

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if (response != null) {
            builder.userAgent(request.getHeader("User-Agent"))
                    .clientIp(SpringUtils.getRemoteIp(request))
                    .status(response.getStatus())
                    .url("[" + request.getMethod() + "]" + request.getRequestURL().toString());
        }

        //获取切面函数结果
        builder.result(CoreConstants.OBJECT_MAPPER.writeValueAsString(result));
        try {
            builder.serverIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            builder.serverIp("Unknown Host");
        }

        final Parameter[] parameters = signature.getMethod().getParameters();
        final Object[] args = joinPoint.getArgs();
        List<Field> argsList = new ArrayList<>(0);
        for (int i = 0; i < parameters.length; i++) {
            final IgnoreAudit ignoreAudit = parameters[i].getAnnotation(IgnoreAudit.class);
            if (ignoreAudit != null) {
                continue;
            }
            final DeIdentify deIdentify = parameters[i].getAnnotation(DeIdentify.class);
            if (deIdentify != null&&args[i]!=null) {
                if (parameters[i].getType().getName().equals(String.class.getCanonicalName())) {
                    args[i] = DeIdentifyUtils.deIdentify(args[i].toString(), deIdentify.left(), deIdentify.right(), deIdentify.fromLeft(), deIdentify.fromRight());
                }
            }
            Field field=new Field();
            field.setName(parameters[i].getName());
            field.setType(parameters[i].getType().getTypeName());
            if(args[i]==null){
                field.setValue(null);
            }else {
                field.setValue(args[i].toString());
            }
            argsList.add(field);
        }
        builder.fields(CoreConstants.OBJECT_MAPPER.writeValueAsString(argsList));
        AuditEvent auditEvent = builder.build();
        final IgnoreAudit ignoreAudit = signature.getMethod().getAnnotation(IgnoreAudit.class);
        if (ignoreAudit == null) {
            auditOutput.output(auditEvent);
        }
    }

}
