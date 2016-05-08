package com.yard.manager.modules.user.entity;

public class UserStarViewEntity extends UserStarEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 所属院子
	 */
	private String courtyardName;
	
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
	
}
