/**
 * Leo  2016年1月11日 上午9:57:43
 * CommonUtil
 */
package cn.edu.cqupt.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Leo 通用工具类
 */
public class CommonUtil {
	
	public static int ceil(int a ,int b){
		int c = a % b;
		int d = a / b;
		return c>0?(d+1) : d;
	}
	
	/**
	 * 判断字符串不为null 和空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (null == str || 0 == str.trim().length()) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(Collection<?> src) {
		if (null == src || src.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(Map<?, ?> src) {
		if (null == src || src.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(Object[] src) {
		if (null == src || 0 == src.length) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前时间，并转为字符串，格式为yyyyMMddHHmmss
	 * @author Leo
	 * @createtime 2016年1月19日 下午3:05:25
	 * @return
	 */
	public static String nowToStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date d = new Date();
    	return sdf.format(d);
	}
	
	/**
	 * 把字符串转化为时间格式
	 * @author Leo
	 * @createtime 2016年1月19日 下午3:30:20
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseStrToDate(String str,String format) throws ParseException{
		if(isNullOrEmpty(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = sdf.parse(str);
		return d;
	}
	
	/**
	 * 字符串转化为时间，默认时间格式为: yyyy-MM-dd HH:mm:ss
	 * @author Leo
	 * @createtime 2016年1月19日 下午3:32:43
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStrToDate(String str) throws ParseException{
		String	format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = sdf.parse(str);
		return d;
	}
	
	/**
	 * 在指定时间位置上加减时间
	 * @author Leo
	 * @createtime 2016年1月19日 下午3:43:12
	 * @param d 给定时间
	 * @param type 对年月日时分秒进行加减
	 * @param scale 加减的数量
	 * @return
	 */
	public static Date dateAdd(Date d,int type,int scale){
		if(0==scale){
			return d;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(type, scale);
		return c.getTime();
	}
	
	public static Date addYear(Date d,int scale){
		return dateAdd(d,Calendar.YEAR,scale);
	}
	
	public static Date addMonth(Date d,int scale){
		return dateAdd(d,Calendar.MONTH,scale);
	}
	
	public static Date addWeek(Date d,int scale){
		return dateAdd(d,Calendar.WEEK_OF_YEAR,scale);
	}
	
	/**
	 * 给指定时间加上指定天数，如果是减去指定天数则为负数
	 * @author Leo
	 * @createtime 2016年1月19日 下午3:34:25
	 * @return
	 */
	public static Date addDay(Date d,int scale){
		return dateAdd(d,Calendar.DAY_OF_MONTH,scale);
	}
	
	public static Date addHour(Date d,int scale){
		return dateAdd(d,Calendar.HOUR_OF_DAY,scale);
	}
	
	public static Date addSecond(Date d,int scale){
		return dateAdd(d,Calendar.SECOND,scale);
	}
	
	public static Date addMinute(Date d,int scale){
		return dateAdd(d,Calendar.MINUTE,scale);
	}
	
	
	/**
	 * 把字符串数组拼接成用于 in 条件的 sql
	 * @author Leo
	 * @createtime 2016年1月23日 下午8:38:38
	 * @param ids
	 * @param join
	 * @return
	 */
	public static String spellSqlInCondtion(String[] ids){
		StringBuilder sb = new StringBuilder();
		if(!isNullOrEmpty(ids)){
			int c = 0 ;
			for(String id :ids){
				if(c==0){
					sb.append("'").append(id).append("'");
				}else{
					sb.append(",").append("'").append(id).append("'");
				}
				c ++;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 实现bean的toString方法，把所有字符串打出字符串
	 * @author Leo
	 * @createtime 2016年1月24日 下午9:50:01
	 * @param obj
	 * @return
	 */
	public static String beanToString(Object obj){
		if(null==obj){
			return null;
		}
		Map<String, Integer> counter = new HashMap<String, Integer>();
		Method[] ms = obj.getClass().getMethods();
		StringBuilder sb = new StringBuilder("[");
		for (Method m : ms) {
			insertIntoMap(obj,m,counter);
		}
		Set<String> keys = counter.keySet();
		if (!CommonUtil.isNullOrEmpty(keys)) {
			for (String key : keys) {
				appendToStringBuilder(obj,key,counter,sb);
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private static void appendToStringBuilder(Object obj, String key,
			Map<String, Integer> counter, StringBuilder ret) {
		Integer c = counter.get(key);
		if (null != c && c == 2) {
			try {
				PropertyDescriptor pd = new PropertyDescriptor(key,
						obj.getClass());
				Method r = pd.getReadMethod();
				ret.append(key).append("=").append(r.invoke(obj))
						.append(";");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static void insertIntoMap(Object obj, Method m,
			Map<String, Integer> counter) {
		boolean isPublic = Modifier.isPublic(m.getModifiers());
		String name = m.getName();
		if (isPublic && (name.startsWith("get") || name.startsWith("set"))) {
			String fn = name.substring(3, name.length());
			StringBuilder field = new StringBuilder();
			field.append(fn.substring(0, 1).toLowerCase()).append(
					fn.substring(1));
			String fdn = field.toString();
			Integer c = counter.get(fdn);
			if (null == c) {
				c = 0;
			}
			c++;
			counter.put(fdn, c);
		}
	}
	
	/**
	 * 
	 * @author Leo
	 * @createtime 2016年2月25日 下午4:59:07
	 * @param status 1成功，2失败
	 * @param msg 成功失败消息
	 * @return
	 */
	public static String createReturnMsg(String status,String msg){
		return "{success:'"+status+"',message:'"+msg+"'}";
	}
	
	/**
	 * 根据指定长度来返回字符串，字符串长度小于指定长度时左边添0
	 * @param ch 给定的字符串
	 * @param len 指定长度
	 * @return
	 */
	public static String fillLeftWithZero(String ch, int len){
		if(isNullOrEmpty(ch)){//ch is null or empty , return len * 0
			return fillString(len,'0');
		}
		if(ch.length()>=len){
			return ch.substring(0,len);
		}
		int r = len - ch.length();
		String f = fillString(r,'0');
		return f+ch;
	}
	
	/**
	 * 用指定字符填充一个指定长度的字符串
	 * @param len 指定长度
	 * @param ch 指定字符
	 * @return
	 */
	public static String fillString(int len, char ch) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<len ;i++){
			sb.append(""+ch);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.err.println(ceil(100,33));
		System.err.println(fillLeftWithZero("213",3));
	}
}
