package com.yard.manager.modules.post.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.entity.query.PostReplyQueryEntity;
import com.yard.manager.modules.post.service.PostReplyService;

/**
 * 帖子评论
 * @author : xiaym
 * @date : 2015年6月19日 下午5:46:34
 * @version : 1.0
 */
@Results({ 
		@Result(type = "freemarker", name = "postreplyindex", location = "/WEB-INF/content/modules/post/postreplyindex.html"),
	})
public class PostReplyAction extends BaseAction {
	private static final long serialVersionUID = -9206212665794844993L;
	private static final String NAMESPACE = "/post/reply";
	private static final PostReplyService rs = PostReplyService.getInstance();
	
	/**
	 * 进入话题，帮帮， 邀约评论
	 */
	@Action(NAMESPACE + "/replyindex")
	public String ReplyIndex() {
		return "postreplyindex"; 
	}
	
	/**
	 * 获取评论列表
	 */
	@Action(NAMESPACE + "/queryreplylist")
	public String queryReplyList() {
		try {
			map.put("total", rs.queryCount(query, postId, contentType));
			map.put("rows", rs.queryList(query, page, rows, postId, contentType));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostViewEntity>());
		}
		return MAP;
	}	
	
	@Action(NAMESPACE + "/del")
	public String del() {
		rs.del(id, map);
		return MAP;
	}
	
	private PostReplyQueryEntity query;
	private Integer id;
	private Integer postId;
	private Integer contentType;

	public PostReplyQueryEntity getQuery() {
		return query;
	}

	public void setQuery(PostReplyQueryEntity query) {
		this.query = query;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
