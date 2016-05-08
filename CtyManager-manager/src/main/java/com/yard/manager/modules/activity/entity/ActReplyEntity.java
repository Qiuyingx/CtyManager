package com.yard.manager.modules.activity.entity;

/**
 * 活动评论表
 * 
 * @author : xiaym
 * @date : 2015年6月23日 下午12:15:36
 * @version : 1.0
 * @table t_activity_reply
 */
public class ActReplyEntity {
	/**
	 * 活动评论ID
	 */
	private Integer id;
	/**
	 * 活动ID
	 */
	private Integer actId;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 评论用户ID
	 */
	private Integer replyerId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 被评论者ID
	 */
	private Integer targetId;
	/**
	 * 回复的评论ID
	 */
	private Integer replyId;
	/**
	 * 评论时间
	 */
	private long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
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
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
