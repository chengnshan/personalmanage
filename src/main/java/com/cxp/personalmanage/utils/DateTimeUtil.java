package com.cxp.personalmanage.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {

	private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN_TWO = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_PATTERN_TWO = "yyyy/MM/dd";

	/***
	 * 获取当前时间的毫秒数,是自1970-1-01 00:00:00.000 到当前时刻的时间距离 如:1445090702909 多线程,有很多重复值
	 * 
	 * @return 返回类型为String
	 */
	public static String getCurrentTimeMillis() {
		return String.valueOf(System.currentTimeMillis());
	}

	/***
	 * 获取当前时间的纳秒,1ms=1000000ns 精准1000000倍的取纳秒的方法 多线程,基本无重复值
	 * 
	 * @return
	 */
	public static String getNanoTime() {
		return String.valueOf(System.nanoTime());
	}

	/***
	 * 返回当前年，如：2015
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/***
	 * 返回当前月,如：07
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			return "0" + month;
		}
		return month;
	}

	/***
	 * 返回当前日,如：26
	 * 
	 * @return
	 */
	public static String getCurrentDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 把日期字符串转换成指定格式的日期
	 * 
	 * @param pattern
	 * @param dateString
	 * @return Date
	 */
	public static Date parse(String pattern, String dateString) {
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateString);
		} catch (ParseException e) {
			log.error("时间解析错误 "+e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 把时间转换成指定格式的时间字符串
	 * 
	 * @param pattern
	 * @param date
	 * @return String
	 */
	public static String format(String pattern, Date date) {
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} catch (Exception e) {
			log.error("时间解析错误 "+e.getMessage(), e);
			return null;
		}
		
	}
	
	/***
	 * 返回当前时间 年月，如：2015-01
	 * 
	 * @return
	 */
	public static String getDateTime() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String datetime = year + "-" + month;
		return datetime;
	}

	/***
	 * 返回当前时间的前一月份，如：2015-01前一月份是2014-12
	 * 
	 * @return
	 */
	public static String getMonthAgo() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (month.equals("1")) {
			year = String.valueOf(calendar.get(Calendar.YEAR) - 1);
			month = "12";
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH));
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		String datetime = year + "-" + month;
		return datetime;
	}

	/***
	 * 返回当前时间的前年，如：2015前一年是2014
	 * 
	 * @return
	 */
	public static String getYearAgo() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR) - 1);
		return year;
	}

	/***
	 * 返回查询时间
	 * 
	 * @return
	 */
	static Calendar c = Calendar.getInstance();
	// 获取当前月
	final int month = c.get(Calendar.MONTH) + 1;

	public int getmonth() {
		return month;
	}

	// 获取到当前年
	final int year = c.get(Calendar.YEAR);

	public int getYear() {
		return year;
	}

	// 获取上一个季度
	public int getCurrentQuarger() {
		if (month >= 1 && month <= 3)
			return 4;
		if (month >= 4 && month <= 6)
			return 1;
		if (month >= 7 && month <= 9)
			return 2;
		return 3;
	}

	// 获取上一个月
	public int getCurrentMonthAgo() {
		if (month == 1) {
			return 12;
		}
		int currentMonth = month - 1;
		return currentMonth;
	}

	// 获取上一年
	public int getCurrentYearAgo() {
		return year - 1;
	}

	// 获取上个月的最后一天
	public static String getMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		String data = df.format(cal.getTime());
		return data;

	}

	// 获取本月的最后一天
	public String getThisMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	/** 获取某月有几天
	 * 
	 * @param pTime
	 * @return
	 */
	public static Integer getThisMonthDay(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			log.error("DatetimeUtil.getThisMonthDay:" + e.getMessage());
		}
		System.out.println(
				"------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/** 获取上个月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String data = df.format(cal.getTime());
		return data;
	}

	/** 获取本月的第一天
	 * 
	 * @return
	 */
	public String getThisMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String data = df.format(cal.getTime());
		return data;
	}

	/** 获取当前日期是周几
	 * 
	 * @return
	 */
	public static int getDay() {
		int getWeek = c.get(Calendar.DAY_OF_WEEK);
		if (getWeek == 1) {
			return 7;
		} else {
			return getWeek - 1;
		}
	}

	// 获取到本周周一//获取本周周一请用此方法
	public String getMondaytoday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		c.add(Calendar.WEEK_OF_YEAR, 0);
		String data = df.format(c.getTime());
		return data;
	}

	// 获取本周周日
	public String getMondaySumDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		c.add(Calendar.WEEK_OF_YEAR, 0);
		c.add(Calendar.DAY_OF_YEAR, 6);
		String data = df.format(c.getTime());
		System.out.print(data);
		return data;
	}

	// 获取到上个周周一
	public String getMondayLast() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		String data = df.format(cal.getTime());
		return data;
	}
	
	// 获取到上个周周天
	public String getLastSum() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		cal.add(Calendar.DATE, +6);
		String data = df.format(cal.getTime());
		return data;
	}

	// 获取两分钟前
	public String getLastMinute() {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -15);// 15分钟前
		String data = time.format(cal.getTime());
		return data;
	}


	/**
	 * 获取前一天日期
	 * @return
	 */
	public static String getYesterDayDate() {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);// 获取一天前
		String data = time.format(cal.getTime());
		return data;
	}

	/** 获取七天前日期
	 * 
	 * @return
	 */
	public static String getLastweek() {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);// 7前
		String data = time.format(cal.getTime());
		return data;
	}

	// 获取到七个周前周一
	public String getMondayLastSeven() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //
		c.add(Calendar.WEEK_OF_YEAR, -7);
		String data = df.format(c.getTime());
		return data;
	}

	// 获取到半年前时间
	public String getSexMonth() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Calendar.MONDAY - 6, Calendar.DATE);
		String data = df.format(cal.getTime());
		return data;
	}

	/**
	 * @Title getViewTime
	 * @author lize16713
	 * @Description 获取年数
	 */
	public static String getViewTime(Long ms) {
		double yesr = ms / (1000.0 * 60.0 * 60.0 * 24.0 * 30.0 * 12.0);
		String time = String.format("%.1f", yesr);

		if (time.contains("-")) {
			return time = "0 年";
		}

		String[] str = time.split("\\.");

		int i = Integer.parseInt(str[0]);
		int d = Integer.parseInt(str[1]);
		if (i > 0 && d >= 5) {
			time = str[0] + ".5 年";
		} else if (i > 0 && d < 5) {
			time = str[0] + " 年";
		} else if (i <= 0 && d >= 5) {
			time = "1  年";
		} else {
			time = "0.5 年";
		}
		return time;
	}

	/**
	 * 给定时间判断此时间所在周的周一*（周日）
	 **/
	public String convertWeekByDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime()); // 周一
		// cal.add(Calendar.DATE, 6);
		// String imptimeEnd = sdf.format(cal.getTime());//周天
		return imptimeBegin;
	}

	public static class MyDate {
		int day;
		int month;
		int year;
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");

		public MyDate(Date d) {
			this.day = Integer.valueOf(sdf2.format(d));
			this.month = Integer.valueOf(sdf1.format(d));
			this.year = Integer.valueOf(sdf0.format(d));
			;
		}

		public long funtion(MyDate d) {
			int newDay = d.day;
			int newMonth = d.month;
			int newYear = d.year;
			Calendar c1 = Calendar.getInstance();
			c1.set(newYear, newMonth, newDay);
			long n1 = c1.getTimeInMillis();
			Calendar c2 = Calendar.getInstance();
			c2.set(year, month, day);
			long n2 = c2.getTimeInMillis();
			return Math.abs((n1 - n2) / 24 / 3600000);
		}
	}

	/**
	 * 两个时间之间的查（天）
	 * 
	 **/
	public static long getDateDiffe(Date a, Date b) {
		MyDate d1 = new MyDate(a);
		MyDate d2 = new MyDate(b);
		return d1.funtion(d2);
	}

	/**
	 * 获取本月有多少天）
	 * 
	 **/
	public static int getDateOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回给定时间是周几
	 * 
	 * @throws ParseException
	 **/
	public static int getDayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			log.error("DatetimeUtil.getDayForWeek:" + e.getMessage());
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 获取给定时间的星期
	 * 
	 * @param time
	 * @return
	 */
	public static String getWeek(Date time) {
		if (time == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 1) {
			return "星期一";
		}
		if (week == 2) {
			return "星期二";
		}
		if (week == 3) {
			return "星期三";
		}
		if (week == 4) {
			return "星期四";
		}
		if (week == 5) {
			return "星期五";
		}
		if (week == 6) {
			return "星期六";
		}
		if (week == 0) {
			return "星期日";
		}
		return null;
	}

	/**
	 * 获取当前时间前num天的日期字符串
	 * 
	 * @param num
	 * @return
	 */
	public static List<String> getDateList(int num) {
		List<String> dateList = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateList.add(sdf.format(c.getTime()));
		if (num <= 0) {
			return dateList;
		}

		for (int i = 1; i < num; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -i);
			dateList.add(sdf.format(calendar.getTime()));
			calendar.clear();
		}
		return dateList;
	}

}
