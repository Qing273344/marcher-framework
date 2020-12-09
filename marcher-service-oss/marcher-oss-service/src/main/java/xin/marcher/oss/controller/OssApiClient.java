package xin.marcher.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.common.tuple.Tuple2;
import xin.marcher.oss.client.api.OssApi;
import xin.marcher.oss.client.model.request.MoveReqs;
import xin.marcher.oss.client.model.request.UploadReqs;
import xin.marcher.oss.client.model.response.OssSignatureResp;
import xin.marcher.oss.manager.OssManager;

/**
 * oss feign client
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/oss", produces = MediaType.APPLICATION_JSON_VALUE)
public class OssApiClient implements OssApi {

    private final OssManager ossManager;
    private final OssProperties ossProperties;

    @Autowired
    public OssApiClient(OssManager ossManager, OssProperties ossProperties) {
        this.ossManager = ossManager;
        this.ossProperties = ossProperties;
    }

    @Override
    @GetMapping("/signature")
    public OssSignatureResp signature() {
        Tuple2<String, String> tuple2 = ossManager.signature();

        OssSignatureResp signatureResp = new OssSignatureResp();
        signatureResp.setEndpoint(ossProperties.getEndPoint());
        signatureResp.setBucket(ossProperties.getTempBucketName());
        signatureResp.setAccessKeyId(ossProperties.getAccessKeyId());
        signatureResp.setPolicy(tuple2.getItem1());
        signatureResp.setSignature(tuple2.getItem2());
        return signatureResp;
    }

    @Override
    @PostMapping("/upload")
    public String upload(@RequestBody UploadReqs reqs) {
        return ossManager.putFile(reqs.getFileName(), reqs.getInput());
    }

    @Override
    @PostMapping("/move")
    public String move(@RequestBody MoveReqs reqs) {
        return ossManager.move(reqs.getSrcUrl(), reqs.getDirectoryList());
    }
}
