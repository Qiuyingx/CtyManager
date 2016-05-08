package com.yard.manager.merchant.train.entity;

public class TrainCourseViewEntity extends TrainCourseEntity {
	/**
	 * 推荐BannerId
	 */
	private Integer bannerId;
	/**
	 * 推荐列表ID
	 */
	private Integer listId;
	/**
	 * 学堂名称
	 */
	private String trainName;
	
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
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
}
