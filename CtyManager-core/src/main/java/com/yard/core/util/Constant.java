package com.yard.core.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统常量类
 * 
 */
public final class Constant {
	public static final Logger logger = LoggerFactory.getLogger(Constant.class);
	// 上传文件保存路径
	public static String SAVE_PATH = "D:/software/nginx-1.7.2/html/attached/";
	// 上传文件访问路径
	public static String SAVE_URL = "/attached/";
	// 数据库类型
	public static DbType DB_TYPE;
	static {
		CompositeConfiguration config = new CompositeConfiguration();
		try {
			config.addConfiguration(new PropertiesConfiguration("application.properties"));
			DB_TYPE = DbType.valueOf(config.getString("DB_TYPE", null));
			if (DB_TYPE == null) {
				logger.error("[bluebox-core]-数据库类型未配置-[DB_TYPE]");
			}
			SAVE_PATH = config.getString("SAVE_PATH", null);
			if (SAVE_PATH == null) {
				logger.error("[bluebox-core]-上传文件保存路径未配置-[SAVE_PATH]");
			}
			SAVE_URL = config.getString("SAVE_URL", null);
			if (SAVE_URL == null) {
				logger.error("[bluebox-core]-上传文件访问路径未配置-[SAVE_URL]");
			}
		} catch (ConfigurationException e) {
			logger.error("[bluebox-core]-配置错误-[application.properties]");
		}
	}

	/**
	 * 数据库类型
	 * 
	 */
	public enum DbType {
		MYSQL, ORACLE, MONGODB
	}

	/**********************************************************************/
	public static final Integer PAGE_SIZE = 10;
	// 默认密码
	public static final String DEFAULT_PWD = "123456";
	// session名称
	public static final String SESSION_NAME = "LOGIN_USER";
	// 广告图片保存文件夹
	public static final String PATHIMAGE = "images/poster";
	// 新闻图片保存地址
	public static final String PATHNEWSIMAGE = "images/news";
	// 文件
	public static final String PATHFILE = "smestar/files";
}
