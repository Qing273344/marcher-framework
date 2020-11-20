package xin.marcher.oss.client.model.request;

import lombok.Data;

/**
 * 上传请求
 *
 * @author marcher
 */
@Data
public class UploadReqs {

    /**
     * 文件名
     */
    private String fileName;


    /**
     * 文件流
     */
    private byte[] input;
}
