package com.yard.manager.system.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.system.entity.SysUserForm;
import com.yard.manager.system.entity.SysUserViewEntity;
import com.yard.manager.system.service.SysUserService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 系统用户ACTION
 * 
 * @author 源码自动生成
 *
 */
@Results({ @Result(type = "freemarker", name = "sysUserindex", location = "/WEB-INF/content/system/sysUserindex.html") })
public class SysUserAction extends BaseAction {
	private static final long serialVersionUID = 8556671802117593L;
	private static final String NAMESPACE = "/system/sysUser";
	private static final SysUserService cs = SysUserService.getInstance();

	/**
	 * Action，跳转请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/sysUserindex")
	public String sysUserIndex() {
		return "sysUserindex";
	}

	/**
	 * Action，列表请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/querysysUserlist")
	public String querySysUserList() {
		try {
			User user = (User) ActionContext.getContext().getSession().get(ManagerConstant.SESSION_USER);

			map.put("total", cs.querySysUserListCount(user, condition));
			map.put("rows", cs.querySysUserList(user, condition, sort, order, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<SysUserViewEntity>());
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
		User user = ShiroUtil.getUser();
		cs.add(sysUser, user, map);
		return MAP;
	}

	/**
	 * Action，更新请求
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		cs.update(sysUser, map);
		return MAP;
	}

	/**
	 * Action，启用/禁止 操作
	 * 
	 * @return
	 */
	@Action(NAMESPACE + "/updatestatus")
	public String updateStatus() {
		cs.updateStatus(sysUser.getSysUserId(), map);
		return MAP;
	}

	private SysUserForm sysUser;

	public SysUserForm getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUserForm sysUser) {
		this.sysUser = sysUser;
	}
}
