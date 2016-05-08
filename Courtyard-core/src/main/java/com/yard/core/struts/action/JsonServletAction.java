package com.yard.core.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

public class JsonServletAction extends JsonAction implements SessionAware, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -448924273030971346L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void print(String msg) {
		try {
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(boolean msg) {
		try {
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
