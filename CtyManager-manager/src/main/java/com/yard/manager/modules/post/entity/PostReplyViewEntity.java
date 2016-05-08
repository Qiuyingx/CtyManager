package com.yard.manager.modules.post.entity;

public class PostReplyViewEntity extends PostReplyEntity{
	/**
	 * 帖子标题
	 */
	private String postName;
	/**
	 * 评论者昵称
	 */
	private String replyerName;
	/**
	 * 回复评论者昵称
	 */
	private String replyName;
	
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getReplyerName() {
		return replyerName;
	}
	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	
}
