package com.yard.manager.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author : xiaym
 * @date : 2015年6月17日 下午5:37:30
 * @version : 1.0
 */
public class SysUserEntity implements Serializable {
	
	    private static final long serialVersionUID = 4272943107063778L;
	    
		private String sysuserid;
		private String sysuserno;
		private String sysusername;
		private String sysuserpwd;
		private String sysgroupid;
		private Date createtime;
		private Integer status;
		private String createUserName;
		
		public String getSysuserid() {
			return sysuserid;
		}
		public void setSysuserid(String sysuserid) {
			this.sysuserid = sysuserid;
		}
		public String getSysuserno() {
			return sysuserno;
		}
		public void setSysuserno(String sysuserno) {
			this.sysuserno = sysuserno;
		}
		public String getSysusername() {
			return sysusername;
		}
		public void setSysusername(String sysusername) {
			this.sysusername = sysusername;
		}
		public String getSysuserpwd() {
			return sysuserpwd;
		}
		public void setSysuserpwd(String sysuserpwd) {
			this.sysuserpwd = sysuserpwd;
		}
		public String getSysgroupid() {
			return sysgroupid;
		}
		public void setSysgroupid(String sysgroupid) {
			this.sysgroupid = sysgroupid;
		}
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getCreateUserName() {
			return createUserName;
		}
		public void setCreateUserName(String createUserName) {
			this.createUserName = createUserName;
		}
	
	}
