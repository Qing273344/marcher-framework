package xin.marcher.framework.common.util;

import java.math.BigDecimal;

/**
 * 数据校验工具类
 *
 * @author marcher
 */
public class DataValidationUtil {

    /** --------------------------------------------------------------------------------------------------------------- isNullOrLeZero */
    public static boolean isNullOrLeZero(Float data) {
        return null == data || data <= 0;
    }

    public static boolean isNullOrLeZero(Integer data) {
        return null == data || data <= 0;
    }

    public static boolean isNullOrLeZero(Long data) {
        return null == data || data <= 0;
    }

    public static boolean isNullOrLeZero(BigDecimal data) {
        return null == data || (data.compareTo(BigDecimal.ZERO) <= 0);
    }

    /** --------------------------------------------------------------------------------------------------------------- isNotNullAndGtZero */
    public static boolean isNotNullAndGtZero(Float data) {
        return null != data && data > 0.0F;
    }

    public static boolean isNotNullAndGtZero(Integer data) {
        return null != data && data > 0;
    }

    public static boolean isNotNullAndGtZero(Long data) {
        return null != data && data > 0;
    }

    public static boolean isNotNullAndGtZero(BigDecimal data) {
        return null != data && (data.compareTo(BigDecimal.ZERO) > 0);
    }

    public static <T> boolean isNotNullAndGtZero(String data, Class<T> clz) {
        if (EmptyUtil.isEmpty(data) || EmptyUtil.isEmpty(clz)) {
            return false;
        }

        if (clz.isAssignableFrom(Integer.class)) {
            return isNotNullAndGtZero(Integer.valueOf(data));
        }
        if (clz.isAssignableFrom(Float.class)) {
            return isNotNullAndGtZero(Float.valueOf(data));
        }
        if (clz.isAssignableFrom(Long.class)) {
            return isNotNullAndGtZero(Long.valueOf(data));
        }

        return true;
    }

    public static boolean isNotNullAndGtZero(Object data) {
        return isNotNullAndGtZero(data, null);
    }

    public static boolean isNotNullAndGtZero(Object data, Class clz) {
        if (EmptyUtil.isEmpty(data)) {
            return false;
        }

        if (data instanceof Float) {
            return isNotNullAndGtZero((Float) data);
        }
        if (data instanceof Integer) {
            return isNotNullAndGtZero((Integer) data);
        }
        if (data instanceof Long) {
            return isNotNullAndGtZero((Long) data);
        }
        if (data instanceof String) {
            return isNotNullAndGtZero(data.toString().trim(), clz);
        }

        return true;
    }

}
