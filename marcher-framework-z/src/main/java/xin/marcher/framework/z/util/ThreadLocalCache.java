package xin.marcher.framework.z.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author biscuits
 * @date 2020/8/31
 */
public class ThreadLocalCache {

    private final static Cache<Long, Map<String, String>> cache;

    static {
        cache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();
    }

    public static <T> T get(String key, Class<T> clz) {

        Map<String, String> threadCache = getCurrentCache();
        if (threadCache == null) {
            return null;
        }
        String val = threadCache.get(key);
        if (StringUtils.isEmpty(val)) {
            return null;
        }
        return JsonUtils.fromJson(val, clz);
    }

    public static void set(String key, Object val) {
        if (val == null) {
            return;
        }
        long threadId = Thread.currentThread().getId();
        Map<String, String> threadCache = getCurrentCache();
        if (threadCache == null) {
            threadCache = new HashMap<>();
            cache.put(threadId, threadCache);
        }
        threadCache.put(key, JsonUtils.toJson(val));
    }

    public static void clean() {
        long threadId = Thread.currentThread().getId();
        cache.invalidate(threadId);
    }

    public static void remove(String key) {

        Map<String, String> threadCache = getCurrentCache();
        if (threadCache == null) {
            return;
        }
        threadCache.remove(key);
    }


    public static boolean containsKey(String key) {
        Map<String, String> currentCache = getCurrentCache();
        if (currentCache == null) {
            return false;
        }
        return currentCache.containsKey(key);
    }

    private static Map<String, String> getCurrentCache() {
        return cache.getIfPresent(Thread.currentThread().getId());
    }
}
