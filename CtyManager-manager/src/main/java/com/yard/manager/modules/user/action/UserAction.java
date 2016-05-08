package com.yard.manager.modules.user.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.wxsdk.user.manager.UserManager;
import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.user.entity.query.UserQueryEntity;
import com.yard.manager.modules.user.service.UserService;

/**
 * 用户基础信息
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "userindex", location="/WEB-INF/content/modules/user/userindex.html"),
	@Result(type = "freemarker", name = "userhomeindex", location="/WEB-INF/content/modules/user/userhomeindex.html"),
	@Result(type = "freemarker", name = "valildsourceindex", location="/WEB-INF/content/modules/user/valildsourceindex.html")
})
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 5044411513754625635L;
	private static final String NAMESPACE = "/user/info";
	private static final UserService us = UserService.getInstance();
	
	/**
	 * 进入用户列表页面
	 */
	@Action(NAMESPACE + "/userindex")
	public String userIndex() {
		try {
			UserManager.getUserWebInfo("", "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "userindex";
	}
	
	/**
	 * 进入用户空间
	 */
	@Action(NAMESPACE + "/userhomeindex")
	public String userHomeIndex(){
		return "userhomeindex";
	}
	
	/**
	 * 进入验证邻豆来源页面
	 */
	@Action(NAMESPACE + "/valildsourceindex")
	public String valildSourceIndex() {
		return "valildsourceindex";
	}
 	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/queryuserlist")
	public String queryUserList() {
		try {
			map.put("total", us.queryUserCount(query));
			map.put("rows", us.queryUserList(query, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 时间禁用
	 */
	@Action(NAMESPACE + "/limittime")
	public String limitTime() {
		us.toDisabled(id, time, map);
		return MAP;
	}
	
	/**
	 * 解除禁用
	 */
	@Action(NAMESPACE + "/cancellimittime")
	public String cancelLimitTime() {
		us.cancelLimitTime(id, map);
		return MAP;
	}
	
	/**
	 * 后台赠送邻豆
	 */
	@Action(NAMESPACE + "/admingetlindou")
	public String adminGetLindou() {
		us.adminToLindou(id, count, remark, map);
		return MAP;
	}
	
	/**
	 * 通过昵称模糊查找用户
	 */
	@Action(NAMESPACE + "/getuserbynickname") 
	public String getUserByNickName() {
		map.put("data", us.getUserByNickName(nickName));
		return MAP;
	}
	
	private UserQueryEntity query;
	private Integer id;  // 用户ID
	private String time; // 禁用时间
	private String remark; // 备注
	private Integer count; // 数量
	private String nickName; // 用户昵称

	public UserQueryEntity getQuery() {
		return query;
	}

	public void setQuery(UserQueryEntity query) {
		this.query = query;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
