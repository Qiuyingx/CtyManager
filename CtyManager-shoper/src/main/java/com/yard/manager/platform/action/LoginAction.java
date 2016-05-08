package com.yard.manager.platform.action;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.core.kaptcha.Constants;
import com.yard.core.security.sha.sha4j.ShaUtil;
import com.yard.core.struts.action.JsonServletAction;

/**
 * 登录验证
 * 
 * @Description:用户登录处理，后台主界面
 */
@Results({
// 管理中心
		@Result(name = "CENTER", type = "chain", location = "center"),
		// 登录页面
		@Result(name = "LOGIN", type = "freemarker", location = "/WEB-INF/content/login.html"),
		// 商户登录页面
		@Result(name = "SHOPLOGIN", type = "freemarker", location = "/WEB-INF/content/shopLogin.html"),
		// 主界面
		@Result(name = "LOGOUT", type = "redirect", location = "main") })
public class LoginAction extends JsonServletAction {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN = "LOGIN";
	// 用户登陆信息
	private String account;
	private String pwd;
	private String code;

	/**
	 * 直接跳转到主界面(后台登录界面)
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action("/main")
	public String main() throws Exception {
		return LOGIN;
	}
	
	/**
	 * 商户后台登录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action("/")
	public String shopLogin() throws Exception {
		return LOGIN;
	}

	/**
	 * 登录处理
	 * 
	 * @return
	 */
	@Action("/login")
	public String login() {
		try {
			// 判断验证码是否正确
			String kaptchaExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (code == null || !code.equalsIgnoreCase(kaptchaExpected)) {
				setResult(false, "验证码错误");
				return MAP;
			}

			// 验证用户名密码
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(account, ShaUtil.toSha256String(pwd));
			currentUser.login(token);

			setResult(true, "成功");
		} catch (UnknownAccountException uae) {
			setResult(false, "用户名无效");
		} catch (IncorrectCredentialsException ice) {
			setResult(false, "密码无效");
		} catch (LockedAccountException lae) {
			setResult(false, "帐号锁定");
		} catch (AuthenticationException ae) {
			setResult(false, "认证未通过，请输入正确的用户名和密码");
		} catch (IOException e) {
			e.printStackTrace();
			setResult(false, "认证未通过，发生异常");
		}
		return MAP;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@Action("/logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return LOGIN;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setCode(String code) {
		this.code = code;
	}
}