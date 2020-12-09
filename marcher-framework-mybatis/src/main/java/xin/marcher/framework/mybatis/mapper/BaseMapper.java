package xin.marcher.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import xin.marcher.framework.common.mvc.request.PageParam;
import xin.marcher.framework.common.wrapper.PageWO;

import java.util.Collection;

/**
 * 拓展 MyBatis Plus BaseMapper类
 *
 * @author marcher
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param collection 批量插入数据
     * @return int
     */
    int insertByBatch(@Param("collection") Collection<T> collection);

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return 实体
     */
    default T selectLimitOne(QueryWrapper<T> queryWrapper) {
        queryWrapper.last("LIMIT 1");

        return selectOne(queryWrapper);
    }

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
        return iPage.getTotal() >= 1 ? iPage.getRecords().get(0) : null;
    }

    /**
     * 分页数据包装
     *
     * @param iPage mybatis plus分页数据
     * @return
     *      自定义分页数据封装
     */
    default PageWO<T> convert(IPage<T> iPage) {
        return new PageWO<>(iPage.getRecords(), iPage.getTotal());
    }
}
