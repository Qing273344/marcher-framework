package xin.marcher.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * json工具类
 *
 * @author marcher
 */
public class JSONUtil {

    public static boolean isJsonObject(String text) {
        if (EmptyUtil.isEmptyTrim(text)) {
            return false;
        }

        JsonElement jsonElement = JsonParser.parseString(text);
        return jsonElement.isJsonObject();
    }

    public static <T> T jsonStr2Obj(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static String obj2JsonStr(Object obj) {
        if (EmptyUtil.isEmpty(obj)) {
            return "";
        }
        return JSON.toJSONString(obj);
    }

    public static String obj2JsonStrWithNull(Object obj) {
        if (EmptyUtil.isEmpty(obj)) {
            return "";
        }
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * jsonArray 转换成 List
     *
     * @param jsonArray json数组数据
     * @param clazz     装换成的对象class
     * @param <T>       T
     * @return
     *      对象
     */
    public static <T> List<T> jsonArrayToObjList(JSONArray jsonArray, Class<T> clazz){
        if (EmptyUtil.isEmpty(jsonArray)){
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++){
            list.add(jsonArray.getObject(i, clazz));
        }
        return list;
    }
}
