package com.yard.manager.modules.activity.entity;

public class ActInfoViewEntity extends ActInfoEntity {
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 发布者昵称
	 */
	private String mNickName;
	/**
	 * 发布者登录名
	 */
	private String mUserName;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 是否进行推送  1是 2否
	 */
	private Integer isPushAll;
	
	/**
	 * @id nickmame 列表集合
	 */
	private String atTarget;
	
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getmNickName() {
		return mNickName;
	}
	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getIsPushAll() {
		return isPushAll;
	}
	public void setIsPushAll(Integer isPushAll) {
		this.isPushAll = isPushAll;
	}
	public String getAtTarget() {
		return atTarget;
	}
	public void setAtTarget(String atTarget) {
		this.atTarget = atTarget;
	}
	
}
