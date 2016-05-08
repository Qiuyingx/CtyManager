package com.yard.manager.modules.vali.entity;

public class ValiUserViewEntity extends ValiUserEntity {
	/**
	 * 所属院子名称
	 */
	private String courtyardName;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户电话
	 */
	private String userMobile;
	
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
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
