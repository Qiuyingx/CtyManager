package com.yard.manager.modules.post.entity;

public class PostTopEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 帖子ID
	 */
	private Integer postId;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 置顶类型 0 本社区置顶 1 周边置顶
	 */
	private Integer topType;
	/**
	 * 置顶优先级 数字越大，排前面
	 */
	private Integer priority;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public Integer getTopType() {
		return topType;
	}
	public void setTopType(Integer topType) {
		this.topType = topType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
