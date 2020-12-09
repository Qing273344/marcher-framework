package xin.marcher.framework.z.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author biscuits
 * @date 2020/07/08
 */
@Slf4j
public final class JsonUtils {

    private final static ThreadLocal<Gson> cache = new ThreadLocal<>();

    private static Gson getGson() {
        Gson gson = cache.get();
        if (gson == null) {
            cache.remove();
            cache.set(new Gson());
        }
        return cache.get();
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clz) {

        return getGson().fromJson(json, clz);
    }

    public static <T> List<T> arrayFromJson(String json) {
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        return getGson().fromJson(json, type);
    }

    /**
     * 专门用于解析第三方的返回
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T parseResp(String json, Class<T> clz) throws UtilException {
        try {
            return getGson().fromJson(json, clz);
        } catch (Exception e) {
            log.error("第三方异常返回", e);
            throw new UtilException("支付第三方异常返回");
        }
    }

    /**
     * 解析json中的json
     * 需要解析的json(jin)包含在一个json(jout)中
     * 且jin为jout的一个键值对的value
     *
     * @param json jout
     * @param clz  jin的类型
     * @param <T>  最后序列化对应的类型
     * @param key  jin对应的键值对的key
     * @return jin对应的对象
     */
    public static <T> T fromInnerJson(String json, String key, Class<T> clz) {
        Gson gson = getGson();
        JsonMap entry = gson.fromJson(json, JsonMap.class);
        Object val = entry.get(key);
        if (String.class.equals(val.getClass())) {
            return gson.fromJson((String) val, clz);
        }
        return gson.fromJson(gson.toJson(val), clz);
    }

    public static void remove() {
        cache.remove();
    }

    //如果有修改 需要进行单元测试
    public static <T> T parseInnerResp(String json, Class<T> clz, String... keys) throws UtilException {

        try {
            Gson gson = getGson();
            //该字符串必须是一个json对象，否则抛出异常
            JsonObject obj = getJsonObject(JsonParser.parseString(json));
            //层层解析直到最后一层，每一层必须是一个json对象，否则抛出异常
            for (int i = 0; i < keys.length - 1; i++) {
                obj = getJsonObject(obj.get(keys[i]));
            }
            //获取要解析的最后一层判断其类型
            JsonElement result = obj.get(keys[keys.length - 1]);
            //如果json内是一个对象，则直接解析对象
            if (result.isJsonObject()) {
                JsonObject jsonObject = result.getAsJsonObject();
                return gson.fromJson(jsonObject, clz);
            }
            //如果json内是简单数据类型，则根据返回类型返回对应的对象
            if (result.isJsonPrimitive()) {
                JsonPrimitive primitive = result.getAsJsonPrimitive();
                if (clz.equals(String.class)) {
                    return (T) primitive.getAsString();
                }
                if (clz.equals(Boolean.class)) {
                    Object bool = primitive.getAsBoolean();
                    return (T) bool;
                }
                if (clz.isInstance(Number.class)) {
                    return (T) primitive.getAsNumber();
                }

            }
            //如果json内是null，则返回null
            if (result.isJsonNull()) {
                return null;
            }
            //如果json内是数组，则抛出异常
            if (result.isJsonArray()) {
                throw new UtilException("预期的类型是对象，第三方返回数组");
            }
            //当前case理论上是不存在的，从健壮性的角度上讲，做简单的解析处理
            return gson.fromJson(result.getAsString(), clz);
        } catch (Exception e) {
            log.error("第三方异常返回，json={}", json, e);
            throw new UtilException("支付第三方异常返回");
        }
    }

    private static JsonObject getJsonObject(JsonElement element) throws UtilException {
        if (!element.isJsonObject()) {
            long l = System.currentTimeMillis();
            log.error("第三方返回异常[{}]:期望是一个对象，实际解析不是对象{}", l, element.toString());
            throw new UtilException("支付第三方异常返回[" + l + "]");
        }
        return element.getAsJsonObject();
    }

    /**
     * 线程销毁
     */
    @PreDestroy
    public void destory() {
        cache.remove();
    }

    private static class JsonMap extends HashMap<String, Object> {

    }
}
