package xin.marcher.framework.common.util;

import java.util.Arrays;

/**
 * array util
 *
 * @author marcher
 */
public class ArrayUtil {

    private static final Object[] EMPTY = new Object[0];

    public static Object[] empty() {
        return EMPTY;
    }

    /**
     * 向数组内追加元素
     *
     * @param original  原数组
     * @param t         追加的元素
     * @param <T>       <T>
     *
     * @return
     *      新的数组
     */
    public static <T> T[] add(T[] original, T t) {
        int capacity = original.length + 1;
        T[] newArray = Arrays.copyOf(original, capacity);
        newArray[original.length] = t;
        return newArray;
    }

}
