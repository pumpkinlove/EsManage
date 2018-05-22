package com.miaxis.esmanage.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by xu.nan on 2018/1/31.
 */

public class DateUtil {

    public static String toHourMinString(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm:ss");
        return myFmt.format(date);
    }

    public static String toMonthDay(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(date);
    }

    public static String toAll(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(date);
    }

    public static String toAllms(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return myFmt.format(date);
    }

    public static Date fromMonthDay(String date) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.parse(date);
    }

    public static Date addOneDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);  //把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();      //这个时间就是日期往后推一天的结果
    }

    /**
     *
     * 时间范围：年
     */

    public static final int YEAR = 1;

    /**
     *
     * 时间范围：季度
     */

    public static final int QUARTER = 2;

    /**
     *
     * 时间范围：月
     */

    public static final int MONTH = 3;

    /**
     *
     * 时间范围：旬
     */

    public static final int TENDAYS = 4;

    /**
     *
     * 时间范围：周
     */

    public static final int WEEK = 5;

    /**
     *
     * 时间范围：日
     */

    public static final int DAY = 6;

	/* 基准时间 */

    private Date fiducialDate = null;

    private Calendar cal = null;

    private DateUtil(Date fiducialDate) {

        if (fiducialDate != null) {

            this.fiducialDate = fiducialDate;

        } else {

            this.fiducialDate = new Date(System.currentTimeMillis());

        }

        this.cal = Calendar.getInstance();

        this.cal.setTime(this.fiducialDate);

        this.cal.set(Calendar.HOUR_OF_DAY, 0);

        this.cal.set(Calendar.MINUTE, 0);

        this.cal.set(Calendar.SECOND, 0);

        this.cal.set(Calendar.MILLISECOND, 0);

        this.fiducialDate = this.cal.getTime();

    }

    /**
     *
     * 获取DateHelper实例
     *
     *
     *
     * @param fiducialDate
     *
     *            基准时间
     *
     * @return Date
     */

    public static DateUtil getInstance(Date fiducialDate) {

        return new DateUtil(fiducialDate);

    }

    /**
     *
     * 获取DateHelper实例, 使用当前时间作为基准时间
     *
     *
     *
     * @return Date
     */

    public static DateUtil getInstance() {

        return new DateUtil(null);

    }

    /**
     *
     * 获取年的第一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDayOfYear(int offset) {

        cal.setTime(this.fiducialDate);

        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);

        cal.set(Calendar.MONTH, Calendar.JANUARY);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();

    }

    /**
     *
     * 获取年的最后一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDayOfYear(int offset) {

        cal.setTime(this.fiducialDate);

        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);

        cal.set(Calendar.MONTH, Calendar.DECEMBER);

        cal.set(Calendar.DAY_OF_MONTH, 31);

        return cal.getTime();

    }

    /**
     *
     * 获取季度的第一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDayOfQuarter(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.MONTH, offset * 3);

        int mon = cal.get(Calendar.MONTH);

        if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {

            cal.set(Calendar.MONTH, Calendar.JANUARY);

            cal.set(Calendar.DAY_OF_MONTH, 1);

        }

        if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {

            cal.set(Calendar.MONTH, Calendar.APRIL);

            cal.set(Calendar.DAY_OF_MONTH, 1);

        }

        if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {

            cal.set(Calendar.MONTH, Calendar.JULY);

            cal.set(Calendar.DAY_OF_MONTH, 1);

        }

        if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {

            cal.set(Calendar.MONTH, Calendar.OCTOBER);

            cal.set(Calendar.DAY_OF_MONTH, 1);

        }

        return cal.getTime();

    }

    public Date getYesterday() {

        long time = this.fiducialDate.getTime() - 60 * 60 * 24 * 1000;

        return new Date(time);

    }

    public Date getTomorrow() {

        long time = this.fiducialDate.getTime() + 60 * 60 * 24 * 1000;

        return new Date(time);

    }

    /**
     *
     * 获取季度的最后一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDayOfQuarter(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.MONTH, offset * 3);

        int mon = cal.get(Calendar.MONTH);

        if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {

            cal.set(Calendar.MONTH, Calendar.MARCH);

            cal.set(Calendar.DAY_OF_MONTH, 31);

        }

        if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {

            cal.set(Calendar.MONTH, Calendar.JUNE);

            cal.set(Calendar.DAY_OF_MONTH, 30);

        }

        if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {

            cal.set(Calendar.MONTH, Calendar.SEPTEMBER);

            cal.set(Calendar.DAY_OF_MONTH, 30);

        }

        if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {

            cal.set(Calendar.MONTH, Calendar.DECEMBER);

            cal.set(Calendar.DAY_OF_MONTH, 31);

        }

        return cal.getTime();

    }

    /**
     *
     * 获取月的最后一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDayOfMonth(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.MONTH, offset);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();

    }

    /**
     *
     * 获取月的最后一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDayOfMonth(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.MONTH, offset + 1);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.getTime();

    }

    /**
     *
     * 获取旬的第一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDayOfTendays(int offset) {

        cal.setTime(this.fiducialDate);

        int day = cal.get(Calendar.DAY_OF_MONTH);

        if (day >= 21) {

            day = 21;

        } else if (day >= 11) {

            day = 11;

        } else {

            day = 1;

        }

        if (offset > 0) {

            day = day + 10 * offset;

            int monOffset = day / 30;

            day = day % 30;

            cal.add(Calendar.MONTH, monOffset);

            cal.set(Calendar.DAY_OF_MONTH, day);

        } else {

            int monOffset = 10 * offset / 30;

            int dayOffset = 10 * offset % 30;

            if ((day + dayOffset) > 0) {

                day = day + dayOffset;

            } else {

                monOffset = monOffset - 1;

                day = day - dayOffset - 10;

            }

            cal.add(Calendar.MONTH, monOffset);

            cal.set(Calendar.DAY_OF_MONTH, day);

        }

        return cal.getTime();

    }

    /**
     *
     * 获取旬的最后一天
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDayOfTendays(int offset) {

        Date date = getFirstDayOfTendays(offset + 1);

        cal.setTime(date);

        cal.add(Calendar.DAY_OF_MONTH, -1);

        return cal.getTime();

    }

    /**
     *
     * 获取周的第一天(MONDAY)
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDayOfWeek(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.DAY_OF_MONTH, offset * 7);

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return cal.getTime();

    }

    /**
     *
     * 获取周的最后一天(SUNDAY)
     *
     *
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDayOfWeek(int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(Calendar.DAY_OF_MONTH, offset * 7);

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        cal.add(Calendar.DAY_OF_MONTH, 6);

        return cal.getTime();

    }

    /**
     *
     * 获取指定时间范围的第一天
     *
     *
     *
     * @param dateRangeType
     *
     *            时间范围类型
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getFirstDate(int dateRangeType, int offset) {

        return null;

    }

    /**
     *
     * 获取指定时间范围的最后一天
     *
     *
     *
     * @param dateRangeType
     *
     *            时间范围类型
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date getLastDate(int dateRangeType, int offset) {

        return null;

    }

    /**
     *
     * 根据日历的规则，为基准时间添加指定日历字段的时间量
     *
     *
     *
     * @param field
     *
     *            日历字段, 使用Calendar类定义的日历字段常量
     *
     * @param offset
     *
     *            偏移量
     *
     * @return Date
     */

    public Date add(int field, int offset) {

        cal.setTime(this.fiducialDate);

        cal.add(field, offset);

        return cal.getTime();

    }
    /**
     *
     * 根据日历的规则，为基准时间添加指定日历字段的单个时间单元
     *
     *
     *
     * @param field
     *
     *            日历字段, 使用Calendar类定义的日历字段常量
     *
     * @param up
     *
     *            指定日历字段的值的滚动方向。true:向上滚动 / false:向下滚动
     *
     * @return Date
     */

    public Date roll(int field, boolean up) {

        cal.setTime(this.fiducialDate);

        cal.roll(field, up);

        return cal.getTime();

    }

    /**
     *
     * 把字符串转换为日期
     *
     *
     *
     * @param dateStr
     *
     *            日期字符串
     *
     * @param format
     *
     *            日期格式
     *
     * @return Date
     */

    public static Date strToDate(String dateStr, String format) {

        Date date = null;

        if (dateStr != null && (!dateStr.equals(""))) {

            DateFormat df = new SimpleDateFormat(format);

            try {

                date = df.parse(dateStr);

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

        return date;

    }

    /**
     *
     * 把字符串转换为日期，日期的格式为yyyy-MM-dd HH:ss
     *
     *
     *
     * @param dateStr
     *
     *            日期字符串
     *
     * @return Date
     */

    public static Date strToDate(String dateStr) {

        Date date = null;

        if (dateStr != null && (!dateStr.equals(""))) {

            if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {

                dateStr = dateStr + " 00:00";

            } else if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}")) {

                dateStr = dateStr + ":00";

            } else {

                System.out.println(dateStr + " 格式不正确");

                return null;

            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss");

            try {

                date = df.parse(dateStr);

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

        return date;

    }

    /**
     *
     * 把日期转换为字符串
     *
     *
     *
     * @param date
     *
     *            日期实例
     *
     * @param format
     *
     *            日期格式
     *
     * @return Date
     */

    public static String dateToStr(Date date, String format) {

        return (date == null) ? "" : new SimpleDateFormat(format).format(date);

    }

    public static String dateToStr(Date date) {

        return (date == null) ? ""
                : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

    }

    /**
     *
     * 取得当前日期 年-月-日
     *
     *
     *
     * @return Date
     */

    public static String getCurrentDate() {

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        return f.format(Calendar.getInstance().getTime());

    }
    public static String getCurrentMonth() {

        DateFormat f = new SimpleDateFormat("yyyy-MM");

        return f.format(Calendar.getInstance().getTime());

    }
    public static void main(String[] args) {



//		DateUtil dateHelper = DateUtil.getInstance();
//
//		/* Year */
//
//		for (int i = -5; i <= 5; i++) {
//
//			System.out.println("FirstDayOfYear(" + i + ") = "
//
//			+ dateHelper.getFirstDayOfYear(i));
//
//			System.out.println("LastDayOfYear(" + i + ") = "
//
//			+ dateHelper.getLastDayOfYear(i));
//
//		}
//
//		/* Quarter */
//
//		for (int i = -5; i <= 5; i++) {
//
//			System.out.println("FirstDayOfQuarter(" + i + ") = "
//
//			+ dateHelper.getFirstDayOfQuarter(i));
//
//			System.out.println("LastDayOfQuarter(" + i + ") = "
//
//			+ dateHelper.getLastDayOfQuarter(i));
//
//		}
//
//		/* Month */
//
//		for (int i = -5; i <= 5; i++) {
//
//			System.out.println("FirstDayOfMonth(" + i + ") = "
//
//			+ dateHelper.getFirstDayOfMonth(i));
//
//			System.out.println("LastDayOfMonth(" + i + ") = "
//
//			+ dateHelper.getLastDayOfMonth(i));
//
//		}
//
//		/* Week */
//
//		for (int i = -5; i <= 5; i++) {
//
//			System.out.println("FirstDayOfWeek(" + i + ") = "
//
//			+ dateHelper.getFirstDayOfWeek(i));
//
//			System.out.println("LastDayOfWeek(" + i + ") = "
//
//			+ dateHelper.getLastDayOfWeek(i));
//
//		}
//
//		/* Tendays */
//
//		for (int i = -5; i <= 5; i++) {
//
//			System.out.println("FirstDayOfTendays(" + i + ") = "
//
//			+ dateHelper.getFirstDayOfTendays(i));
//
//			System.out.println("LastDayOfTendays(" + i + ") = "
//
//			+ dateHelper.getLastDayOfTendays(i));
//
//		}
//		System.out.println(dateToStr(DateUtil.getInstance(getDate(null,null)).add(DAY, 7),"yyyy-MM-dd"));
        long dates=DateUtil.getDistanceDays(DateUtil.getDate("2014-2-13", "yyyy-MM-dd"),DateUtil.getDate("2014-2-20", "yyyy-MM-dd"));
        System.out.println(dates);
    }

    /**
     *
     * 取当前日期的字符串形式,"XXXX年XX月XX日"
     *
     *
     *
     * @return java.lang.String
     */

    public static String getPrintDate() {

        String printDate = "";

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        printDate += calendar.get(Calendar.YEAR) + "年";

        printDate += (calendar.get(Calendar.MONTH) + 1) + "月";

        printDate += calendar.get(Calendar.DATE) + "日";

        return printDate;

    }

    /**
     *
     * 将指定的日期字符串转化为日期对象
     *
     *
     *
     * @param dateStr
     *
     *            日期字符串
     *
     * @return java.util.Date
     */

    public static Date getDate(String dateStr, String format) {

        if (dateStr == null) {

            return new Date();

        }

        if (format == null) {

            format = "yyyy-MM-dd";

        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {

            Date date = sdf.parse(dateStr);

            return date;

        } catch (Exception e) {

            return null;

        }

    }

    /**
     *
     * 从指定Timestamp中得到相应的日期的字符串形式 日期"XXXX-XX-XX"
     *
     *
     *
     * @param dateTime
     *
     * @return 、String
     */

    public static String getDateFromDateTime(Timestamp dateTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(dateTime).toString();

    }

    /**
     *
     * 得到当前时间 return java.sql.Timestamp
     *
     *
     *
     * @return Timestamp
     */

    public static Timestamp getNowTimestamp() {

        long curTime = System.currentTimeMillis();

        return new Timestamp(curTime);

    }

    public static long getDistanceDays(Date fdate, Date sdate) {
        Calendar cal = Calendar.getInstance();
//		      cal.add(Calendar.DATE,-3);

        long days=0;
        cal.setTime(fdate);
        Date time1 = cal.getTime();
        cal.setTime(sdate);
        Date time2 = cal.getTime();
        long diff =time2.getTime() - time1.getTime(); ;

        days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

    /**
     * 比较字符串时间大小
     * @param s1 "yyyy-MM-dd HH:mm:ss"
     * @param s2 "yyyy-MM-dd HH:mm:ss"
     * @return true - s1>=s2      false s1 < s2
     */
    public static boolean compareTime(String s1, String s2){
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Calendar c1=java.util.Calendar.getInstance();
        java.util.Calendar c2=java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(s1));
            c2.setTime(df.parse(s2));
        }catch(java.text.ParseException e){
            System.err.println("格式不正确");
        }
        int result=c1.compareTo(c2);
        if(result==0){
            return false;
        } else if(result<0) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 获得星期几
     * @return
     * @throws Exception
     */
    public static int dayForWeek(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(date));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     *
     * @param date
     * @param n 星期几 1-7
     * @return
     */
    public static List<String> getWeekDays(Date date, int n){

        List<String> dates = new ArrayList<>();
        List<String> datesBefore = new ArrayList<>();
        Date need;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        calendar.setTime(date);
        for(int i=1;i<n;i++){
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            need = calendar.getTime();
            String nextDate = sdf.format(need);
            datesBefore.add(nextDate);
        }
        for(int i=datesBefore.size()-1;i>=0;i--){
            dates.add(datesBefore.get(i));
        }
        String dateStr = sdf.format(date);
        dates.add(dateStr);
        calendar.setTime(date);
        for(int i=n;i<7;i++){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            need = calendar.getTime();
            String nextDate = sdf.format(need);
            dates.add(nextDate);
        }

        return dates;
    }

    public static String getLastWeekDay(){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return f.format(calendar.getTime());
    }
    public static String getDayOfThisWeek(String day) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int i= Integer.parseInt(day);
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + i);
        return f.format(c.getTime());
    }
    public static String getDayOfThisMonth(String day){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int i= Integer.parseInt(day);
        Calendar c = Calendar.getInstance();
        int day_of_month=c.get(Calendar.DAY_OF_MONTH);
        if(day_of_month==0)
            day_of_month=31;
        c.add(Calendar.DATE, -day_of_month + i);
        return f.format(c.getTime());
    }
    public static String getTimeOfDay(String option){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i= Integer.parseInt(option);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MINUTE, i*15-15);
        return f.format(c.getTime());
    }
    public static String getMonthDayOfThisYear(String month, String day){
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int m= Integer.parseInt(month);
        int d= Integer.parseInt(day);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, m-1);
        c.set(Calendar.DATE, d);
        return f.format(c.getTime());
    }

    public static Date getDayOfThisWeek0(int i) {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, - day_of_week + i);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getFirstDayOfThisMonth() {
        Calendar c = Calendar.getInstance();
        int minDay = c.getActualMinimum(Calendar.DAY_OF_MONTH) - 1;
        c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), minDay, 0, 0, 0);
        return c.getTime();
    }

    public static Date getLastDayOfThisMonth() {
        Calendar c = Calendar.getInstance();
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), maxDay, 23, 59, 59);
        return c.getTime();
    }

    public static Date getFirstDayOfThisSeason() {
        Calendar c = Calendar.getInstance();
        int minDay = c.getActualMinimum(Calendar.DAY_OF_MONTH) - 1;
        int minMonth = getSeasonFirstMonth(c.get(Calendar.MONTH));
        c.set( c.get(Calendar.YEAR), minMonth, minDay, 0, 0, 0);
        return c.getTime();
    }

    public static Date getLastDayOfThisSeason() {
        Calendar c = Calendar.getInstance();
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_YEAR);
        int maxMonth = getSeasonLastMonth(c.get(Calendar.MONTH));
        c.set( c.get(Calendar.YEAR), maxMonth, maxDay, 23, 59, 59);
        return c.getTime();
    }

    private static int getSeasonFirstMonth(int month) {
        if (month >= 1 && month <=3) {
            return 1;
        } else if (month >= 4 && month <= 6 ) {
            return 4;
        } else if (month >= 7 && month <= 9) {
            return 7;
        } else if (month >= 10 && month <= 12) {
            return 10;
        } else {
            return -1;
        }
    }

    private static int getSeasonLastMonth(int month) {
        if (month >= 1 && month <= 3) {
            return 3;
        } else if (month >= 4 && month <= 6 ) {
            return 6;
        } else if (month >= 7 && month <= 9) {
            return 9;
        } else if (month >= 10 && month <= 12) {
            return 12;
        } else {
            return -1;
        }
    }

    public static Date getFirstDayOfThisYear() {
        Calendar c = Calendar.getInstance();
        int minDay = c.getActualMinimum(Calendar.DAY_OF_YEAR) - 1;
        c.set( c.get(Calendar.YEAR), 1, minDay, 0, 0, 0);
        return c.getTime();
    }

    public static Date getLastDayOfThisYear() {
        Calendar c = Calendar.getInstance();
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_YEAR);
        c.set( c.get(Calendar.YEAR), 1, maxDay, 23, 59, 59);
        return c.getTime();
    }
}
