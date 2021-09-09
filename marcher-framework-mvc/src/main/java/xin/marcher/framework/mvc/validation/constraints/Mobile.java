package xin.marcher.framework.mvc.validation.constraints;

import xin.marcher.framework.mvc.validation.constraintvalidators.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * InEnum
 * <p>
 * 栗子: @Mobile(isRequired = false)
 *
 * @author marcher
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

    String message() default "手机号格式错误";

    /**
     * 是否强制校验, 默认 false, 有值校验, 无值不校验
     *
     * @return  true/false
     */
    boolean required() default false;

    /**
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * @return Payload 数组
     */
    Class<? extends Payload>[] payload() default {};
}
