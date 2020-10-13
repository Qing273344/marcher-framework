package xin.marcher.framework.crypto;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
