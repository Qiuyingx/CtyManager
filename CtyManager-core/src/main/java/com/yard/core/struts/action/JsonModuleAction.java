package com.yard.core.struts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

public abstract class JsonModuleAction extends JsonAction implements SessionAware, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -448924273030971346L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	public String _id = "";
	public String _parent;
	public long page = 1;
	public long rows = 10;

	// 返回常量
	// 返回到列表页面GET
	// public static final String INDEX = "index";
	// public static final String SHOW = "show";
	// public static final String EDIT = "edit";

	// ------
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	// @SuppressWarnings("rawtypes")
	// public abstract List queryHandler() throws Exception;
	//
	// public abstract long countHandler() throws Exception;
	//
	// public abstract Object showHandler() throws Exception;
	//
	// public abstract void editHandler(boolean existId) throws Exception;
	//
	// public abstract boolean saveHandler(boolean existId);
	//
	// public abstract boolean deleteHandler();

	// ------
	// public String index() {
	// return "index";
	// }
	//
	// public String query() throws Exception {
	// map.put("total", countHandler());
	// map.put("rows", queryHandler());
	// return MAP;
	// }
	//
	// public String show() throws Exception {
	// object = showHandler();
	// return "show";
	// }
	//
	// // protected String create() {
	// // return "edit";
	// // }
	//
	// public String edit() throws Exception {
	// editHandler(existId());
	// return "edit";
	// }
	//
	// public String save() {
	// boolean state = saveHandler(existId());
	// map.put("state", state);
	// return MAP;
	// }
	//
	// // protected String update() {
	// // return MAP;
	// // }
	//
	// public String delete() {
	// boolean state = deleteHandler();
	// map.put("state", state);
	// return MAP;
	// }

	// ------

	public long getPage() {
		return page;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public String get_parent() {
		return _parent;
	}

	public void set_parent(String _parent) {
		this._parent = _parent;
	}

	public boolean existId() {
		if (_id == null || _id.equalsIgnoreCase(""))
			return false;
		return true;
	}
}
