package com.yard.manager.modules.user.entity;

/**
 * 用户基础信息
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午2:54:36
 * @version : 1.0
 */
public class UserEntity {
	/**
	 * 用户ID
	 */
	private Integer id;
	/**
	 * 用户电话
	 */
	private String tel;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 职业ID
	 */
	private Integer careerId;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 头像地址
	 */
	private String headIcon;
	/**
	 * 邀请码
	 */
	private Integer inviteCode;
	/**
	 * 最后登录时间
	 */
	private long lastLoginTime;
	/**
	 * 注册平台
	 */
	private Integer platform;
	/**
	 * 注册时间
	 */
	private long registerTime;
	/**
	 * 禁用时间
	 */
	private long banningTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getCareerId() {
		return careerId;
	}
	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public Integer getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(Integer inviteCode) {
		this.inviteCode = inviteCode;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	
	public long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}
	public long getBanningTime() {
		return banningTime;
	}
	public void setBanningTime(long banningTime) {
		this.banningTime = banningTime;
	}
}
