package com.yard.manager.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;

import com.yard.core.struts.action.JsonAction;
import com.yard.manager.base.util.HttpUtils;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 自定义拦截器
 * @author jiangbo
 *
 */
public class CustomInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	boolean state = true;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		JsonAction action = (JsonAction) invocation.getAction();
		//static/page 打头跳过验证
		
		action.getMap().put("httpUrl",HttpUtils.ServerUrl);
		
		String namespace = invocation.getProxy().getNamespace();
		if(!StringUtils.isBlank(namespace) && namespace.startsWith("/static/page")){
			return invocation.invoke();
		}
		
		// 验证账号是否过期
		if (!ShiroUtil.isAuthenticated()) {
			// 看是不是在操作登录
			if (!invocation.getAction().getClass().getSimpleName().equalsIgnoreCase("LoginAction")) {
				// 过期后直接跳到登录页面
				action.getMap().put("interceptorMsg", "用户帐号登录已过期，请重新登录!");
				
				if (isAjaxRequest(ServletActionContext.getRequest())) {
					HttpServletResponse resp = ServletActionContext.getResponse();
					resp.setHeader("interceptorInfo", "ajax_sessiontimeout");
					return null;
				} else {
					return "sessionTimeout";
				}
			}
		} else {

			// 验证权限
			if (!checkPermession(invocation)) {
				// 过期后直接跳到登录页面
				action.getMap().put("interceptorMsg", "你试图非法操作，请登录验证!");
				SecurityUtils.getSubject().logout();
				if (isAjaxRequest(ServletActionContext.getRequest())) {
					HttpServletResponse resp = ServletActionContext.getResponse();
					resp.setHeader("interceptorInfo", "permissionValid");
					return null;
				} else {
					return "sessionTimeout";
				}
			}
		}
		
		return invocation.invoke();
	}

	private boolean checkPermession(ActionInvocation invocation) {
		// 得到请求验证的权限
		String shiroRole = invocation.getProxy().getNamespace().replace("/", "");
		String shiroMethod = invocation.getProxy().getActionName();

		String shiroPermession = shiroRole + ":" + shiroMethod;

		// System.out.println("请求权限验证，权限名称->" + shiroPermession);

		// 判断权限是否是系统要求验证的
		if (ShiroUtil.isSystemCheck(shiroPermession)) {
			// 使用shiro框架进行验证
			return SecurityUtils.getSubject().isPermitted(shiroPermession);
		} else {
			// 未在sys_menu表中配置url的，不进行权限验证
			return true;
		}
	}

	/**
	 * 判断是不是ajax请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
}
