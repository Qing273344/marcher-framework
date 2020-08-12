package xin.marcher.framework.excel.listener;

import com.alibaba.excel.context.AnalysisContext;

import java.util.ArrayList;
import java.util.List;

/**
 * excel 监听器, 用在读取数据
 *
 * @author marcher
 */
public class ListGetListener<T> extends BaseListener<T> {

    private final List<T> list = new ArrayList<>();

    /**
     * 每一条数据解析都会调用
     *
     * @param data      数据
     * @param context   context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * 所有数据解析完成调用
     *
     * @param context   context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //
    }

    public List<T> getData() {
        return list;
    }
}
