package com.yard.core.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * action
 * 
 * @author ln
 * 
 */
public abstract class BaseAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = -2930973173742324572L;

	protected  static HttpServletRequest request;
	protected  HttpServletResponse response;

	@Override
	public void prepare() throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		
		if (checkUserLegality()) {

		}
	}

	public void closeCache(HttpServletResponse res) {
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
	}

	/**
	 * 检查用户的合法性
	 * 
	 * @return
	 */
	private boolean checkUserLegality() {
		return false;
	}
	
}
