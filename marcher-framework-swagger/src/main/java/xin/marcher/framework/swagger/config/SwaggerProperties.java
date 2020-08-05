package xin.marcher.framework.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger properties
 *
 * @author marcher
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String groupName;

    private String title;

    private String description;

    private String version;

    private String contactName;
}
