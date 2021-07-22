package xin.marcher.framework.common.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author marcher
 */
public class DateUtil {

    /**
     * 一天的小时
     */
    private static final long DAY_HOUR = 24L;

    /**
     * 一小时的毫秒值
     */
    public static final long HOUR_TIMESTAMP = 3600000L;

    /**
     * 一天的时间毫秒值
     */
    public static final long DATE_TIMESTAMP = 86400000L;

    public static final String PATTERN_DATE = "yyyyMMdd";
    public static final String PATTERN_LOGOGRAM_DATE = "yyMMdd";
    public static final String PATTERN_TIME = "yyyyMMdd HH:mm:ss";

    public static final String PATTERN_DIVIDE_DATE = "yyyy/MM/dd";
    public static final String PATTERN_DIVIDE_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_DIVIDE_MINUTE_TIME = "yyyy/MM/dd HH:mm";

    public static final String PATTERN_HYPHEN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_HYPHEN_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_HYPHEN_MINUTE_TIME = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_HYPHEN_MINUTE_TIME_LONG = "yyyyMMddHHmm";

    public static final String PATTERN_STR_TIME = "M月d日 HH:mm";


    /**
     * 当前时间毫秒值
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    public static String now(String pattern) {
        return formatDate(now(), pattern);
    }

    /**
     * 间隔天数
     *
     * @return 天数
     */
    public static int getDateDiff(Long time1, Long time2) {
        if ((time1 == null) || (time2 == null)) {
            return 0;
        }

        long diff = time1 - time2;

        long longValue = diff / DATE_TIMESTAMP;
        return (int) longValue;
    }

    /**
     * 日期解析成时间戳
     *
     * @param dateTime 时间字符串
     * @param pattern  格式
     * @return 返回时间毫秒值
     */
    public static Long parseDate(String dateTime, String pattern) {
        if (EmptyUtil.isEmpty(dateTime)) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date data = null;
        try {
            data = sdf.parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return null;
        }
        return Long.valueOf(String.valueOf(data.getTime()));
    }

    /**
     * 日期格式化
     *
     * @param timeStamp 时间戳
     * @param pattern   格式
     */
    public static String formatDate(Long timeStamp, String pattern) {
        if (timeStamp == null || timeStamp == 0) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(timeStamp);
    }

    /**
     * 指定日期开始时间
     *
     * @param dateTime 日期
     * @param pattern  日期格式
     * @return 开始时间
     */
    public static Long getBeginTime(String dateTime, String pattern) {
        return parseDate(dateTime, pattern);
    }

    /**
     * 指定日期结束时间
     *
     * @param dateTime 日期
     * @param pattern  日期格式
     * @return 结束时间
     */
    public static Long getEndTime(String dateTime, String pattern) {
        if (EmptyUtil.isEmpty(dateTime)) {
            return null;
        }
        return getBeginTime(dateTime, pattern) + 86400000;
    }

    public static Long getLastDayTime(Long dateTime) {
        return dateTime - 86400000;
    }

    /**
     * 获取本年
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1);
    }

    /**
     * 获取本年
     */
    public static int getCurrentYear(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(1);
    }

    /**
     * 获取本月
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1;
    }

    /**
     * 获取本月
     */
    public static int getCurrentMonth(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(2) + 1;
    }

    /**
     * 获取本月的当前日期数
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(5);
    }

    /**
     * 获取本月的当前日期数
     */
    public static int getCurrentDay(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(5);
    }

    /**
     * 获取今天开始时间和结束时间
     */
    public static long getTodayRange(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (isStart) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 24);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取昨天的开始时间和结束时间
     */
    public static long getYesterdayRange(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (isStart) {
            calendar.set(Calendar.HOUR_OF_DAY, -24);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获得本周开始时间和结束时间
     */
    public static Long getWeekRange(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (isStart) {
            calendar.add(Calendar.DATE, -week + 1);
        } else {
            calendar.add(Calendar.DATE, -week + 8);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获得本月开始时间和结束时间
     */
    public static Long getMonthRange(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (isStart) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.add(Calendar.DAY_OF_WEEK, 7);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 24);
        }

        return calendar.getTimeInMillis();
    }

    public static boolean isToday(String date) {
        return isToday(date, PATTERN_DATE);
    }

    public static boolean isToday(String date, String pattern) {
        Long dayTime = parseDate(date, pattern);
        if (dayTime == null) {
            return false;
        }

        return getTodayRange(true) <= dayTime;
    }

    public static boolean isYesterday(String date) {
        return isYesterday(date, PATTERN_DATE);
    }

    public static boolean isYesterday(String date, String pattern) {
        Long dayTime = parseDate(date, pattern);
        if (dayTime == null) {
            return false;
        }

        long yesterdayBeginTime = getYesterdayRange(true);
        long yesterdayEndTime = getYesterdayRange(false);
        return yesterdayBeginTime <= dayTime && dayTime < yesterdayEndTime;
    }

    public static long getSevenDayAgeTime() {
        return new DateTime().minusDays(6).getMillis();
    }

    public static long getThirtyDayAgoTime() {
        return new DateTime().minusDays(29).getMillis();
    }

    public static List<String> getDayBetween(Long beginTime, Long endTime, String pattern) {
        List<String> list = new ArrayList<>();
        list.add(formatDate(beginTime, pattern));

        DateTime beginDateTime = new DateTime(beginTime);
        // minus(1000)防止开始时间顺延天数后小于结束时间
        DateTime endDateTime = new DateTime(endTime).minus(1000);
        while (endDateTime.isAfter(beginDateTime)) {
            beginDateTime = beginDateTime.plusDays(1);
            list.add(formatDate(beginDateTime.getMillis(), pattern));
        }
        return list;
    }

    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }
}
