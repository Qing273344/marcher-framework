package xin.marcher.oss.client.model.response;

import lombok.Data;

/**
 * oss 签名响应
 *
 * @author marcher
 */
@Data
public class OssSignatureResp {

    private String endpoint;

    private String bucket;

    private String accessKeyId;

    private String policy;

    private String signature;

}
