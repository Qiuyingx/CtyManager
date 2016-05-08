package com.yard.manager.modules.activity.entity;

/**
 * 活动基础信息  @table t_activity_info
 * @author : xiaym
 * @date : 2015年6月23日 下午12:04:51
 * @version : 1.0
 */
public class ActInfoEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 活动标题
	 */
	private String actTitle;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 活动内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 发布者ID
	 */
	private String managerId;
	/**
	 * 封面图
	 */
	private String image;
	/**
	 * 人数限制
	 */
	private Integer countLimit;
	/**
	 * 是否可报名 1(不可报名) 0可报名
	 */
	private Integer isDisable;
	/**
	 * 是否面向所有社区 1（是）；0（否）
	 */
	private Integer isAllYards;
	/**
	 * 社区IDs
	 */
	private String courtyardIds;
	/**
	 * 院子名称s
	 */
	private String courtyardNames;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 描述
	 */
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getCountLimit() {
		return countLimit;
	}
	public void setCountLimit(Integer countLimit) {
		this.countLimit = countLimit;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public Integer getIsAllYards() {
		return isAllYards;
	}
	public void setIsAllYards(Integer isAllYards) {
		this.isAllYards = isAllYards;
	}
	public String getCourtyardIds() {
		return courtyardIds;
	}
	public void setCourtyardIds(String courtyardIds) {
		this.courtyardIds = courtyardIds;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCourtyardNames() {
		return courtyardNames;
	}
	public void setCourtyardNames(String courtyardNames) {
		this.courtyardNames = courtyardNames;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
