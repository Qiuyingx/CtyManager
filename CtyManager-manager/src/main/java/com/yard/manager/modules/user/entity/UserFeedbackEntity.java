package com.yard.manager.modules.user.entity;

/**
 * 用户反馈
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午4:18:21
 * @version : 1.0
 */
public class UserFeedbackEntity {
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 电话
	 */
	private String userTel;
	/**
	 * 反馈时间
	 */
	private long createTime;
	/**
	 * 图片s
	 */
	private String imageNames;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
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
