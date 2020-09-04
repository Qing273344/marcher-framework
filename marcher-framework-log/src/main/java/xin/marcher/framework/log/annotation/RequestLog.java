package xin.marcher.framework.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求日志
 *
 * @author marcher
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLog {

    /**
     * 日志描述
     *
     * @return  value
     */
    String value() default "";

    /**
     * 忽略请求参数
     *
     * @return  boolean
     */
    boolean ignoreParams() default false;

    /**
     * 忽略响应参数
     *
     * @return  boolean
     */
    boolean ignoreResponse() default false;
}
