package com.yard.manager.system.entity;

import java.io.Serializable;

/**
* 源码自动生成
*/
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 2877720398166771L;
    
    // fields
    	private java.lang.String sysmenuid;
		private java.lang.String sysmenuname;
		private java.lang.String parentmenuid;
		private java.lang.String url;
		private java.lang.Integer isfunction;
		private java.lang.Integer status;
		private java.lang.Integer sort;
	
    //fields
        /**
	 * Return the value associated with the column: sysmenuid
	 */
	public java.lang.String getSysmenuid () {
		return sysmenuid;
	}
	
	/**
	 * Set the value related to the column: sysmenuid
	 * @param sysmenuid the sysmenuid value
	 */
	public void setSysmenuid (java.lang.String sysmenuid) {
		this.sysmenuid = sysmenuid;
	}
	    /**
	 * Return the value associated with the column: sysmenuname
	 */
	public java.lang.String getSysmenuname () {
		return sysmenuname;
	}
	
	/**
	 * Set the value related to the column: sysmenuname
	 * @param sysmenuname the sysmenuname value
	 */
	public void setSysmenuname (java.lang.String sysmenuname) {
		this.sysmenuname = sysmenuname;
	}
	    /**
	 * Return the value associated with the column: parentmenuid
	 */
	public java.lang.String getParentmenuid () {
		return parentmenuid;
	}
	
	/**
	 * Set the value related to the column: parentmenuid
	 * @param parentmenuid the parentmenuid value
	 */
	public void setParentmenuid (java.lang.String parentmenuid) {
		this.parentmenuid = parentmenuid;
	}
	    /**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}
	
	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}
	    /**
	 * Return the value associated with the column: isfunction
	 */
	public java.lang.Integer getIsfunction () {
		return isfunction;
	}
	
	/**
	 * Set the value related to the column: isfunction
	 * @param isfunction the isfunction value
	 */
	public void setIsfunction (java.lang.Integer isfunction) {
		this.isfunction = isfunction;
	}
	    /**
	 * Return the value associated with the column: status
	 */
	public java.lang.Integer getStatus () {
		return status;
	}
	
	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.Integer status) {
		this.status = status;
	}
	    /**
	 * Return the value associated with the column: sort
	 */
	public java.lang.Integer getSort () {
		return sort;
	}
	
	/**
	 * Set the value related to the column: sort
	 * @param sort the sort value
	 */
	public void setSort (java.lang.Integer sort) {
		this.sort = sort;
	}
	}
