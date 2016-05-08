package com.yard.manager.platform.shiro;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
//import java.util.Collection;
//import java.util.LinkedHashSet;
//import java.util.Set;


import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.yard.core.security.sha.sha4j.ShaUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.service.UserService;

public class CustomAuthenticator extends AuthorizingRealm {
	private static UserService service = UserService.getServer();

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String userNo = (String) getAvailablePrincipal(principals);
		return ShiroUtil.getAuthInfo(userNo);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) authcToken;
		String userNo = upToken.getUsername();
		AuthenticationInfo info = null;
		if (userNo == null) {
			throw new AccountException("[bluebox-core]-用户验证错误-[用户名为空]");
		}

		try {
			// 判断是不是内置用户登录
			if (userNo.equals(ManagerConstant.SYSTEM_USER)) {
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				String adminPassword = ShaUtil.toSha256String(ManagerConstant.SYSTEM_PASSWORD + df.format(new Date()));
				info = buildAuthenticationInfo(userNo, adminPassword.toCharArray());
			} else {
				User user = service.show(userNo);
				if (user == null)
					throw new AccountException("[bluebox-core]-用户验证错误[用户信息不存在].");
				if (user.getStatus() != ManagerConstant.ENABLED)
					throw new AccountException("[bluebox-core]-用户验证错误[用户已禁用].");

				// 将用户名密码放入认证信息内，此方法内会将LoginAction.login()方法中的UsernamePasswordToken进行比较
				info = buildAuthenticationInfo(user.getSysUserNo(), user.getSysUserPwd().toCharArray());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccountException("[bluebox-core]-用户验证错误[查询用户信息异常].");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccountException("[bluebox-core]-用户验证错误[查询用户信息异常].");
		}
		return info;
	}

	protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
		return new SimpleAuthenticationInfo(username, password, getName());
	}

	protected Set<String> getRoleNamesForUser(String username) {
		try {
			return service.getRoleNamesForUser(username);
		} catch (Exception e) {
			e.printStackTrace();
			return new LinkedHashSet<String>();
		}
	}

	protected Set<String> getPermissions(String username, Collection<String> roleNames) {
		try {
			return service.getPermissions(username, roleNames);
		} catch (Exception e) {
			e.printStackTrace();
			return new LinkedHashSet<String>();
		}
	}
}
