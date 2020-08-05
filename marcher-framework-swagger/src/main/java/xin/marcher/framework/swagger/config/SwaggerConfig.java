package xin.marcher.framework.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 config
 *
 * @author marcher
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})   // 该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@ConditionalOnProperty(prefix = "swagger", value = "enable", matchIfMissing = true) // 运行使用 swagger.enable = false 禁用 Swagger
@EnableConfigurationProperties(SwaggerProperties.class)
@Profile({"dev", "local", "test"})
public class SwaggerConfig {

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public Docket createRestApi() {
        SwaggerProperties properties = swaggerProperties();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(properties.getGroupName())
                .apiInfo(apiInfo(properties))
                .select()
                // 自行修改为自己的包路径
//                .apis(RequestHandlerSelectors.basePackage("com.soushi88"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), null, null))
                .build();
    }
}
