package com.elite.webdata.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtil {

    /**
     * 取得格式化效果的系统日期！ 格式如：yyyy-MM-dd kk:mm:ss
     */
    public final static String getFormateDate(String formate) {
        SimpleDateFormat f = new SimpleDateFormat(formate, Locale.US);
        return f.format(new Date());
    }

    public static boolean compareDate(String s, String e) {
        if ((fomatDate(s) == null) || (fomatDate(e) == null)) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fomatTime(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(date);
    }

    public static String fomatDate(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date);
    }

    public static String fomatStringDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.format(fmt.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String fomatDate_new(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.format(fmt.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date fomatDateHMS(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isValidDate_New(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long aa = 0L;
            int years = (int) ((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / 86400000L / 365L);
            return years;
        } catch (Exception e) {
        }
        return 0;
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / 86400000L;

        return day;
    }

    public static String getAfterDayDate(Integer days) {

        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, days);
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    public static String getAfterDayDate(String startDate, Integer days) {
        String[] time = startDate.split("-");

        Calendar canlendar = Calendar.getInstance();
        canlendar.set(Integer.parseInt(time[0]), Integer.parseInt(time[1]) - 1, Integer.parseInt(time[2]));
        canlendar.add(5, days);
        Date date = canlendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);

        return dateStr;
    }

    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static String getDateByTimeStamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(timestamp));

        return date;
    }

    public static long getTimeStamp(String days) {
        long unixTimestamp = 0L;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(days);
            unixTimestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTimestamp;
    }

    public static long getTimeStamp() {
        long unixTimestamp = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        unixTimestamp = date.getTime();


        return unixTimestamp;
    }

    public static long getTimeStampAsSecond() {
        long unixTimestamp = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        unixTimestamp = date.getTime()/1000;


        return unixTimestamp;
    }

    public static long getTimeStampSdfDay(String days) {
        long unixTimestamp = 0L;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date date = format.parse(days);
            unixTimestamp = date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return unixTimestamp;
    }

    /**
     * 时间差
     *
     * @throws ParseException
     */
    public static String getTiming(String date1, long startTime) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //java.util.Date now = df.parse(date2);
        Date date = df.parse(date1);

        long l = startTime - date.getTime() / 1000;

        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000));
        long min = ((l / (60 * 1000)));
        if (min < 60) {
            return Long.toString(min) + "分钟前";
        } else if (hour < 24) {
            return Long.toString(hour) + "小时前";
        } else if (day < 2) {
            return Long.toString(day) + "天前";
        } else {
            return date1;
        }

    }

    public static String getTimings(String date1, String date2) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = df.parse(date2);
        Date date = df.parse(date1);

        long l = now.getTime() - date.getTime();

        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000));
        long min = ((l / (60 * 1000)));
        if (min < 60) {
            return Long.toString(min) + "分钟前";
        } else if (hour < 24) {
            return Long.toString(hour) + "小时前";
        } else if (day < 2) {
            return Long.toString(day) + "天前";
        } else {
            return date1;
        }

    }


    /**
     * 格式化输出指定的calendar的格式
     *
     * @param calendar calendar对象
     * @return 日期字符串
     */
    public static String format(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 格式化输出指定的calendar的格式
     *
     * @param calendar calendar对象
     * @param format   格式
     * @return 日期字符串
     */
    public static String format(Calendar calendar, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取昨天Calendar对象
     *
     * @return calendar实例
     */
    public static Calendar getYesterday() {
        Calendar calendar = Calendar.getInstance();//今天
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }

    /**
     * @param days
     * @return
     */
    public static String getBeforeday(String days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;


    }

    /**
     * 获取昨天的日期对象
     *
     * @return
     */
    public static String yesterday() {
        return format(getYesterday(), "yyyy-MM-dd");
    }


    public static Calendar getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        return calendar;
    }

    public static String tomorrow() {
        return format(getTomorrow(), "yyyy-MM-dd");
    }

    public static Calendar getBeforeMonthsDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -30);
        return calendar;
    }

    public static Calendar getAfterMonthsDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 30);
        return calendar;
    }

    public static String BeforeMonthsDay() {
        return format(getBeforeMonthsDay(), "yyyy-MM-dd");
    }

    public static String AfterMonthsDay() {
        return format(getAfterMonthsDay(), "yyyy-MM-dd");
    }

    public static String getnowDate() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf2.format(new Date());
        return date;
    }

    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取某一日期的前几天或后几天是星期几
     *
     * @param dd  时间
     * @param num 天数
     * @return
     */
    public static String getWeekValue(Date dd, int num) {
        String getWeek = "";
        String[] weekDatas = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dd);
        int week_index = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        calendar1.add(Calendar.DATE, num);
        week_index = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        getWeek = weekDatas[week_index];
        return getWeek;
    }

    //获取某个时间段内所有的日期
    public static List<String> findDates(Date dBegin, Date dEnd) {
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * 根据时间类型比较时间大小
     *
     * @param source
     * @param traget
     * @param type   "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义
     * @return 0 ：source和traget时间相同
     * 1 ：source比traget时间大
     * -1：source比traget时间小
     * @throws Exception
     */
    public static int DateCompare(String source, String traget, String type) throws Exception {
        int ret = 2;
        SimpleDateFormat format = new SimpleDateFormat(type);
        Date sourcedate = format.parse(source);
        Date tragetdate = format.parse(traget);
        ret = sourcedate.compareTo(tragetdate);
        return ret;
    }

    /**
     * 时间戳转时间
     *
     * @param stamp 13位时间戳
     * @return 转换成24小时制的时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static Date stampToDate(String stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long lt = new Long(stamp);
        Date stampDate = new Date(lt);
        String dateStr = sdf.format(stampDate);
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("时间戳转时间出错！！！" + stamp, e);
        }
        return result;
    }

    /**
     * 获取当前时间的当年第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date result = null;
        try {
            String dateStr = sdf.format(calendar.getTime());
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("获取当前时间的当年第一天出错！！！" + date, e);
        }
        return result;
    }

    /**
     * 获取当前时间的当年最后一天
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        Date result = null;
        try {
            String dateStr = sdf.format(calendar.getTime());
            result = sdf.parse(dateStr);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(result.getTime()), ZoneId.systemDefault());
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            result = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        } catch (ParseException e) {
            log.error("获取当前时间的当年最后一天出错！！！" + date, e);
        }
        return result;
    }

    /**
     * 获取当前时间的当月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date result = null;
        try {
            String dateStr = sdf.format(calendar.getTime());
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("获取当前时间的当月第一天出错！！！" + date, e);
        }
        return result;
    }

    /**
     * 获取当前时间的当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date result = null;
        try {
            String dateStr = sdf.format(calendar.getTime());
            result = sdf.parse(dateStr);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(result.getTime()), ZoneId.systemDefault());
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            result = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        } catch (ParseException e) {
            log.error("获取当前时间的当月最后一天出错！！！" + date, e);
        }
        return result;
    }

    /**
     * date2比date1多出的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer differentDays(Date date1, Date date2) {
        int days;
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long diff = time2 - time1;
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    public static long differentMs(Date date1, Date date2) {
        int days;
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return time2 - time1;
    }

    public static boolean isToday(String date) {//传入时间yyyy-MM-dd是否为今日时间
        boolean flag = false;
        String today = getTodayDate();//yyyy-MM-dd
        if (date.equals(today)) {
            flag = true;
        }
        return flag;
    }

    public static boolean isToday(Date date) {//传入时间是否为今日时间
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        if (date.after(paseBeginTime) && date.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }


    /**
     * 获取过去n天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static List<String> dayList(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        String result = format.format(today);
        return result;
    }

    public static Date getPastDate(Date date, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);//+后 -前
        Date result = calendar.getTime();
        return result;
    }

    /**
     * 获取指定日期之后几天的日期
     *
     * @param date
     * @param past
     * @return
     */
    public static Date getFetureDate(Date date, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);//+后 -前
        Date result = calendar.getTime();
        return result;
    }

    /**
     * 获取某个日期所在年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(String date) {
        LocalDate localDate = LocalDate.parse(date);
        int year = localDate.getYear();
        return year;
    }

    /**
     * 获取某个日期所在月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        int month = localDate.getMonth().getValue();
        return month;
    }

    /**
     * 获取某个日期所在日期
     *
     * @param date
     * @return
     */
    public static Integer getDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        int day = localDate.getDayOfMonth();
        return day;
    }

    /**
     * 获取某个日期所在月份的天数
     *
     * @param date
     * @return
     */
    public static Integer getLengthOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        int days = localDate.lengthOfMonth();
        return days;
    }

    public static Date getStartOfDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return c.getTime();
    }

    public static void main(String[] args) {
        String afterDayDate = getAfterDayDate("2019-12-07", 1);
        System.out.println(afterDayDate);
    }


}