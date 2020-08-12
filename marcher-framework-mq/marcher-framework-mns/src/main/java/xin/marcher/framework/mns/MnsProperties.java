package xin.marcher.framework.mns;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云 mns
 *
 * @author marcher
 */
@Data
@ConfigurationProperties("aliyun.mns")
public class MnsProperties {

    private String accessId;

    private String accessKey;

    private String accountEndpoint;

}
