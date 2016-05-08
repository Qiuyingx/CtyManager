package com.yard.manager.merchant.train.entity;

public class TrainOpenViewEntity extends TrainOpenEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户账号
	 */
	private String userName;
	/**
	 * 是否需要自动创建商家账号 1自动创建
	 */
	private String isCreated;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIsCreated() {
		return isCreated;
	}
	public void setIsCreated(String isCreated) {
		this.isCreated = isCreated;
	}
	
}
