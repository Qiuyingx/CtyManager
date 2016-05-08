package com.yard.manager.base.util;

import org.apache.commons.lang3.StringUtils;

import com.yard.manager.base.constant.UploadFilePath;

public class HttpUtils {
	public static final String ServerUrl = UploadFilePath.HTTP_REQUEST;
	public static final String ServerBlackUrl = UploadFilePath.HTTP_BLACK_PATH;

	/**
	 * 将url前加上http://
	 * 
	 * @param urlPath
	 * @return
	 */
	public static String valiUrl(String urlPath) {
		if (!StringUtils.isBlank(urlPath) && !urlPath.startsWith("http:") && !urlPath.startsWith("https:")) {
			return ServerUrl + urlPath;
		}
		return urlPath;
	}

	/**
	 * url 参数 拼接
	 * 
	 * @param urlPath
	 * @param param
	 * @return
	 */
	public static String concatParam(String urlPath, String param) {
		if (!StringUtils.isBlank(urlPath)) {
			if (urlPath.indexOf("?") >= 0) {
				return urlPath + "&" + param;
			} else {
				return urlPath + "?" + param;
			}
		}
		return "";
	}
}
