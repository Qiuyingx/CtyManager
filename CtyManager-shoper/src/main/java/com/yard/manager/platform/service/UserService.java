package com.yard.manager.platform.service;

import java.util.Collection;
import java.util.Set;

import com.yard.core.service.common.BaseService;
import com.yard.manager.platform.entity.User;

/**
 * 
 * @Title: UserService.java
 * @Description:系统用户
 */
public class UserService extends BaseService<User> {
	private static final UserService service = new UserService();
	private String SQL = "";

	private UserService() {
		super();
	}

	public static UserService getServer() {
		return service;
	}

	public User show(String userNo) throws Exception {
		SQL = "SELECT a.*, b.id trainId FROM sys_user a, t_train_info b WHERE a.sysuserno = ? AND a.sysuserid = b.managerId";
		return super.show(SQL, userNo);
	}

	/**
	 * 获取用户角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> getRoleNamesForUser(String username) throws Exception {
		return null;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param username
	 * @param roleNames
	 * @return
	 */
	public Set<String> getPermissions(String username, Collection<String> roleNames) throws Exception {
		return null;
	}
}
