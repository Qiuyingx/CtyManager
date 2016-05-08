package com.yard.core.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 上传文件扩展名限定类
 * 
 * @author jacky
 * 
 */
public class ExtMap {
	private Map<String, Set<String>> extMap = new HashMap<String, Set<String>>();

	/**
	 * 增加分类下的扩展名
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		if (extMap.containsKey(key)) {
			if (!extMap.get(key).contains(value)) {
				extMap.get(key).add(value);
			}
		} else {
			Set<String> set = new HashSet<String>();
			set.add(value);
			extMap.put(key, set);
		}

	}

	/**
	 * 获取所有分类
	 * 
	 * @return
	 */
	public Set<String> keySet() {
		return extMap.keySet();
	}

	/**
	 * 获取分类下的所有扩展名
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> entrySet(String key) {
		return extMap.get(key);
	}

	/**
	 * 判断扩展名是否允许
	 * 
	 * @param key
	 *            分类
	 * @param value
	 *            扩展名
	 * @return
	 */
	public boolean containsKey(String key, String value) {
		if (extMap.containsKey(key)) {
			return extMap.get(key).contains(value);
		} else {
			return false;
		}
	}

	public boolean containsKey(String key) {
		return extMap.containsKey(key);
	}
}
