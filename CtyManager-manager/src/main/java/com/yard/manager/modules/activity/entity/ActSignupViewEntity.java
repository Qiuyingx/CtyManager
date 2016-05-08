package com.yard.manager.modules.activity.entity;

public class ActSignupViewEntity extends ActSignup {
	/**
	 * 报名用户昵称
	 */
	private String nickName;
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 活动标题
	 */
	private String actTitle;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	
}
