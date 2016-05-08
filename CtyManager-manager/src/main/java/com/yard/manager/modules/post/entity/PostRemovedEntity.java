package com.yard.manager.modules.post.entity;

/**
 * 帖子删除
 * @author : xiaym
 * @date : 2015年6月19日 下午4:59:53
 * @version : 1.0
 */
public class PostRemovedEntity {
	/**
	 * 帖子ID
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 发帖人ID
	 */
	private Integer userId;
	/**
	 * 内容类型
	 */
	private Integer contentType;
	/**
	 * 图片地址
	 */
	private String imageNames;
	/**
	 * 是否紧急
	 */
	private Integer priority;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 标签
	 */
	private Integer tag;
	/**
	 * 是否周围可见
	 */
	private Integer showAround;
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
	public Integer getShowAround() {
		return showAround;
	}
	public void setShowAround(Integer showAround) {
		this.showAround = showAround;
	}
	public Integer getTopicTag() {
		return topicTag;
	}
	public void setTopicTag(Integer topicTag) {
		this.topicTag = topicTag;
	}
	
}
