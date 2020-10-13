package xin.marcher.framework.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author marcher
 */
public class RegexUtil {

    /**
     * IP
     */
    public static final String REGEX_IP = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";

    /**
     * 手机号
     */
    public static final String REGEX_MOBILE = "^1\\d{10}$";
    /**
     * 电话号码
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";

    /**
     * 邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * URL
     */
    public static final String REGEX_URL = "^(http|https)://[a-zA-Z0-9_-]+.*";

    /**
     * 中文和英文
     */
    public static final String REGEX_CH_AND_EG = "^[(\\u4e00-\\u9fa5)|A-Za-z]+$";
    /**
     * 纯中文
     */
    public static final String REGEX_ALL_CH = "^[(\\u4e00-\\u9fa5)]+$";

    /**
     * number
     */
    public static final String REGEXP_NUMBER = "[0-9]+";

    /**
     * 正整数
     */
    public static final String REGEXP_POSITIVE_NUMBER = "[1-9]+\\d*";

    /**
     * image
     */
    public static final String REGEXP_IMAGE = ".*?\\.(jpg|gif|bmp|bnp|png)$";

    /**
     * 印度电话区号
     */
    public static final String INDIA_PHONE_ZONE = "[+]?91";

    /**
     * 匹配手机号
     *
     * @param str 待验证文本
     * @return 匹配: true 不匹配: false
     */
    public static boolean isMobile(String str) {
        return checkRegex(str, REGEX_MOBILE);
    }

    /**
     * 匹配电话号码
     *
     * @param str 待验证文本
     * @return 匹配: true 不匹配: false
     */
    public static boolean isTel(String str) {
        return checkRegex(str, REGEX_TEL);
    }

    /**
     * 匹配邮箱
     *
     * @param str 待验证文本
     * @return 匹配: true 不匹配: false
     */
    public static boolean isEmail(String str) {
        return checkRegex(str, REGEX_EMAIL);
    }

    /**
     * 匹配IP
     *
     * @param str 待验证文本
     * @return 匹配: true 不匹配: false
     */
    public static boolean isIP(String str) {
        return checkRegex(str, REGEX_IP);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   待验证文本
     * @return 如果str 符合 regex的正则表达式格式, 返回true, 否则返回 false;
     */
    public static List<String> match(String str, String regex) {
        if (null == str) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        List<String> list = new LinkedList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 校验正则匹配
     *
     * @param str   待验证文本
     * @param regex 正则表达式
     * @return true: 匹配成功, false: 匹配失败
     */
    public static boolean checkRegex(String str, String regex) {
        if (null == str) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 替换
     *
     * @param str   str
     * @param regex regex
     * @return  result
     */
    public static String replaceFirst(String str, String regex) {
        if (checkRegex(str, regex)) {
            return str.replaceFirst(regex, "").trim();
        }
        return str;
    }


    public static void main(String[] args) {
        String str = "1878787878";
        String s = replaceFirst(str, INDIA_PHONE_ZONE);
        System.out.println(s);
    }

}