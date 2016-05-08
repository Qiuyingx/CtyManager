package com.yard.manager.system.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.TreeNodeEntity;
import com.yard.manager.platform.entity.User;
import com.yard.manager.system.entity.SysRoleForm;
import com.yard.manager.system.entity.SysRoleViewEntity;
import com.yard.manager.system.service.SysRoleService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户角色管理ACTION
 * 
 * @author 源码自动生成
 *
 */
@Results({ @Result(type = "freemarker", name = "sysRoleindex", location = "/WEB-INF/content/system/sysRoleindex.html") })
public class SysRoleAction extends BaseAction {
	private static final long serialVersionUID = 6475985574044562L;
	private static final String NAMESPACE = "/system/sysRole";
	private static final SysRoleService cs = SysRoleService.getInstance();

	/**
	 * Action，跳转请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/sysRoleindex")
	public String sysRoleIndex() {
		return "sysRoleindex";
	}

	/**
	 * Action，列表请求
	 * 
	 * @return
	 */
	/*
	 * @Action(NAMESPACE + "/querysysRolelist") public String querySysRoleList() { try { map.put("total",
	 * cs.querySysRoleListCount(condition)); map.put("rows", cs.querySysRoleList(condition, sort, order, page, rows)); } catch
	 * (Exception e) { e.printStackTrace(); map.put("total", 0); map.put("rows", new ArrayList<SysRoleViewEntity>()); } return
	 * MAP; }
	 */
	@Action(NAMESPACE + "/queryrolelist")
	public String queryRoleList() {
		try {
			map.put("rows", cs.queryRoleList(sort, order));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("rows", new ArrayList<SysRoleViewEntity>());
		}
		return MAP;
	}

	@Action(NAMESPACE + "/getpermissiontree")
	public String getPermissionTree() {
		User user = (User) ActionContext.getContext().getSession().get(ManagerConstant.SESSION_USER);

		// 初使化一个根节点
		TreeNodeEntity rootNode = new TreeNodeEntity();
		rootNode.setChecked(false);
		rootNode.setId("0");
		rootNode.setText("所有权限");
		rootNode.setState("open");

		TreeNodeEntity nodeEntity = cs.getPermissionTree(user, rootNode, null);

		treeNode = new ArrayList<TreeNodeEntity>();
		treeNode.add(nodeEntity);

		return TREENODE;
	}

	@Action(NAMESPACE + "/getrolepermission")
	public String getRolePermission() {
		array = cs.getRolePermission(roleId);
		return ARRAY;
	}

	/**
	 * Action，新增请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		User user = (User) ActionContext.getContext().getSession().get(ManagerConstant.SESSION_USER);

		cs.add(sysRole, map, user);
		return MAP;
	}

	/**
	 * Action，更新请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		cs.update(sysRole, map);
		return MAP;
	}

	/**
	 * Action，删除请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/updatestatus")
	public String updateStatus() {
		cs.updateStatus(sysRole.getSysRoleId(), map);
		return MAP;
	}

	@Action(NAMESPACE + "/save")
	public String save() {
		cs.save(roleId, menuIds, map);
		return MAP;
	}

	private SysRoleForm sysRole;
	private String roleId;
	private String menuIds;
	private boolean isDispAll;
	
	public boolean getIsDispAll() {
		return isDispAll;
	}

	public void setIsDispAll(boolean isDispAll) {
		this.isDispAll = isDispAll;
	}

	public SysRoleForm getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRoleForm sysRole) {
		this.sysRole = sysRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

}
