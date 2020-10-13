package xin.marcher.framework.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * map util
 *
 * @author marcher
 */
public class MapUtil {

    public static <K, T> Map<K, T> list2Map(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (k1, k2) -> k2));
    }

    public static <K, T> Map<K, T> list2MapPre(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (k1, k2) -> k1));
    }

    public static <T, K, U> Map<K, U> list2Map(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (k1, k2) -> k2));
    }

    public static <T, K, U> Map<K, U> list2MapPre(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (k1, k2) -> k1));
    }

    public static <K, T> Map<K, T> list2LinkedMap(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), (k1, k2) -> k2, LinkedHashMap::new));
    }

    public static <K, T> Map<K, List<T>> list2MapList(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }

    public static <T, K, U> Map<K, List<U>> list2MapList(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }

        return list.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    public static <K, T> Map<K, List<T>> list2TreeMapList(List<T> list, Function<? super T, ? extends K> keyMapper) {
        if (EmptyUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper, TreeMap::new, Collectors.toList()));
    }

    /**
     * 合并两个map, 存在相同的key替换
     */
    public static <K, T> Map<K, T> merge(Map<K, ? extends T> map1, Map<K, ? extends T> map2) {
        return Stream.of(map1, map2).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
    }

    /**
     * 合并两个map, 存在相同的key不替换
     */
    public static <K, T> Map<K, T> mergeNoReplace(Map<K, ? extends T> map1, Map<K, ? extends T> map2) {
        return Stream.of(map1, map2).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }

    /**
     * 获取 key
     *
     * @param map   map
     * @param <T>   T
     * @return  result
     */
    public static <T> List<String> keys(Map<String, T> map) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    /***
     * 获取 value
     *
     * @param map   map
     * @param <K>   K
     * @param <V>   V
     * @return  result
     */
    public static <K, V> List<V> values(Map<K, V> map) {
        List<V> list = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
}
