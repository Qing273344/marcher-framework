package xin.marcher.framework.oss.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xin.marcher.framework.util.EmptyUtil;

/**
 * 存储配置
 *
 * @author marcher
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    /** 阿里云外网EndPoint */
    protected String endPoint;

    /** 阿里云内网EndPoint */
    protected String internalEndPoint;

    /** 阿里云AccessKeyId */
    protected String accessKeyId;

    /** 阿里云AccessKeySecret */
    protected String accessKeySecret;

    /** 阿里云BucketName(正式) */
    protected String bucketName;

    /** 阿里云BucketName(临时) */
    protected String tempBucketName;

    /** 阿里云CDN */
    protected String cdn;

    /** 阿里云文件域名(正式) */
    protected String region;

    /** 阿里云文件域名(临时) */
    protected String tempRegion;

    public String getHost() {
        if (EmptyUtil.isNotEmpty(region)) {
            return region;
        }
        return "http://" + bucketName + "." + endPoint + "/";
    }

    public String getTempHost() {
        if (EmptyUtil.isNotEmpty(tempRegion)) {
            return tempRegion;
        }
        return "http://" + tempBucketName + "." + endPoint + "/";
    }

    public String getCdnHost() {
        if (cdn.toLowerCase().startsWith("http")) {
            return cdn;
        }
        return "http://" + cdn;
    }
}
