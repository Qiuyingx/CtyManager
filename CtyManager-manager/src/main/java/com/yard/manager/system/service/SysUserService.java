package com.yard.manager.system.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.security.sha.sha4j.ShaUtil;
import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.ConditionEntity;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.system.entity.SysUserForm;
import com.yard.manager.system.entity.SysUserViewEntity;

/**
 * Service
 * 
 * @author 源码自动生成
 *
 */
public class SysUserService extends BaseService<SysUserViewEntity> {
	private static final SysUserService cs = new SysUserService();

	private SysUserService() {

	}

	public static SysUserService getInstance() {
		return cs;
	}

	/**
	 * 列表数据（分页）
	 * 
	 * @param user
	 * @param condition
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<SysUserViewEntity> querySysUserList(User user, ConditionEntity condition, String sort, String order, long page,
			long rows) {
		List<Object> params = new ArrayList<Object>();

		StringBuilder sql = new StringBuilder();
		sql.append("select a.*, b.sysgroupname, a.createusername createUserName from sys_user a, sys_group b where a.sysgroupid = b.sysgroupid ");

		// 判断是不是内置账户，内置账户可以看到所有的用户，其它的只能看见自己公众号的用户
		if (!user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER) && !user.getSysUserNo().equals(ManagerConstant.SYSTEM_SUPPER)) {
			// 非内置账户
			sql.append("and a.createuserid = ? ");
			params.add(user.getSysUserId());
		}
		
		// 其它查询条件
		if (null != condition) {
			if (null != condition.getStartDate() && !"".equals(condition.getStartDate())) {
				sql.append("and a.createtime >= ? ");
				params.add(condition.getStartDate() + " 00:00:00");
			}

			if (null != condition.getEndDate() && !"".equals(condition.getEndDate())) {
				sql.append("and a.createtime <= ? ");
				params.add(condition.getEndDate() + " 23:59:59");
			}
		}

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
	public long querySysUserListCount(User user, ConditionEntity condition) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from sys_user a where 1=1 ");

		// 判断是不是内置账户，内置账户可以看到所有的用户，其它的只能看见自己公众号的用户
		if (!user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER) && !user.getSysUserNo().equals(ManagerConstant.SYSTEM_SUPPER)) {
			// 非内置账户
			sql.append("and a.createuserid = ? ");
			params.add(user.getSysUserId());
		}
		
		if (null != condition) {
			if (null != condition.getStartDate() && !"".equals(condition.getStartDate())) {
				sql.append("and a.createtime >= ? ");
				params.add(condition.getStartDate() + " 00:00:00");
			}

			if (null != condition.getEndDate() && !"".equals(condition.getEndDate())) {
				sql.append("and a.createtime <= ? ");
				params.add(condition.getEndDate() + " 23:59:59");
			}
		}

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 创建普通后台管理员账号
	 */
	public void add(SysUserForm sysUser, User user, Map<String, Object> map) {
		// 排除系统内置账户
		if (sysUser.getSysUserNo().equals(ManagerConstant.SYSTEM_USER) || sysUser.getSysUserName().equals(ManagerConstant.SYSTEM_USER) ||
				sysUser.getSysUserNo().equals(ManagerConstant.SYSTEM_SUPPER) || sysUser.getSysUserName().equals(ManagerConstant.SYSTEM_SUPPER)) {
			JsonResult.toJson(map, false, "该用户编号和用户名称不允许使用或已被使用！");
			return;
		}
		
		StringBuilder update = new StringBuilder();
		update.append("insert into sys_user(sysuserno, sysusername, sysuserpwd, sysgroupid, status, createtime, createuserid, createusername, yardids) ");
		update.append("values(?, ?, ?, ?, ?, now(), ?, ?, ?)");

		try {

			if (((Long) certain("Select count(*) From sys_user Where sysuserno = ? ", sysUser.getSysUserNo())).intValue() >= 1) {
				JsonResult.toJson(map, false, "新增系统用户失败，用户编号【" + sysUser.getSysUserNo() + "】重复！");
				return;
			}
			// 新增
			if (updates(update.toString(), sysUser.getSysUserNo(), sysUser.getSysUserName(), ShaUtil.toSha256String(sysUser.getSysUserPwd()),
					sysUser.getSysGroupId(), ManagerConstant.ENABLED, user.getSysUserId(), user.getSysUserName(), sysUser.getYardids()) == 1) {
				JsonResult.toJson(map, true, "新增系统用户成功！");
			} else {
				JsonResult.toJson(map, false, "新增系统用户失败，数据库操作失败！");
			}
			
			ShiroUtil.refreshUserRolePermission(sysUser.getSysUserNo()); // 刷新当前用户的权限
		} catch (Exception e) {
			JsonResult.toJson(map, false, "新增系统用户失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建商户
	 * @param email 邮箱
	 * @param courtyardIds 所属院子
	 */
	public int createTrainAdmin(Connection conn, String email, Map<String, Object> map) {
		StringBuilder update = new StringBuilder();
		update.append("insert into sys_user(sysuserno, sysusername, sysuserpwd, sysgroupid, status, createtime, createuserid, createusername) ");
		update.append("values(?, ?, ?, ?, ?, now(), ?, ?)");

		try {
			if (((Long) certain("Select count(*) From sys_user Where sysuserno = ? ", email)).intValue() >= 1) {
				JsonResult.toJson(map, false, "新增系统用户失败，后台账号【" + email + "】已经被使用！");
				return 0;
			}
			// 新增
			int result = add(conn, update.toString(), email, email, ShaUtil.toSha256String(ManagerConstant.TRAIN_INFO_DEFAULT_PWD),
					ManagerConstant.TRAIN_INFO_SYS_GROUP_ID, ManagerConstant.ENABLED, ManagerConstant.TRAIN_INFO_SYS_CREATOR_ID,
					ManagerConstant.TRAIN_INFO_SYS_CREATOR_NAME);
			if (result >= 1) {
				JsonResult.toJson(map, true, "新增系统用户成功！");
				return result;
			} else {
				JsonResult.toJson(map, false, "新增系统用户失败，数据库操作失败！");
				return 0;
			}
			
		} catch (Exception e) {
			JsonResult.toJson(map, false, "新增系统用户失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modify by jiangbo 20150414 修改用户
	 * 
	 */
	public void update(SysUserForm sysUser, Map<String, Object> map) {
		try {
			// 排除系统内置账户
			if (sysUser.equals(ManagerConstant.SYSTEM_USER) || sysUser.getSysUserName().equals(ManagerConstant.SYSTEM_USER)) {
				JsonResult.toJson(map, false, "该用户编号和用户名称不允许使用或已被使用！");
				return;
			}
			
			// 准备SQL和值
			List<Object> params = new ArrayList<Object>(); // 问号参数

			StringBuilder update = new StringBuilder();
			update.append("update sys_user set sysusername = ?, sysgroupid = ?, yardids = ? ");
			params.add(sysUser.getSysUserName());
			params.add(sysUser.getSysGroupId());
			params.add(sysUser.getYardids());

			// 判断修改用户信息时，是否填写过密码，如果填过，则说明要修改密码
			if (!StringUtils.isEmpty(sysUser.getSysUserPwd())) {
				// 不为空，修改密码
				update.append(", sysuserpwd = ? ");
				params.add(ShaUtil.toSha256String(sysUser.getSysUserPwd()));
			}

			update.append("Where sysuserid = ? ");
			params.add(sysUser.getSysUserId());

			// 修改
			if (updates(update.toString(), params.toArray()) == 1) {
				JsonResult.toJson(map, true, "修改系统用户成功！");
			} else {
				map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
				map.put(ManagerConstant.JSON_COL_MSG, "修改系统用户失败，数据库操作失败！");
				JsonResult.toJson(map, false, "修改系统用户失败，数据库操作失败！");
			}
			
			ShiroUtil.refreshUserRolePermission(sysUser.getSysUserNo()); // 刷新当前用户的权限
		} catch (Exception e) {
			JsonResult.toJson(map, false, "修改系统用户失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param channel
	 * @param map
	 */
	public void del(SysUserForm sysUser, Map<String, Object> map) {
		/** 原则上是不允许提供删除用户功能 ，屏蔽代码delete by jiangbo 20150414 start */
		// StringBuilder sql = new StringBuilder();
		// sql.append("delete from sys_user where sysuserid = ? ");
		//
		// try {
		// if (updates(sql.toString(), sysUser.getSysuserid()) == 1) {
		// map.put(ManagerConstant.JSON_COL_ISSUCCESS, true);
		// map.put(ManagerConstant.JSON_COL_MSG, "删除成功！");
		// } else {
		// map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
		// map.put(ManagerConstant.JSON_COL_MSG, "删除失败，数据库操作失败！");
		// }
		// } catch (Exception e) {
		// map.put(ManagerConstant.JSON_COL_ISSUCCESS, false);
		// map.put(ManagerConstant.JSON_COL_MSG, "修改失败，服务器繁忙请稍候再试！");
		// e.printStackTrace();
		// }
		/** 原则上是不允许提供删除用户功能，屏蔽代码 delete by jiangbo 20150414 end */
	}
	
	/**
	 * 禁用/启用用户
	 * 
	 * @param channel
	 * @param map
	 */
	public void updateStatus(String sysUserId, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder();
		sql.append("update sys_user set status = abs(status - 1) where sysuserid = ? ");

		try {
			if (updates(sql.toString(), sysUserId) == 1) {
				JsonResult.toJson(map, true, "禁用/启用用户成功！");
			} else {
				JsonResult.toJson(map, false, "禁用/启用用户失败，数据库操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "禁用/启用用户失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}
}
