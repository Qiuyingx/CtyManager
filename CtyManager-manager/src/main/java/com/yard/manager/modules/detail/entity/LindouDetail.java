package com.yard.manager.modules.detail.entity;

/**
 * 邻豆获取明细
 * @author : xiaym
 * @date : 2015年6月30日 上午11:52:21
 * @version : 1.0
 */
public class LindouDetail {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 变更数量 + 增加 ；-减少
	 */
	private Integer changeAmount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 获取时间
	 */
	private long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(Integer changeAmount) {
		this.changeAmount = changeAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
