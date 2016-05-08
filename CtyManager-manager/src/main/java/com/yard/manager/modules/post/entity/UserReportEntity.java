package com.yard.manager.modules.post.entity;

/**
 * 帖子举报删除
 * @author : xiaym
 * @date : 2015年6月19日 下午4:18:35
 * @version : 1.0
 */
public class UserReportEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 举报者ID
	 */
	private Integer userId;
	/**
	 * 举报内容类型  1邀约 2帮帮 3 话题
	 */
	private Integer contentType;
	/**
	 * 举报帖子ID
	 */
	private Integer targetId;
	/**
	 * 所属院子ID
	 */
	private Integer courtyardId;
	/**
	 * 举报时间
	 */
	private long createTime;
	/**
	 * 状态 0 举报 1 处理并删除
	 */
	private Integer status;
	/**
	 * 举报内容
	 */
	private String content;
	/**
	 * 举报类型
	 */
	private Integer reportType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	
	
}
