package xin.marcher.framework.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {

    public static final String PATTERN_HYPHEN_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
