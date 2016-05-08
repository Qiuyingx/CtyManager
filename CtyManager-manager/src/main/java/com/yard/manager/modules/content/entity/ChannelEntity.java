package com.yard.manager.modules.content.entity;

/**
 * 栏目
 * @author : xiaym
 * @date : 2015年7月3日 下午4:30:53
 * @version : 1.0
 */
public class ChannelEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 创建者ID
	 */
	private Integer managerId;
	/**
	 * 栏目名称
	 */
	private Integer channelName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getChannelName() {
		return channelName;
	}
	public void setChannelName(Integer channelName) {
		this.channelName = channelName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
