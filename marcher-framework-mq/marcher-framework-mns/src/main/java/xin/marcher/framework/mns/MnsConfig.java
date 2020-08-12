package xin.marcher.framework.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mns config
 *
 * @author marcher
 */
@Configuration
@ConditionalOnClass({CloudAccount.class})   // 该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@ConditionalOnProperty(prefix = "aliyun.mns", value = "enable", matchIfMissing = true) // 运行使用 aliyun.mns.enable = false 禁用 Swagger
@EnableConfigurationProperties(MnsProperties.class)
public class MnsConfig {

    @Bean
    @ConditionalOnMissingBean
    public MnsProperties mnsProperties() {
        return new MnsProperties();
    }

    @Bean
    public MNSClient mnsClient() {
        MnsProperties mnsProperties = mnsProperties();

        CloudAccount cloudAccount = new CloudAccount(mnsProperties.getAccessId(), mnsProperties.getAccessKey(), mnsProperties.getAccountEndpoint());
        return cloudAccount.getMNSClient();
    }
}
