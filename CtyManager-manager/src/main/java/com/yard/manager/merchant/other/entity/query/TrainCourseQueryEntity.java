package com.yard.manager.merchant.other.entity.query;

public class TrainCourseQueryEntity {
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 课程状态
	 */
	private Integer status;
	/**
	 * 学堂ID
	 */
	private Integer trainId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	
}
