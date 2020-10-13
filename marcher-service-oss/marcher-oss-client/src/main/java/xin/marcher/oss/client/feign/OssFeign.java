package xin.marcher.oss.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.oss.client.model.request.MoveReqs;

/**
 * oss feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-oss-client", contextId = "oss-feign")
@RequestMapping(value = "/oss")
public interface OssFeign {

    @PostMapping("/move")
    String move(@RequestBody MoveReqs reqs);


}
