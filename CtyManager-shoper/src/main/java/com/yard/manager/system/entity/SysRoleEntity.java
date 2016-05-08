package com.yard.manager.system.entity;

import java.io.Serializable;

/**
 * 源码自动生成
 */
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 8678294414808092L;

	private String sysRoleId;
	private String sysRoleName;
	private Integer status;
	private String wxConfigId;

	public String getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(String sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public String getSysRoleName() {
		return sysRoleName;
	}

	public void setSysRoleName(String sysRoleName) {
		this.sysRoleName = sysRoleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(String wxConfigId) {
		this.wxConfigId = wxConfigId;
	}
}
