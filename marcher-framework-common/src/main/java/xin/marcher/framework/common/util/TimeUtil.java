package xin.marcher.framework.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * time util
 *
 * @author marcher
 */
public class TimeUtil {

    private final static long YEAR = 1000 * 60 * 60 * 24 * 365L;
    private final static long MONTH = 1000 * 60 * 60 * 24 * 30L;
    private final static long DAY = 1000 * 60 * 60 * 24L;
    private final static long HOUR = 1000 * 60 * 60L;
    private final static long MINUTE = 1000 * 60L;

    /**
     * 日期转换成字符串
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_HYPHEN_TIME);
        return format.format(date);
    }

    /**
     * 字符串转换成日期
     */
    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_HYPHEN_TIME);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据时间查询时间属于哪个时刻
     */
    public static String natureTime(Date date){
        Date now = new Date();
        long between = now.getTime() - date.getTime();
        if (between > YEAR){
            return ((between - YEAR) / YEAR + 1) + "年前";
        }
        if (between > MONTH){
            return ((between - MONTH) / MONTH + 1) + "月前";
        }
        if (between > DAY){
            return ((between - DAY) / DAY + 1) + "天前";
        }
        if (between > HOUR){
            return ((between - HOUR) / HOUR + 1) + "小时前";
        }
        if (between > MINUTE){
            return ((between - MINUTE) / MINUTE + 1) + "分钟前";
        }
        return "刚刚";
    }

}

