package xin.marcher.framework.log.aspect;

import cn.hutool.core.date.StopWatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import xin.marcher.framework.common.constants.GlobalConstant;
import xin.marcher.framework.log.annotation.RequestLog;
import xin.marcher.framework.common.util.HttpContextUtil;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 请求日志 aspect
 *
 * @author marcher
 */
@Slf4j
@Aspect
public class RequestLogAspect {

    @Pointcut("@annotation(xin.marcher.framework.log.annotation.RequestLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object addAroundLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("暂不支持非方法注解");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object object = joinPoint.proceed();

        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        if (requestLog == null) {
            stopWatch.stop();
            return object;
        }

        StringBuilder logStr = new StringBuilder();
        appendIp(logStr);
        appendAction(logStr);
        appendName(logStr, requestLog.value());
        ifPresentAppendParams(logStr, requestLog.ignoreParams(), joinPoint);
        if (!requestLog.ignoreResponse()) {
            ObjectMapper objectMapper = new ObjectMapper();
            logStr.append("result=").append(objectMapper.writeValueAsString(object)).append(GlobalConstant.CHAR_SPACE);
        }
        logStr.append("time=").append(stopWatch.getTotalTimeMillis()).append("ms");
        log.info(logStr.toString());

        return object;
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "ex")
    public void addAfterThrowingLog(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("暂不支持非方法注解");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        if (requestLog == null) {
            return;
        }

        StringBuilder logStr = new StringBuilder();
        appendIp(logStr);
        appendAction(logStr);
        appendName(logStr, requestLog.value());
        ifPresentAppendParams(logStr, requestLog.ignoreParams(), joinPoint);
        logStr.append("msg=").append(ex.getMessage());

        log.error(logStr.toString());
    }


    private void appendIp(StringBuilder logStr) {
        logStr.append("ip=").append(HttpContextUtil.getRequestIp()).append(GlobalConstant.CHAR_SPACE);
    }

    private void appendAction(StringBuilder logStr) {
        logStr.append("action=").append(HttpContextUtil.getRequest().getRequestURL()).append(GlobalConstant.CHAR_SPACE);
    }

    private void appendName(StringBuilder logStr, String name) {
        logStr.append("name=").append(name).append(GlobalConstant.CHAR_SPACE);
    }

    private void ifPresentAppendParams(StringBuilder logStr, boolean ignoreParams, JoinPoint joinPoint) {
        if (!ignoreParams) {
            logStr.append("params=").append(Arrays.toString(joinPoint.getArgs())).append(GlobalConstant.CHAR_SPACE);
        }
    }

}
