package xin.marcher.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mybatis.page.PageWrapper;

/**
 * 拓展 MyBatis Plus BaseMapper类
 *
 * @author marcher
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * page data wrapper
     *
     * @param pageParam 分页数据
     * @return
     *      分页数据
     */
    default IPage<T> pageWrapper(PageParam pageParam) {
        return new Page<>(pageParam.getPageCurrent(), pageParam.getPageSize());
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
        return iPage.getTotal() >= 1 ? iPage.getRecords().get(0) : null;
    }

    /**
     * 分页数据包装
     *
     * @param iPage mybatis plus分页数据
     * @return
     *      自定义分页数据封装
     */
    default PageWrapper<T> convert(IPage<T> iPage) {
        PageWrapper<T> pageWrapper = new PageWrapper<>();
        return pageWrapper.setList(iPage.getRecords())
                .setTotal((int) iPage.getTotal());
    }
}
