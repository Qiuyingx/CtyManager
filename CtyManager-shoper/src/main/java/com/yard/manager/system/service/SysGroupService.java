package com.yard.manager.system.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yard.core.service.common.BaseService;
import com.yard.core.service.common.DBService;
import com.yard.core.util.GenUUID;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.ConditionEntity;
import com.yard.manager.base.entity.TreeNodeEntity;
import com.yard.manager.platform.entity.Menu;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.system.entity.SysGroupForm;
import com.yard.manager.system.entity.SysGroupViewEntity;
import com.yard.manager.system.entity.SysRoleEntity;

/**
 * Service
 * 
 * @author 源码自动生成
 *
 */
public class SysGroupService extends BaseService<SysGroupViewEntity> {
	private static final SysGroupService cs = new SysGroupService();

	private SysGroupService() {

	}

	public static SysGroupService getInstance() {
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
	public List<SysGroupViewEntity> querySysGroupList(ConditionEntity condition, String sort, String order, long page, long rows) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from sys_group a where 1=1 ");

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
	public long querySysGroupListCount(ConditionEntity condition) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from sys_group a where 1=1 ");

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 新增用户组
	 * 
	 */
	public void add(SysGroupForm sysGroup, String userId, Map<String, Object> map, User user) {
		StringBuilder update = new StringBuilder();
		update.append("insert into sys_group(sysgroupname, status, sysgroupid) values(?, ?, ?) ");

		try {
			// 新增
			if (updates(update.toString(), sysGroup.getSysGroupName(), ManagerConstant.ENABLED, GenUUID.uuid()) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "新增用户分组成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "新增用户分组失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "新增用户分组失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户组
	 * 
	 */
	public void update(SysGroupForm sysGroup, Map<String, Object> map) {
		StringBuilder update = new StringBuilder();
		update.append("update sys_group set sysgroupname = ? where sysgroupid = ? ");

		try {
			// 修改
			if (updates(update.toString(), sysGroup.getSysGroupName(), sysGroup.getSysGroupId()) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "修改用户分组成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "修改用户分组失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "修改用户分组失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 禁用/启用用户组
	 * 
	 * @param channel
	 * @param map
	 */
	public void updateStatus(String sysGroupId, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		sql.append("update sys_group set status = abs(status - 1) where sysgroupid = ? ");

		try {
			if (updates(sql.toString(), sysGroupId) == 1) {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
				map.put(ManagerConstant.JSON_COL_MSG, "禁用/启用用户组成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "禁用/启用用户组失败，数据库操作失败！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
			map.put(ManagerConstant.JSON_COL_MSG, "禁用/启用用户组失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	public TreeNodeEntity getGroupPermissionTree(String groupId, TreeNodeEntity currentNode, String nodeId) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT D.SYSMENUID ID, D.SYSMENUNAME TEXT, 'open' STATE ");
			sql.append("FROM SYS_GROUPROLE A, SYS_ROLE B, SYS_ROLEMENU C, SYS_MENU D ");
			sql.append("WHERE A.SYSROLEID = B.SYSROLEID AND B.SYSROLEID = C.SYSROLEID AND C.SYSMENUID = D.SYSMENUID ");
			sql.append("AND B.STATUS = ").append(ManagerConstant.ENABLED).append(" AND D.STATUS = ").append(ManagerConstant.ENABLED)
					.append(" ");
			sql.append("AND A.SYSGROUPID = ? ");

			List<Object> params = new ArrayList<Object>();
			params.add(groupId);

			if (null == nodeId || "".equals(nodeId.trim())) {
				sql.append("AND D.PARENTMENUID IS NULL ");
			} else {
				sql.append("AND D.PARENTMENUID = ? ");
				params.add(nodeId);
			}

			sql.append("ORDER BY SORT ");

			List<TreeNodeEntity> tree = query(TreeNodeEntity.class, sql.toString(), params.toArray());

			if (null != tree && tree.size() > 0) {
				for (TreeNodeEntity node : tree) {
					getGroupPermissionTree(groupId, node, node.getId());
				}

				currentNode.setChildren(tree);
			}

			return currentNode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public void save(String groupId, String roleRows, Map<String, Object> map) {
		Connection conn = null;
		try {
			conn = DBService.getDataSource().getConnection();
			conn.setAutoCommit(false);

			// 删除这个用户组所有角色
			String delSql = "DELETE FROM SYS_GROUPROLE WHERE SYSGROUPID = ? ";
			updates(conn, delSql, groupId);

			if (!roleRows.trim().equals("") && null != roleRows) {
				String[] roleArray = roleRows.split(",");
				if (roleArray.length > 0) {
					String sql = "INSERT INTO SYS_GROUPROLE (SYSGROUPID, SYSROLEID) VALUES (?, ?)";
					for (String roleId : roleArray) {
						if (updates(conn, sql, groupId, roleId) != 1) {
							conn.rollback();
							map.put(ManagerConstant.JSON_COL_MSG, "保存权限失败，保存权限过程中出现异常！");
							return;
						}
					}
				}
			}
			
			conn.commit();
			map.put(ManagerConstant.JSON_COL_MSG, "保存用户组权限成功！");

			// 刷新权限缓存
			if (!ShiroUtil.refreshAllUserRolePermission()) {
				// 刷新失败
				map.put(ManagerConstant.JSON_COL_MSG, "保存用户组权限成功，但刷新权限缓存失败，请重新保存一次！");
			}
		} catch (Exception e) {
			map.put(ManagerConstant.JSON_COL_MSG, "保存权限失败，服务器繁忙，请稍候再试！");
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

	public List<SysRoleEntity> getGroupRole(String groupId) {
		try {
			return query(SysRoleEntity.class, "SELECT SYSROLEID FROM SYS_GROUPROLE WHERE SYSGROUPID = ? ", groupId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SysRoleEntity>();
		}
	}
}
