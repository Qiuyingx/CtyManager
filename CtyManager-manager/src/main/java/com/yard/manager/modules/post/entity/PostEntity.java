package com.yard.manager.modules.post.entity;

/**
 * 话题帮帮实体类
 * 
 * @author : xiaym
 * @date : 2015年6月18日 上午1:18:51
 * @version : 1.0
 */
public class PostEntity {
	/**
	 * 话题，帮帮ID
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	protected Integer courtyardId;
	/**
	 * 发帖人ID
	 */
	protected Integer userId;
	/**
	 * 内容类型 2帮帮 3话题
	 */
	protected Integer contentType;
	/**
	 * 图片地址
	 */
	private String imageNames;
	/**
	 * 邻居帮帮是否是紧急求助
	 */
	protected Integer priority;
	/**
	 * 帖子标题
	 */
	protected String title;
	/**
	 * 帖子内容
	 */
	protected String content;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 邻居帮帮的标签
	 */
	private Integer tag;
	/**
	 * 帮帮采纳的回复ID
	 */
	private Integer acceptId;
	/**
	 * 帮帮悬赏额
	 */
	private Integer reward;
	/**
	 * 是否是介绍自己
	 */
	protected Integer myself;
	/**
	 * 审核状态（审核通过将发送推送消息）0提交审核 1审核成功 2审核失败
	 */
	private Integer valiStatus;
	/**
	 * 城市ID
	 */
	protected String cityIds;
	/**
	 * 是否发送至所有城市下所有的院子 1是2否
	 */
	protected Integer isAllCity;
	/**
	 * 院子IDs
	 */
	protected String yardIds;
	/**
	 * 是否发送至所有院子  1 是 2否
	 */
	protected Integer isAllYards;
	/**
	 * 帖子标签
	 */
	private Integer topicTag;
	
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getImageNames() {
		return imageNames;
	}
	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getAcceptId() {
		return acceptId;
	}
	public void setAcceptId(Integer acceptId) {
		this.acceptId = acceptId;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
	public Integer getMyself() {
		return myself;
	}
	public void setMyself(Integer myself) {
		this.myself = myself;
	}
	public Integer getValiStatus() {
		return valiStatus;
	}
	public void setValiStatus(Integer valiStatus) {
		this.valiStatus = valiStatus;
	}
	public Integer getIsAllCity() {
		return isAllCity;
	}
	public void setIsAllCity(Integer isAllCity) {
		this.isAllCity = isAllCity;
	}
	public String getYardIds() {
		return yardIds;
	}
	public void setYardIds(String yardIds) {
		this.yardIds = yardIds;
	}
	public Integer getIsAllYards() {
		return isAllYards;
	}
	public void setIsAllYards(Integer isAllYards) {
		this.isAllYards = isAllYards;
	}
	public String getCityIds() {
		return cityIds;
	}
	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}
	public Integer getTopicTag() {
		return topicTag;
	}
	public void setTopicTag(Integer topicTag) {
		this.topicTag = topicTag;
	}
	
}
