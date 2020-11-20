package xin.marcher.framework.mvc.response;

import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mvc.PageConstant;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.wrapper.PageWO;

import java.io.Serializable;
import java.util.Collections;
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

    private Long totalRow;

    private Integer pageCurrent;

    private Integer pageSize;

    private PageResult() {
        this(PageConstant.DEFAULT_TOTAL, new PageParam());
    }

    private PageResult(Long totalRow, Integer pageCurrent, Integer pageSize) {
        this(totalRow, new PageParam(pageCurrent, pageSize));
    }

    private PageResult(Long totalRow, PageParam pageParam) {
        this(Collections.emptyList(), totalRow, pageParam);
    }

    private PageResult(List<T> list, Long totalRow, PageParam pageParam) {
        this.list = list;
        this.pageCurrent = pageParam.getPageNo();
        this.pageSize = pageParam.getPageSize();
        this.totalRow = totalRow;
    }

    public static <T> PageResult<T> rest() {
        return rest(PageConstant.DEFAULT_TOTAL, new PageParam());
    }

    public static <T> PageResult<T> rest(Long totalRow, Integer pageCurrent, Integer pageSize) {
        return rest(totalRow, new PageParam(pageCurrent, pageSize));
    }

    public static <T> PageResult<T> rest(Long totalRow, PageParam pageParam) {
        return rest(Collections.emptyList(), totalRow, pageParam);
    }

    public static <T> PageResult<T> rest(List<T> list, Integer totalRow, PageParam pageParam) {
        return rest(list, (long) totalRow, pageParam);
    }

    public static <T> PageResult<T> rest(PageWO<T> pageWo, PageParam pageParam) {
        return rest(pageWo.getList(), pageWo.getTotal(), pageParam);
    }

    public static <T> PageResult<T> rest(List<T> list, Long totalRow, PageParam pageParam) {
        return new PageResult<>(list, totalRow, pageParam);
    }

}
