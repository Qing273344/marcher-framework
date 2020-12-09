package xin.marcher.framework.common.mvc.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import xin.marcher.framework.common.mvc.PageConstant;
import xin.marcher.framework.common.util.DataValidationUtil;

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
    private Integer pageNo = PageConstant.DEFAULT_CURRENT;

    @Min(value = 1, message = "每页显示最小值为 1")
    private Integer pageSize = PageConstant.DEFAULT_SIZE;

    public PageParam(Integer pageCurrent, Integer pageSize) {
        this.pageNo = DataValidationUtil.isNullOrLeZero(pageCurrent) ? PageConstant.DEFAULT_CURRENT : pageCurrent ;
        this.pageSize = DataValidationUtil.isNullOrLeZero(pageSize) ? PageConstant.DEFAULT_SIZE : pageSize;
    }

    public Integer getOffSet() {
        return (this.pageNo - 1) * this.pageSize;
    }
}
