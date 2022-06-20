package com.learn.admin.utils;


import com.learn.admin.common.constant.SystemConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *日期工具类
 */
public class DateUtil {

	/**
	 * 把日期转化为字符类型
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param pattern 日期类型
	 * @return String
	 */
	public static String nowStringDate(String pattern) {
		return dateToString(new Date(), pattern);
	}

	/**
	 * 得到当前日期 
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param pattern 日期类型
	 * @return Date
	 */
	public static Date nowDate(String pattern) {
		String nowStringDate = nowStringDate(pattern);
		return stringToDate(nowStringDate, pattern);
	}

	/**
	 *  日期转化字符
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @param pattern 日期类型
	 * @param locale
	 * @return String
	 */
	public static String dateToString(Date date, String pattern, Locale locale) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
			return sdf.format(date); 
		}
		catch(Exception e){
			return "";
		}
	}

	/**
	 * 日期转化字符
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @param pattern 日期类型
	 * @return String
	 */
	public static String dateToString(Date date, String pattern) {
		Locale locale = Locale.getDefault();
		return dateToString(date, pattern, locale);
	}

	/**
	 * 字符类型日期转化为长类型
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param strDate 开始日期
	 * @param pattern 日期类型
	 * @param locale
	 * @throws ParseException
	 * @return long
	 */
	public static long stringToLong(String strDate, String pattern, Locale locale)throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		Date date = sdf.parse(strDate);
		return date.getTime();
	}

	/**
	 * 字符类型日期转化为长类型 
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param strDate 开始日期
	 * @param pattern 日期类型
	 * @throws ParseException
	 * @return long
	 */
	public static long stringToLong(String strDate, String pattern) throws ParseException {
		Locale locale = Locale.CHINESE;
		return stringToLong(strDate, pattern, locale);
	}

	/**
	 * 字符转化为日期
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param strDate 开始日期
	 * @param pattern 日期类型
	 * @return Date
	 */
	public static Date stringToDate(String strDate, String pattern) {
		try{
			long ltime = stringToLong(strDate, pattern);
			return new Date(ltime);
		}
		catch(Exception ex){
			return null;
		}
	}

	/**
	 *  字符转化为日期
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param strDate 开始日期
	 * @param pattern 日期类型
	 * @param otherPattern 其他日期类型
	 * @return Date
	 */
	public static Date stringToDate(String strDate, String pattern, String otherPattern) {
		try{
			long ltime = stringToLong(strDate, pattern);
			return new Date(ltime);
		}
		catch(Exception ex){
			try{
				long ltime = stringToLong(strDate, otherPattern);
				return new Date(ltime);
			}
			catch(Exception e){
				return null;
			}
		}
	}

	/**
	 *  格式化日期
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @param pattern 日期类型
	 * @return Date
	 */
	public static Date formatDate(Date date, String pattern) {
		String s = dateToString(date, pattern);
		return stringToDate(s, pattern);
	}

	/**
	 *  取得当前日期的天分
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @return int
	 */
	public static int getEmbodyDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int embodyDay = calendar.get(Calendar.DAY_OF_MONTH);
		return embodyDay;

	}

	/**
	 * 取得当前日期的月份
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @return int
	 */
	public static int getEmbodyMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;

	}

	/**
	 * 取得当前日期的年份
	 * 
	 * @author 焦运磊
	 * @date 2011-4-6
	 * @param date 日期
	 * @return int
	 */
	public static int getEmbodyYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 日期的增减
	 *
	 * @param date 传入日期
	 * @param type 0:天，1:月，2：年
	 * @param number 具体数值，若要减则传入负数
	 * @return Date
	 * @author luoqi 
	 * @date 2013-7-25
	 */
	public static Date getDate(Date date,int type,int number) {
		if (null == date) {  
		   return null;  
		}
		int xType = 0;
		switch (type) {
		case 0:
			xType = Calendar.DATE;
			break;
		case 1:
			xType = Calendar.MONTH;
			break;
		case 2:
			xType = Calendar.YEAR;
			break;
		default:
			return null;
		}
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);   //设置当前日期  
        calendar.add(xType, number); //
        return formatDate(calendar.getTime(), SystemConstant.DATE_PATTEN);
	}
	
	/**
	 * 生成日期
	 *
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return void
	 * @author luoqi 
	 * @date 2013-7-25
	 */
	public static Date generateDate(Integer year,Integer month, Integer day) {
		Calendar calendar = Calendar.getInstance();
		if(year != null) {
			calendar.set(Calendar.YEAR, year);
		}
		if(month != null) {
			calendar.set(Calendar.MONTH, month-1);
		}
		if(day != null) {
			calendar.set(Calendar.DATE, day);
		}
		return formatDate(calendar.getTime(), SystemConstant.DATE_PATTEN);
	}
	
	/**
	 * 获取月份有多少天
	 *
	 * @return int
	 * @author luoqi 
	 * @date 2013-8-14
	 */
	public static int getMonthDays(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		//把日期设置为当月第一天
		calendar.set(Calendar.DATE, 1);
		//日期回滚一天，也就是最后一天
		calendar.roll(Calendar.DATE, -1);
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 生产专用，用于判断传入的日期是否属于某一个月的计划。
	 * 
	 *@author 罗琦
	 *@date 2013-11-25
	 *@param date 日期
	 *@return Date
	 */
	public static Date getActualPlanDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if(day > 25) {
			month = month + 1;
		}
		return generateDate(year,month,1);
	}
	
	public static List<String> getDayList(int year, int month) {
		List<String> list = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M");
		for (int i = 1; i <= 25; i++) {
			String dayString = sdf.format(calendar.getTime()) + "-" + i;
			list.add(dayString);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		for (int i = 26; i <= calendar.get(Calendar.DAY_OF_MONTH); i++) {
			String dayString = sdf.format(calendar.getTime()) + "-" + i;
			list.add(dayString);
		}
		return list;
	}
	
	/**
	 * 计算时间差
	 * 
	 * @author 李俊
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param dateType 0:日期格式  1:时间格式
	 * @param type h:小时,m:分钟,s:秒
	 * @return long
	 */
	public static long getDateDifference(String startDate,String endDate,String dateType,String type){
		long result = 0;
		String dType = SystemConstant.DATE_PATTEN;
		if("1".equals(dateType)){
			dType =SystemConstant.TIME_PATTEN;
		}
		long sd = Objects.requireNonNull(DateUtil.stringToDate(startDate, dType)).getTime();
		long ed = Objects.requireNonNull(DateUtil.stringToDate(endDate, dType)).getTime();
		if ("h".equals(type)) {
			result = Math.abs(ed-sd)/(1000*60*60*24);
		}else if ("m".equals(type)) {
			result = Math.abs(ed-sd)/(1000*60);
		}else{
			result = Math.abs(ed-sd);
		}
		return result;
	}
	
	/**
	 * 比较时间大小
	 * @param d1 第一个时间
	 * @param d2 第二个时间
	 * @return int
	 * 0:相等 <br>
	 * 1:大于 <br>
	 * -1:小于
	 */
	 public static int compare_date(String d1, String d2) {
        try {
        	Date date1 = stringToDate(d1, SystemConstant.TIME_PATTEN);
        	Date date2 = stringToDate(d2, SystemConstant.TIME_PATTEN);
			return Long.compare(date1.getTime(), date2.getTime());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	 
	 
	 /**
	  * 得到本周周一
	  * 
	  * @return yyyy-MM-dd
	  */
	 public static String getMondayOfThisWeek() {
		 try {
			  Calendar c = Calendar.getInstance();
			  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			  if (day_of_week == 0){
				  day_of_week = 7;
			  }
			  c.add(Calendar.DATE, -day_of_week + 1);
			  SimpleDateFormat df2 = new SimpleDateFormat(SystemConstant.DATE_PATTEN);
			  return df2.format(c.getTime());
		 } catch (Exception exception) {
	          exception.printStackTrace();
         }
		 return null; 
	 }

	 /**
	  * 得到本周周日
	  * 
	  * @return yyyy-MM-dd
	  */
	 public static String getSundayOfThisWeek() {
		 try{
			 Calendar c = Calendar.getInstance();
			 int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			 if (day_of_week == 0){
				 day_of_week = 7;
			 }
			 c.add(Calendar.DATE,-day_of_week + 7);
			 SimpleDateFormat df2 = new SimpleDateFormat(SystemConstant.DATE_PATTEN);
			 return df2.format(c.getTime());
		 } catch (Exception exception) {
	         exception.printStackTrace();
	     }
		 return null;
	 }
	 
	 /**
	  * 月份加减计算
	  *
	  *@author 李俊
	  *@date 2019年4月16日
	  *@param year 年
	  *@param month 月
	  *@param num 数值
	  *@param forward 正向计算还是反向计算 1 为正向 -1为反向
	  *@return Object
	  */
	 public static Map<String,String> calculationDate(Integer year,Integer month,Integer num,Integer forward){
		 Map<String,String> resultMap = new HashMap<String, String>(3);
		 Integer tempYear = year;
		 Integer tempMonth = month;
		 tempMonth =  month+(num*forward);
		 if(tempMonth >= 13){
			 tempYear = year+1;
			 tempMonth = tempMonth-12;
		 }else if(tempMonth <= 0 ){
			 tempYear = year-1;
			 tempMonth = 12+tempMonth;
		 }
		 resultMap.put("year",tempYear.toString());
		 resultMap.put("month",tempMonth.toString());
		 if(tempMonth <10){
			 resultMap.put("month","0"+tempMonth);
		 }
		 return resultMap;
	}
	
	/**
	 * 计算两个日期相差多少天
	 *
	 *@author 李俊
	 *@date 2019年5月6日
	 *@return Object
	 */
	public static Integer daysDifference(String date1,String date2){
		Date sd1 = DateUtil.stringToDate(date1, SystemConstant.DATE_PATTEN);
		Date sd2 = DateUtil.stringToDate(date2, SystemConstant.DATE_PATTEN);
		long days=(sd1.getTime()-sd2.getTime())/(1000*3600*24);
		return Math.abs((int) days);
	}
	
	/**
	 * 获取传入日期所在的周数
	 *@author 李俊
	 *@date 2019年5月7日
	 *@return Object
	 */
	public static Integer getWeekNumbers(String date){
		Date dt = DateUtil.stringToDate(date, SystemConstant.DATE_PATTEN);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(dt);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static String formatDate(){
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern(SystemConstant.DATE_PATTEN);
		return localDate.format(dateTimeFormatter);
	}

	public static String formatDateTime(){
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern(SystemConstant.TIME_PATTEN);
		return localDateTime.format(dateTimeFormatter);
	}

}
