package com.yard.manager.merchant.other.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.merchant.other.entity.TrainInfoViewEntity;
import com.yard.manager.merchant.other.service.TrainInfoService;

/**
 * 店铺资料完善
 * @author : xiaym
 * @date : 2015年8月13日 下午4:53:17
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "traininfoindex", location = "/WEB-INF/content/merchant/other/traininfoindex.html")
})
public class TrainInfoAction extends BaseAction {
	private static final long serialVersionUID = -3719531580009240976L;
	private static final String NAMESPACE = "/other/train/traininfo";
	private static final TrainInfoService tis = TrainInfoService.getInstance();
	
	/**
	 * 进入开店审核页面
	 */
	@Action(NAMESPACE + "/traininfoindex")
	public String index() {
		entity = tis.findById(managerId);
		if(entity == null) {
			entity = new TrainInfoViewEntity();
		}
		return "traininfoindex";
	}
	
	/**
	 * 第一次开通培训室
	 */
	@Action(NAMESPACE + "/complete")
	public String complete() {
		tis.completeHouseMessage(entity, map);
		return MAP;
	}
	
	/**
	 * 后期修改店铺信息
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		tis.updateHouseMessage(entity, map);
		return MAP;
	}
	
	private TrainInfoViewEntity entity;
	private Integer userId; // 当前学堂的用户ID
	private Integer managerId; // 商家账号登录ID

	public TrainInfoViewEntity getEntity() {
		return entity;
	}

	public void setEntity(TrainInfoViewEntity entity) {
		this.entity = entity;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

}
