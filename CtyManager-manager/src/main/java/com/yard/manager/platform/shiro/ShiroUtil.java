package com.yard.manager.platform.shiro;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;

import com.opensymphony.xwork2.ActionContext;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.User;

public class ShiroUtil {
	public static ShiroService ss = ShiroService.getInstance();
	
	public static Map<String, String> allPermissionMap = Collections.synchronizedMap(new HashMap<String, String>());
	public static Map<String, Set<String>> userRoleMap = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	public static Map<String, Set<String>> userPermissionMap = Collections.synchronizedMap(new HashMap<String, Set<String>>());
	public static Map<String, SimpleAuthorizationInfo> userAuthInfoMap = Collections.synchronizedMap(new HashMap<String, SimpleAuthorizationInfo>());
	
	static {
		ss.getAllPermission(allPermissionMap);
		ss.getAllRolePermission(userRoleMap, userPermissionMap, userAuthInfoMap);
	}

	/**
	 * 获取当前登录帐号
	 * 
	 * @return
	 */
	public static String currentAccount() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null)
			return currentUser.getPrincipal() == null ? null : currentUser.getPrincipal().toString();
		else
			return null;
	}
	
	/**
	 * 从Session中获取用户对象
	 * 
	 * @return
	 */
    public static User getUser(){
		return (User) ActionContext.getContext().getSession().get(ManagerConstant.SESSION_USER);
    }
    
    /**
     * 从Session中获取用户ID
     * 
     * @return 用户ID
     */
    public static String getUserId(){
    	User user = getUser();
    	return user.getSysUserId();
    }
    
    /**
     * 获取当前管理员所属公众平台ID
     * 
     * @return
     */
    public static String getWxConfigId(){
    	User user = getUser();
    	return user.getWxConfigId();
    }
    

	/**
	 * 验证用户是否认证通过
	 * @return
	 */
	public static boolean isAuthenticated() {
		Subject currentUser = SecurityUtils.getSubject();
		return currentUser.isAuthenticated();
	}

	/**
	 * 判断该请求是否是系统要求验证的请求
	 * @param p
	 * @return
	 */
	public static boolean isSystemCheck(String p) {
		return allPermissionMap.containsKey(p);
	}
	
	/**
	 * 刷新单个用户的角色和权限
	 * @param userId
	 * @return
	 */
	public static boolean refreshUserRolePermission(String userNo) {
		return ss.getAllRolePermissionByUser(userNo, userRoleMap, userPermissionMap, userAuthInfoMap);
	}
	
	/**
	 * 刷新所有用户的角色和权限
	 * @return
	 */
	public static boolean refreshAllUserRolePermission() {
		return ss.getAllRolePermission(userRoleMap, userPermissionMap, userAuthInfoMap);
	}
	
	public static Set<String> getRoleByUser(String userNo) {
		return userRoleMap.get(userNo);
	}
	
	public static Set<String> getPermissionByUser(String userNo) {
		return userPermissionMap.get(userNo);
	}
	
	public static SimpleAuthorizationInfo getAuthInfo(String userNo) {
		return userAuthInfoMap.get(userNo);
	}
	
	/**
	 * 获取当前登录用户所管理的社区
	 */
	public static String getUserYards(){
		String courtyardIds = "2323";
		User user = getUser();
		if(user != null && !user.getSysGroupId().equals(ManagerConstant.GROUP_SUPPER) &&
				!user.getSysGroupId().equals(ManagerConstant.GROUP_TEST) &&
					!user.getSysGroupId().equals(ManagerConstant.GROUP_INNER)) {
			courtyardIds = user.getYardids();
		} else {
			courtyardIds = null;
		}
		return courtyardIds;
	}
}
