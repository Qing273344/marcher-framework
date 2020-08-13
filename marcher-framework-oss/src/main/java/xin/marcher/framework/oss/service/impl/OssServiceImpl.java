package xin.marcher.framework.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.oss.manager.OssManager;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.oss.service.OssService;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.FileUtil;
import xin.marcher.framework.util.UrlPathUtil;

import java.io.File;
import java.util.List;

/**
 * 阿里云存储
 *
 * @author marcher
 */
public class OssServiceImpl implements OssService {

    private OssManager ossManager;

    public OssServiceImpl setOssManager(OssProperties config, boolean isExternal) {
        this.ossManager = OssManager.getInstance(config, isExternal);
        return this;
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
            putObjectResult = this.ossManager.putObject(bucketName, key, input);
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
            putObjectResult = this.ossManager.putObject(bucketName, key, file);
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
     * @param directorys        目录参数
     * @return
     *      返回正式文件key
     */
    @Override
    public String copyOssObject(String srcBucketName, String srcFileUrl, String destBucketName, String... directorys){
        String srcOssKey = UrlPathUtil.getPathNoStartSeparate(srcFileUrl);
        String destOssKey = UrlPathUtil.createPath(srcFileUrl, directorys);
        if (EmptyUtil.isEmpty(srcOssKey) || EmptyUtil.isEmpty(destOssKey)){
            return "";
        }

        try {
            this.ossManager.copyObject(srcBucketName, srcOssKey, destBucketName, destOssKey);
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
        this.ossManager.delObject(bucketName, key);
    }

    @Override
    public void delFromUrl(String bucketName, String url) {
        this.ossManager.delFromUrl(bucketName, url);
    }

    /**
     * 批量删除
     *
     * @param bucketName    桶
     * @param keys          keys
     */
    @Override
    public List<String> delObjects(String bucketName, List<String> keys) {
        return this.ossManager.delObjects(bucketName, keys);
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
        return this.ossManager.listObjects(bucketName, keyPrefix);
    }
}