package com.yard.manager.modules.user.entity;

public class UserFeedbackViewEntity extends UserFeedbackEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户电话
	 */
	private String tel;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
