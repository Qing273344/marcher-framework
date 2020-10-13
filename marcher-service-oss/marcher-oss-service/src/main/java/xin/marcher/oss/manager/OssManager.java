package xin.marcher.oss.manager;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.framework.oss.OSSFactory;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.util.CollectionUtil;
import xin.marcher.framework.util.DateUtil;
import xin.marcher.framework.util.UrlPathUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author marcher
 */
@Component
@Slf4j
public class OssManager {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件url
     */
    public String putFile(MultipartFile file) {
        String dateStr = DateUtil.formatDate(DateUtil.now(), DateUtil.PATTERN_HYPHEN_DATE);
        String ossKey = UrlPathUtil.createPath(file.getOriginalFilename(), CollectionUtil.asList(dateStr));

        try {
            ossKey = OSSFactory.build(ossProperties).putObject(ossProperties.getTempBucketName(), ossKey, file.getBytes());
        } catch (OSSException | ClientException | IOException e) {
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
}
