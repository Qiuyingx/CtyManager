package com.yard.manager.platform.entity;

public class User {
	private String sysUserId;
	private String sysUserName;
	private String sysUserPwd;
	private int status;
	private String createTime;
	private String wxConfigId;
	private String sysUserNo;
	private String sysGroupId;
	private String createUserName;
	private String yardids;
	private Integer trainId;
	
	public String getSysGroupId() {
		return sysGroupId;
	}

	public void setSysGroupId(String sysGroupId) {
		this.sysGroupId = sysGroupId;
	}

	public String getSysUserNo() {
		return sysUserNo;
	}

	public void setSysUserNo(String sysUserNo) {
		this.sysUserNo = sysUserNo;
	}

	public String getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(String wxConfigId) {
		this.wxConfigId = wxConfigId;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getSysUserPwd() {
		return sysUserPwd;
	}

	public void setSysUserPwd(String sysUserPwd) {
		this.sysUserPwd = sysUserPwd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getYardids() {
		return yardids;
	}

	public void setYardids(String yardids) {
		this.yardids = yardids;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

}
