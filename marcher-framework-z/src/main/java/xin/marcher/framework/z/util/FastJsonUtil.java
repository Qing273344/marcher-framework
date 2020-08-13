package xin.marcher.framework.z.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author: Jack
 * @Description: fastjson工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public class FastJsonUtil {

	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		// 使用和json-lib兼容的日期输出格式
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
		// 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
	}

	private static final SerializerFeature[] features = {
			// 输出空置字段
			SerializerFeature.WriteMapNullValue,
			// list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullListAsEmpty,
			// 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullNumberAsZero,
			// Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullBooleanAsFalse,
			// 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteNullStringAsEmpty,
			//是否需要格式化输出Json数据
			SerializerFeature.PrettyFormat
	};

	/**
	 * 将对象转成成Json对象
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	/**
	 * 使用和json-lib兼容的日期输出格式
	 * @param object
	 * @return
	 */
	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	/**
	 * 将Json数据转换成JSONObject
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject toJsonObj(String jsonStr) {
		return (JSONObject) JSON.parse(jsonStr);
	}
	
	/**
	 * 将Json数据转换成Object
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T toBean(String jsonStr, Class<T> clazz) {
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * 将Json数据转换为数组
	 * @param jsonStr
	 * @param <T>
	 * @return
	 */
	public static <T> Object[] toArray(String jsonStr) {
		return toArray(jsonStr, null);
	}

	/**
	 * 将Json数据转换为数组
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> Object[] toArray(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz).toArray();
	}

	/**
	 * 将Json数据转换为List
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	/**
	 * 将javabean转化为序列化的JSONObject对象
	 * @param bean
	 * @return
	 */
	public static JSONObject beanToJsonObj(Object bean) {
		String jsonStr = JSON.toJSONString(bean);
		JSONObject objectJson = (JSONObject) JSON.parse(jsonStr);
		return objectJson;
	}

	/**
	 * json字符串转化为map
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> stringToCollect(String jsonStr) {
		Map<?, ?> map = JSONObject.parseObject(jsonStr);
		return map;
	}

	/**
	 * 将map转化为string
	 * @param map
	 * @return
	 */
	public static String collectToString(Map<?, ?> map) {
		String jsonStr = JSONObject.toJSONString(map);
		return jsonStr;
	}

	/**
	 * 将对象的Json数据写入文件。
	 * @param t
	 * @param file
	 * @param <T>
	 * @throws IOException
	 */
	public static <T> void writeJsonToFile(T t, File file) throws IOException {
		String jsonStr = JSONObject.toJSONString(t, SerializerFeature.PrettyFormat);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		bw.write(jsonStr);
		bw.close();
	}

	/**
	 * 将对象的Json数据写入文件。
	 * @param t
	 * @param filename
	 * @param <T>
	 * @throws IOException
	 */
	public static <T> void writeJsonToFile(T t, String filename) throws IOException {
		writeJsonToFile(t, new File(filename));
	}

	/**
	 * 将文件中的Json数据转换成Object对象
	 * @param cls
	 * @param file
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T readJsonFromFile(Class<T> cls, File file) throws IOException {
		StringBuilder strBuilder = getStringBuilder(file);
		return JSONObject.parseObject(strBuilder.toString(), cls);
	}

	/**
	 * 将文件中的Json数据转换成Object对象
	 * @param cls
	 * @param filename
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T readJsonFromFile(Class<T> cls, String filename) throws IOException {
		return readJsonFromFile(cls, new File(filename));
	}

	/**
	 * 从文件中读取出Json对象
	 * @param typeReference
	 * @param file
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T readJsonFromFile(TypeReference<T> typeReference, File file) throws IOException {
		StringBuilder strBuilder = getStringBuilder(file);
		return JSONObject.parseObject(strBuilder.toString(), typeReference);
	}

	private static StringBuilder getStringBuilder(File file) throws IOException {
		StringBuilder strBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while ((line = br.readLine()) != null) {
			strBuilder.append(line);
		}
		br.close();
		return strBuilder;
	}

	/**
	 * 从文件中读取出Json对象
	 * @param typeReference
	 * @param filename
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T readJsonFromFile(TypeReference<T> typeReference, String filename) throws IOException {
		return readJsonFromFile(typeReference, new File(filename));
	}
}