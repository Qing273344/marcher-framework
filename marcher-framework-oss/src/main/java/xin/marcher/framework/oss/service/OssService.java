package xin.marcher.framework.oss.service;

import java.io.File;
import java.util.List;

/**
 * 阿里云oss相关
 *
 * @author marcher
 */
public interface OssService {

    /**
     * 上传文件
     *
     * @param bucketName 桶名
     * @param key        key
     * @param input      文件
     * @return
     *      文件key
     */
    String putObject(String bucketName, String key, byte[] input);

    /**
     * 上传文件
     *
     * @param bucketName    桶名
     * @param input         文件
     * @param fileName      文件名
     * @param isReplace     是否重置文件名
     * @param directorys    目录参数
     * @return
     *      文件key
     */
    String putObject(String bucketName, byte[] input, String fileName, boolean isReplace, String... directorys);

    /**
     * 上传文件
     *
     * @param bucketName    同名
     * @param key           key
     * @param file          文件
     * @return
     * @return
     *      文件key
     */
    String putObject(String bucketName, String key, File file) ;

    /**
     * copy oss
     *
     * @param srcBucketName  源桶
     * @param srcFileUrl     源文件url
     * @param destBucketName 正式桶
     * @param directorys     新文件目录参数
     * @return 返回新文件key
     */
    String copyOssObject(String srcBucketName, String srcFileUrl, String destBucketName, String... directorys);

    /**
     * 删除
     *
     * @param bucketName 桶
     * @param key        key
     */
    void delObject(String bucketName, String key);

    /**
     * 删除
     *
     * @param bucketName 桶
     * @param url        yrl
     */
    void delFromUrl(String bucketName, String url);

    /**
     * 批量删除
     *
     * @param bucketName 桶
     * @param keys       keys
     */
    List<String> delObjects(String bucketName, List<String> keys);

    /**
     * 列举指定桶下的文件(文档说最多100个)
     *
     * @param bucketName 桶
     * @param keyPrefix  指定前缀(非必填)
     * @return
     *      文件keys
     */
    List<String> listObjects(String bucketName, String keyPrefix);
}
