package xin.marcher.framework.z.util;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp
 *
 * @author marcher
 */
public class OkHttpUtil {

    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");

    private final static OkHttpClient okHttpClient;

    /*
     * 可用 Spring Bean 方式
     */
    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
    }

    public static Response get(String url, HashMap<String,String> headerMap) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        if (headerMap != null && headerMap.keySet().size() > 0) {
            for (String key : headerMap.keySet()) {
                requestBuilder.header(key, headerMap.get(key));
            }
        }
        Request request = requestBuilder.url(url)
                .get()
                .build();
        return okHttpClient.newCall(request).execute();
    }

    public static Response sendJsonPost(String url, HashMap<String, String> headerMap, String paramJson) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        if (headerMap != null && headerMap.keySet().size() > 0) {
            for (String key : headerMap.keySet()) {
                requestBuilder.header(key, headerMap.get(key));
            }
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), paramJson);

        Request request = requestBuilder.url(url)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送表单请求
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static Response sendFromPost(String url, HashMap<String, String> headerMap, HashMap<String, String> paramMap) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        if (headerMap != null && headerMap.keySet().size() > 0) {
            for (String key : headerMap.keySet()) {
                requestBuilder.header(key, headerMap.get(key));
            }
        }

        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (paramMap != null && paramMap.keySet().size() > 0) {
            for (String key : paramMap.keySet()) {
                bodyBuilder.add(key, paramMap.get(key));
            }
        }

        Request request = requestBuilder.url(url)
                .post(bodyBuilder.build())
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送文件上传请求
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static Response sendFilePost(String url, HashMap<String, String> headerMap, HashMap<String, Object> paramMap) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        if (headerMap != null && headerMap.keySet().size() > 0) {
            for (String key : headerMap.keySet()) {
                requestBuilder.header(key, headerMap.get(key));
            }
        }

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(FROM_DATA);
        if (paramMap != null && paramMap.keySet().size() > 0) {
            for (String key : paramMap.keySet()) {
                if (paramMap.get(key) instanceof File) {
                    File file = (File) paramMap.get(key);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    bodyBuilder.addFormDataPart(key, file.getName(), fileBody);
                }
                if (paramMap.get(key) instanceof byte[] || paramMap.get(key) instanceof Byte[]) {
                    byte[] file = (byte[]) paramMap.get(key);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    bodyBuilder.addFormDataPart(key, key, fileBody);
                } else {
                    bodyBuilder.addFormDataPart(key, paramMap.get(key).toString());
                }
            }
        }

        Request request = requestBuilder.url(url)
                .post(bodyBuilder.build())
                .build();

        return okHttpClient.newCall(request).execute();
    }
}
