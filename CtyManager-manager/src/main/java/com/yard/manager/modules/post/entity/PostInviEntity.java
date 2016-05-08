package com.yard.manager.modules.post.entity;

/**
 * 活动邀约
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午12:59:52
 * @version : 1.0
 */
public class PostInviEntity {
	/**
	 * 邀约ID
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 发邀约的用户ID
	 */
	private Integer userId;
	/**
	 * 邀约类型
	 */
	private Integer inviType;
	/**
	 * 活动地点
	 */
	private String place;
	/**
	 * 邀约时间
	 */
	private long time;
	/**
	 * 邀约内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 图片（多图）
	 */
	private String imageNames;
	
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
	public Integer getInviType() {
		return inviType;
	}
	public void setInviType(Integer inviType) {
		this.inviType = inviType;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
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
	public String getImageNames() {
		return imageNames;
	}
	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}
	
}
