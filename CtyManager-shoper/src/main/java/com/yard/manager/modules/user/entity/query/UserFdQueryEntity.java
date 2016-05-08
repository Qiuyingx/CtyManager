package com.yard.manager.modules.user.entity.query;

/**
 * 用户反馈筛选
 * @author : xiaym
 * @date : 2015年6月28日 下午1:33:42
 * @version : 1.0
 */
public class UserFdQueryEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 所属院子
	 */
	private String courtyardIds;
	/**
	 * 用户电话
	 */
	private String userMobile;
	/**
	 * 反馈开始时间
	 */
	private String startTime;
	/**
	 * 反馈结束时间
	 */
	private String endTime;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCourtyardIds() {
		return courtyardIds;
	}
	public void setCourtyardIds(String courtyardIds) {
		this.courtyardIds = courtyardIds;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
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
	
}
