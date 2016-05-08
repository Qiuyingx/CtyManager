package com.yard.manager.system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.ConditionEntity;
import com.yard.manager.system.entity.SysMenuForm;
import com.yard.manager.system.entity.SysMenuViewEntity;

/**
 * Service
 * 
 * @author 源码自动生成
 *
 */
public class SysMenuService extends BaseService<SysMenuViewEntity> {
	private static final SysMenuService cs = new SysMenuService();

	private SysMenuService() {

	}

	public static SysMenuService getInstance() {
		return cs;
	}

	/**
	 * 列表数据（分页）
	 * 
	 * @param condition
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<SysMenuViewEntity> querySysMenuList(ConditionEntity condition, String sort, String order, long page, long rows) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from sys_menu a where 1=1 ");

		List<Object> params = new ArrayList<Object>();

		// 排序
		if (null != sort && !"".equals(sort) && null != order && !"".equals(order)) {
			sql.append("order by ").append(sort).append(" ").append(order);
		}

		sql.append(" LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);

		try {
			return query(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 列表总行数
	 * 
	 * @param condition
	 * @return
	 */
	public long querySysMenuListCount(ConditionEntity condition) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from sys_menu a where 1=1 ");

		List<Object> params = new ArrayList<Object>();

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 新增
	 * 
	 */
	public void add(SysMenuForm sysMenu, String userId, Map<String, Object> map) {
		String sysid = System.currentTimeMillis() + "";
		if (!isRepeatMenuId(sysid)) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "菜单ID已经存在！");
			return;
		}
		String param = "sysmenuname  ,parentmenuid  ,url  ,isfunction  ,status  ,sort";
		String paramValue = "?  ,?  ,?  ,?  ,?  ,? ";
		StringBuilder update = new StringBuilder();
		update.append("insert into sys_menu(" + param + ",sysmenuid) values("+ paramValue + ",?)");
		String pr = sysMenu.getParentmenuid();
		if (pr.equals("0")) {
			pr = null;
		}
		try {
			// 新增
			if (updates(update.toString(), sysMenu.getSysmenuname(), pr, sysMenu.getUrl(), sysMenu.getIsfunction(),
					sysMenu.getStatus(), sysMenu.getSort(), sysid) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "新增系统菜单管理成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "新增系统菜单管理失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "新增系统菜单管理失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	public boolean isRepeatMenuId(String sysmenuid) {
		StringBuilder update = new StringBuilder();
		update.append("select * from sys_menu where sysmenuid = ?");
		try {
			List<SysMenuViewEntity> list = query(update.toString(), sysmenuid);
			if (list == null || list.size() <= 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	/**
	 * 修改
	 * 
	 */
	public void update(SysMenuForm sysMenu, Map<String, Object> map) {
		String param = "sysmenuname = ?  ,url = ? ,isfunction = ? ,status = ?"
				+ " ,sort = ?";

		StringBuilder update = new StringBuilder();
		update.append("update sys_menu set " + param + " where sysmenuid = ? ");

		try {
			Integer isFunction = sysMenu.getIsfunction();
			String parentMenuid = sysMenu.getParentmenuid();
			if (parentMenuid.equals("0")) {
				parentMenuid = null;
			}
			if (isFunction == 1) {
				parentMenuid = null;
			}

			// 修改
			if (updates(update.toString(), sysMenu.getSysmenuname(),  sysMenu.getUrl(), isFunction,
					sysMenu.getStatus(), sysMenu.getSort(), sysMenu.getSysmenuid()) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "修改系统菜单管理成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "修改系统菜单管理失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "修改系统菜单管理失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 删除栏目
	 * 
	 * @param channel
	 * @param map
	 */
	public void del(SysMenuForm sysMenu, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from sys_menu where sysmenuid = ? ");

		try {
			if (updates(sql.toString(), sysMenu.getSysmenuid()) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "删除成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "删除失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "修改失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}
}
