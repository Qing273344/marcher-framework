package xin.marcher.framework.crypto;

import cn.hutool.http.HttpUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.util.EmptyUtil;

import javax.annotation.PostConstruct;

/**
 * @author marcher
 */
@Data
@Component
@ConfigurationProperties(prefix = "crypto")
public class CryptoPropertyResolver implements EncryptablePropertyResolver {

    private String key;

    private String keyUrl;

    @PostConstruct
    public void init() {
        if (EmptyUtil.isNotEmptyTrim(key)) {
            this.key = key;
        }

        // 当 key 为空时调用配置服务器, 我肯定是会调用这个的啦
        if (EmptyUtil.isEmptyTrim(keyUrl)) {
            throw new UtilException("项目配置文件加密项不能为空哟... 请看 application.yml 中是否配置了 crypto.key-url");
        }
        this.key = HttpUtil.get(keyUrl);
    }

    @Override
    public String resolvePropertyValue(String value) {
        if (EmptyUtil.isEmpty(value)) {
            return value;
        }
        // 值以 AES@ 开头的均为 AES 加密, 需要解密
        if (value.startsWith(AESUtil.CRYPTO_PREFIX)) {
            return decrypt(value.substring(AESUtil.CRYPTO_PREFIX.length()));
        }
        // 不需要解密的值直接返回
        return value;
    }

    private String decrypt(String value) {
        // 自定义 DES 密文解密
        return AESUtil.decrypt(key, value);
    }

    /**
     * 需要获取加密后的调用此方法
     */
    private void encrypt() {
        String value = "这里是需要加密的东东...";
        String encrypt = AESUtil.encrypt(key, value);
    }
}
