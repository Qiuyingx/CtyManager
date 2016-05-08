package com.yard.manager.modules.user.entity.query;

/**
 * 用户信息筛选
 * @author : xiaym
 * @date : 2015年6月24日 上午10:04:41
 * @version : 1.0
 */
public class UserQueryEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户电话
	 */
	private String tel;
	/**
	 * 职业
	 */
	private Integer careerId;
	/**
	 * 所属院子IDs
	 */
	private String courtyardId;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 注册平台
	 */
	private Integer platform;
	/**
	 * 
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 最后登录时间（起）
	 */
	private String lastLoginStartTime;
	/**
	 * 最后登录时间（止）
	 */
	private String lastLoginEndTime;
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	public UserQueryEntity() {
		
	}
	
	public UserQueryEntity(Integer code) {
		this.userId = code;
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
	public Integer getCareerId() {
		return careerId;
	}
	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}
	
	public String getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(String courtyardId) {
		this.courtyardId = courtyardId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getLastLoginStartTime() {
		return lastLoginStartTime;
	}
	public void setLastLoginStartTime(String lastLoginStartTime) {
		this.lastLoginStartTime = lastLoginStartTime;
	}
	public String getLastLoginEndTime() {
		return lastLoginEndTime;
	}
	public void setLastLoginEndTime(String lastLoginEndTime) {
		this.lastLoginEndTime = lastLoginEndTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
