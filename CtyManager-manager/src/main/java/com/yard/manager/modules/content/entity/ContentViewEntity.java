package com.yard.manager.modules.content.entity;

public class ContentViewEntity extends ContentEntity {
	/**
	 * 推荐BannerId
	 */
	private Integer bannerId;
	/**
	 * 推荐列表ID
	 */
	private Integer listId;
	
	public Integer getBannerId() {
		return bannerId;
	}
	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}
	public Integer getListId() {
		return listId;
	}
	public void setListId(Integer listId) {
		this.listId = listId;
	}
}
