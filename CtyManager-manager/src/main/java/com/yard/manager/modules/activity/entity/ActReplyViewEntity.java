package com.yard.manager.modules.activity.entity;

public class ActReplyViewEntity extends ActReplyEntity {
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 活动标题
	 */
	private String actTitle;
	/**
	 * 评论用户昵称
	 */
	private String rNickName;
	/**
	 * 被评论者昵称
	 */
	private String tNickName;
	
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
	public String getrNickName() {
		return rNickName;
	}
	public void setrNickName(String rNickName) {
		this.rNickName = rNickName;
	}
	public String gettNickName() {
		return tNickName;
	}
	public void settNickName(String tNickName) {
		this.tNickName = tNickName;
	}
	
}
