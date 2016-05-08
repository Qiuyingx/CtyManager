package com.yard.manager.modules.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesFactory {
	/**
	 * 推送配置
	 */
	private static String CONFIG_PUSH_PATH;
	private static String CONFIG_PUSH_PASS;
	private static boolean CONFIG_PUSH_ISPRODUCT;
	/**
	 * 邮箱配置
	 */
	private static String MAIL_SERVER_HOST;
	private static String MAIL_SERVER_PORT;
	private static boolean MAIL_VALIDATE;
	private static String MAIL_USERNAME;
	private static String MAIL_FROMADDRESS;
	private static String MAIL_PASSWORD;
	/**
	 * 短信配置
	 */
	private static String SMS_URL;
	private static String SMS_SNAME;
	private static String SMS_SPWD;
	private static String SMS_SPRDID;
	
	/**
	 * 开店申请审核通过邮件内容配置区
	 */
	private static String CONFIG_TRAIN_EMAIL_shoperDefaultPwd;
	private static String CONFIG_TRAIN_EMAIL_shoperManagerUrl;
	private static String CONFIG_TRAIN_EMAIL_ctyTel;
	
	/**
	 * 文字提示配置
	 */
	private static String TEXT_SMS_TRAIN_SUCCESS;
	private static String TEXT_SMS_TRAIN_FAILED;
	private static String TEXT_TRAIN_MANAGER_OPEN;
	private static String TEXT_PUSH_TRAIN_MANAGER_OPEN;
	private static String TEXT_TRAIN_MANAGER_DOWN;
	private static String TEXT_PUSH_TRAIN_MANAGER_DOWN;
	private static String TEXT_USER_STAR_MANAGER_SUCCESS;
	private static String TEXT_PUSH_USER_STAR_MANAGER_SUCCESS;
	private static String TEXT_USER_STAR_MANAGER_FAILED;
	private static String TEXT_PUSH_USER_STAR_MANAGER_FAILED;

	static {
		try {
			Properties prop = new Properties();
			InputStream in = PropertiesFactory.class.getResourceAsStream("/setting.properties");
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));  
			prop.load(bf);
			CONFIG_PUSH_PATH = prop.getProperty("CONFIG_PUSH_PATH").trim();
			CONFIG_PUSH_PASS = prop.getProperty("CONFIG_PUSH_PASS").trim();
			CONFIG_PUSH_ISPRODUCT = Boolean.parseBoolean(prop.getProperty("CONFIG_PUSH_ISPRODUCT").trim());
			
			MAIL_SERVER_HOST = prop.getProperty("CONFIG_MAIL_SERVER_HOST").trim();
			MAIL_SERVER_PORT = prop.getProperty("CONFIG_MAIL_SERVER_PORT").trim();
			MAIL_VALIDATE = Boolean.parseBoolean(prop.getProperty("CONFIG_MAIL_VALIDATE").trim());
			MAIL_USERNAME = prop.getProperty("CONFIG_MAIL_USERNAME").trim();
			MAIL_FROMADDRESS = prop.getProperty("CONFIG_MAIL_FROMADDRESS").trim();
			MAIL_PASSWORD = prop.getProperty("CONFIG_MAIL_PASSWORD").trim();
			
			SMS_URL = prop.getProperty("CONFIG_SMS_URL").trim();
			SMS_SNAME = prop.getProperty("CONFIG_SMS_SNAME").trim();
			SMS_SPWD = prop.getProperty("CONFIG_SMS_SPWD").trim();
			SMS_SPRDID = prop.getProperty("CONFIG_SMS_SPRDID").trim();
			
			CONFIG_TRAIN_EMAIL_shoperDefaultPwd = prop.getProperty("CONFIG_TRAIN_EMAIL_shoperDefaultPwd").trim();
			CONFIG_TRAIN_EMAIL_shoperManagerUrl = prop.getProperty("CONFIG_TRAIN_EMAIL_shoperManagerUrl").trim();
			CONFIG_TRAIN_EMAIL_ctyTel = prop.getProperty("CONFIG_TRAIN_EMAIL_ctyTel").trim();
			
			TEXT_SMS_TRAIN_SUCCESS = prop.getProperty("TEXT_SMS_TRAIN_SUCCESS").trim();
			TEXT_SMS_TRAIN_FAILED = prop.getProperty("TEXT_SMS_TRAIN_FAILED").trim();
			TEXT_TRAIN_MANAGER_OPEN = prop.getProperty("TEXT_TRAIN_MANAGER_OPEN").trim();
			TEXT_PUSH_TRAIN_MANAGER_OPEN = prop.getProperty("TEXT_PUSH_TRAIN_MANAGER_OPEN").trim();
			TEXT_TRAIN_MANAGER_DOWN = prop.getProperty("TEXT_TRAIN_MANAGER_DOWN").trim();
			TEXT_PUSH_TRAIN_MANAGER_DOWN = prop.getProperty("TEXT_PUSH_TRAIN_MANAGER_DOWN").trim();
			TEXT_USER_STAR_MANAGER_SUCCESS = prop.getProperty("TEXT_USER_STAR_MANAGER_SUCCESS").trim();
			TEXT_PUSH_USER_STAR_MANAGER_SUCCESS = prop.getProperty("TEXT_PUSH_USER_STAR_MANAGER_SUCCESS").trim();
			TEXT_USER_STAR_MANAGER_FAILED = prop.getProperty("TEXT_USER_STAR_MANAGER_FAILED").trim();
			TEXT_PUSH_USER_STAR_MANAGER_FAILED = prop.getProperty("TEXT_PUSH_USER_STAR_MANAGER_FAILED").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 私有构造方法，不需要创建对象
	 */
	private PropertiesFactory() {

	}
	public static String getCONFIG_PUSH_PATH() {
		return CONFIG_PUSH_PATH;
	}

	public static void setCONFIG_PUSH_PATH(String cONFIG_PUSH_PATH) {
		CONFIG_PUSH_PATH = cONFIG_PUSH_PATH;
	}

	public static String getCONFIG_PUSH_PASS() {
		return CONFIG_PUSH_PASS;
	}

	public static void setCONFIG_PUSH_PASS(String cONFIG_PUSH_PASS) {
		CONFIG_PUSH_PASS = cONFIG_PUSH_PASS;
	}

	public static boolean getCONFIG_PUSH_ISPRODUCT() {
		return CONFIG_PUSH_ISPRODUCT;
	}

	public static void setCONFIG_PUSH_ISPRODUCT(boolean cONFIG_PUSH_ISPRODUCT) {
		CONFIG_PUSH_ISPRODUCT = cONFIG_PUSH_ISPRODUCT;
	}
	
	public static String getMAIL_SERVER_HOST() {
		return MAIL_SERVER_HOST;
	}
	public static void setMAIL_SERVER_HOST(String mAIL_SERVER_HOST) {
		MAIL_SERVER_HOST = mAIL_SERVER_HOST;
	}
	public static String getMAIL_SERVER_PORT() {
		return MAIL_SERVER_PORT;
	}
	public static void setMAIL_SERVER_PORT(String mAIL_SERVER_PORT) {
		MAIL_SERVER_PORT = mAIL_SERVER_PORT;
	}
	public static boolean isMAIL_VALIDATE() {
		return MAIL_VALIDATE;
	}
	public static void setMAIL_VALIDATE(boolean mAIL_VALIDATE) {
		MAIL_VALIDATE = mAIL_VALIDATE;
	}
	public static String getMAIL_USERNAME() {
		return MAIL_USERNAME;
	}
	public static void setMAIL_USERNAME(String mAIL_USERNAME) {
		MAIL_USERNAME = mAIL_USERNAME;
	}
	public static String getMAIL_FROMADDRESS() {
		return MAIL_FROMADDRESS;
	}
	public static void setMAIL_FROMADDRESS(String mAIL_FROMADDRESS) {
		MAIL_FROMADDRESS = mAIL_FROMADDRESS;
	}
	public static String getMAIL_PASSWORD() {
		return MAIL_PASSWORD;
	}
	public static void setMAIL_PASSWORD(String mAIL_PASSWORD) {
		MAIL_PASSWORD = mAIL_PASSWORD;
	}
	public static void main(String args[]) {
		System.out.println(getCONFIG_PUSH_PATH());
	}
	public static String getSMS_URL() {
		return SMS_URL;
	}
	public static void setSMS_URL(String sMS_URL) {
		SMS_URL = sMS_URL;
	}
	public static String getSMS_SNAME() {
		return SMS_SNAME;
	}
	public static void setSMS_SNAME(String sMS_SNAME) {
		SMS_SNAME = sMS_SNAME;
	}
	public static String getSMS_SPWD() {
		return SMS_SPWD;
	}
	public static void setSMS_SPWD(String sMS_SPWD) {
		SMS_SPWD = sMS_SPWD;
	}
	public static String getSMS_SPRDID() {
		return SMS_SPRDID;
	}
	public static void setSMS_SPRDID(String sMS_SPRDID) {
		SMS_SPRDID = sMS_SPRDID;
	}
	public static String getTEXT_SMS_TRAIN_SUCCESS() {
		return TEXT_SMS_TRAIN_SUCCESS;
	}
	public static void setTEXT_SMS_TRAIN_SUCCESS(String tEXT_SMS_TRAIN_SUCCESS) {
		TEXT_SMS_TRAIN_SUCCESS = tEXT_SMS_TRAIN_SUCCESS;
	}
	public static String getTEXT_TRAIN_MANAGER_OPEN() {
		return TEXT_TRAIN_MANAGER_OPEN;
	}
	public static void setTEXT_TRAIN_MANAGER_OPEN(String tEXT_TRAIN_MANAGER_OPEN) {
		TEXT_TRAIN_MANAGER_OPEN = tEXT_TRAIN_MANAGER_OPEN;
	}
	public static String getTEXT_TRAIN_MANAGER_DOWN() {
		return TEXT_TRAIN_MANAGER_DOWN;
	}
	public static void setTEXT_TRAIN_MANAGER_DOWN(String tEXT_TRAIN_MANAGER_DOWN) {
		TEXT_TRAIN_MANAGER_DOWN = tEXT_TRAIN_MANAGER_DOWN;
	}
	public static String getTEXT_USER_STAR_MANAGER_SUCCESS() {
		return TEXT_USER_STAR_MANAGER_SUCCESS;
	}
	public static void setTEXT_USER_STAR_MANAGER_SUCCESS(String tEXT_USER_STAR_MANAGER_SUCCESS) {
		TEXT_USER_STAR_MANAGER_SUCCESS = tEXT_USER_STAR_MANAGER_SUCCESS;
	}
	public static String getTEXT_USER_STAR_MANAGER_FAILED() {
		return TEXT_USER_STAR_MANAGER_FAILED;
	}
	public static void setTEXT_USER_STAR_MANAGER_FAILED(String tEXT_USER_STAR_MANAGER_FAILED) {
		TEXT_USER_STAR_MANAGER_FAILED = tEXT_USER_STAR_MANAGER_FAILED;
	}
	public static String getTEXT_SMS_TRAIN_FAILED() {
		return TEXT_SMS_TRAIN_FAILED;
	}
	public static void setTEXT_SMS_TRAIN_FAILED(String tEXT_SMS_TRAIN_FAILED) {
		TEXT_SMS_TRAIN_FAILED = tEXT_SMS_TRAIN_FAILED;
	}
	public static String getTEXT_PUSH_TRAIN_MANAGER_OPEN() {
		return TEXT_PUSH_TRAIN_MANAGER_OPEN;
	}
	public static void setTEXT_PUSH_TRAIN_MANAGER_OPEN(String tEXT_PUSH_TRAIN_MANAGER_OPEN) {
		TEXT_PUSH_TRAIN_MANAGER_OPEN = tEXT_PUSH_TRAIN_MANAGER_OPEN;
	}
	public static String getTEXT_PUSH_TRAIN_MANAGER_DOWN() {
		return TEXT_PUSH_TRAIN_MANAGER_DOWN;
	}
	public static void setTEXT_PUSH_TRAIN_MANAGER_DOWN(String tEXT_PUSH_TRAIN_MANAGER_DOWN) {
		TEXT_PUSH_TRAIN_MANAGER_DOWN = tEXT_PUSH_TRAIN_MANAGER_DOWN;
	}
	public static String getTEXT_PUSH_USER_STAR_MANAGER_SUCCESS() {
		return TEXT_PUSH_USER_STAR_MANAGER_SUCCESS;
	}
	public static void setTEXT_PUSH_USER_STAR_MANAGER_SUCCESS(String tEXT_PUSH_USER_STAR_MANAGER_SUCCESS) {
		TEXT_PUSH_USER_STAR_MANAGER_SUCCESS = tEXT_PUSH_USER_STAR_MANAGER_SUCCESS;
	}
	public static String getTEXT_PUSH_USER_STAR_MANAGER_FAILED() {
		return TEXT_PUSH_USER_STAR_MANAGER_FAILED;
	}
	public static void setTEXT_PUSH_USER_STAR_MANAGER_FAILED(String tEXT_PUSH_USER_STAR_MANAGER_FAILED) {
		TEXT_PUSH_USER_STAR_MANAGER_FAILED = tEXT_PUSH_USER_STAR_MANAGER_FAILED;
	}
	public static String getCONFIG_TRAIN_EMAIL_shoperDefaultPwd() {
		return CONFIG_TRAIN_EMAIL_shoperDefaultPwd;
	}
	public static void setCONFIG_TRAIN_EMAIL_shoperDefaultPwd(String cONFIG_TRAIN_EMAIL_shoperDefaultPwd) {
		CONFIG_TRAIN_EMAIL_shoperDefaultPwd = cONFIG_TRAIN_EMAIL_shoperDefaultPwd;
	}
	public static String getCONFIG_TRAIN_EMAIL_shoperManagerUrl() {
		return CONFIG_TRAIN_EMAIL_shoperManagerUrl;
	}
	public static void setCONFIG_TRAIN_EMAIL_shoperManagerUrl(String cONFIG_TRAIN_EMAIL_shoperManagerUrl) {
		CONFIG_TRAIN_EMAIL_shoperManagerUrl = cONFIG_TRAIN_EMAIL_shoperManagerUrl;
	}
	public static String getCONFIG_TRAIN_EMAIL_ctyTel() {
		return CONFIG_TRAIN_EMAIL_ctyTel;
	}
	public static void setCONFIG_TRAIN_EMAIL_ctyTel(String cONFIG_TRAIN_EMAIL_ctyTel) {
		CONFIG_TRAIN_EMAIL_ctyTel = cONFIG_TRAIN_EMAIL_ctyTel;
	}

}
