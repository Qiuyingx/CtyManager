package com.yard.manager.system.entity;

import java.io.Serializable;

/**
 * 源码自动生成
 */
public class SysGroupEntity implements Serializable {
	private static final long serialVersionUID = 8082889063493190L;

	private String sysGroupId;
	private String sysGroupName;
	private Integer status;
	private String wxConfigId;
	
	public String getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(String wxConfigId) {
		this.wxConfigId = wxConfigId;
	}

	public String getSysGroupId() {
		return sysGroupId;
	}

	public void setSysGroupId(String sysGroupId) {
		this.sysGroupId = sysGroupId;
	}

	public String getSysGroupName() {
		return sysGroupName;
	}

	public void setSysGroupName(String sysGroupName) {
		this.sysGroupName = sysGroupName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
