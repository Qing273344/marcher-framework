package xin.marcher.framework.util;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author marcher
 */
public class CollectionUtil {

    public static <T> Set<T> asSet(T... objs) {
        return new HashSet<>(Arrays.asList(objs));
    }

    public static <T> List<T> asList(T... objs) {
        return Arrays.asList(objs);
    }

    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertListWipeNull(List<T> from, Function<T, U> func) {
        return from.stream().filter(EmptyUtil::isNotEmpty).map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertListDistinct(List<T> from, Function<T, U> func) {
        return from.stream().filter(EmptyUtil::isNotEmpty).map(func).distinct().collect(Collectors.toList());
    }

    public static <T, U> Set<U> convertSet(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toSet());
    }

    public static <T, U> Set<U> convertSetWipeNull(List<T> from, Function<T, U> func) {
        return from.stream().filter(EmptyUtil::isNotEmpty).map(func).collect(Collectors.toSet());
    }

    public static <T, K, V> Map<K, V> convertMap(List<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc));
    }

    public static <T, K> Map<K, T> convertMap(List<T> from, Function<T, K> keyFunc) {
        return from.stream().collect(Collectors.toMap(keyFunc, item -> item));
    }

    /**
     * 是否存在交集
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return CollectionUtils.containsAny(source, candidates);
    }

    /**
     * 返回 a+b 的新 List.
     *
     * @param <T> the type parameter
     * @param a   the a
     * @param b   the b
     *
     * @return the list
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回 a+b 的新 List, 去除重复.
     *
     * @param <T> the type parameter
     * @param a   the a
     * @param b   the b
     *
     * @return the list
     */
    public static <T> List<T> unionAndDistinct(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<>(a);
        result.addAll(b);
        return ListUtil.distinct(result);
    }

    /**
     * 返回 a-b 的新List.
     *
     * @param <T> the type parameter
     * @param a   the a
     * @param b   the b
     *
     * @return the list
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<>(a);
        for (T element : b) {
            list.remove(element);
        }

        return list;
    }

    /**
     * 返回 a 与 b 的交集的新 List.
     *
     * @param <T> the type parameter
     * @param a   the a
     * @param b   the b
     *
     * @return the list
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }
}
