package xin.marcher.framework.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * base listener
 *
 * @author marcher
 */
public class BaseListener<T> extends AnalysisEventListener<T> {

    @Override
    public void invoke(T data, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public void validate(T data) {

    }
}
