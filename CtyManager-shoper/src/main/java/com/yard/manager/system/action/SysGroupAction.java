package com.yard.manager.system.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.TreeNodeEntity;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.system.entity.SysGroupForm;
import com.yard.manager.system.entity.SysGroupViewEntity;
import com.yard.manager.system.service.SysGroupService;

/**
 * 用户分组管理ACTION
 * 
 * @author 源码自动生成
 *
 */
@Results({ @Result(type = "freemarker", name = "sysGroupindex", location = "/WEB-INF/content/system/sysGroupindex.html") })
public class SysGroupAction extends BaseAction {
	private static final long serialVersionUID = 1833157226034616L;
	private static final String NAMESPACE = "/system/sysGroup";
	private static final SysGroupService cs = SysGroupService.getInstance();

	/**
	 * Action，跳转请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/sysGroupindex")
	public String sysGroupIndex() {
		return "sysGroupindex";
	}

	/**
	 * Action，列表请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/querysysGrouplist")
	public String querySysGroupList() {
		try {
			map.put("total", cs.querySysGroupListCount(condition));
			map.put("rows", cs.querySysGroupList(condition, sort, order, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<SysGroupViewEntity>());
		}
		return MAP;
	}

	/**
	 * Action，新增请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		User user = (User) ActionContext.getContext().getSession().get(ManagerConstant.SESSION_USER);

		cs.add(sysGroup, ShiroUtil.currentAccount(), map, user);
		return MAP;
	}

	/**
	 * Action，更新请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		cs.update(sysGroup, map);
		return MAP;
	}

	/**
	 * Action，禁用启用请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/updatestatus")
	public String updateStatus() {
		cs.updateStatus(sysGroup.getSysGroupId(), map);
		return MAP;
	}

	@Action(NAMESPACE + "/getgrouppermissiontree")
	public String getGroupPermissionTree() {
		// 初使化一个根节点
		TreeNodeEntity rootNode = new TreeNodeEntity();
		rootNode.setId("0");
		rootNode.setText("所有权限");
		rootNode.setState("open");

		treeNode = new ArrayList<TreeNodeEntity>();

		if (null == nodeId) {
			treeNode.add(rootNode);
		} else {
			treeNode.add(cs.getGroupPermissionTree(groupId, rootNode, null));
		}
		return TREENODE;
	}

	@Action(NAMESPACE + "/save")
	public String save() {
		cs.save(groupId, roleRows, map);
		return MAP;
	}

	@Action(NAMESPACE + "/getgrouprole")
	public String getGroupRole() {
		array = cs.getGroupRole(groupId);
		return ARRAY;
	}

	private SysGroupForm sysGroup;
	private String groupId;
	private String nodeId;
	private String roleRows;

	public SysGroupForm getSysGroup() {
		return sysGroup;
	}

	public void setSysGroup(SysGroupForm sysGroup) {
		this.sysGroup = sysGroup;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getRoleRows() {
		return roleRows;
	}

	public void setRoleRows(String roleRows) {
		this.roleRows = roleRows;
	}

}
