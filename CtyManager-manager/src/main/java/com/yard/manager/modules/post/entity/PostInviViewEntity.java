package com.yard.manager.modules.post.entity;

public class PostInviViewEntity extends PostInviEntity{
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 发邀用户昵称
	 */
	private String nickName;
	/**
	 * 发邀用户电话
	 */
	private String tel;
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
