package xin.marcher.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.util.DataValidationUtil;
import xin.marcher.framework.util.EmptyUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作工具类
 * 使用该工具类的 JavaBean 必须实现 Serializable 接口
 * 注: 一定要设置过期时间, 一定要设置过期时间, 一定要设置过期时间
 *
 * @author marcher
 */
@Component
public final class RedisService {

    /** 不设置过期时间 */
    public final long NOT_EXPIRE = -1;

    private static final TimeUnit timeUnit = TimeUnit.HOURS;
    private static final TimeUnit timeUnitDay = TimeUnit.DAYS;
    private static final TimeUnit timeUnitMinute = TimeUnit.MINUTES;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    /**
     * 操作 value, 适用于 String Object
     *
     * @return  ValueOperations
     */
    public ValueOperations<String, Object> opsValue() {
        return redisTemplate.opsForValue();
    }

    /**
     * 缓存字符串
     *
     * @param key    key
     * @param value  数据
     * @param expire 过期时间,为null则不设置
     */
    public void setStr(String key, String value, Long expire) {
        opsValue().set(key, value);
        setExpire(key, expire);
    }

    /**
     * 获取缓存字符串数据
     *
     * @param key key
     * @return 缓存字符串数据
     */
    public String getStr(String key) {
        if (!hasKey(key)) {
            return "";
        }
        return String.valueOf(opsValue().get(key));
    }


    public void setObj(String key, Object data, Long expire) {
        opsValue().set(key, data);
        setExpire(key, expire);
    }

    public <T> T getObj(String key, Class<T> clazz) {
        if (!hasKey(key)) {
            return null;
        }
        Object data = opsValue().get(key);
        return cast(data, clazz);
    }


    public ListOperations<String, Object> opsList() {
        return redisTemplate.opsForList();
    }

    public <T> void setList(String key, List<T> list, Long expire) {
        ListOperations<String, Object> opsForList = opsList();
        opsForList.rightPushAll(key, list);
        for (T obj : list) {
            opsForList.rightPush(key, obj);
        }
        setExpire(key, expire);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (!hasKey(key)) {
            return list;
        }

        ListOperations<String, Object> opsForList = opsList();
        Long size = opsForList.size(key);
        if (DataValidationUtil.isNullOrLeZero(size)) {
            return list;
        }

        for (int i = 0; i < size; i++) {
            Object data = opsForList.index(key, i);
            list.add(cast(data, clazz));
        }
        return list;
    }


    public SetOperations<String, Object> opsSet() {
        return redisTemplate.opsForSet();
    }

    public <T> void setSet(String key, Set<T> set, Long expire) {
        opsSet().add(key, set);
        setExpire(key, expire);
    }

    public <T> Set<T> getSet(String key, Class<T> clazz) {
        Set<T> set = new HashSet<>();
        if (!hasKey(key)) {
            return set;
        }
        Set<Object> cacheSet = opsSet().members(key);
        if (null == cacheSet) {
            return set;
        }

        for (Object data : cacheSet) {
            set.add(cast(data, clazz));
        }
        return set;
    }


    public <V> HashOperations<String, String, V> opsHash() {
        return redisTemplate.opsForHash();
    }

    public <V> void setMap(String key, Map<String, V> map, Long expire) {
        opsHash().putAll(key, map);
        setExpire(key, expire);
    }

    public <V> Map<String, V> getMap(String key) {
        if (!hasKey(key)) {
            return Collections.emptyMap();
        }
        HashOperations<String, String, V> opsForHash = opsHash();
        return opsForHash.entries(key);
    }

    /**
     * 根据前缀清除缓存
     *
     * @param prefix 前缀
     */
    public void deleteByPrex(String prefix) {
        if (EmptyUtil.isNotEmpty(prefix)) {
            Set<String> keys = redisTemplate.keys(prefix + "*");
            if (EmptyUtil.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }
    }

    /**
     * 根据 key 清除缓存
     *
     * @param key key
     */
    public void deleteByKey(String key) {
        if (EmptyUtil.isNotEmpty(key)) {
            redisTemplate.delete(key);
        }
    }

    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new UtilException("递增因子必须大于 0");
        }
        return opsValue().increment(key, delta);
    }

    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new UtilException("递减因子必须大于 0");
        }
        return opsValue().increment(key, -delta);
    }

    public boolean hasKey(String key) {
        Boolean isExists = redisTemplate.hasKey(key);
        return isExists != null && isExists;
    }

    /**
     * set缓存过期时间
     *
     * @param key    key
     * @param expire 过期时间(秒)
     */
    private void setExpire(String key, long expire) {
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    private long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 数据转换
     *
     * @param data  数据
     * @param clazz 数据class
     * @param <T>   泛型
     * @return 转换后的数据
     */
    private <T> T cast(Object data, Class<T> clazz) {
        if (EmptyUtil.isNotEmpty(data)) {
            if (clazz.isInstance(data)) {
                return clazz.cast(data);
            }
        }
        return null;
    }

}
