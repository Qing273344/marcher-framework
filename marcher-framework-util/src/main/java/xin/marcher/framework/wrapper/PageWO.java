package xin.marcher.framework.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据包装
 *
 * @author marcher
 * @param <T>   泛型定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PageWO<T> implements Serializable {

    private static final long serialVersionUID = -2400938350369092797L;

    private List<T> list;

    private Long total;

    public static <T> PageWO<T> rest(List<T> list, Long total) {
        return new PageWO<>(list, total);
    }

    public boolean hashEmpty() {
        return this.list.size() <= 0;
    }
}
