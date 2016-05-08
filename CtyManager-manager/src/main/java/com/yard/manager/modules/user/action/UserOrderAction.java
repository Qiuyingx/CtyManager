package com.yard.manager.modules.user.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.user.entity.query.UserOrderQueryEntity;
import com.yard.manager.modules.user.service.UserOrderService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 用户订单
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "userorderindex", location="/WEB-INF/content/modules/user/userorderindex.html")
})
public class UserOrderAction extends BaseAction {
	private static final long serialVersionUID = 5044411513754625635L;
	private static final String NAMESPACE = "/user/info";
	private static final UserOrderService ubd = UserOrderService.getInstance();
	
	/**
	 * 进入用户订单列表页面
	 */
	@Action(NAMESPACE + "/userorderindex")
	public String userIndex() {
		return "userorderindex";
	}
	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/queryuserorderlist")
	public String queryUserList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", ubd.queryUserOrderCount(query, courtyardIds));
			map.put("rows", ubd.queryUserOrderList(query, page, rows, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	private UserOrderQueryEntity query;

	public UserOrderQueryEntity getQuery() {
		return query;
	}

	public void setQuery(UserOrderQueryEntity query) {
		this.query = query;
	}
	
}
