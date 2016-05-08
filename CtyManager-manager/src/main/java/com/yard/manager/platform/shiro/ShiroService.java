package com.yard.manager.platform.shiro;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.Menu;
import com.yard.manager.platform.entity.User;
import com.yard.core.service.common.BaseService;

@SuppressWarnings("rawtypes")
public class ShiroService extends BaseService {
	private static ShiroService ss = new ShiroService();

	private ShiroService() {

	}

	public static ShiroService getInstance() {
		return ss;
	}

	/**
	 * 获取系统中所有需要验证的权限
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public void getAllPermission(Map<String, String> map) {
		try {
			// 得到所有需要验证的权限（包括状态为0的）
			List<Menu> list = query(Menu.class, "SELECT * FROM SYS_MENU WHERE URL IS NOT NULL");

			for (Menu m : list) {
				map.put(m.getUrl().replace("/", ":"), m.getUrl());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有用户的所有权限
	 * @param userNo
	 * @param roleMap
	 * @param permissionMap
	 * @param userAuthInfoMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean getAllRolePermission(Map<String, Set<String>> roleMap, Map<String, Set<String>> permissionMap,
			Map<String, SimpleAuthorizationInfo> userAuthInfoMap) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM SYS_USER WHERE STATUS = ").append(ManagerConstant.ENABLED);

			List<User> list = query(User.class, sql.toString());

			for (User u : list) {
				if (!getAllRolePermissionByUser(u.getSysUserNo(), roleMap, permissionMap, userAuthInfoMap)) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取指定用户的所有权限
	 * @param userNo
	 * @param roleMap
	 * @param permissionMap
	 * @param userAuthInfoMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean getAllRolePermissionByUser(String userNo, Map<String, Set<String>> roleMap,
			Map<String, Set<String>> permissionMap, Map<String, SimpleAuthorizationInfo> userAuthInfoMap) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT F.* FROM SYS_USER A, SYS_GROUP B, SYS_GROUPROLE C, SYS_ROLE D, SYS_ROLEMENU E, SYS_MENU F ");
			sql.append("WHERE A.SYSGROUPID = B.SYSGROUPID AND B.SYSGROUPID = C.SYSGROUPID AND C.SYSROLEID = D.SYSROLEID ");
			sql.append("AND D.SYSROLEID = E.SYSROLEID AND E.SYSMENUID = F.SYSMENUID ");
			sql.append("AND A.STATUS = ").append(ManagerConstant.ENABLED).append(" AND B.STATUS = ")
					.append(ManagerConstant.ENABLED).append(" ");
			sql.append("AND D.STATUS = ").append(ManagerConstant.ENABLED).append(" AND F.STATUS = ")
					.append(ManagerConstant.ENABLED).append(" ");
			sql.append("AND F.URL IS NOT NULL AND A.SYSUSERNO = ? ");

			List<Menu> list = query(Menu.class, sql.toString(), userNo);

			if (roleMap.containsKey(userNo)) {
				roleMap.remove(userNo);
			}

			if (permissionMap.containsKey(userNo)) {
				permissionMap.remove(userNo);
			}

			if (userAuthInfoMap.containsKey(userNo)) {
				userAuthInfoMap.remove(userNo);
			}

			Set<String> permission = new HashSet<String>();
			Set<String> role = new HashSet<String>();
			for (Menu m : list) {
				String tmp[] = m.getUrl().split("/");
				if (tmp.length > 1) {
					role.add(tmp[0]);
					permission.add(m.getUrl().replaceAll("/", ":"));
				}
			}

			roleMap.put(userNo, role);
			permissionMap.put(userNo, permission);

			// shiro权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(role);
			info.setStringPermissions(permission);

			userAuthInfoMap.put(userNo, info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
