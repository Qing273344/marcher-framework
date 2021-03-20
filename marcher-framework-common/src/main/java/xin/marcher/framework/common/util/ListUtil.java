package xin.marcher.framework.common.util;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * list util
 *
 * @author marcher
 */
public class ListUtil {

    public static <T> List<T> empty() {
        return new ArrayList<>();
    }

    public static <T> List<T> singleton(T element) {
        List<T> list = new ArrayList<>();
        list.add(element);
        return list;
    }

    public static List<String> multiple(String... args) {
        return Arrays.asList(args);
    }

    public static <T> String list2Str(List<T> list, String delimiter) {
        if (EmptyUtil.isEmpty(list)) {
            return null;
        }
        return Joiner.on(delimiter).join(list);
    }

    /**
     * 通过指定的分割符进行分割
     * TODO 暂时只支持 int, long, String，有待支持所有类型
     *
     * @param splitStr  需要分割的字符串
     * @param delimiter 分割符
     * @param clazz     分割后的list元素类型
     * @param <T>   <T>
     *
     * @return
     *      List<T>
     */
    public static <T> List<T> splitWithDelimiter(String splitStr, String delimiter, Class<T> clazz) {
        if (EmptyUtil.isEmptyTrim(splitStr)) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>();
        String[] split = splitStr.split(delimiter);
        for (String str : split) {
            str = str.trim();
            try {
                if (clazz.isAssignableFrom(Integer.class)) {
                    list.add((T) Integer.valueOf(str));
                } else if (clazz.isAssignableFrom(Long.class)) {
                    list.add((T) Long.valueOf(str));
                } else if (clazz.isAssignableFrom(Double.class)) {
                    list.add((T) Double.valueOf(str));
                } else if (clazz.isAssignableFrom(Float.class)) {
                    list.add((T) Float.valueOf(str));
                } else if (clazz.isAssignableFrom(Boolean.class)) {
                    list.add((T) Boolean.valueOf(str));
                } else {
                    list.add((T) str);
                }
            } catch (Exception e) {
                // TODO
            }
        }
        return list;
    }

    /**
     * list中是否存在重复元素
     *
     * @param list list
     */
    public static boolean hasSame(List<?> list) {
        if (EmptyUtil.isEmpty(list)) {
            return false;
        }
        return list.size() != new HashSet<>(list).size();
    }

    /**
     * String to Long
     *
     * @param list
     * @return
     */
    public static List<Long> convertToLong(List<String> list) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<Long> longList = new ArrayList<>();
        for (String str : list) {
            longList.add(Long.valueOf(str));
        }
        return longList;
    }

    /**
     * 去除重复
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> distinct(List<T> list) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取第一个元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getFirst(List<T> list) {
        return EmptyUtil.isEmpty(list) ? null : list.get(0);
    }
}
