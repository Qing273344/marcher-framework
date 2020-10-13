package xin.marcher.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.oss.manager.OssManager;

import javax.validation.constraints.NotNull;

/**
 * 上传
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private OssManager ossManager;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件url
     */
    @PostMapping("/file")
    public BaseResult<String> upload(@NotNull(message = "上传文件不能为空") @RequestParam("file") MultipartFile file) {
        String fileUlr = ossManager.putFile(file);
        return BaseResult.success(fileUlr);
    }

}
