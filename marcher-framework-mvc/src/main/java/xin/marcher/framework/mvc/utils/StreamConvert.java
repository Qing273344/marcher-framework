package xin.marcher.framework.mvc.utils;

import cn.hutool.core.net.URLDecoder;
import lombok.extern.slf4j.Slf4j;
import xin.marcher.framework.common.util.EmptyUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * convert
 *
 * @author marcher
 */
@Slf4j
public class StreamConvert {

    public static Map<String, List<String>> form2Map(HttpServletRequest request) {
        String bodyStr = getBody2String(request);
        return urlParamsToMap(bodyStr);
    }

    /**
     * 将 url 参数转换成 map
     *
     * @param param aa=11&bb=22&cc=33
     * @return Map
     */
    private static Map<String, List<String>> urlParamsToMap(String param) {
        Map<String, List<String>> paramHashValues = new HashMap<>(0);
        if (EmptyUtil.isEmpty(param)) {
            return paramHashValues;
        }

        String[] params = param.split("&");
        for (String data : params) {
            String[] keyValue = data.split("=");
            if (keyValue.length == 1) {
                String name = URLDecoder.decode(keyValue[0], Charset.defaultCharset());
                List<String> values = paramHashValues.computeIfAbsent(name, k -> new ArrayList<>(1));
                values.add(null);
            }
            if (keyValue.length == 2) {
                String name = URLDecoder.decode(keyValue[0], Charset.defaultCharset());
                String value = URLDecoder.decode(keyValue[1], Charset.defaultCharset());

                List<String> values = paramHashValues.computeIfAbsent(name, k -> new ArrayList<>(1));
                values.add(value);
            }
        }
        return paramHashValues;
    }

    /**
     * 获取请求 Body
     */
    public static String getBody2String(HttpServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求 Body
     */
    public static byte[] getBody2Array(HttpServletRequest request) {
        try {
            return inputStream2Byte(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 inputStream 里的数据读取出来并转换成字符串
     */
    private static String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }

        return sb.toString();
    }

    /**
     * 将 inputStream 里的数据读取出来并转换成 byte[]
     */
    private static byte[] inputStream2Byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int ch;
        while ((ch = inputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, ch);
        }
        byte[] data = byteStream.toByteArray();
        byteStream.close();
        return data;
    }
}
