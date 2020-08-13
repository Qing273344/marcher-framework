package xin.marcher.framework.mvc.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import xin.marcher.framework.mvc.PageConstant;
import xin.marcher.framework.util.DataValidationUtil;

import javax.validation.constraints.Min;

/**
 * 分页参数
 *
 * @author marcher
 */
@Data
@NoArgsConstructor
public class PageParam {

    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageCurrent = PageConstant.DEFAULT_CURRENT;

    @Min(value = 1, message = "每页显示最小值为 1")
    private Integer pageSize = PageConstant.DEFAULT_SIZE;

    public PageParam(Integer pageCurrent, Integer pageSize) {
        this.pageCurrent = DataValidationUtil.isNullOrLeZero(pageCurrent) ? PageConstant.DEFAULT_CURRENT : pageCurrent ;
        this.pageSize = DataValidationUtil.isNullOrLeZero(pageSize) ? PageConstant.DEFAULT_SIZE : pageSize;
    }

    public Integer getOffSet() {
        return (this.pageCurrent - 1) * this.pageSize;
    }
}
