package com.wynlink.park_platform.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;


public final class DateFormatUtil {



	
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String dateFormat(Date d){
		return null != d ? df.format(d) : null;
	}
	
	public static String dateFormatSimple(Date d){
        return null != d ? dfs.format(d) : null;
    }
	
	public static String dateFormat(Date d, String fmt){
		return null != d && null != fmt ? new SimpleDateFormat(fmt).format(d) : null;
	}
	
	
	/**
	 * 给指定时间加上月数得到新的日期
	 * @param months 月数
	 */
	public static Date addMonths(Date oldDate,int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(Calendar.MONTH, months);
	
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 
	 * @param str
	 * @return
	 *
	 */
	public static Date strFormat(String str){
		try {
			return null != str ? df.parse(str) : null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * 
	 * @param str
	 * @param fstr
	 * @return
	 */
	public static Date strFormat(String str, String fstr){
		try {
			return null != str ? new SimpleDateFormat(fstr).parse(str) : null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String strFormatJg(Integer str){
		Date d = new Date();
		return df.format(new Date(d.getTime() + (long) str * 1000));
	}

	/**
	 * long型日期转string格式
	 * @param str
	 * @return
	 */
	public static String longFormat(String str){
		if (!StringUtils.isEmpty(str)) {
			Long time = new Long(str);
			return df.format(time);
		}
		return null;
	}
	
	/**
	 * string格式转long型日期
	 * @param str
	 * @return
	 */
	public static Long strToLongFormat(String str){
		Long time = null;
		if (!StringUtils.isEmpty(str)) {
			try {
				Date date = df.parse(str);
				time = null != str ? date.getTime() : null;
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return time;
	}
	
	
	/**
	 * 时间减一秒
	 */
	public static String minusOneSeconds(String dateStr){
		Date date = DateFormatUtil.strFormat(dateStr, "yyyyMMddHHmmss");
		return df1.format(date.getTime() -1*1000);
	}
	
	/**
	 * 时分秒与当前时间的年月日拼接
	 * @param hms 时分秒
	 * @return
	 * @throws ParseException
	 */
	public static String splitFormat(String hms){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		return df.format(new Date()) + hms;
		
	}
	
	public static long differenceSeconds(String dateStr1, String dateStr2){
		Date date1 = DateFormatUtil.strFormat(dateStr1, "yyyyMMddHHmmss");
		Date date2 = DateFormatUtil.strFormat(dateStr2, "yyyyMMddHHmmss");
		long temp = date2.getTime() - date1.getTime();
		return temp/1000;
	}
	
	
	public static void main(String[] args) {
		
//		System.out.println(toArrFormat("2017-05-01"));
//		String time1 = "2016-12-29 00:00:00";
//		String time2 = "2016-12-29 23:59:59";
//		
//		System.out.println(strToLongFormat(time1));
//		System.out.println(strToLongFormat(time2));
//		
//		System.out.println(strToArrFormat("2016-12-29"));
//		
//		System.out.println(longFormat("1483631999000"));
		System.out.println(minusOneSeconds("20170601162002"));
	}
}
