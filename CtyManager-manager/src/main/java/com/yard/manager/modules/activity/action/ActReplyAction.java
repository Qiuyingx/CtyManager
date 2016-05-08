package com.yard.manager.modules.activity.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.activity.service.ActReplyService;
import com.yard.manager.modules.post.entity.PostViewEntity;

/**
 * 活动评论
 * @author : xiaym
 * @date : 2015年6月19日 下午5:46:34
 * @version : 1.0
 */
@Results({ 
		@Result(type = "freemarker", name = "actreplyindex", location = "/WEB-INF/content/modules/act/actreplyindex.html"),
	})
public class ActReplyAction extends BaseAction {
	private static final long serialVersionUID = -9206212665794844993L;
	private static final String NAMESPACE = "/act/reply";
	private static final ActReplyService ars = ActReplyService.getInstance();
	
	/**
	 * 评论列表
	 */
	@Action(NAMESPACE + "/replyindex")
	public String replyIndex() {
		return "actreplyindex"; 
	}
	
	/**
	 * 获取评论列表
	 */
	@Action(NAMESPACE + "/queryreplylist")
	public String queryReplyList() {
		try {
			map.put("total", ars.queryCount(actId));
			map.put("rows", ars.queryList(page, rows, actId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostViewEntity>());
		}
		return MAP;
	}	
	
	/**
	 * 删除指定评论
	 */
	@Action(NAMESPACE + "/del")
	public String del() {
		ars.del(id, map);
		return MAP;
	}
	
	private Integer actId;
	private Integer id;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
