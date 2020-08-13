package xin.marcher.framework.mybatis.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import xin.marcher.framework.util.DataValidationUtil;
import xin.marcher.framework.util.EmptyUtil;

import java.util.Collection;

/**
 * 拓展 MyBatis Plus QueryWrapper 类，主要增加如下功能：
 * <p>
 * 1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @param <T> 数据类型
 * @author YunaiV
 */
public class BaseQueryWrapper<T> extends QueryWrapper<T> {

    public BaseQueryWrapper<T> likeIfPresent(SFunction<T, ?> func, Object value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().like(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> eqIfPresent(SFunction<T, ?> func, Object value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().eq(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> eqIfPresentWithGtZero(SFunction<T, ?> func, Number value) {
        if (DataValidationUtil.isNotNullAndGtZero(value)) {
            this.lambda().eq(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> neIfPresent(SFunction<T, ?> func, Object value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().ne(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> gtIfPresent(SFunction<T, ?> func, Number value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().gt(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> gtIfPresentWithGtZero(SFunction<T, ?> func, Number value) {
        if (DataValidationUtil.isNotNullAndGtZero(value)) {
            this.lambda().gt(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> geIfPresent(SFunction<T, ?> func, Number value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().ge(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> geIfPresentWithGtZero(SFunction<T, ?> func, Number value) {
        if (DataValidationUtil.isNotNullAndGtZero(value)) {
            this.lambda().ge(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> ltIfPresent(SFunction<T, ?> func, Number value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().lt(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> ltIfPresentWithGtZero(SFunction<T, ?> func, Number value) {
        if (DataValidationUtil.isNotNullAndGtZero(value)) {
            this.lambda().lt(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> leIfPresent(SFunction<T, ?> func, Number value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().le(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> leIfPresentWithGtZero(SFunction<T, ?> func, Number value) {
        if (DataValidationUtil.isNotNullAndGtZero(value)) {
            this.lambda().le(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> inIfPresent(SFunction<T, ?> func, Collection<?> value) {
        if (EmptyUtil.isNotEmpty(value)) {
            this.lambda().in(func, value);
        }
        return this;
    }

    public BaseQueryWrapper<T> orderByCondition(String applySql) {
        this.doIt(true, SqlKeyword.ORDER_BY, () -> this.columnToString(applySql));
        return this;
    }
}
