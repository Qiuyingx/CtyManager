package com.yard.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProFileReader {
	private Properties p;

	public ProFileReader(String fileName) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ProFileReader(InputStream is) {
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getParamValue(String param) {
		return find(p.getProperty(param));
	}
	
	/**
	 * 替换表达式
	 * @param value
	 * @return
	 */
	private String find(String value) {
		// xxxxx${123}aaaaaa
		String expText = "(?<=\\$\\{)[^\\}]+"; // 可取到123
		String expSelf = "\\$\\{\\w*\\}"; // 可取到${123}
		
		Pattern patText = Pattern.compile(expText);  
		Matcher matText = patText.matcher(value);
		
		boolean isFind = false;
		List<String> list = new ArrayList<String>();
		while (matText.find()) {
			// 找到${}变量/表达式，通过表达式，取到表达式的值
			list.add(getParamValue(matText.group()));
			isFind = true;
		}
		
		String[] tmp = null;
		if (isFind) {
			// 字符串中有表达式
			tmp = value.split(expSelf);
			
			// 分割出来的长度要么等于要么只大1
			if (tmp.length == list.size() || tmp.length - list.size() == 1) {
				String _value = "";
				for (int i = 0; i < list.size(); i++) {
					_value += tmp[i] + list.get(i);
				}
				
				if (list.size() < tmp.length) {
					_value += tmp[list.size()];
				}
				
				return _value;
			}
		}
		
		return value;
	}
}
