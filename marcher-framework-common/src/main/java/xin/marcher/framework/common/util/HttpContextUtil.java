package xin.marcher.framework.common.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xin.marcher.framework.common.constants.GlobalConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request util
 *
 * @author marcher
 */
public class HttpContextUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getDomain() {
        HttpServletRequest request = getRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        return getRequest().getHeader("Origin");
    }

    public static String getMethod() {
        return getRequest().getMethod();
    }

    public static String getAllMethods() {
        return "GET, POST, OPTIONS, PUT, DELETE";
    }

    public static String getHeaders() {
        return getRequest().getHeader("Access-Control-Request-Headers");
    }

    public static String getUserAgent() {
        String userAgent = getRequest().getHeader("user-agent");
        return EmptyUtil.isEmptyTrim(userAgent) ? GlobalConstant.CHAR_BLANK : userAgent;
    }

    public static String getContentType() {
        return getRequest().getContentType();
    }

    public static String getRequestURL() {
        return getRequest().getRequestURL().toString();
    }

    public static boolean isGet() {
        return getMethod().equalsIgnoreCase("get");
    }

    public static boolean isPost() {
        return getMethod().equalsIgnoreCase("post");
    }

    public static String getAuthorization() {
        String authorization = getRequest().getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        int index = authorization.indexOf("Bearer ");
        if (index == -1) {
            return null;
        }

        String bearerValue = authorization.substring(index + 7).trim();
        if ("null".equals(bearerValue)) {
            return null;
        }
        return bearerValue;
    }

    /**
     * 获取 ip
     *
     * @return ip
     */
    public static String getRequestIp() {
        HttpServletRequest request = getRequest();

        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            if (ip.contains(GlobalConstant.CHAR_COMMA)) {
                String[] ips = ip.split(",");
                ip = ips[0];
            }
        }

        boolean isUnknown = "unknown".equalsIgnoreCase(ip);
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getHeader("X-Real-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (EmptyUtil.isEmptyTrim(ip) || isUnknown) {
            ip = request.getRemoteAddr();
        }

        final String[] arr = ip.split(GlobalConstant.CHAR_COMMA);
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }

        return ip;
    }
}
