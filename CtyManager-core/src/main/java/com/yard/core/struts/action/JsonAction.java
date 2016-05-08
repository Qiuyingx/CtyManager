package com.yard.core.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.core.model.JsonResult;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json")
@Results({ @Result(type = "json", name = "MAP", params = { "root", "map" }),
		@Result(type = "json", name = "ARRAY", params = { "root", "array" }),
		@Result(type = "json", name = "OBJECT", params = { "root", "object" }) })
// @ExceptionMappings({ @ExceptionMapping(exception = "java.lang.Exception",
// result = "exception") })
@SuppressWarnings("rawtypes")
public class JsonAction extends ActionSupport {
	private static final long serialVersionUID = -8158865407882024475L;
	public static final String MAP = "MAP";
	public static final String ARRAY = "ARRAY";
	public static final String OBJECT = "OBJECT";

	// ------
	public Map<String, Object> map = new HashMap<String, Object>();
	public List array = new ArrayList();
	public Object object = new Object();
	public JsonResult result = new JsonResult();

	// ------
	public void setResult(boolean state, String msg) {
		result.setState(state);
		result.setMsg(msg);
		map.put("result", result);
	}

	// ------
	public Map getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List getArray() {
		return array;
	}

	public void setArray(List array) {
		this.array = array;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public JsonResult getResult() {
		return result;
	}

	public void setResult(JsonResult result) {
		this.result = result;
	}

}
