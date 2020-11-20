package xin.marcher.oss.manager;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.oss.OSSFactory;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.tuple.Tuple;
import xin.marcher.framework.tuple.Tuple2;
import xin.marcher.framework.util.CollectionUtil;
import xin.marcher.framework.util.DateUtil;
import xin.marcher.framework.util.UrlPathUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author marcher
 */
@Component
@Slf4j
public class OssManager {

    @Autowired
    private OssProperties ossProperties;

    private static final Long EXPIRE_TIME = 60 * 60L;

    /**
     * 上传文件
     *
     * @param fileName 文件 name
     * @return 文件url
     */
    public String putFile(String fileName, byte[] input) {
        String dateStr = DateUtil.formatDate(DateUtil.now(), DateUtil.PATTERN_HYPHEN_DATE);
        String ossKey = UrlPathUtil.createPath(fileName, CollectionUtil.asList(dateStr));

        try {
            ossKey = OSSFactory.build(ossProperties).putObject(ossProperties.getTempBucketName(), ossKey, input);
        } catch (OSSException | ClientException e) {
            log.error("put oss error!", e);
        }
        return ossProperties.getTempHost() + ossKey;
    }

    /**
     * 迁移资源
     *
     * @param srcFileUrl    原资源 url
     * @param directoryList 目录集合
     * @return 新资源 url
     */
    public String move(String srcFileUrl, List<String> directoryList) {
        return OSSFactory.build(ossProperties).move(ossProperties.getTempBucketName(), srcFileUrl, ossProperties.getBucketName(), directoryList);
    }

    /**
     * signature
     *
     * @return
     */
    public Tuple2<String, String> signature() {
        String dir = "";

        try {
            Date expiration = new Date(DateUtil.now() + (EXPIRE_TIME * 1000));

            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = OSSFactory.build(ossProperties).generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);

            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = OSSFactory.build(ossProperties).calculatePostSignature(postPolicy);
            return Tuple.create(policy, signature);
        } catch (Exception ex) {
            throw new UtilException("生成 oss 签名出错");
        }
    }
}
