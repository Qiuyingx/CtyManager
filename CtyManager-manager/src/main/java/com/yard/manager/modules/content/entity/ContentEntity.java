package com.yard.manager.modules.content.entity;

/**
 * 内容
 * @author : xiaym
 * @date : 2015年7月3日 下午4:31:01
 * @version : 1.0
 */
public class ContentEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 创建者ID
	 */
	private Integer managerId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简述
	 */
	private String description;
	/**
	 * 封面图
	 */
	private String titleImg;
	/**
	 * banner图
	 */
	private String bannerImg;
	/**
	 * 发布时间
	 */
	private long createTime;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 栏目ID
	 */
	private Integer channelId;
	/**
	 * 状态 (0草稿 1发布 2删除)
	 */
	private Integer status;
	/**
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 相关的学堂推荐IDs
	 */
	private String trainIds;
	/**
	 * 相关课程推荐IDs
	 */
	private String courseIds;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitleImg() {
		return titleImg;
	}
	
	public String getBannerImg() {
		return bannerImg;
	}
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getTrainIds() {
		return trainIds;
	}
	public void setTrainIds(String trainIds) {
		this.trainIds = trainIds;
	}
	public String getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}
	
}
