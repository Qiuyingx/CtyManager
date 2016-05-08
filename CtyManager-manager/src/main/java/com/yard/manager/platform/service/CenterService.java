package com.yard.manager.platform.service;

import java.sql.SQLException;
import java.util.List;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.CenterMenu;
import com.yard.manager.platform.entity.Menu;
import com.yard.manager.platform.entity.User;

/**
 * 管理界面首页加载服务
 * @author jiangbo
 *
 */
public class CenterService extends BaseService<CenterMenu> {
	private static final CenterService service = new CenterService();

	private CenterService() {
		super();
	}

	public static CenterService getService() {
		return service;
	}

	/**
	 * 构建用户菜单
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<CenterMenu> getCenterMenus(User user) throws SQLException {
		// 模型，1级菜单
		StringBuilder mouldSql = new StringBuilder();
		// 模块，2级菜单
		StringBuilder moduleSql = new StringBuilder();

		// 判断用户是不是内置账户
		if (user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER)) {
			mouldSql.append("SELECT * FROM SYS_MENU WHERE STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			mouldSql.append("AND ISFUNCTION = ").append(ManagerConstant.ISFUNCTION_NO).append(" ");
			mouldSql.append("AND PARENTMENUID IS NULL ");
			mouldSql.append("AND SYSMENUID LIKE '").append(ManagerConstant.SYSTEM_PERMISSION).append("' ");
			// 这一句无具体用处，主要是为了带一个参数，IF之外的代码不用重写
			mouldSql.append("OR SYSMENUID = ? ORDER BY SORT ");
			
			moduleSql.append("SELECT * FROM SYS_MENU WHERE STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			moduleSql.append("AND ISFUNCTION = ").append(ManagerConstant.ISFUNCTION_NO).append(" ");
			moduleSql.append("AND PARENTMENUID = ? ");
			moduleSql.append("AND SYSMENUID LIKE '").append(ManagerConstant.SYSTEM_PERMISSION).append("' ");
			// 这一句无具体用处，主要是为了带一个参数，IF之外的代码不用重写
			moduleSql.append("OR SYSMENUID = ? ORDER BY SORT ");
		} else {
			mouldSql.append("SELECT DISTINCT F.* FROM SYS_USER A, SYS_GROUP B, SYS_GROUPROLE C, SYS_ROLE D, SYS_ROLEMENU E, SYS_MENU F, SYS_MENU G ");
			mouldSql.append("WHERE A.SYSGROUPID = B.SYSGROUPID AND B.SYSGROUPID = C.SYSGROUPID AND C.SYSROLEID = D.SYSROLEID ");
			mouldSql.append("AND D.SYSROLEID = E.SYSROLEID AND E.SYSMENUID = F.SYSMENUID ");
			mouldSql.append("AND A.STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			mouldSql.append("AND B.STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			mouldSql.append("AND D.STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			mouldSql.append("AND F.STATUS = ").append(ManagerConstant.ENABLED).append(" ");
			mouldSql.append("AND F.ISFUNCTION = ").append(ManagerConstant.ISFUNCTION_NO).append(" ");
			mouldSql.append("AND F.PARENTMENUID IS NULL AND G.PARENTMENUID = F.SYSMENUID ");
			mouldSql.append("AND G.ISFUNCTION = ").append(ManagerConstant.ISFUNCTION_NO).append(" ");
			mouldSql.append("AND G.STATUS = ").append(ManagerConstant.ENABLED).append(" AND A.SYSUSERID = ? ORDER BY F.SORT");

			moduleSql
					.append("SELECT DISTINCT F.* FROM SYS_USER A, SYS_GROUP B, SYS_GROUPROLE C, SYS_ROLE D, SYS_ROLEMENU E, SYS_MENU F ");
			moduleSql.append("WHERE A.SYSGROUPID = B.SYSGROUPID AND B.SYSGROUPID = C.SYSGROUPID AND C.SYSROLEID = D.SYSROLEID ");
			moduleSql.append("AND D.SYSROLEID = E.SYSROLEID AND E.SYSMENUID = F.SYSMENUID AND A.STATUS = ")
					.append(ManagerConstant.ENABLED).append(" ");
			moduleSql.append("AND B.STATUS = ").append(ManagerConstant.ENABLED).append(" AND D.STATUS = ")
					.append(ManagerConstant.ENABLED).append(" ");
			moduleSql.append("AND F.STATUS = ").append(ManagerConstant.ENABLED).append(" AND F.ISFUNCTION = ")
					.append(ManagerConstant.ISFUNCTION_NO).append(" ");
			moduleSql.append("AND F.PARENTMENUID = ? AND A.SYSUSERID = ? ORDER BY F.SORT");
		}
		
		List<CenterMenu> menu = super.query(mouldSql.toString(), user.getSysUserId());
		for (CenterMenu m : menu) {
			List<Menu> lm = super.query(Menu.class, moduleSql.toString(), m.getSysMenuId(), user.getSysUserId());
			m.setModules(lm);
		}
		
		return menu;
	}
}
