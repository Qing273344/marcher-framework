package xin.marcher.framework.mvc.validation.constraintvalidators;

import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.RegexUtil;
import xin.marcher.framework.mvc.validation.constraints.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author marcher
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private boolean required = false;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return RegexUtil.isMobile(value);
        } else {
            if (EmptyUtil.isEmpty(value)) {
                return true;
            } else {
                return RegexUtil.isMobile(value);
            }
        }
    }
}
