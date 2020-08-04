package xin.marcher.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存分页工具
 *
 * @author marcher
 */
public class RamPageUtil {

    /**
     * 分页
     *
     * @param data      数据集
     * @param pageNum   当前页
     * @param pageSize  每页条数
     * @param <T>       <T>
     *
     * @return
     *      分页后的数据
     */
    public static <T> List<T> page(List<T> data, Integer pageNum, Integer pageSize) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }

        pageNum = pageNum == null || pageNum < 0 ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;

        int from = (pageNum - 1) * pageSize;
        int to = pageNum * pageSize;
        if (to > data.size()) {
            to = data.size();
        }
        if (from >= data.size() || to <= from) {
            return new ArrayList<>();
        }
        return data.subList(from, to);
    }
}
