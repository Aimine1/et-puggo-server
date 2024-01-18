package com.etrade.puggo.common.weblog;

import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.service.AuthDTO;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.utils.JsonUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description http请求统一日志
 * @Author liuyuqing
 * @Date 2021/6/25 14:51
 **/
@Aspect
@Component
@Slf4j
@RefreshScope
public class WebLogAspect extends BaseService {

    private static final Integer REQUEST = 1;
    private static final Integer RESPONSE = 2;

    @Value("${api.request-time:2000}")
    private Long API_REQUEST_TIME_ALARM_THRESHOLD;

    @Pointcut("@annotation(com.etrade.puggo.common.weblog.WebLog)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        StopWatch sw = new StopWatch();
        sw.start();
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 打印请求 url
        // 打印调用 controller 的全路径以及执行方法
        // 打印请求的 IP

        String requestURI = request.getRequestURI();
        AuthDTO authDTO = getAuthDTO();

        log.info("URL call info {} " +
                "nickname {} " +
                "userid {} " +
                "Class Method {}#{}  " +
                "IP {} ",
            requestURI,
            AuthContext.getUserName(),
            AuthContext.getUserId(),
            proceedingJoinPoint.getSignature().getDeclaringTypeName(),
            proceedingJoinPoint.getSignature().getName(),
            request.getRemoteAddr()
        );

        if (parseMethodResponse(proceedingJoinPoint, REQUEST)) {
            // 打印请求入参

            List<Object> params =
                Arrays.stream(proceedingJoinPoint.getArgs())
                    .filter(o -> !(o instanceof ServletResponse))
                    .filter(o -> !(o instanceof ServletRequest))
                    .filter(o -> !(o instanceof InputStreamSource))
                    .collect(Collectors.toList());

            log.info("URL Request {} {}  auth {}", requestURI, JsonUtils.toJson(params), authDTO);
        }

        Object result = proceedingJoinPoint.proceed();

        if (parseMethodResponse(proceedingJoinPoint, RESPONSE)) {
            log.info("URL Response {} {} auth {}", requestURI, JsonUtils.toJson(result), authDTO);
        }

        sw.stop();
        long millis = sw.getTime(TimeUnit.MILLISECONDS);

        if (millis >= API_REQUEST_TIME_ALARM_THRESHOLD) {
            log.warn("接口执行时长超过设定阈值 {} s", String.format("URI: %s ,time：%sms", requestURI, millis));
        }

        log.info("URL COST TIME {}, {} ms", requestURI, millis);
        return result;
    }

    private boolean parseMethodResponse(ProceedingJoinPoint proceedingJoinPoint, Integer type)
        throws ClassNotFoundException {
        //切点路径名
        String targetName = proceedingJoinPoint.getTarget().getClass().getName();
        //获取类名
        Class<?> targetClass = Class.forName(targetName);
        //类下面所有方法
        Method[] methods = targetClass.getMethods();
        //方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //方法入参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == args.length) {
                    if (Objects.equals(type, REQUEST)) {
                        return method.getAnnotation(WebLog.class).isNeedRequestArg();
                    } else if (Objects.equals(type, RESPONSE)) {
                        return method.getAnnotation(WebLog.class).isNeedResponseArg();
                    }
                }
            }
        }
        return true;
    }

}
