package xin.marcher.framework.mvc.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.framework.common.constants.GlobalConstant;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.ObjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 处理 Filter 抛出的异常
 *
 * @author marcher
 */
@RestController
public class FilterController extends BasicErrorController {

    public FilterController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));

        BaseResult<Object> error = BaseResult.error(getBusinessStatus(request), String.valueOf(body.get("message")));
        Map<String, Object> map = ObjectUtil.objectToMap(error);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 重写获取状态, 默认的会根据系统返回状态码, 此处加上自定义状态码逻辑
     * @param request
     * @return
     */
    public Integer getBusinessStatus(HttpServletRequest request) {
        Object status = request.getAttribute(GlobalConstant.BUSINESS_STATUS);
        if (EmptyUtil.isEmpty(status)) {
            return super.getStatus(request).value();
        }
        return (Integer) status;
    }
}
