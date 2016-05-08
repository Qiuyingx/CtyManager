package com.yard.manager.base.constant;

import com.yard.core.util.ProFileReader;

public class UploadFilePath {
	// 资源根地址
	public static String BASE_PATH_DISK = "";
	// 拍摄类型图片保存地址
	public static String MAIN_IMG_PATH = "";
	// 拍摄类型图片url地址
	public static String MAIN_IMG_URL = "";
	// 图片大小
	public static int MAIN_IMG_LEN = 500;
	// 请求地址
	public static String HTTP_REQUEST = "";
	// 后台请求地址
	public static String HTTP_BLACK_PATH = "";

	// 汽车车标
	public static String CARBRAND_IMG_PATH = "";
	public static String CARBRAND_IMG_URL = "";
	public static int CARBRAND_IMG_LEN = 500;

	static {
		try {
			ProFileReader pfr = new ProFileReader("uploadfilepath.properties");

			BASE_PATH_DISK = pfr.getParamValue("BASE_PATH_DISK");
			MAIN_IMG_PATH = pfr.getParamValue("MAIN_IMG_PATH");
			MAIN_IMG_URL = pfr.getParamValue("MAIN_IMG_URL");
			HTTP_REQUEST = pfr.getParamValue("HTTP_REQUEST");
			HTTP_BLACK_PATH = pfr.getParamValue("HTTP_BLACK_PATH");
			
			CARBRAND_IMG_PATH = pfr.getParamValue("CARBRAND_IMG_PATH");
			CARBRAND_IMG_URL = pfr.getParamValue("CARBRAND_IMG_URL");

			MAIN_IMG_LEN = Integer.parseInt(pfr.getParamValue("MAIN_IMG_LEN"));
			CARBRAND_IMG_LEN = Integer.parseInt(pfr.getParamValue("CARBRAND_IMG_LEN"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
