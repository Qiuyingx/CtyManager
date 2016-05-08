package com.yard.manager.modules.content.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.content.entity.ContentEntity;
import com.yard.manager.modules.content.entity.ContentViewEntity;
import com.yard.manager.modules.content.entity.query.ContentQueryEntity;
import com.yard.manager.modules.content.service.ContentService;
/**
 * 文章管理
 * @author : xiaym
 * @date : 2015年6月23日 下午2:37:41
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "contentindex", location="/WEB-INF/content/modules/content/contentindex.html"),
	@Result(type = "freemarker", name = "newsindex", location="/WEB-INF/content/modules/content/newsindex.html"),
	@Result(type = "freemarker", name = "content_edit", location="/WEB-INF/content/modules/content/content_edit.html"),
	@Result(type = "freemarker", name = "news_edit", location="/WEB-INF/content/modules/content/news_edit.html")
})
public class ContentAction extends BaseAction {
	private static final long serialVersionUID = -2170789104855964439L;
	private static final String NAMESPACE = "/content/info";
	private static final ContentService cs = ContentService.getInstance();
	
	/**
	 * 进入专题编辑页面
	 */
	@Action(NAMESPACE + "/content_edit")
	public String contentEdit() {
		return "content_edit";
	}
	
	/**
	 * 进入资讯编辑页面
	 */
	@Action(NAMESPACE + "/news_edit")
	public String newsEdit() {
		return "news_edit";
	}
	
	/**
	 * 进入专题列表页
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "contentindex";
	}
	
	/**
	 * 进入资讯列表页
	 * @return
	 */
	@Action(NAMESPACE + "/newsindex")
	public String newsIndex() {
		return "newsindex";
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", cs.queryCount(query, channelId));
			map.put("rows", cs.queryList(query, page, rows, channelId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<ContentViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 文章添加
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		cs.add(entity, map);
		return MAP;
	}
	
	/**
	 * 文章编辑
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		cs.update(entity, map);
		return MAP;
	}
	
	/**
	 * 草稿/发布 文章
	 */
	@Action(NAMESPACE + "/changestatus") 
	public String changeStatus() {
	  	cs.changeStatus(entity, map);
	  	return MAP;
	}
	
	/**
	 * 删除文章
	 */
	@Action(NAMESPACE + "/del")
	public String del() {
		cs.del(entity, map);
		return MAP;
	}
	
	/**
	 * 根据ID获取内容详情
	 * @return
	 */
	@Action(NAMESPACE + "/getcontentinfo")
	public String getContentInfo() {
		cs.getContentInfo(id, map);
		return MAP;
	}
	
	private ContentQueryEntity query;
	private ContentEntity entity;
	private Integer channelId;
	private Integer id;
	private String process; //操作类型  update 修改 add 添加（默认）

	public ContentQueryEntity getQuery() {
		return query;
	}

	public void setQuery(ContentQueryEntity query) {
		this.query = query;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public ContentEntity getEntity() {
		return entity;
	}
	public void setEntity(ContentEntity entity) {
		this.entity = entity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	
} 
