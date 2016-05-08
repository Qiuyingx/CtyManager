package com.yard.manager.merchant.train.entity.query;

public class TrainCourseQueryEntity {
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 查询课程banner推荐
	 */
	private String isBannerTop;
	/**
	 * 查询课程列表推荐
	 */
	private String isListTop;
	/**
	 * 课程状态
	 */
	private Integer status;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsBannerTop() {
		return isBannerTop;
	}
	public void setIsBannerTop(String isBannerTop) {
		this.isBannerTop = isBannerTop;
	}
	public String getIsListTop() {
		return isListTop;
	}
	public void setIsListTop(String isListTop) {
		this.isListTop = isListTop;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
