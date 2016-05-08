package com.yard.manager.modules.post.entity;

public class UserReportViewEntity extends UserReportEntity {
	/**
	 * 举报者昵称
	 */
	private String oNickName;
	/**
	 * 举报者电话
	 */
	private String oTel;
	/**
	 * 发铁人昵称
	 */
	private String sNickName;
	/**
	 * 发帖人电话
	 */
	private String sTel;
	/**
	 * 被举报帖子标题
	 */
	private String title;
	/**
	 * 院子名称
	 */
	private String courtyardName;
	
	public String getoTel() {
		return oTel;
	}
	public void setoTel(String oTel) {
		this.oTel = oTel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getsNickName() {
		return sNickName;
	}
	public void setsNickName(String sNickName) {
		this.sNickName = sNickName;
	}
	public String getsTel() {
		return sTel;
	}
	public void setsTel(String sTel) {
		this.sTel = sTel;
	}
	public String getoNickName() {
		return oNickName;
	}
	public void setoNickName(String oNickName) {
		this.oNickName = oNickName;
	}
	
}
