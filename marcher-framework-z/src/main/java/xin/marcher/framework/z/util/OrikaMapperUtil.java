package xin.marcher.framework.z.util;

import cn.hutool.core.exceptions.UtilException;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import xin.marcher.framework.z.SL;
import xin.marcher.framework.z.SS;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Orika工具类
 *
 * @author marcher
 **/
public enum OrikaMapperUtil {

    /**
     * 实例
     */
    INSTANCE;

    /**
     * 默认字段实例
     */
    private final MapperFacade MAPPER_FACADE;

    static class BaseMapper {
        final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
    }

    OrikaMapperUtil() {
        //用于String转Date,不注册转换格式会出现异常
        BaseMapper.MAPPER_FACTORY.getConverterFactory().registerConverter(new DateToStringConverter("yyyy-MM-dd HH:mm:ss"));
        MAPPER_FACADE = BaseMapper.MAPPER_FACTORY.getMapperFacade();
    }


    /**
     * 默认字段实例集合
     */
    private static final Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap<>();


    /**
     * 映射实体（默认字段）
     *
     * @param data    数据（对象）
     * @param toClass 映射类对象
     * @return 映射类对象
     */
    public <T,E> E map(T data,Class<E> toClass) {
        return MAPPER_FACADE.map(data, toClass);
    }

    /**
     * 映射实体（默认字段）
     *
     * @param sourceObject    源对象
     * @param destinationObject 映射对象
     * @return 映射类对象
     */
    public <S, D> void map(S sourceObject, D destinationObject) {
        MAPPER_FACADE.map(sourceObject, destinationObject);
    }

    /**
     * 映射实体（自定义配置）
     *
     * @param data      数据（对象）
     * @param toClass   映射类对象
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    public <T,E> E map(T data,Class<E> toClass, Map<String, String> configMap) {
        MapperFacade mapperFacade = this.getMapperFacade(toClass, data.getClass(), configMap);
        return mapperFacade.map(data, toClass);
    }

    /**
     * 映射集合（默认字段）
     *
     * @param data    数据（集合）
     * @param toClass 映射类对象
     * @return 映射类对象
     */
    public <T,E> List<E> mapAsList(Collection<T> data,Class<E> toClass) {
        return MAPPER_FACADE.mapAsList(data, toClass);
    }


    /**
     * 映射集合（自定义配置）
     *
     * @param toClass   映射类
     * @param data      数据（集合）
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    public <T,E> List<E> mapAsList(Collection<T> data,Class<E> toClass, Map<String, String> configMap) {
        T t = data.stream().findFirst().orElseThrow(() -> new UtilException("映射集合，数据集合为空"));
        MapperFacade mapperFacade = this.getMapperFacade(toClass, t.getClass(), configMap);
        return mapperFacade.mapAsList(data, toClass);
    }

    /**
     * 获取自定义映射
     *
     * @param toClass   映射类
     * @param dataClass 数据映射类
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    private <T,E> MapperFacade getMapperFacade(Class<T> dataClass, Class<E> toClass,  Map<String, String> configMap) {
        String mapKey = dataClass.getCanonicalName() + "_" + toClass.getCanonicalName();
        MapperFacade mapperFacade = CACHE_MAPPER_FACADE_MAP.get(mapKey);
        if (Objects.isNull(mapperFacade)) {
            MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
            ClassMapBuilder classMapBuilder = mapperFactory.classMap(dataClass, toClass);
            configMap.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
            mapperFacade = mapperFactory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
        }
        return mapperFacade;
    }

    public static void main(String[] args) {
        SL sl = new SL();
        sl.setTime(new Date());
        sl.setName("ss");
        sl.setNn(1600010648124L);

        SS ss = OrikaMapperUtil.INSTANCE.map(sl, SS.class);
        System.out.println(ss);
    }

}