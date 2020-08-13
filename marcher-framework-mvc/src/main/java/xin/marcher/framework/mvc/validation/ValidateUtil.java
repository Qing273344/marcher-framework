package xin.marcher.framework.mvc.validation;

import org.hibernate.validator.HibernateValidator;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.util.ArrayUtil;

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

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> ValidateResult violation(T t, Class<?>... groups) {
        try {
            Set<ConstraintViolation<T>> constraintViolations;
            if (groups == null || groups.length <= 0) {
                constraintViolations = validator.validate(t, Default.class);
            } else {
                // 将Default加入group中
                Class<?>[] destGroups = ArrayUtil.add(groups, Default.class);
                constraintViolations = validator.validate(t, destGroups);
            }
            if (constraintViolations.size() >= 1) {
                String message = constraintViolations.iterator().next().getMessage();
                return ValidateResult.errorInstance(message);
            }
        } catch (Exception e){
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
