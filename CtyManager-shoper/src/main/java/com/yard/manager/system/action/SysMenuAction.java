package com.yard.manager.system.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.system.entity.SysMenuForm;
import com.yard.manager.system.entity.SysMenuViewEntity;
import com.yard.manager.system.service.SysMenuService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 系统菜单管理ACTION
 * @author 源码自动生成
 *
 */
@Results({ @Result(type = "freemarker", name = "sysMenuindex", location = "/WEB-INF/content/system/sysMenuindex.html") })
public class SysMenuAction extends BaseAction {
	private static final long serialVersionUID = 6547515799122537L;
	private static final String CONTENT = "/system/sysMenu";
	private static final SysMenuService cs = SysMenuService.getInstance();
	
	/**
	 * Action，跳转请求
	 * @return
	 */
	@Action(CONTENT + "/sysMenuindex")
	public String sysMenuIndex() {
		return "sysMenuindex";
	}
	
	/**
	 * Action，列表请求
	 * @return
	 */
	@Action(CONTENT + "/querysysMenulist")
	public String querySysMenuList() {
		try {
			map.put("total", cs.querySysMenuListCount(condition));
			map.put("rows", cs.querySysMenuList(condition, sort, order, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<SysMenuViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * Action，新增请求
	 * @return
	 */
	@Action(CONTENT + "/add")
	public String add() {
		cs.add(sysMenu, ShiroUtil.currentAccount(), map);
		return MAP;
	}
	
	/**
	 * Action，更新请求
	 * @return
	 */
	@Action(CONTENT + "/update")
	public String update() {
		cs.update(sysMenu, map);
		return MAP;
	}
	
	/**
	 * Action，删除请求
	 * @return
	 */
	@Action(CONTENT + "/del")
	public String del() {
		cs.del(sysMenu, map);
		return MAP;
	}
	
	private SysMenuForm sysMenu;

	public SysMenuForm getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenuForm sysMenu) {
		this.sysMenu = sysMenu;
	}
}
