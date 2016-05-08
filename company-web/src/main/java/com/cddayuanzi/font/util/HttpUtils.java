package com.cddayuanzi.font.util;

import org.apache.commons.lang3.StringUtils;

import com.cddayuanzi.font.base.manager.UploadFilePath;
/**
 * 
 * @author xiayuanming
 *
 */
public class HttpUtils {
	public static final String ServerUrl = UploadFilePath.HTTP_REQUEST;

	/**
	 * 将url前加上http://
	 * 
	 * @param urlPath
	 * @return
	 */
	public static String valiUrl(String urlPath) {
		if (!StringUtils.isBlank(urlPath) && !urlPath.startsWith("http:")) {
			return ServerUrl + urlPath;
		}
		return urlPath;
	}
}
