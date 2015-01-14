package com.topsem.common.utils;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateUtil extends DateUtils{

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
        "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

    private static final SimpleDateFormat formater = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    private static final SimpleDateFormat HOUR_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH");

    private static final long ND = 1000 * 24 * 60 * 60;// 一天的毫秒数
    private static final long NH = 1000 * 60 * 60;// 一小时的毫秒数
    private static final long NM = 1000 * 60;// 一分钟的毫秒数

    public static int getCurSeconds() {
        return (int) (DateTimeUtils.currentTimeMillis() / DateTimeConstants.MILLIS_PER_SECOND);
    }

    public static int getCurentHour() {
        return DateTime.now().hourOfDay().get();
    }

    public static String format(long nextStartTime) {
        return formater.format(new Date(nextStartTime * DateTimeConstants.MILLIS_PER_SECOND));
    }

    public static Date parseHourDate(String date) {
        try {
            return HOUR_DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            throw Throwables.propagate(e);
        }
    }


    /**
     * 格式化日志 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return formater.format(date);
    }

    public static Date getDateFromString(String date) {
        try {
            return formater.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getDateFromLong(long l) {
        Date date = new Date(l * 1000);
        return date;
    }

    public static int getDayOfWeek() {
        return DateTime.now().dayOfWeek().get();
    }

    public static int getDayOfWeek(long timeStamp) {
        DateTime dateTime = new DateTime(timeStamp * 1000);
        return dateTime.dayOfWeek().get();
    }

    public static Long getTimeStampFromString(String dataStr) {
        try {
            return formater.parse(dataStr).getTime() / 1000;
        } catch (Exception e) {
            return null;
        }
    }

    public static long getDiffDays(Date startDate, Date endDate) {
        return TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }

    public static long getDiffHours(Date startDate, Date endDate) {
        return TimeUnit.HOURS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }

    public static long getDiffMinutes(Date startDate, Date endDate) {
        return TimeUnit.MINUTES.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }

    public static long getTimeStampOfDayBegin(long timeStamp) {
        DateTime dateTime = new DateTime(timeStamp * 1000);
        return timeStamp - dateTime.getSecondOfDay();
    }

    public static long getTimeStampOfDayEnd(long timeStamp) {
        return getTimeStampOfDayBegin(timeStamp) + (3600 * 24) - 1;
    }

    /**
     * 往前推算24小时
     *
     * @param lastHour
     * @return
     */
    public static List<Integer> hours24(int lastHour) {
        List<Integer> hours = Lists.newArrayList();
        for (int i = lastHour + 1; i < 24; i++) {
            hours.add(i);
        }
        for (int i = 0; i <= lastHour; i++) {
            hours.add(i);
        }
        return hours;
    }


    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }


    public static Date getDateStart(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getTodayZeroTime() {
        return getZeroTime(new Date());
    }

    public static Date getZeroTime(Date date) {
        return getZeroTime(new DateTime(date));
    }

    public static Date getZeroTime(DateTime dateTime) {
        return dateTime.minusMillis(dateTime.getMillisOfDay()).toDate();
    }
//
//    public static void main(String[] args) {
//
//        List<Integer>  hour24 = DateUtil.hours24(0);
//        System.out.println(hour24.toArray(new Integer[hour24.size()]));
//    }
//	public static void main(String[] args) {
//		System.out.println(getCurentHour());
//
//		long l1 = 1399132800L;
//		System.out.println(getDayOfWeek(l1));
//
//		System.out.println(getCurSeconds());
//
//		System.out.println(format(1334160000));
//
//		long l = 1353581584;
//
//		l = l - (2 * 3600 * 24);
//		System.out.println(getTimeStampOfDayBegin(l));
//		System.out.println(getTimeStampOfDayEnd(l));
//
//		System.out.println(format(l));
//
//		System.out.println(format(getTimeStampOfDayBegin(l)));
//
//		System.out.println(format(getTimeStampOfDayEnd(l)));
//
//		System.out.println("getCurSeconds:" + getCurSeconds());
//
//		Date date = getDateFromString("2012-11-22 17:36:00");
//		System.out.println(date.getTime() / 1000);
//		// DateTime dateqq = DateTime.now().minusDays(-1);
//		long i1 = DateTime.now().minusDays(-1).toDate().getTime() / 1000;
//		long i2 = DateTime.now().minusDays(1).toDate().getTime() / 1000;
//
//		DateTime datetime = new DateTime(date);
//		System.out.println(datetime.toDate().getTime());
//
//		System.out.println(">>>>" + datetime.getMinuteOfDay());
//
//		System.out.println(i2 - i1);
//		System.out.println("----");
//		System.out.println(3600 * 48);
//
//		System.out.println("" + DateTime.now().hourOfDay().get());
//	}
}
