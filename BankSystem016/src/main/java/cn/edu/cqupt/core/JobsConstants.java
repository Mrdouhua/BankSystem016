/**
 * JobsConstants
 */
package cn.edu.cqupt.core;

/**
 * 
 * @author BINGBING
 *
 */
public class JobsConstants {
	
	/**************************登录用户类型*************************************/
	//登录类型为班级普通用户
	public static final String LOGINUSER_TYPE_STUDENT = "m";
	//登录类型为班委
	public static final String LOGINUSER_TYPE_TEACHER = "T";
	//登录类型为管理员
	public static final String LOGINUSER_TYPE_ADMIN = "A";
	/**
	 * session和cookie的时间
	 */
	//session超出时间20(分钟)
	public static final int SESSION_DATE = 60 * 20;
	//cookie过期日期为 24(小时)
	public static final int COOKIE_DATE = 60 * 60 * 24;
	
	/**
	 * 重置的默认密码
	 */
	public static final String DEFAULT_PASSWORD = MD5Encrypt.makeMD5("123456");
	
	/**
	 * 登陆的用户的状态:【正常】
	 */
	public static final String LOGIN_USER_STATUS_NORMAL = "1";
	
	/**
	 * 登陆的用户的状态:【冻结】
	 * 注意小写
	 */
	public static final String LOGIN_USER_STATUS_FREEZE = "4";
}
