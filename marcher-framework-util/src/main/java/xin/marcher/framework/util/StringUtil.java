package xin.marcher.framework.util;

import com.google.common.base.Joiner;
import org.springframework.util.StringUtils;
import xin.marcher.framework.constants.GlobalConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * string util
 *
 * @author marcher
 */
public class StringUtil {

    /**
     * has text
     * @param str   str
     * @return
     *      is
     */
    public static boolean hasText(String str) {
        return StringUtils.hasText(str);
    }

    /**
     * list to string
     *
     * @param list      list
     * @param delimiter 分割符
     *
     * @return
     *      String
     */
    public static String join(Collection<?> list, String delimiter) {
        return Joiner.on(delimiter).join(list);
    }

    public static List<String> split(String toSplit, String delimiter) {
        String[] stringArray = StringUtils.tokenizeToStringArray(toSplit, delimiter);
        return Arrays.asList(stringArray);
    }

    public static List<Integer> splitToInt(String toSplit, String delimiter) {
        String[] stringArray = StringUtils.tokenizeToStringArray(toSplit, delimiter);
        List<Integer> array = new ArrayList<>(stringArray.length);
        for (String string : stringArray) {
            array.add(Integer.valueOf(string));
        }
        return array;
    }

    public static List<Long> splitToLong(String toSplit, String delimiter) {
        String[] stringArray = StringUtils.tokenizeToStringArray(toSplit, delimiter);
        List<Long> array = new ArrayList<>(stringArray.length);
        for (String string : stringArray) {
            array.add(Long.valueOf(string));
        }
        return array;
    }

    /**
     * params 进行格式化。
     *
     * @param messagePattern 消息模版
     * @param params         参数
     * @return
     *      格式化后的数据
     */
    public static String doFormat(String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }

    public static String replace(String text, String oldPattern, String newPattern) {
        return StringUtils.replace(text, oldPattern, newPattern);
    }

    /**
     * 将首字母和带 "_" 后第一个字母 转换成大写
     *
     * @param str   str
     */
    public static String upperStr(String str) {
        // 字符串缓冲区
        StringBuffer sbf = new StringBuffer();
        // 如果字符串包含下划线
        if (str.contains(GlobalConstant.CHAR_UNDER_LINE)) {
            // 按下划线来切割字符串为数组
            String[] split = str.split(GlobalConstant.CHAR_UNDER_LINE);
            // 循环数组操作其中的字符串
            for (int i = 0, index = split.length; i < index; i++) {
                // 递归调用本方法
                String upperTable = upperStr(split[i]);
                sbf.append(upperTable);
            }
        }
        // 字符串不包含下划线
        else {
            char[] ch = str.toCharArray();
            // 判断首字母是否是字母
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                // 利用ASCII码实现大写
                ch[0] = (char) (ch[0] - 32);
            }
            sbf.append(ch);
        }
        return sbf.toString();
    }
}
