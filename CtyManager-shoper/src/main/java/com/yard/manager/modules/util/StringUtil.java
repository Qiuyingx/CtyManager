package com.yard.manager.modules.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	/**
	 * 截取字符串前20个字符
	 * @param content
	 * @return
	 */
	public static String subStr20(String content) {
		if(StringUtils.isBlank(content)) {
			return "";
		}
		if(content.length() > 20) {
			return content.substring(0, 20);
		}
		return content;
	}
	
	/**
	 * str == null 则返回“”
	 * @param str
	 * @return
	 */
	public static String str(String str) {
		if(str == null) {
			return "";
		}
		return str;
	}
}
