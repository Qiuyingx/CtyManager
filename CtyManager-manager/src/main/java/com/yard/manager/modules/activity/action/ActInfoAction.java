package com.yard.manager.modules.activity.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.activity.entity.ActInfoViewEntity;
import com.yard.manager.modules.activity.entity.query.ActQueryEntity;
import com.yard.manager.modules.activity.service.ActInfoService;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.platform.shiro.ShiroUtil;
/**
 * 活动基础信息
 * @author : xiaym
 * @date : 2015年6月23日 下午2:37:41
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "actinfoindex", location="/WEB-INF/content/modules/act/actinforindex.html"),
	@Result(type = "freemarker", name = "act_edit", location="/WEB-INF/content/modules/act/act_edit.html")
})
public class ActInfoAction extends BaseAction {
	private static final long serialVersionUID = -2170789104855964439L;
	private static final String NAMESPACE = "/act/info";
	private static final ActInfoService ais = ActInfoService.getInstance();
	
	/**
	 * 进入活动编辑页面
	 */
	@Action(NAMESPACE + "/act_edit")
	public String actEdit() {
		return "act_edit";
	}
	
	/**
	 * 进入活动列表页
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "actinfoindex";
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", ais.queryCount(query, courtyardIds));
			map.put("rows", ais.queryList(query, page, rows, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 活动添加
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		ais.add(entity, map);
		return MAP;
	}
	
	/**
	 * 活动编辑
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		ais.update(entity, map);
		return MAP;
	}
	
	/**
	 * 活动编辑
	 */
	@Action(NAMESPACE + "/deleteact")
	public String deleteAct() {
		ais.deleteAct(entity, map);
		return MAP;
	}
	
	/**
	 * 通过活动ID获取活动内容并返回
	 */
	@Action(NAMESPACE + "/getcontentbyid")
	public String getContentById() {
		ais.getActContent(id, map);
		return MAP;
	}
	
	/**
	 * 根据ID获取内容详情
	 * @return
	 */
	@Action(NAMESPACE + "/getcontentinfo")
	public String getContentInfo() {
		ais.getContentInfo(id, map);
		return MAP;
	}
	
	private ActQueryEntity query;
	private ActInfoViewEntity entity;
	private Integer id;
	private String process; //操作类型  update 修改 add 添加（默认）

	public ActQueryEntity getQuery() {
		return query;
	}

	public void setQuery(ActQueryEntity query) {
		this.query = query;
	}

	public ActInfoViewEntity getEntity() {
		return entity;
	}

	public void setEntity(ActInfoViewEntity entity) {
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
