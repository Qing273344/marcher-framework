package xin.marcher.framework.mvc.validation.constraintvalidators;

import xin.marcher.framework.common.core.IEnumCodeWithIntro;
import xin.marcher.framework.mvc.validation.constraints.InEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校验参数是否在指定枚举范围内
 *
 * @author marcher
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    /**
     * 值数组
     */
    private Set<Integer> values;

    /**
     * intro
     */
    private String intro;

    @Override
    public void initialize(InEnum annotation) {
        IEnumCodeWithIntro[] values = annotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            IEnumCodeWithIntro arrayValuable = values[0];
            this.values = Arrays.stream(arrayValuable.array()).boxed().collect(Collectors.toSet());
            intro = arrayValuable.intro().length() > 0 ? arrayValuable.intro() + " " : arrayValuable.intro();
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // 校验通过
        if (values.contains(value)) {
            return true;
        }

        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(
                    intro + context.getDefaultConstraintMessageTemplate()
                    .replaceAll("\\{value}", values.toString())
                ).addConstraintViolation(); // 重新添加错误提示语句

        return false;
    }
}
