package com.yard.manager.merchant.train.entity;

public class TrainInfoViewEntity extends TrainInfoEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 推荐时间
	 */
	private long getTime;

	public TrainInfoViewEntity() {
		
	}
	
	public TrainInfoViewEntity(Integer userId, Integer managerId) {
		this.userId = userId;
		this.managerId = managerId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}
	
}
