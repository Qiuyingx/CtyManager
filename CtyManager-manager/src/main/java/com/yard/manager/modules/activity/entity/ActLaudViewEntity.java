package com.yard.manager.modules.activity.entity;

public class ActLaudViewEntity extends ActLaudEntity {
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 点赞用户昵称
	 */
	private String userName;
	/**
	 * 用户电话
	 */
	private String userTel;
	/**
	 * 活动标题
	 */
	private String actTitle;
	
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	 
}
