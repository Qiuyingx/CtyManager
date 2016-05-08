package com.yard.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Title: DateUtil.java
 * @Description:时间处理
 * @author Wangchaoyong
 * @date 上午11:25:53
 * @version V1.0
 */
public class DateUtil {
	public static final long DATE_BASE = 946828800000l;// 基础时间 取 2000-01-03
	// 00:00:00 星期1

	public static final String DATE_FORMAR_STRING = "yyyy-MM-dd";// 时间格式化字符串
	public static final String DATE_MM_DD_FORMAR_STRING = "MM月dd日";// 时间格式化字符串
	public static final String DATETIME_FORMAR_STRING = "yyyy-MM-dd HH:mm:ss";// 时间格式化字符串
	public static final String TIME_FORMAR_STRING = "HH:mm:ss";// 时间格式化字符串
	public static final String DATE_NOLINE_STRING = "yyyyMMdd";// 时间格式化字符串
	public static final String DATE_YEAL_MM_DD_STRING = "yyyy年MM月dd日HH:mm";// 时间格式化字符串

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAR_STRING);
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_FORMAR_STRING);
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAR_STRING);
	private static final SimpleDateFormat timeFormatMD = new SimpleDateFormat(DATE_MM_DD_FORMAR_STRING);
	private static final SimpleDateFormat dateNoLineFormat = new SimpleDateFormat(DATE_NOLINE_STRING);
	private static final SimpleDateFormat dateFormatYMD = new SimpleDateFormat(DATE_YEAL_MM_DD_STRING);

	/**
	 * yyyy年MM月dd日HH:mm
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateYMD(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateFormatYMD.format(date);
		}
	}

	/**
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatNoLineDate(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateNoLineFormat.format(date);
		}
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat(format).format(date);
		}
	}
	
	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date);
		}
	}

	/**
	 * MM月dd日
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateMD(Date date) {
		if (date == null) {
			return "";
		} else {
			return timeFormatMD.format(date);
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		} else {
			return datetimeFormat.format(date);
		}
	}

	/**
	 * HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (date == null) {
			return "";
		} else {
			return timeFormat.format(date);
		}
	}

	/**
	 * 时间转换
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            格式
	 * @return String
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		}

	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 yyyy-MM-dd
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return dateFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 HH:mm:ss
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseTime(String dateString) throws ParseException {
		return timeFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型 转换后的时间格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateString) throws ParseException {
		return datetimeFormat.parse(dateString);
	}

	/**
	 * 字符串转换成时间类型
	 * 
	 * @param dateString
	 *            时间
	 * @param format
	 *            格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String dateString, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateString);
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字
	 * 
	 * @param date
	 *            传入时间
	 * @return 串
	 */
	public static String getStringForDate(Date date) {

		String result = "";
		Calendar inputdate = Calendar.getInstance();
		inputdate.setTime(date);
		Calendar now = Calendar.getInstance();

		if (now.get(Calendar.YEAR) == inputdate.get(Calendar.YEAR) && now.get(Calendar.MONTH) == inputdate.get(Calendar.MONTH)) {
			int day = now.get(Calendar.DATE) - inputdate.get(Calendar.DATE);
			switch (day) {
			case 0:
				break;
			case 1:
				result = "昨天";
				break;
			default:
				result = format(inputdate.getTime(), "yyyy年MM月dd日");
				break;
			}
			result = result + format(inputdate.getTime(), "HH:mm");
		} else {
			result = format(inputdate.getTime(), "yyyy年MM月dd日HH:mm");

		}

		return result;
	}

	/**
	 * 通过传入时间与当前时间比较，获得时间差值形成文字(简易)
	 * 
	 * @param date
	 *            传入时间
	 * @return String
	 */
	public static String getSimpleStringForDate(Date date) {
		String result = "";
		Calendar inputdate = Calendar.getInstance();
		inputdate.setTime(date);
		Calendar now = Calendar.getInstance();

		int day = now.get(Calendar.DATE) - inputdate.get(Calendar.DATE);
		switch (day) {
		case 0:
			result = format(inputdate.getTime(), "HH:mm");
			break;
		default:
			result = format(inputdate.getTime(), "yyyy-MM-dd");
			break;
		}

		return result;
	}

	/**
	 * 获得与当前系统时间的相差天数
	 * 
	 * @param date
	 * @return 相差天数 如果传入时间大于当前系统时间为负数
	 */
	public static long compareDate(long date) {
		long result = 0;
		long now = System.currentTimeMillis() - DateUtil.DATE_BASE;// 系统时间 -
		// 基数时间
		long inputdate = date - DateUtil.DATE_BASE;// 最后天数时间

		long day = 1000 * 3600 * 24;
		result = now / day - inputdate / day;
		return result;

	}

	/**
	 * 获得传入两个时间的相差天数
	 * 
	 * @param date
	 * @return 相差天数
	 */
	public static long compareDate(long startdate, long enddate) {
		long result = 0;
		long starttime = startdate - DateUtil.DATE_BASE;// 系统时间 -
		long endtime = enddate - DateUtil.DATE_BASE;// 最后天数时间

		long day = 1000 * 3600 * 24;
		result = endtime / day - starttime / day;
		return result;

	}

	/**
	 * 获得与当前系统时间的相差周数
	 * 
	 * @param date
	 * @return 相差周数 如果传入时间大于当前系统时间为负数
	 */
	public static long compareWeek(long date) {
		long result = 0;
		long now = System.currentTimeMillis() - DateUtil.DATE_BASE;// 系统时间 -
		// 基数时间
		long inputdate = date - DateUtil.DATE_BASE;// 最后天数时间

		long week = 1000 * 3600 * 24 * 7;
		result = now / week - inputdate / week;
		return result;
	}

	/**
	 * 获得与当前系统时间的相差月数
	 * 
	 * @param date
	 * @return 相差月数 如果传入时间大于当前系统时间为负数
	 * 
	 * @author 刘宇
	 * @date 2011-05-18
	 */
	public static long compareMonth(long date) {
		Calendar now = Calendar.getInstance();
		Calendar input = Calendar.getInstance();
		input.setTimeInMillis(date);
		int yearnow = now.get(Calendar.YEAR);
		int monthnow = now.get(Calendar.MONTH);
		int yearinput = input.get(Calendar.YEAR);
		int monthinput = input.get(Calendar.MONTH);
		return yearnow * 12 + monthnow - yearinput * 12 - monthinput;

	}

	/**
	 * 计算年龄
	 * 
	 * @param date
	 *            出生日期
	 * @return
	 */
	public static int calcAge(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar now = Calendar.getInstance();
		int age = now.get(Calendar.YEAR);
		now.setTime(date);
		return age - now.get(Calendar.YEAR);
	}

	/**
	 * 根据传入时间 获得本周第一天（周一)的时间
	 * 
	 * @param date
	 * @return 返回时间 时分秒毫秒为0
	 */
	public static long getFristDayForWeek(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int tempday = calendar.get(Calendar.DAY_OF_WEEK);
		switch (tempday) {
		case 1:
			// 星期天
			calendar.add(Calendar.DATE, -6);
			break;
		default:
			// 周一到周六
			calendar.add(Calendar.DATE, -(tempday - 2));
			break;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 根据传入时间 获得本周第一天（周一)的时间
	 * 
	 * @return 返回时间 时分秒毫秒为0
	 */
	public static long getFristDayForWeek() {
		return getFristDayForWeek(System.currentTimeMillis());
	}

	/**
	 * 根据传入时间 获得本周最后一天（周日)的时间
	 * 
	 * @param date
	 * @return 返回时间 时分秒毫秒为23:59:59 999
	 */
	public static long getLastDayForWeek(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int tempday = calendar.get(Calendar.DAY_OF_WEEK);
		switch (tempday) {
		case 1:
			// 星期天
			break;
		default:
			// 周一到周六
			calendar.add(Calendar.DATE, 8 - tempday);
			break;
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime().getTime();
	}

	/**
	 * 根据传入时间 获得本周最后一天（周日)的时间
	 * 
	 * @return 返回时间 时分秒毫秒为23:59:59 999
	 */
	public static long getLastDayForWeek() {
		return getLastDayForWeek(System.currentTimeMillis());
	}

	/**
	 * 根据传入时间 获得此时间所在月的 第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFristDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在月的 最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在年的 第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearFristDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 根据传入时间 获得此时间所在年的 最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearLastDay(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 返回查询的开始时间
	 * 
	 * @param date
	 *            查询时间
	 * @return
	 */
	public static String getBeginTime(Date date) {
		return date == null ? "2011-12-01 00:00:00" : datetimeFormat.format(date);
	}

	/**
	 * 返回查询的结束时间
	 * 
	 * @param date
	 *            查询时间
	 * @return
	 */
	public static String getEndTime(Date date) {
		return date == null ? datetimeFormat.format(System.currentTimeMillis()) : datetimeFormat.format(date);
	}

	public static String currentTime() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	public static String currentDate() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	/**
	 * 两个时间比较，是否相差24小时
	 * 
	 * @param old
	 * @param news
	 * @return true 超过24小时 false 未超过
	 */
	public static boolean compleDate(Date old, Date news) {
		if (old != null && news != null) {
			long oldLong = old.getTime();
			long newsLong = news.getTime();
			long cs = newsLong - oldLong;
			int s = 24 * 60 * 60 * 1000;
			if (cs > s) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取字符串时间的毫秒数
	 * @param time 文本时间 如：2015-06-08 22:15:20
	 * @return  
	 */
	public static long getMillisTime(String time) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(parseDateTime(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.getTimeInMillis();
	}
	
	public static void main(String[] args) {
		long s;
		try {
			s = parseDateTime("2015-07-20 15:09:20").getTime();
			System.out.println("===>>s:"+s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
