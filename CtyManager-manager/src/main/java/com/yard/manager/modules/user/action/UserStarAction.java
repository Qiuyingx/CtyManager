package com.yard.manager.modules.user.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.user.entity.UserStarViewEntity;
import com.yard.manager.modules.user.service.UserStarService;

/**
 * 技能审核认证
 * @author : xiaym
 * @date : 2015年8月17日 下午7:22:16
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "userstarindex", location="/WEB-INF/content/modules/user/userstarindex.html")
})
public class UserStarAction extends BaseAction {
	private static final long serialVersionUID = 7072208647035864925L;
	private static final String NAMESPACE = "/user/star";
	private UserStarService uss = UserStarService.getInstance();
	
	/**
	 * 进入技能认证页面
	 */
	@Action(NAMESPACE + "/userstarindex")
	public String userStarIndex() {
		return "userstarindex";
	}
 	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryUserList() {
		try {
			map.put("total", uss.queryCount());
			map.put("rows", uss.queryList(page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<UserStarViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 设呢和技能认证
	 */
	@Action(NAMESPACE + "/updatestatus")
	public String updateStatus() {
		uss.updateStatus(entity, map);
		return MAP;
	}
	
	private UserStarViewEntity entity;

	public UserStarViewEntity getEntity() {
		return entity;
	}

	public void setEntity(UserStarViewEntity entity) {
		this.entity = entity;
	}
	
}
