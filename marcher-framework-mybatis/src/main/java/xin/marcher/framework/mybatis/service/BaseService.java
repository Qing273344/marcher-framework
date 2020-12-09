package xin.marcher.framework.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.framework.common.mvc.request.PageParam;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.wrapper.PageWO;

/**
 * 自定义基础 service
 *
 * @author marcher
 */
public interface BaseService<T> extends IService<T> {

    /**
     * page data wrapper
     *
     * @param pageParam 分页数据
     * @return
     *      分页数据
     */
    default IPage<T> pageWrapper(PageParam pageParam) {
        return new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
    }

    /**
     * page data wrapper(一条数据)
     *
     * @return
     *      分页数据
     */
    default IPage<T> pageOneWrapper() {
        return new Page<>(1, 1);
    }

    /**
     * 获取分页的第一条数据
     *
     * @param iPage 分页结果
     * @return
     *      分页的第一条数据
     */
    default T getPageOne(IPage<T> iPage) {
        return iPage.getTotal() > 1 ? iPage.getRecords().get(0) : null;
    }

    /**
     * 是否为空
     *
     * @param pageWO   pageWO
     * @return  result
     */
    default boolean isEmpty(PageWO pageWO) {
        return pageWO.getList().size() <= 0;
    }

    /**
     * 返回空
     *
     * @return  result
     */
    default BaseResult pageEmpty() {
        return pageEmpty(0);
    }

    default BaseResult pageEmpty(long total) {
        return BaseResult.success(new PageWO<>(null, total));
    }

}
