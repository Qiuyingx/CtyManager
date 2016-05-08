package com.yard.manager.modules.post.entity;

/**
 * 帖子点赞扩展字段
 * @author : xiaym
 * @date : 2015年6月18日 上午10:54:16
 * @version : 1.0
 */
public class PostLaudViewEntity {
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 帖子标题
	 */
	private String postName;
	/**
	 * 点赞人名称
	 */
	private String userName;
	
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
