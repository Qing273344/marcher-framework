package xin.marcher.framework.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.oss.OssProvider;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.oss.service.OssService;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.FileUtil;
import xin.marcher.framework.util.UrlPathUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 阿里云存储
 *
 * @author marcher
 */
public class OssServiceImpl implements OssService {

    private OssProvider ossProvider;

    public OssServiceImpl setOssManager(OssProperties config, boolean isExternal) {
        this.ossProvider = OssProvider.getInstance(config, isExternal);
        return this;
    }


    /**
     * 策略
     * @param expiration    过期时间
     * @param conds
     * @return
     */
    @Override
    public String generatePostPolicy(Date expiration, PolicyConditions conds) {
        return this.ossProvider.generatePostPolicy(expiration, conds);
    }

    /**
     * 生成签名
     * @param postPolicy
     * @return
     */
    @Override
    public String calculatePostSignature(String postPolicy) {
        return this.ossProvider.calculatePostSignature(postPolicy);
    }


    /**
     * 上传文件
     *
     * @param bucketName        桶名
     * @param key               key
     * @param input             文件
     */
    @Override
    public String putObject(String bucketName, String key, byte[] input) {
        PutObjectResult putObjectResult;
        try {
            putObjectResult = this.ossProvider.putObject(bucketName, key, input);
        } catch (Exception e) {
            throw new UtilException("marcher-framework-oss aliyun sdk oss put object(byte) error!", e);
        }
        if (EmptyUtil.isEmpty(putObjectResult)) {
            return null;
        }
        return key;
    }

    /**
     * 上传文件
     *
     * @param bucketName        桶名
     * @param input             文件
     * @param fileName          文件名
     * @param directorys        目录参数
     */
    @Override
    public String putObject(String bucketName, byte[] input, String fileName, boolean isReplace, String... directorys) {
        if (isReplace) {
            fileName = FileUtil.replaceFileName(fileName);
        }
        String basePath = FileUtil.createBasePath(directorys);
        String key = FileUtil.concat(basePath, fileName);
        return putObject(bucketName, key, input);
    }

    @Override
    public String putObject(String bucketName, String key, File file) {
        PutObjectResult putObjectResult;
        try {
            putObjectResult = this.ossProvider.putObject(bucketName, key, file);
        } catch (Exception e) {
            throw new UtilException("marcher-framework-oss aliyun sdk oss put object(file) error!", e);
        }
        if (EmptyUtil.isEmpty(putObjectResult)) {
            return null;
        }
        return key;
    }

    /**
     * copy oss
     *
     * @param srcBucketName     源桶
     * @param srcFileUrl        源文件url
     * @param destBucketName    正式桶
     * @param directoryList     目录参数
     * @return
     *      返回正式文件key
     */
    @Override
    public String move(String srcBucketName, String srcFileUrl, String destBucketName, List<String> directoryList){
        String srcOssKey = UrlPathUtil.getPathNoStartSeparate(srcFileUrl);
        String destOssKey = UrlPathUtil.createPath(srcFileUrl, directoryList);
        if (EmptyUtil.isEmpty(srcOssKey) || EmptyUtil.isEmpty(destOssKey)){
            return "";
        }

        try {
            this.ossProvider.move(srcBucketName, srcOssKey, destBucketName, destOssKey);
        } catch (OSSException | ClientException e) {
            throw new UtilException("marcher-framework-oss aliyun sdk oss copy object error!", e);
        }
        return destOssKey;
    }

    /**
     * 删除
     *
     * @param bucketName    桶
     * @param key           key
     */
    @Override
    public void delObject(String bucketName, String key) {
        this.ossProvider.delObject(bucketName, key);
    }

    @Override
    public void delFromUrl(String bucketName, String url) {
        this.ossProvider.delFromUrl(bucketName, url);
    }

    /**
     * 批量删除
     *
     * @param bucketName    桶
     * @param keys          keys
     */
    @Override
    public List<String> delObjects(String bucketName, List<String> keys) {
        return this.ossProvider.delObjects(bucketName, keys);
    }

    /**
     * 列举指定桶下的文件(最多100个)
     *
     * @param bucketName    桶
     * @param keyPrefix     指定前缀(非必填)
     * @return
     *      文件list
     */
    @Override
    public List<String> listObjects(String bucketName, String keyPrefix) {
        return this.ossProvider.listObjects(bucketName, keyPrefix);
    }
}
