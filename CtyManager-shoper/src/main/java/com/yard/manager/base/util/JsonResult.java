package com.yard.manager.base.util;

import java.util.List;
import java.util.Map;

import com.yard.manager.base.constant.ManagerConstant;

public class JsonResult {

	/**
	 * 返回json结果
	 * 
	 * @param map
	 * @param isSuccess
	 * @param msg
	 */
	public static void toJson(Map<String, Object> map, boolean isSuccess, String msg) {
		map.put(ManagerConstant.JSON_COL_ISSUCCESS, isSuccess);
		map.put(ManagerConstant.JSON_COL_MSG, msg);
	}
	
	public static void toList(Map<String, Object> map, List<? extends Object> list, int rowCount) {
		map.put(ManagerConstant.JSON_COL_ROWS, list);
		map.put(ManagerConstant.JSON_COL_TOTAL, rowCount);
	}
}
