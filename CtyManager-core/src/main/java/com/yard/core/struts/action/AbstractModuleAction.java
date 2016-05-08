package com.yard.core.struts.action;

import com.opensymphony.xwork2.ModelDriven;

public abstract class AbstractModuleAction<T> extends JsonServletAction implements
		ModelDriven<T> {
	private static final long serialVersionUID = 1L;
	public static final String INDEX = "INDEX";
	public static final String EDIT = "EDIT";
	public String _id = "";
	public String _parent;
	public long page = 1;
	public long rows = 10;
	public T model;

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
		if (_id == null || _id.trim().equalsIgnoreCase(""))
			return false;
		return true;
	}

	public boolean isRoot() {
		if (_parent == null || _parent.trim().equalsIgnoreCase(""))
			return true;
		return false;
	}

	public abstract T getModel();
}
