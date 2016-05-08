package com.yard.manager.merchant.train.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.merchant.train.entity.TrainOpenViewEntity;
import com.yard.manager.merchant.train.service.TrainOpenService;

/**
 * 开店申请处理
 * @author : xiaym
 * @date : 2015年8月13日 下午4:53:17
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "trainopenindex", location = "/WEB-INF/content/merchant/train/trainopenindex.html")
})
public class TrainOpenAction extends BaseAction {
	private static final long serialVersionUID = -3719531580009240976L;
	private static final String NAMESPACE = "/train/trainopen";
	private static final TrainOpenService tos = TrainOpenService.getInstance();
	
	/**
	 * 进入开店审核页面
	 */
	@Action(NAMESPACE + "/trainopenindex")
	public String index() {
		return "trainopenindex";
	}
	
	/**
	 * 获取列表数据
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", tos.queryCount());
			map.put("rows", tos.queryList(page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<TrainOpenViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 添加/修改审核备注
	 */
	@Action(NAMESPACE + "/addorupdateremark")
	public String addOrUpdateRemark() {
		tos.addOrUpdateRemark(entity, map);
		return MAP;
	}

	/**
	 * 审核操作
	 */
	@Action(NAMESPACE + "/udpatestatus")
	public String updateStatus() {
		tos.updateStatus(entity, map);
		return MAP;
	}
	
	/**
	 * 绑定商户账号
	 * @return
	 */
	@Action(NAMESPACE + "/binduser")
	public String bindUser() {
		tos.bindNameMap(openId, tel, map);
		
		return MAP;
	}
	
	/**
	 * 商家入驻申请通过
	 * @return
	 */
	@Action(NAMESPACE + "/submittrain")
	public String submitTrain() {
		tos.trainSubmit(entity, map);
		return MAP;
	}
	
	private TrainOpenViewEntity entity;
	private Integer openId; // 申请ID
	private String tel; // 商户账号

	public TrainOpenViewEntity getEntity() {
		return entity;
	}

	public void setEntity(TrainOpenViewEntity entity) {
		this.entity = entity;
	}

	public Integer getOpenId() {
		return openId;
	}

	public void setOpenId(Integer openId) {
		this.openId = openId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
