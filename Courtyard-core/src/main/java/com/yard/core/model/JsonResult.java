package com.yard.core.model;

public class JsonResult {
	private boolean state = false;
	private String msg = "";

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
