package xin.marcher.framework.mvc.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import xin.marcher.framework.common.util.EmptyUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * web help
 *
 * @author marcher
 */
public class WebHelp {

    private static final int BUFFER_SIZE = 1024;

    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";


    public static String getParameters(HttpServletRequest request) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(request.getInputStream(), BUFFER_SIZE);
            int contentLength = Integer.parseInt(request.getHeader("Content-Length"));
            byte[] bytes = new byte[contentLength];
            int readCount = 0;
            while (readCount < contentLength) {
                readCount += is.read(bytes, readCount, contentLength - readCount);
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(is);
        }

        return null;
    }

    public static boolean isGet(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod());
    }

    public static boolean isFormPost(HttpServletRequest request) {
        String contentType = request.getContentType();
        return (contentType != null
                && contentType.contains(FORM_CONTENT_TYPE)
                && HttpMethod.POST.matches(request.getMethod()));
    }

    public static boolean isPost(HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod());
    }

    public static boolean isMultipart(HttpServletRequest request) {
        String contentType = request.getContentType();
        return (contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE));
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    public static boolean isJson(HttpServletRequest request) {
        if (EmptyUtil.isNotEmptyTrim(request.getContentType())) {
            return request.getContentType().toLowerCase().equals(MediaType.APPLICATION_JSON_VALUE) ||
                    request.getContentType().toLowerCase().equals(MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase());
        }
        return false;
    }
}
