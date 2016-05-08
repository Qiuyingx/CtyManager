package com.yard.manager.modules.post.entity;

/**
 * 帖子点赞
 * 
 * @author : xiaym
 * @date : 2015年6月18日 上午10:50:51
 * @version : 1.0
 */
public class PostLaud {
	/**
	 * 点赞记录ID
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 帖子ID
	 */
	private Integer postId;
	/**
	 * 发帖人ID
	 */
	private Integer postSenderId;
	/**
	 * 点赞人ID
	 */
	private Integer userId;
	/**
	 * 点赞时间
	 */
	private long createTime;
	
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
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getPostSenderId() {
		return postSenderId;
	}
	public void setPostSenderId(Integer postSenderId) {
		this.postSenderId = postSenderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
