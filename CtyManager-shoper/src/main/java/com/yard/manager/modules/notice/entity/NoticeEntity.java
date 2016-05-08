package com.yard.manager.modules.notice.entity;

/**
 * 通知
 * @author : xiaym
 * @date : 2015年7月9日 上午10:11:38
 * @version : 1.0
 */
public class NoticeEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 推送内容
	 */
	protected String content;
	/**
	 * 院子ID  0表示所有院子
	 */
	protected Integer courtyardId;
	/**
	 * 用户ID 0 表示所有用
	 */
	protected Integer userId;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 通知类型 0 系统通知 1 社区公告  2 答案被采纳 3 求助通知 4 官方活动通知 5 官方报名成功 6 邀约报名成功
	 */
	protected Integer noticeType;
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
	 * 推送内容
	 */
	protected String pushContent;
	/**
	 * 文章ID
	 */
	private Integer contentId;
	/**
	 * 通知名称
	 */
	private String title;
	/**
	 * 指向内容ID
	 */
	private Integer append;
	/**
	 * 推送类型
	 */
	private Integer pushType;
	
	public NoticeEntity() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getCityIds() {
		return cityIds;
	}
	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
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

	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getAppend() {
		return append;
	}

	public void setAppend(Integer append) {
		this.append = append;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

}
