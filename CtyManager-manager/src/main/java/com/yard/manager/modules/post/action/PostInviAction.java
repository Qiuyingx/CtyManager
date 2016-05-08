package com.yard.manager.modules.post.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.post.entity.query.PostInviQueryEntity;
import com.yard.manager.modules.post.service.PostInviService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 帖子邀约
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午1:19:11
 * @version : 1.0
 */
@Results({
	@Result(type="freemarker", name="inviindex", location="/WEB-INF/content/modules/post/inviindex.html")
})
public class PostInviAction extends BaseAction{
	private static final long serialVersionUID = 109332538292122382L;
	private static final String NAMESPACE = "/post/invi";
	private static final PostInviService pis = PostInviService.getInstance();

	/**
	 * 进入列表页面
	 * @return
	 */
	@Action(NAMESPACE + "/inviindex")
	public String ivnviIndex() {
		
		return "inviindex";
	}
	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/querypostinvilist")
	public String queryPostInviList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", pis.queryPostInviCount(query, courtyardIds));
			map.put("rows", pis.queryPostInviList(query, page, rows, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	private PostInviQueryEntity query;

	public PostInviQueryEntity getQuery() {
		return query;
	}

	public void setQuery(PostInviQueryEntity query) {
		this.query = query;
	}
	
}
