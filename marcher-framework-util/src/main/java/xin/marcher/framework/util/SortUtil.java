package xin.marcher.framework.util;

import xin.marcher.framework.exception.UtilException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 排序工具类
 * 栗子: Collections.sort(list, new SortUtil("sort", true));
 *
 * @author marcher
 */
public class SortUtil implements Comparator {

    /**
     * 要排序的字段
     */
    private String name;

    /**
     * 是否升序, 默认升序
     */
    private boolean hasUp = true;

    /**
     * string类型转换成何种类型进行比较, 防止排序出现 1,10,11,2,3,4
     */
    private Class<?> stringConvertClz;

    /**
     * @param name  要排序的属性名
     */
    public SortUtil(String name) {
        super();
        this.name = name;
    }

    /**
     * @param name  要排序的属性名
     * @param hasUp 是否升序
     */
    public SortUtil(String name, boolean hasUp) {
        super();
        this.name = name;
        this.hasUp = hasUp;
    }

    /**
     * @param name              要排序的属性名
     * @param hasUp             是否升序
     * @param stringConvertClz  string类型转换成何种类型进行比较
     */
    public SortUtil(String name, Boolean hasUp, Class<?> stringConvertClz) {
        super();
        this.name = name;
        this.hasUp = hasUp;
        this.stringConvertClz = stringConvertClz;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int result = 0;
        // 入参为空|参数类型不相等
        if (o1 == null || o2 == null || !o1.getClass().getName().equals(o2.getClass().getName())) {
            return result;
        }
        // 7大基本类型的处理(boolean除外.且把Collections.reverse()/Collections.sort()的事也做了)
        if (isBaseType(o1)) {
            return baseTypeSort(o1, o2);
        }

        try {
            Field f1 = o1.getClass().getDeclaredField(name);
            Field f2 = o2.getClass().getDeclaredField(name);
            f1.setAccessible(true);
            f2.setAccessible(true);

            result = baseTypeSort(f1.get(o1), f2.get(o2));
        } catch (IllegalAccessException e) {
            throw new UtilException("参数错误: location in SortUtil.sort", e);
        } catch (NoSuchFieldException e) {
            throw new UtilException("类字段不存在: location in SortUtil.sort", e);
        }
        return result;
    }

    private int baseTypeSort(Object o1, Object o2) {
        int result = 0;
        if (o1 instanceof Character) {
            result = ((Character) o1).compareTo(((Character) o2));
        } else if (o1 instanceof Short) {
            result = ((Short) o1).compareTo((Short) o2);
        } else if (o1 instanceof Integer) {
            result = ((Integer) o1).compareTo((Integer) o2);
        } else if (o1 instanceof Float) {
            result = ((Float) o1).compareTo((Float) o2);
        } else if (o1 instanceof Double) {
            result = ((Double) o1).compareTo((Double) o2);
        } else if (o1 instanceof Long) {
            result = ((Long) o1).compareTo((Long) o2);
        } else if(o1 instanceof String) {
            result = baseStringSort(o1, o2);
        }

        if (hasUp) {
            return result;
        }
        return -result;
    }

    private int baseStringSort(Object o1, Object o2) {
        // 若为数值的string类型, 转换为其它类型比较
        if (EmptyUtil.isNotEmpty(stringConvertClz)) {
            if (stringConvertClz.isAssignableFrom(Integer.class)) {
                return DataConverterUtil.convertIntegerOrNull(o1).compareTo(DataConverterUtil.convertIntegerOrNull(o2));
            } else if (stringConvertClz.isAssignableFrom(Float.class)) {
                return DataConverterUtil.convertFloatOrNull(o1).compareTo(DataConverterUtil.convertFloatOrNull(o2));
            } else if (stringConvertClz.isAssignableFrom(Double.class)) {
                return DataConverterUtil.convertDoubleOrNull(o1).compareTo(DataConverterUtil.convertDoubleOrNull(o2));
            } else if (stringConvertClz.isAssignableFrom(Long.class)) {
                return DataConverterUtil.convertLongOrNull(o1).compareTo(DataConverterUtil.convertLongOrNull(o2));
            }
        }
        return o1.toString().compareTo(o2.toString());
    }

    private boolean isBaseType(Object obj) {
        return (obj instanceof String) || (obj instanceof Integer)
                || (obj instanceof Double) || (obj instanceof Float)
                || (obj instanceof Character) || (obj instanceof Short)
                || (obj instanceof Long);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public static <T, U extends Comparable<? super U>> List<T> sort(List<T> list, Function<T, ? extends U> func) {
        return list.stream().sorted(Comparator.comparing(func)).collect(Collectors.toList());
    }

    public static <T, U extends Comparable<? super U>> List<T> sortDesc(List<T> list, Function<T, ? extends U> func) {
        return list.stream().sorted(Comparator.comparing(func).reversed()).collect(Collectors.toList());
    }

    public static <T> List<T> sortCount(List<T> list) {
        Map<T, Long> collectMap = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return collectMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static <T, U extends Comparable<? super U>> List<U> sortCount(List<T> list, Function<T, ? extends U> func) {
        Map<? extends U, Long> collectMap = list.stream().map(func).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return collectMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * @param list1 排序依据的列表
     * @param list2 待排序列表
     * @param fun1  排序依据的列表获取用作key的唯一编码的方法
     * @param fun2  待排序列表获取用作key的唯一编码的方法
     * @param <T>   排序依据的列表的元素的类型
     * @param <A>   待排序列表的元素的类型
     */
    public static <T, A> List<A> sortByList(List<T> list1, List<A> list2, Function<T, Object> fun1, Function<A, Object> fun2) {
        /*
         * 当元素数量小于等于1的时候 不需要排序，直接返回
         */
        if (list1.size() <= 1 || list2.size() <= 1) {
            return list2;
        }
        Map<Object, Integer> sorted = new HashMap<>(list1.size());
        AtomicInteger i = new AtomicInteger(0);
        list1.forEach(o -> sorted.put(fun1.apply(o), i.getAndIncrement()));
        list2.sort((o1, o2) -> {
            Object key1 = fun2.apply(o1);
            Object key2 = fun2.apply(o2);
            Integer order1 = sorted.get(key1);
            Integer order2 = sorted.get(key2);
            if (order1 == null || order2 == null) {
                throw new UtilException("无法匹配输入");
            }
            return order1 > order2 ? 1 : 0;
        });
        return list2;
    }

    /**
     * 排序
     *
     * @param list  list
     * @param field 排序字段(xxx)
     * @param isUp  是否升序
     * @param <T>   泛型
     */
    public static <T> void sort(List<T> list, final String field, final boolean isUp) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T prevObj, T nextObj) {
                int ret = 0;
                try {
                    Field objField = prevObj.getClass().getDeclaredField(field);
                    objField.setAccessible(true);
                    Class<?> type = objField.getType();

                    if (type.isAssignableFrom(Integer.class)) {
                        ret = ((Integer) objField.get(prevObj)).compareTo((Integer) objField.get(nextObj));
                    } else if (type.isAssignableFrom(Float.class)) {
                        ret = ((Float) objField.get(prevObj)).compareTo((Float) objField.get(nextObj));
                    } else if (type.isAssignableFrom(Long.class)) {
                        ret = ((Long) objField.get(prevObj)).compareTo((Long) objField.get(nextObj));
                    } else if (type.isAssignableFrom(Double.class)) {
                        ret = ((Double) objField.get(prevObj)).compareTo((Double) objField.get(nextObj));
                    } else {
                        ret = String.valueOf(objField.get(prevObj)).compareTo(String.valueOf(objField.get(nextObj)));
                    }
                } catch (IllegalArgumentException e) {
                    throw new UtilException("参数错误: location in SortUtil.sort", e);
                } catch (NoSuchFieldException e) {
                    throw new UtilException("类字段不存在: location in SortUtil.sort", e);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isUp) {
                    return ret;
                } else {
                    return -ret;
                }
            }
        });
    }

    /**
     * 排序
     *
     * @param list  list
     * @param field 排序字段(xxx)
     * @param isUp  是否升序 true:升序, false:降序
     * @param <T>   泛型
     */
    public static <T> void sort(List<T> list, final String field, final boolean isUp, final Class<?> clazz) {
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T prevObj, T nextObj) {
                int ret = 0;
                try {
                    Method sortMethod = prevObj.getClass().getMethod(ObjectUtil.getMethodName(field));

                    if (clazz.isAssignableFrom(Integer.class)) {
                        ret = Integer.valueOf(sortMethod.invoke(prevObj).toString().trim()).compareTo(Integer.valueOf(sortMethod.invoke(nextObj).toString().trim()));
                    } else if (clazz.isAssignableFrom(Float.class)) {
                        ret = Float.valueOf(sortMethod.invoke(prevObj).toString().trim()).compareTo(Float.valueOf(sortMethod.invoke(nextObj).toString().trim()));
                    } else if (clazz.isAssignableFrom(Long.class)) {
                        ret = Long.valueOf(sortMethod.invoke(prevObj).toString().trim()).compareTo(Long.valueOf(sortMethod.invoke(nextObj).toString().trim()));
                    } else if (clazz.isAssignableFrom(Double.class)) {
                        ret = Double.valueOf(sortMethod.invoke(prevObj).toString().trim()).compareTo(Double.valueOf(sortMethod.invoke(nextObj).toString().trim()));
                    } else {
                        ret = sortMethod.invoke(prevObj).toString().trim().compareTo(sortMethod.invoke(nextObj).toString().trim());
                    }
                } catch (NoSuchMethodException e) {
                    throw new UtilException("方法不存在: location in SortUtil.sort", e);
                } catch (IllegalAccessException e) {
                    throw new UtilException("没有访问权限: location in SortUtil.sort", e);
                } catch (InvocationTargetException e) {
                    throw new UtilException("目标错误: location in SortUtil.sort", e);
                }

                if (isUp) {
                    return ret;
                } else {
                    return -ret;
                }
            }
        });
    }
}
