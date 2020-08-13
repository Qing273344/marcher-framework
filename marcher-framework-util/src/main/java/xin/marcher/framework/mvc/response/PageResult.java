package xin.marcher.framework.mvc.response;

import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mvc.PageConstant;
import xin.marcher.framework.mvc.request.PageParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * page result
 *
 * @author marcher
 *
 * @param <T>
 */
@Data
@ToString
public  class PageResult<T> implements Serializable {

    private List<T> list;

    private Integer totalRow;

    private Integer totalPage;

    private Integer pageCurrent;

    private Integer pageSize;

    private Boolean firstPage;

    private Boolean lastPage;

    public PageResult() {
        this(PageConstant.DEFAULT_ROW, new PageParam());
    }

    public PageResult(Integer totalRow, Integer pageCurrent, Integer pageSize) {
        this(totalRow, new PageParam(pageCurrent, pageSize));
    }

    public PageResult(List<T> list, Integer totalRow, PageParam pageParam) {
        this(totalRow, pageParam);
        this.list = list;
    }

    public PageResult(Integer totalRow, PageParam pageParam) {
        super();
        this.list = new ArrayList<>();
        this.pageCurrent = pageParam.getPageCurrent();
        this.pageSize = pageParam.getPageSize();
        this.totalRow = totalRow;
        this.totalPage = (int) Math.ceil((double) this.totalRow / this.pageSize);
        this.firstPage = this.pageCurrent == PageConstant.DEFAULT_CURRENT;
        this.lastPage = this.pageCurrent >= this.totalPage;
    }

}
