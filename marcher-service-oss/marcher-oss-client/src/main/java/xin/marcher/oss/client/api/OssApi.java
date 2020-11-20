package xin.marcher.oss.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.oss.client.model.request.MoveReqs;
import xin.marcher.oss.client.model.request.UploadReqs;
import xin.marcher.oss.client.model.response.OssSignatureResp;

/**
 * oss feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-service-oss", contextId = "oss-api")
@RequestMapping(value = "/rpc/oss")
public interface OssApi {

    @GetMapping("/signature")
    OssSignatureResp signature();


    /**
     * upload
     * @param reqs
     * @return
     */
    @PostMapping("/upload")
    String upload(@RequestBody UploadReqs reqs);

    /**
     * move
     * @param reqs
     * @return
     */
    @PostMapping("/move")
    String move(@RequestBody MoveReqs reqs);


}
