package com.yard.manager.modules.post.entity.query;

import com.yard.manager.base.entity.ConditionEntity;

/**
 *  帖子查询条件
 */
public class PostQueryEntity extends ConditionEntity{
	private static final long serialVersionUID = -4389991274084140917L;
	/**
	 * 帖子ID
	 */
	private Integer postId;
	/**
	 * 帖子标题
	 */
	private String title;
	/**
	 * 发帖人昵称
	 */
	private String nickname;
	/**
	 * 所属院子IDs(可多个院子)
	 */
	private String yardids;
	/**
	 * 手机号
	 */
	private String tel;
	/**
	 * 是否紧急
	 */
	private String isHarry;
	/**
	 * 是否已采纳评论
	 */
	private String isAccept;
	/**
	 * 查询内容 1 帮帮和话题 2邀约
	 */
	private Integer contentType;
	/**
	 * 举报标签ID 
	 */
	private String reportType;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 发帖人IDs
	 */
	private String userIds;
	/**
	 *  专题活动标题
	 */
	private String specialTitle;
	/**
	 * 是否查询置顶帖子 
	 */
	private String isTop;
	
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getYardids() {
		return yardids;
	}
	public void setYardids(String yardids) {
		this.yardids = yardids;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIsHarry() {
		return isHarry;
	}
	public void setIsHarry(String isHarry) {
		this.isHarry = isHarry;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSpecialTitle() {
		return specialTitle;
	}
	public void setSpecialTitle(String specialTitle) {
		this.specialTitle = specialTitle;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
}
