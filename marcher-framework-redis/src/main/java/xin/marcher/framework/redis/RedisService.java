package xin.marcher.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xin.marcher.framework.exception.UtilException;
import xin.marcher.framework.util.DataValidationUtil;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ListUtil;

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


    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // --------------------------------------------------------------------------------------------------------- common

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean hasKey = redisTemplate.hasKey(key);
            return hasKey != null && hasKey;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * set 缓存过期时间
     *
     * @param key    key
     * @param expire 过期时间(秒)
     */
    public void setExpire(String key, long expire) {
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key   key, 不能为null
     * @return  时间(秒)  返回 0 代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
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
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (EmptyUtil.isNotEmpty(key)) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------- string

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

    public Object getObj(String key) {
        if (!hasKey(key)) {
            return null;
        }

        return opsValue().get(key);
    }

    public <T> T getObj(String key, Class<T> clazz) {
        Object data = getObj(key);
        return cast(data, clazz);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new UtilException("递增因子必须大于 0");
        }
        return opsValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new UtilException("递减因子必须大于 0");
        }
        return opsValue().increment(key, -delta);
    }

    // ----------------------------------------------------------------------------------------------------------- list

    public ListOperations<String, Object> opsList() {
        return redisTemplate.opsForList();
    }

    public List<Object> lGet(String key) {
        Long size = opsList().size(key);
        if (DataValidationUtil.isNullOrLeZero(size)) {
            return ListUtil.empty();
        }

        return lGet(key, 0, size);
    }

    public <T> List<T> lGet(String key, Class<T> clazz) {
        List<Object> cacheList = lGet(key);

        List<T> list = new ArrayList<>();
        if (EmptyUtil.isNotEmpty(cacheList)) {
            for (Object data : cacheList) {
                list.add(cast(data, clazz));
            }
        }
        return list;
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return  list
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return opsList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return opsList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return opsList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            opsList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            opsList().rightPush(key, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> void lSet(String key, List<T> list, long expire) {
        opsList().rightPushAll(key, list);
        setExpire(key, expire);
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            opsList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return opsList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    // ------------------------------------------------------------------------------------------------------------ set

    public SetOperations<String, Object> opsSet() {
        return redisTemplate.opsForSet();
    }

    public Set<Object> sGet(String key) {
        return opsSet().members(key);
    }

    public <T> Set<T> sGet(String key, Class<T> clazz) {
        Set<T> set = new HashSet<>();
        if (!hasKey(key)) {
            return set;
        }
        Set<Object> cacheSet = sGet(key);
        if (null == cacheSet) {
            return set;
        }

        for (Object data : cacheSet) {
            set.add(cast(data, clazz));
        }
        return set;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return opsSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public <T> void setSet(String key, Set<T> set, Long expire) {
        opsSet().add(key, set);
        setExpire(key, expire);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return opsSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return opsSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRemove(String key, Object... values) {
        try {
            return opsSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    // ------------------------------------------------------------------------------------------------------------ map

    public <V> HashOperations<String, String, V> opsHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return opsHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<String, Object> hmget(String key) {
        if (!hasKey(key)) {
            return Collections.emptyMap();
        }
        return opsHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map, long expire) {
        try {
            opsHash().putAll(key, map);
            setExpire(key, expire);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            opsHash().put(key, item, value);
            setExpire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        opsHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return opsHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return opsHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return opsHash().increment(key, item, -by);
    }


    // ---------------------------------------------------------------------------------------------------------- other

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
