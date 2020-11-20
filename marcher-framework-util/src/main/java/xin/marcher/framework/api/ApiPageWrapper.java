package xin.marcher.framework.api;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据 Wrapper
 *
 * @author marcher
 */
public class ApiPageWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> list;

    private Integer total;

    public List<T> getList() {
        return list;
    }

    public ApiPageWrapper<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public ApiPageWrapper<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public boolean hashEmpty() {
        return this.list.size() <= 0;
    }
}
