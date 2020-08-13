package xin.marcher.framework.mybatis.page;

import java.io.Serializable;
import java.util.List;

/**
 * dao查询数据包装
 *
 * @author marcher
 * @param <T>   泛型定义
 */
public final class PageWrapper<T> implements Serializable {

    private List<T> list;

    private Integer total;

    public List<T> getList() {
        return list;
    }

    public PageWrapper<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public PageWrapper<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public boolean hashEmpty() {
        return this.list.size() <= 0;
    }
}
