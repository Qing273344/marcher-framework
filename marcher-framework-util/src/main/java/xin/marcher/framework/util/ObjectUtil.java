package xin.marcher.framework.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * object util
 *
 * @author marcher
 */
public class ObjectUtil {

    /**
     * copy属性 - 参考 spring copyProperties
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * get对象名称
     *
     * @param obj 对象
     * @return
     *      对象全限定名
     */
    public static String getObjectName(Object obj) {
        return obj.getClass().getName();
    }

    public static String getMethodName(String field) {
        return "get" + firstLetterToCapture(field);
    }

    private static String firstLetterToCapture(String field) {
        char[] chars = field.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

    /**
     * 对象转 Map
     *
     * @param obj   对象
     * @return
     *      Map
     * @throws Exception    异常
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if(obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            // TODO
        }
        return map;
    }
}
