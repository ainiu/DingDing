package com.ghsh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ghsh.code.exception.AppViewException;

public class DateFormatUtil {

	private static final String formatA = "yyyy-MM-dd HH:mm";
	private static final String formatB = "yyyy-MM-dd";

	public static String formatA(long time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatA);
		return dateFormat.format(new Date(time));
	}

	public static String formatB(long time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatB);
		return dateFormat.format(new Date(time));
	}

	public static String formatB(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatB);
		return dateFormat.format(date);
	}

	public static Date formatB(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatB);
		Date date = new Date();
		try {
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
		return date;
	}

	public static String getTimeFromInt(long time) {
		if (time <= 0) {
			return "已结束";
		}
		long day = time / (1 * 60 * 60 * 24);
		long hour = time / (1 * 60 * 60) % 24;
		long minute = time / (1 * 60) % 60;
		long second = time / (1) % 60;
		return day + "天" + hour + "时" + minute + "分" + second + "秒";
	}
	
	public static String getTimeFromIntA(long time) {
		if (time <= 0) {
			return "已结束";
		}
		long day = time / (1 * 60 * 60 * 24);
		long hour = (day*24)+(time / (1 * 60 * 60) % 24);
		long minute = time / (1 * 60) % 60;
		long second = time / (1) % 60;
		return hour + ":" + minute + ":" + second;
	}
	
	public static String getTimeFromIntB(long time) {
		if (time <= 0) {
			return "已结束";
		}
		long day = time / (1 * 60 * 60 * 24);
		long hour = time / (1 * 60 * 60) % 24;
		long minute = time / (1 * 60) % 60;
		long second = time / (1) % 60;
		return day+"天"+hour + "时:" + minute + "分:" + second+"秒";
	}


	public static String addDay(String date, int day) {
		if (date == null || date.equals("") || date.equals("0")) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatB);
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			AppViewException.onViewException(e);
		}
		calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		return DateFormatUtil.formatB(calendar.getTime());
	}
}
