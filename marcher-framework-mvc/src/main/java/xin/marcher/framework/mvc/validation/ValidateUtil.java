package xin.marcher.framework.mvc.validation;

import org.hibernate.validator.HibernateValidator;
import xin.marcher.framework.common.exception.UtilException;
import xin.marcher.framework.common.util.ArrayUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

/**
 * 校验工具类
 *
 * @author marcher
 */
public class ValidateUtil {

    private static final Validator VALIDATOR;

    static {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 是否包含错误
     *
     * @param t     校验对象
     * @param <T>   T
     * @return      是否
     */
    public static <T> boolean hasErrors(T t) {
        return violation(t).isSuccess();
    }

    public static <T> String getMessage(T t) {
        return violation(t).getMessage();
    }

    public static <T> String getMessage(T t, Class<?>... groups) {
        return violation(t, groups).getMessage();
    }

    public static <T> ValidateResult violation(T t, Class<?>... groups) {
        try {
            Set<ConstraintViolation<T>> constraintViolations;
            if (groups == null || groups.length <= 0) {
                constraintViolations = VALIDATOR.validate(t, Default.class);
            } else {
                // 将 Default 加入 group 中
                constraintViolations = VALIDATOR.validate(t, ArrayUtil.add(groups, Default.class));
            }
            if (constraintViolations.size() > 0) {
                String message = constraintViolations.iterator().next().getMessage();
                return ValidateResult.errorInstance(message);
            }
        } catch (Exception e) {
            throw new UtilException("marcher-framework-mvc validation -->  error! to contact the administrator", e);
        }
        return ValidateResult.SUCCESS;
    }

    public static <T> ValidateResult violation(List<T> list, Class<?>... groups) {
        for (T t : list) {
            ValidateResult violation = violation(t, groups);
            if (!violation.isSuccess()) {
                return violation;
            }
        }
        return ValidateResult.SUCCESS;
    }
}
