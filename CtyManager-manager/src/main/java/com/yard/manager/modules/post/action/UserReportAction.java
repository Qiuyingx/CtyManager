package com.yard.manager.modules.post.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.entity.query.PostQueryEntity;
import com.yard.manager.modules.post.service.UserReportService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 举报贴管理
 * @author : xiaym
 * @date : 2015年6月19日 下午5:46:34
 * @version : 1.0
 */
@Results({ 
		@Result(type = "freemarker", name = "userreportindex", location = "/WEB-INF/content/modules/post/userreportindex.html"),
		@Result(type = "freemarker", name = "invireportindex", location = "/WEB-INF/content/modules/post/invireportindex.html") 
	})
public class UserReportAction extends BaseAction {
	private static final long serialVersionUID = -9206212665794844993L;
	private static final String NAMESPACE = "/user/post";
	private static final UserReportService urs = UserReportService.getInstance();
	
	/**
	 * 进入帮帮和话题举报页面 
	 */
	@Action(NAMESPACE + "/userreportindex")
	public String userReportIndex() {
		return "userreportindex"; 
	}
	
	/**
	 * 进入邀约举报页面 
	 */
	@Action(NAMESPACE + "/invireportindex")
	public String inviReportIndex() {
		return "invireportindex"; 
	}
	
	/**
	 * 获取举报列表
	 */
	@Action(NAMESPACE + "/queryuserreportlist")
	public String queryUserReportList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", urs.queryUserReportCount(query, contentType, courtyardIds));
			map.put("rows", urs.queryUserReportList(query, page, rows, contentType, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostViewEntity>());
		}
		return MAP;
	}	
	
	/**
	 * 查看帖子详情
	 */
	@Action(NAMESPACE + "/getpostinfo")
	public String getPostInfo() {
		map.put("data", urs.getInfoById(query.getPostId()));
		
		return MAP;
	}
	
	private PostQueryEntity query;
	private Integer contentType;

	public PostQueryEntity getQuery() {
		return query;
	}

	public void setQuery(PostQueryEntity query) {
		this.query = query;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
}
