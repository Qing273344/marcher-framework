package xin.marcher.framework.util;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;

/**
 * data converter util
 * 注: 例如return data == null || EmptyUtil.isEmptyTrim(data) ? identity : new Float(data.toString().trim()); 涉及到底层的拆箱和装箱, 会出现空指针.
 *
 * @author marcher
 */
public class DataConverterUtil {

    public static Float convertFloatOrNull(Object data) {
        return convertFloat(null, data);
    }

    public static Float convertFloatOrZero(Object data) {
        return convertFloat(0F, data);
    }

    public static Float convertFloat(Float identity, Object data) {
        if (data == null || EmptyUtil.isEmptyTrim(data)) {
            return identity;
        } else {
            return new Float(data.toString().trim());
        }
    }


    public static BigDecimal convertBigDecimalOrNull(Object data) {
        return convertBigDecimal(null, data);
    }

    public static BigDecimal convertBigDecimalOrZero(Object data) {
        return convertBigDecimal(BigDecimal.ZERO, data);
    }

    private static BigDecimal convertBigDecimal(BigDecimal identity, Object data) {
        if (data == null || EmptyUtil.isEmptyTrim(data)) {
            return identity;
        } else {
            return new BigDecimal(data.toString().trim());
        }
    }


    public static Integer convertIntegerOrNull(Object data) {
        return convertInteger(null, data);
    }

    public static Integer convertIntegerOrZero(Object data) {
        return convertInteger(0, data);
    }

    public static Integer convertInteger(Integer identity, Object data) {
        if (data == null || EmptyUtil.isEmptyTrim(data)) {
            return identity;
        } else {
            return Integer.parseInt(data.toString().trim());
        }
    }


    public static Long convertLongOrNull(Object data) {
        return convertLong(null, data);
    }

    public static Long convertLongOrZero(Object data) {
        return convertLong(0L, data);
    }

    public static Long convertLong(Long identity, Object data) {
        if (data == null || EmptyUtil.isEmptyTrim(data)) {
            return identity;
        } else {
            return Long.parseLong(data.toString().trim());
        }
    }


    public static Double convertDoubleOrNull(Object data) {
        return convertDouble(null, data);
    }

    public static Double convertDoubleOrZero(Object data) {
        return convertDouble(0D, data);
    }

    public static Double convertDouble(Double identity, Object data) {
        if (data == null || EmptyUtil.isEmptyTrim(data)) {
            return identity;
        } else {
            return Double.parseDouble(data.toString().trim());
        }
    }


    public static String convertStringOrNull(Object data) {
        return convertString(null, data);
    }

    public static String convertStringOrEmpty(Object data) {
        return convertString("", data);
    }

    public static String convertString(String identity, Object data) {
        return data == null || "".equals(data.toString().trim()) ? identity : data.toString().trim();
    }


    public static JSONArray convertJsonArrayOrNull(Object data) {
        return data == null ? null : (JSONArray) data;
    }

    /**
     * Float类型数据处理
     *
     * @param data	数据
     * @return
     *      data为null或<=0时返回0f, 反之返回data本身
     */
    public static Float convertGeFloatOrZero(Float data) {
        return DataValidationUtil.isNullOrLeZero(data) ? Float.valueOf(0F) : data;
    }

    public static <T> T convert(Object data, Class<T> clz) {
        if (EmptyUtil.isEmpty(data) || EmptyUtil.isEmpty(clz)) {
            return null;
        }

        if (clz.isAssignableFrom(Integer.class)) {
            return (T) convertIntegerOrNull(data);
        }
        if (clz.isAssignableFrom(Float.class)) {
            return (T) convertFloatOrNull(data);
        }
        if (clz.isAssignableFrom(Long.class)) {
            return (T) convertLongOrNull(data);
        }
        return null;
    }


}
