package xin.marcher.framework.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.UrlPathUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Oss工具
 * 配置信息会变动需修改实现
 *
 * @author marcher
 */
public class OssProvider {

    private static final Map<Integer, OssProvider> ossManagerMap = new ConcurrentHashMap<>();
    private static final Map<Integer, OSSClient> ossClientMap = new ConcurrentHashMap<>();
    private OSSClient client;

    private OssProperties config;
    private boolean hasExternal;

    private OssProvider(OssProperties config, Boolean isExternal) {
        this.config = config;
        this.hasExternal = isExternal;
        // 网络环境转换
        convertEnvironment();
    }

    public synchronized static OssProvider getInstance(OssProperties config, final boolean isExternal) {
        // 网络环境
        Integer endPointType = isExternal ? OssEndPointTypeEnum.END_POINT_EXTERNAL.getRealCode() : OssEndPointTypeEnum.END_POINT_INTERNAL.getRealCode();
        OssProvider ossProvider = ossManagerMap.get(endPointType);
        if (null != ossProvider && null != ossProvider.getClient()) {
            return ossProvider;
        }

        if (EmptyUtil.isEmpty(ossProvider)) {
            ossProvider = new OssProvider(config, isExternal);
        }
        // oss不为空, 内部的client为空
        else {
            if (EmptyUtil.isEmpty(ossProvider.getClient())) {
                ossProvider = new OssProvider(config, isExternal);
            }
        }
        ossManagerMap.put(endPointType, ossProvider);
        return ossProvider;
    }

    private void convertEnvironment() {
        // 网络环境
        Integer endPointType = getEndPoint(this.hasExternal);
        this.client = ossClientMap.get(endPointType);
        if (EmptyUtil.isNotEmpty(client)) {
            return;
        }

        // client为空
        if (EmptyUtil.isEmpty(client)) {
            // 初始化
            init();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        if (hasExternal) {
            client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        } else {
            client = new OSSClient(config.getInternalEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        }

        Integer endPointType = getEndPoint(this.hasExternal);
        ossClientMap.put(endPointType, client);
    }

    private Integer getEndPoint(final boolean isExternal) {
        return isExternal ? OssEndPointTypeEnum.END_POINT_EXTERNAL.getRealCode() : OssEndPointTypeEnum.END_POINT_INTERNAL.getRealCode();
    }


    /**
     * 策略
     * @param expiration    过期时间
     * @param conds
     * @return
     */
    public String generatePostPolicy(Date expiration, PolicyConditions conds) {
        return this.client.generatePostPolicy(expiration, conds);
    }

    /**
     * 生成签名
     * @param postPolicy
     * @return
     */
    public String calculatePostSignature(String postPolicy) {
        return this.client.calculatePostSignature(postPolicy);
    }


    /**
     * 流式下载文件
     *
     * @param bucketName 桶名
     * @param key        key
     * @return oss对象
     */
    public OSSObject getObject(String bucketName, String key) {
        return client.getObject(bucketName, key);
    }

    /**
     * 下载文件到本地
     *
     * @param bucketName 桶名
     * @param key        key
     * @param localPath  本地路径
     */
    public void getObject(String bucketName, String key, String localPath) {
        // new File("<yourLocalFile>"这个file对象需要给定一个本地目录，文件会下载到该目录中
        client.getObject(new GetObjectRequest(bucketName, key), new File(localPath));
    }

    /**
     * put文件
     *
     * @param bucketName 桶名
     * @param key        文件key 路径+文件名
     * @param input      文件
     */
    public PutObjectResult putObject(String bucketName, String key, byte[] input) {
        InputStream content = new ByteArrayInputStream(input);
        ObjectMetadata meta = new ObjectMetadata();
        // 长度是必须的
        meta.setContentLength(input.length);
        return client.putObject(bucketName, key, content, meta);
    }

    /**
     * put文件
     *
     * @param bucketName 桶名
     * @param key        文件key 路径+文件名
     * @param file       文件
     * @return
     */
    public PutObjectResult putObject(String bucketName, String key, File file) {
        ObjectMetadata meta = new ObjectMetadata();
        // 长度是必须的
        meta.setContentLength(file.length());
        return client.putObject(bucketName, key, file, meta);
    }

    /**
     * copy 文件
     *
     * @param srcBucketName  源桶
     * @param srcOssKey      源文件key
     * @param destBucketName 目的桶
     * @param destOssKey     文件key
     * @return 返回copy结果
     */
    public CopyObjectResult move(String srcBucketName, String srcOssKey, String destBucketName, String destOssKey) {
        return client.copyObject(srcBucketName, srcOssKey, destBucketName, destOssKey);
    }

    /**
     * 删除
     *
     * @param bucketName 桶
     * @param key        key
     */
    public void delObject(String bucketName, String key) {
        client.deleteObject(bucketName, key);
    }

    /**
     * 删除
     *
     * @param bucketName 桶
     * @param url        url
     */
    public void delFromUrl(String bucketName, String url) {
        String key = UrlPathUtil.getPathNoStartSeparate(url);
        client.deleteObject(bucketName, key);
    }

    /**
     * 批量删除
     *
     * @param bucketName 桶
     * @param keys       keys
     */
    public List<String> delObjects(String bucketName, List<String> keys) {
        DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        return deleteObjectsResult.getDeletedObjects();
    }

    /**
     * 列举指定桶下的文件(最多100个)
     *
     * @param bucketName 桶
     * @param keyPrefix  指定前缀(非必填)
     * @return 文件list
     */
    public List<String> listObjects(String bucketName, String keyPrefix) {
        ObjectListing objectListing = client.listObjects(bucketName, keyPrefix);
        List<OSSObjectSummary> summaries = objectListing.getObjectSummaries();

        List<String> list = new ArrayList<>();
        for (OSSObjectSummary summary : summaries) {
            list.add(summary.getKey());
        }
        return list;
    }

    public OSSClient getClient() {
        return client;
    }

}
