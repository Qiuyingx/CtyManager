package com.yard.manager.modules.relation.action;

import org.apache.struts2.convention.annotation.Action;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.relation.entity.AtRelationsViewEntity;
import com.yard.manager.modules.relation.service.AtRelationsService;

/**
 * 专题，活动资讯@关联
 * @author : xiaym
 * @date : 2015年8月9日 下午5:37:34
 * @version : 1.0
 */
public class AtRelationsAction extends BaseAction {
	private static final long serialVersionUID = -2072837019917077722L;
	private static final String NAMESPACE = "/attarget/info";
	private static final AtRelationsService ars = AtRelationsService.getInstance();
	
	/**
	 * 添加@关联关系
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		if(ars.addRelations(entity)) {
			JsonResult.toJson(map, true, "操作成功！");
		}else {
			JsonResult.toJson(map, false, "操作失败！");
		}
		return MAP;
	}
	
	/**
	 * 删除@关联关系
	 */
	@Action(NAMESPACE + "/del")
	public String del() {
		if(ars.del(entity)) {
			JsonResult.toJson(map, true, "操作成功！");
		}else {
			JsonResult.toJson(map, false, "操作失败！");
		}
		return MAP;
	}
	
	/**
	 * 获取已经存在的@关联关系
	 * @return
	 */
	@Action(NAMESPACE + "/query")
	public String query() {
		map.put("data", ars.query(entity));
		return MAP;
	}
	
	private AtRelationsViewEntity entity;

	public AtRelationsViewEntity getEntity() {
		return entity;
	}

	public void setEntity(AtRelationsViewEntity entity) {
		this.entity = entity;
	}
	
}
