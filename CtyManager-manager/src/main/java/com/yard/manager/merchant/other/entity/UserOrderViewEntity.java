package com.yard.manager.merchant.other.entity;

/**
 * 
 * @author : leihc
 * @date : 2015年10月16日
 * @version : 1.0
 */
public class UserOrderViewEntity extends UserOrderEntity {

	/**
	 * 觅趣用户昵称
	 */
	private String nickname;
	/**
	 * 学堂名称
	 */
	private String trainName;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
	
}
