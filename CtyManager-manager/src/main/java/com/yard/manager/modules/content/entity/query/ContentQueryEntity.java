package com.yard.manager.modules.content.entity.query;

public class ContentQueryEntity {
	/**
	 * 文章ID
	 */
	private Integer contentId;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 所属栏目IDs
	 */
	private String channelIds;
	/**
	 * 发布时间(起)
	 */
	private String startTime;
	/**
	 * 发布时间(止)
	 */
	private String endTime;
	/**
	 * 文章状态 (0草稿 1发布 2删除)
	 */
	private Integer status;
	/**
	 * 推荐Banner
	 */
	private String isBannerTop;
	/**
	 * 查询课程列表推荐
	 */
	private String isListTop;
	
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChannelIds() {
		return channelIds;
	}
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
