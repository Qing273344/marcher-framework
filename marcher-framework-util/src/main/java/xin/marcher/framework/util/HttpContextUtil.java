package xin.marcher.framework.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xin.marcher.framework.constants.GlobalConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * requestUtil
 *
 * @author marcher
 */
public class HttpContextUtil {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        return getHttpServletRequest().getHeader("Origin");
    }

    public static String getMethod() {
        return getHttpServletRequest().getMethod();
    }

    public static String getAllMethods() {
        return "GET, POST, OPTIONS, PUT, DELETE";
    }

    public static String getHanders() {
        return getHttpServletRequest().getHeader("Access-Control-Request-Headers");
    }

    public static String getUserAgent() {
        String userAgent = getHttpServletRequest().getHeader("user-agent");
        if (EmptyUtil.isEmptyTrim(userAgent)) {
            return "";
        }
        return userAgent;
    }

    public static String getContentType() {
        return getHttpServletRequest().getContentType();
    }


    public static String getSsOrigin() {
        return getHttpServletRequest().getHeader("ss-origin");
    }

    public static boolean isGet() {
        return getMethod().equalsIgnoreCase("get");
    }

    public static boolean isPost() {
        return getMethod().equalsIgnoreCase("post");
    }

    /**
     * 获取 ip
     *
     * @return ip
     */
    public static String getRequestIp() {
        HttpServletRequest request = getHttpServletRequest();

        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            if (ip.indexOf(',') > -1) {
                String[] ips = ip.split(",");
                ip = ips[0];
            }
        }

        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (EmptyUtil.isEmptyTrim(ip) || "unknown".equalsIgnoreCase(ip)) {
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
