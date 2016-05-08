package com.yard.manager.modules.post.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.post.entity.PostTopEntity;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.entity.query.PostQueryEntity;
import com.yard.manager.modules.post.service.PostService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 帖子管理 
 * @author xym
 *
 */
@Results({ 
		@Result(type = "freemarker", name = "postindex", location = "/WEB-INF/content/modules/post/postindex.html"), 
		@Result(type = "freemarker", name = "helpindex", location = "/WEB-INF/content/modules/post/helpindex.html"),
	})
public class PostAction extends BaseAction {
	private static final long serialVersionUID = 7862524737561287L;
	private static final String NAMESPACE = "/post/post";
	private static final PostService cs = PostService.getInstance();
	
	/**
	 * 进入话题
	 * @return
	 */
	@Action(NAMESPACE + "/postindex")
	public String postIndex() {
		return "postindex";
	}
	
	/**
	 * 进入帮帮
	 * @return
	 */
	@Action(NAMESPACE + "/helpindex")
	public String helpIndex() {
		return "helpindex";
	}
	
	/**
	 * Action，列表请求
	 * @return
	 */
	@Action(NAMESPACE + "/querypostlist")
	public String queryPostList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", cs.queryPostListCount(query, contentType, courtyardIds));
			map.put("rows", cs.queryPostList(query, page, rows, contentType, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostViewEntity>());
		}
		return MAP;
	}

	/**
	 * 紧急求助审核
	 * @return
	 */
	@Action(NAMESPACE + "/changevalistatus")
	public String changeValiStatus() {
		cs.changeValiStatus(post, map);
		return MAP;
	}
	
	/**
	 * 删除帖子
	 * @return
	 */
	@Action(NAMESPACE + "/deletepost")
	public String deletePost() {
		cs.deletePost(id, map);
		return MAP;
	}
	
	/**
	 * 用户评论被采纳贴列表
	 */
	@Action(NAMESPACE + "/getacceptedpost")
	public String getAcceptedPost() {
		map.put("rows", cs.getAcceptedPost(suserid, cuserid));
		return MAP;
	}
	
	/**
	 * 用户评论采纳与发帖人统计
	 */
	@Action(NAMESPACE + "/getacceptedcount")
	public String getAcceptedCount() {
		map.put("count", cs.getAcceptedCount(cuserid));
		return MAP;
	}
	
	/**
	 * 置顶帖子
	 */
	@Action(NAMESPACE + "/totoppost")
	public String toTopPost() {
		cs.delTopPost(topPost.getPostId());
		if(cs.addTopPost(topPost)){
			JsonResult.toJson(map, true, "置顶成功！");
		}else{
			JsonResult.toJson(map, false, "操作失败了！");
		}
		return MAP;
	}
	
	/**
	 * 取消帖子置顶
	 */
	@Action(NAMESPACE + "/canceltoppost")
	public String cancelTopPost() {
		if(cs.delTopPost(id)) {
			JsonResult.toJson(map, true, "取消置顶成功！");
		}else{
			JsonResult.toJson(map, false, "操作失败了！");
		}
		return MAP;
	}
	
	/**
	 * 获取帖子置顶信息
	 */
	@Action(NAMESPACE + "/gettoppostinfo")
	public String getTopPostInfo() {
		PostTopEntity topEntity = cs.getTopPost(id);
		if(topEntity == null) {
			topEntity = new PostTopEntity();
		}
		map.put("data", topEntity);
		return MAP;
	}
	
	private PostViewEntity post;
	private PostTopEntity topPost;
	private Integer contentType;
	private PostQueryEntity query = new PostQueryEntity();
	private Integer id; // 帖子ID
	private Integer suserid; // 发帖人ID
	private Integer cuserid; // 被采纳者ID

	public PostViewEntity getPost() {
		return post;
	}

	public void setPost(PostViewEntity post) {
		this.post = post;
	}

	public PostTopEntity getTopPost() {
		return topPost;
	}

	public void setTopPost(PostTopEntity topPost) {
		this.topPost = topPost;
	}

	public Integer getSuserid() {
		return suserid;
	}

	public void setSuserid(Integer suserid) {
		this.suserid = suserid;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public PostQueryEntity getQuery() {
		return query;
	}

	public void setQuery(PostQueryEntity query) {
		this.query = query;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCuserid() {
		return cuserid;
	}

	public void setCuserid(Integer cuserid) {
		this.cuserid = cuserid;
	}

}
