package com.yard.manager.modules.user.entity;

import org.apache.commons.lang.StringUtils;

import com.yard.manager.base.constant.ManagerConstant;

/**
 * 推送用户信息
 * @author : xiaym
 * @date : 2015年7月6日 下午8:53:21
 * @version : 1.0
 */
public class UserPush {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 最后登录的平台
	 */
	private Integer lastPlatform;
	/**
	 * 最后登录的设备Token
	 */
	private String lastToken;
	/**
	 * 所属院子
	 */
	private Integer courtyardId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLastPlatform() {
		return lastPlatform;
	}
	public void setLastPlatform(Integer lastPlatform) {
		this.lastPlatform = lastPlatform;
	}
	public String getLastToken() {
		return lastToken;
	}
	public void setLastToken(String lastToken) {
		this.lastToken = lastToken;
	}
	public boolean isIosUser() {
		return this.getLastPlatform()==ManagerConstant.DEVICE_IOS&&StringUtils.isNotBlank(this.getLastToken());
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	
}
