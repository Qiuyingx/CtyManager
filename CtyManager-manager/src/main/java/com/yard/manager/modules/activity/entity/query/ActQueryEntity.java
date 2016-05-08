package com.yard.manager.modules.activity.entity.query;

/**
 * 活动筛选
 * @author : xiaym
 * @date : 2015年6月23日 下午5:52:18
 * @version : 1.0
 */
public class ActQueryEntity {
	/**
	 * 活动标题
	 */
	private String actTitle;
	/**
	 * 所属院子ID
	 */
	private String courtyardId;
	/**
	 * 发布时间（开始时间）
	 */
	private String startTime;
	/**
	 * 发布时间(结束时间)
	 */
	private String endTime;
	/**
	 * 发布者昵称
	 */
	private String nickName;
	/**
	 * 是否可报名
	 */
	private Integer isDisable;
	
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	public String getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(String courtyardId) {
		this.courtyardId = courtyardId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	
}
