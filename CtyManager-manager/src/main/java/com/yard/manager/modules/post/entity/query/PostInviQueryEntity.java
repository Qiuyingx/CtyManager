package com.yard.manager.modules.post.entity.query;

/**
 * 邀约条件筛选
 * @author : xiaym
 * @date : 2015年6月28日 下午3:00:12
 * @version : 1.0
 */
public class PostInviQueryEntity {
	/**
	 * 所属院子
	 */
	private String courtyardIds;
	/**
	 * 邀约类型ID
	 */
	private String inviTypeIds;
	/**
	 * 活动时间 （开始时间）
	 */
	private String toStartTime;
	/**
	 * 活动时间（结束时间）
	 */
	private String toEndTime;
	/**
	 * 邀约发起时间（开始时间）
	 */
	private String startTime;
	/**
	 * 邀约发起时间（结束时间）
	 */
	private String endTime;
	/**
	 * 邀约发起人昵称
	 */
	private String nickName;
	/**
	 * 邀约发起人电话
	 */
	private String userMobile;
	
	public String getCourtyardIds() {
		return courtyardIds;
	}
	public void setCourtyardIds(String courtyardIds) {
		this.courtyardIds = courtyardIds;
	}
	public String getInviTypeIds() {
		return inviTypeIds;
	}
	public void setInviTypeIds(String inviTypeIds) {
		this.inviTypeIds = inviTypeIds;
	}
	public String getToStartTime() {
		return toStartTime;
	}
	public void setToStartTime(String toStartTime) {
		this.toStartTime = toStartTime;
	}
	public String getToEndTime() {
		return toEndTime;
	}
	public void setToEndTime(String toEndTime) {
		this.toEndTime = toEndTime;
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
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
}
