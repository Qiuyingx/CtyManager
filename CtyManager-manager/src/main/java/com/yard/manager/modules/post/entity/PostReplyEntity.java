package com.yard.manager.modules.post.entity;

/**
 * 帖子评论
 * @author : xiaym
 * @date : 2015年6月18日 上午11:04:33
 * @version : 1.0
 */
public class PostReplyEntity {
	/**
	 * 帖子评论ID
	 */
	private Integer id;
	/**
	 * 帖子类型
	 */
	private Integer contentType;
	/**
	 * 评论用户ID
	 */
	private Integer replyerId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论时间
	 */
	private long createTime;
	/**
	 * 回复@对象的ID
	 */
	private Integer atTargetId;
	/**
	 * 发帖人ID
	 */
	private Integer postSenderId;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 回复的评论ID
	 */
	private Integer replyId;
	/**
	 * 回复帮帮被采纳了
	 */
	private Integer accepted;
	/**
	 * 帖子ID
	 */
	private Integer postId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public Integer getReplyerId() {
		return replyerId;
	}
	public void setReplyerId(Integer replyerId) {
		this.replyerId = replyerId;
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
	public Integer getAtTargetId() {
		return atTargetId;
	}
	public void setAtTargetId(Integer atTargetId) {
		this.atTargetId = atTargetId;
	}
	public Integer getPostSenderId() {
		return postSenderId;
	}
	public void setPostSenderId(Integer postSenderId) {
		this.postSenderId = postSenderId;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getAccepted() {
		return accepted;
	}
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	
}
