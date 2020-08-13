package xin.marcher.framework.mvc.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * validator 失败返回模式配置
 * 使用 @Valid 注解，对 RequestParam 对应的参数进行注解，是无效的，需要使用 @Validated 注解来使得验证生效。
 *
 * @author marcher
 */
@Configuration
public class ValidatorConfig {

    /**
     * 方法参数校验中使用快速失败返回模式
     * 对 MethodValidationPostProcessor 进行设置Validator（因为此时不是用的 Validator 进行验证，Validator 的配置不起作用）
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        // 设置validator模式为快速失败返回
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

    /**
     * 配置 hibernate Validator 失败返回模式
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                /*
                 * true：快速失败返回模式, 校验完所有属性, 返回验证失败的信息
                 * false：普通模式, 只要有一个验证失败则立即返回
                 */
                .failFast(true)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}
