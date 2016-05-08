package com.yard.manager.modules.user.entity;

/**
 * 是否接收群发信息开关判断
 * 
 * @author : xiaym
 * @date : 2015年7月17日 下午5:53:36
 * @version : 1.0
 */
public class UserSetting {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 评论提醒
	 */
	private Integer reply;
	/**
	 * 用户点赞提醒
	 */
	private Integer laud;
	/**
	 * 用户私信提醒
	 */
	private Integer message;
	/**
	 * 用户系统提醒
	 */
	private Integer system;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReply() {
		return reply;
	}
	public void setReply(Integer reply) {
		this.reply = reply;
	}
	public Integer getLaud() {
		return laud;
	}
	public void setLaud(Integer laud) {
		this.laud = laud;
	}
	public Integer getMessage() {
		return message;
	}
	public void setMessage(Integer message) {
		this.message = message;
	}
	public Integer getSystem() {
		return system;
	}
	public void setSystem(Integer system) {
		this.system = system;
	}
	
	
}
