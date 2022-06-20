package com.learn.admin.common.constant;

import org.springframework.stereotype.Component;

/**
 * 系统常量
 *
 * @author lijun
 * @date 2022-04-25
 */
@Component
public class SystemConstant {
	
	/** 
	 * 系统日期格式 
	 */
	public static final String DATE_PATTEN = "yyyy-MM-dd";
	
	/** 
	 * 系统时间格式 
	 */
	public static final String TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 用户信息
	 **/
	public static final String CURRENT_USER = "account";
	/**
	 * 当前人员登录session信息(权限列表)
	 **/
	public static final String CURRENT_MODULES = "modules";
	/**
	 * session存放部门标识
	 **/
	public static final String CURRENT_USER_UNIT = "depart";

	/**
	 *session 里面的token名称
	 **/
	public static final String COOKIE_SESSION_NAME = "token";
	public static final String FTP_PATH = "/jwjx";

	//专业此处的专业用于OBS树
	public static final String OBS_SYSTEMS = "局领导,总工,车务,机务,供电,电务,车辆,工务,综合,土房,房建,职教,信息,其他";


	//系统版本
	public static String version;


}
