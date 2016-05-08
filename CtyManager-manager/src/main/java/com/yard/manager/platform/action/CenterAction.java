package com.yard.manager.platform.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.yard.core.struts.action.JsonAction;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.service.CenterService;
import com.yard.manager.platform.service.UserService;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 管理中心控制器
 * 
 * @Title: CenterAction.java
 * @Description:
 * @version V1.0
 */
@Results({ @Result(name = "CENTER", type = "freemarker", location = "/WEB-INF/content/center.html"),
		@Result(name = "LOGIN", type = "chain", location = "login") })
public class CenterAction extends JsonAction implements SessionAware, ServletRequestAware, ServletResponseAware {
	public static final String LOGIN = "LOGIN";
	public static final String CENTER = "CENTER";
	private static final long serialVersionUID = 4256463424516331103L;
	private String id;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;

	// 用户登陆信息
	private String account;
	private String pwd;
	private String kaptcha;
	private CenterService sevice = CenterService.getService();
	private UserService userService = UserService.getServer();

	public String center() {
		return MAP;
	}

	@Override
	public String execute() throws Exception {
		String userNo = ShiroUtil.currentAccount();

		User user = null;

		// 判断是不是系统内置用户登录
		if (userNo.equals(ManagerConstant.SYSTEM_USER)) {
			// 构造一个系统内置用户
			user = ManagerConstant.systemUser;
		} else {
			user = userService.show(userNo);
			user.setSysUserPwd(null);
		}

		// 将用户信息存入session中
		ActionContext.getContext().getSession().put(ManagerConstant.SESSION_USER, user);

		// 获取当前登录用户
		map.put("account", user);
		// 获取当前用户菜单
		map.put("menus", sevice.getCenterMenus(user));
		map.put("now", DateUtil.currentTime());
		map.put("menu_type", "accordion_small_icon_menu");

		return CENTER;
	}

	@Action("/welcome")
	public String welcome() {
		return null;
	}

	public String logout() {
		return null;
	}

	/**
	 * 功能菜单
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String menu() throws SQLException {
		return ARRAY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getKaptcha() {
		return kaptcha;
	}

	public void setKaptcha(String kaptcha) {
		this.kaptcha = kaptcha;
	}

}