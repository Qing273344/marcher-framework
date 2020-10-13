package xin.marcher.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.oss.client.feign.OssFeign;
import xin.marcher.oss.client.model.request.MoveReqs;
import xin.marcher.oss.manager.OssManager;

/**
 * oss feign client
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/oss")
public class OssFeignClient implements OssFeign {

    @Autowired
    private OssManager ossManager;

    @Override
    @PostMapping("/move")
    public String move(@RequestBody MoveReqs reqs) {
        return ossManager.move(reqs.getSrcUrl(), reqs.getDirectoryList());
    }
}
