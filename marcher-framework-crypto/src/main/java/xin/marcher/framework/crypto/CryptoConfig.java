package xin.marcher.framework.crypto;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解密配置
 *
 * @author marcher
 */
@Configuration
public class CryptoConfig {

    @Bean
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new CryptoPropertyResolver();
    }

}
