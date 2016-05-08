package com.yard.manager.modules.user.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.user.entity.query.UserFdQueryEntity;
import com.yard.manager.modules.user.service.UserFeedbackService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 用户反馈
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "userfdindex", location="/WEB-INF/content/modules/user/userfdindex.html")
})
public class UserFeedbackAction extends BaseAction {
	private static final long serialVersionUID = 5044411513754625635L;
	private static final String NAMESPACE = "/user/info";
	private static final UserFeedbackService ubd = UserFeedbackService.getInstance();
	
	/**
	 * 进入用户反馈列表页面
	 */
	@Action(NAMESPACE + "/userfdindex")
	public String userIndex() {
		return "userfdindex";
	}
	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/queryuserfdlist")
	public String queryUserList() {
		try {
			map.put("total", ubd.queryUserFdCount(query));
			map.put("rows", ubd.queryUserFdList(query, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	private UserFdQueryEntity query;

	public UserFdQueryEntity getQuery() {
		return query;
	}

	public void setQuery(UserFdQueryEntity query) {
		this.query = query;
	}
	
}
