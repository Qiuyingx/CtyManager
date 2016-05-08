package com.yard.manager.system.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.service.common.DBService;
import com.yard.core.util.GenUUID;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.ConditionEntity;
import com.yard.manager.base.entity.TreeNodeEntity;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.platform.entity.Menu;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.system.entity.SysRoleForm;
import com.yard.manager.system.entity.SysRoleViewEntity;

/**
 * Service
 * 
 * @author 源码自动生成
 *
 */
public class SysRoleService extends BaseService<SysRoleViewEntity> {
	private static final SysRoleService cs = new SysRoleService();

	private SysRoleService() {

	}

	public static SysRoleService getInstance() {
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
	public List<SysRoleViewEntity> querySysRoleList(User user, ConditionEntity condition, boolean isDispAll, String sort, String order, long page, long rows) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from sys_role a where 1 = 1 ");
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
	public long querySysRoleListCount(User user, ConditionEntity condition, boolean isDispAll) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from sys_role a where 1=1 ");

		/** 列表数据，内置账户可以看见全部的，其它账户只能看见自己公众号的 add by jiangbo 20150415 start */
		// 判断是不是内置账户，内置账户可以看到所有的用户，其它的只能看见自己公众号的用户
		String wxConfigId = StringUtils.isEmpty(user.getWxConfigId()) ? "" : user.getWxConfigId();
		if (!user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER)) {
			// 非内置账户
			sql.append("And a.wxconfigid = ? ");
			params.add(wxConfigId);
		} else {
			// 内置账在页面是否要求显示所有的角色
			if (isDispAll) {
				sql.append("And a.sysuserid = ? ");
				params.add(ManagerConstant.SYSTEM_USER);
			} else {
				sql.append("And a.wxconfigid is null ");
			}
		}
		/** 列表数据，内置账户可以看见全部的，其它账户只能看见自己公众号的 add by jiangbo 20150415 end */
		
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
	public void add(SysRoleForm sysRole, Map<String, Object> map, User user) {
		StringBuilder update = new StringBuilder();
		update.append("insert into sys_role(sysrolename, status, sysroleid, sysuserid) values(?, ?, ?, ?)");

		try {
			// 新增
			if (updates(update.toString(), sysRole.getSysRoleName(), ManagerConstant.ENABLED, GenUUID.uuid(), ShiroUtil.getUserId()) == 1) {
				JsonResult.toJson(map, true, "新增用户角色成功！");
			} else {
				JsonResult.toJson(map, false, "新增用户角色失败，数据库操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "新增用户角色失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 修改
	 * 
	 */
	public void update(SysRoleForm sysRole, Map<String, Object> map) {
		StringBuilder update = new StringBuilder();
		update.append("update sys_role set sysrolename = ? where sysroleid = ? ");

		try {
			// 修改
			if (updates(update.toString(), sysRole.getSysRoleName(), sysRole.getSysRoleId()) == 1) {
				JsonResult.toJson(map, true, "修改用户角色成功！");
			} else {
				JsonResult.toJson(map, false, "修改用户角色失败，数据库操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "修改用户角色失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 禁用/启用角色
	 * 
	 * @param channel
	 * @param map
	 */
	public void updateStatus(String sysRoleId, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		sql.append("update sys_role set status = abs(status - 1) where sysroleid = ? ");

		try {
			if (updates(sql.toString(), sysRoleId) == 1) {
				JsonResult.toJson(map, true, "禁用/启用用户组成功！");
			} else {
				JsonResult.toJson(map, false, "禁用/启用用户组失败，数据库操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "禁用/启用用户组失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 生成权限树
	 * @param currentNode
	 * @param nodeId
	 * @return
	 */
	public TreeNodeEntity getPermissionTree(User user, TreeNodeEntity currentNode, String nodeId) {
		try {
			String sql = "";
			sql = "SELECT *,SYSMENUID ID, SYSMENUNAME TEXT, 'false' CHECKED, 'open' STATE FROM SYS_MENU WHERE 1 = 1 ";

			List<Object> params = new ArrayList<Object>();

			// 是否是内置账户
			if (!user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER)) {
				// 不是内置账户，则只能取所对应公众号创建人的所有权限
				sql += "AND SYSMENUID IN ( ";
				sql += "SELECT DISTINCT D.SYSMENUID FROM SYS_USER B, SYS_GROUPROLE C, SYS_ROLEMENU D ";
				sql += "WHERE B.SYSGROUPID = C.SYSGROUPID AND C.SYSROLEID = D.SYSROLEID) ";
			}
			
			// nodeId为空则说明是取一级权限
			if (StringUtils.isEmpty(nodeId)) {
				sql += "AND PARENTMENUID IS NULL ";
			} else {
				sql += "AND PARENTMENUID = ? ";
				params.add(nodeId);
			}

			sql += "AND STATUS = ? ";
			params.add(ManagerConstant.ENABLED);

			sql += "ORDER BY SORT ";

			List<TreeNodeEntity> tree = query(TreeNodeEntity.class, sql, params.toArray());

			if (null != tree && tree.size() > 0) {
				for (TreeNodeEntity node : tree) {
					getPermissionTree(user, node, node.getId());
					Map<String, Object> attr = new HashMap<String, Object>();
					attr.put("url", node.getUrl());
					attr.put("isfunction", node.getIsfunction());
					attr.put("parentmenuid", node.getParentmenuid());
					attr.put("sort", node.getSort());
					attr.put("sysmenuname", node.getSysmenuname());
					attr.put("status", node.getStatus());
					node.setAttributes(attr);
				}

				currentNode.setChildren(tree);
			}

			return currentNode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取某一角色的权限集合
	 * @param roleId
	 * @return
	 */
	public List<Menu> getRolePermission(String roleId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT C.* FROM SYS_ROLE A, SYS_ROLEMENU B, SYS_MENU C ");
		sql.append("WHERE A.SYSROLEID = B.SYSROLEID AND B.SYSMENUID = C.SYSMENUID AND A.STATUS = ").append(ManagerConstant.ENABLED)
				.append(" ");
		sql.append("AND C.STATUS = ").append(ManagerConstant.ENABLED).append(" AND A.SYSROLEID = ? ORDER BY C.SYSMENUID, C.SORT");

		try {
			return query(Menu.class, sql.toString(), roleId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Menu>();
		}
	}

	/**
	 * 保存角色权限
	 * @param roleId
	 * @param menuIds
	 * @param map
	 */
	public void save(String roleId, String menuIds, Map<String, Object> map) {
		Connection conn = null;
		try {
			conn = DBService.getDataSource().getConnection();
			conn.setAutoCommit(false);

			// 删除这个角色的权限
			String delSql = "DELETE FROM SYS_ROLEMENU WHERE SYSROLEID = ? ";
			updates(conn, delSql, roleId);

			if (!menuIds.trim().equals("") && null != menuIds) {
				String[] menuArray = menuIds.split(",");
				if (menuArray.length > 0) {
					String sql = "INSERT INTO SYS_ROLEMENU (SYSROLEID, SYSMENUID) VALUES (?, ?)";
					for (String menuId : menuArray) {
						if (updates(conn, sql, roleId, menuId) != 1) {
							conn.rollback();
							JsonResult.toJson(map, false, "保存权限失败，保存权限过程中出现异常！");
							return;
						}
					}
				}
			}
			
			conn.commit();
			ShiroUtil.refreshAllUserRolePermission(); // 刷新所有用户的权限
			JsonResult.toJson(map, true, "保存角色权限成功！");
		} catch (Exception e) {
			JsonResult.toJson(map, false, "保存权限失败，服务器繁忙，请稍候再试！");
			try {
				if (null != conn) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<SysRoleViewEntity> queryRoleList(String sort, String order) {
		try {
			List<Object> params = new ArrayList<Object>();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM SYS_ROLE WHERE 1 = 1 ");

			return query(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<SysRoleViewEntity>();
		}
	}

}
