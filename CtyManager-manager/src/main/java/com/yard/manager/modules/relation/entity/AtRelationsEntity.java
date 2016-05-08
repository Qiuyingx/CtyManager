package com.yard.manager.modules.relation.entity;

/**
 * 专题，活动资讯at关联表
 * @author : xiaym
 * @date : 2015年8月9日 上午11:07:36
 * @version : 1.0
 */
public class AtRelationsEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 发艾特的用户
	 */
	private Integer userId;
	/**
	 * 艾特对象的ID
	 */
	private Integer atTargetId;
	/**
	 * 艾特对象的昵称
	 */
	private String atNickName;
	/**
	 * 发艾特的场景，{@link ContentType}
	 */
	private Integer scene;
	/**
	 * 附加信息，可能是话题，邀约等的ID
	 */
	private Integer append;
	/**
	 * 发艾特的时间
	 */
	private long createTime;
	
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
	public Integer getAtTargetId() {
		return atTargetId;
	}
	public void setAtTargetId(Integer atTargetId) {
		this.atTargetId = atTargetId;
	}
	public String getAtNickName() {
		return atNickName;
	}
	public void setAtNickName(String atNickName) {
		this.atNickName = atNickName;
	}
	public Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene) {
		this.scene = scene;
	}
	public Integer getAppend() {
		return append;
	}
	public void setAppend(Integer append) {
		this.append = append;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
