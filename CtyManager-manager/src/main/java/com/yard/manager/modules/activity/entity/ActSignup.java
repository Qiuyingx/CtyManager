package com.yard.manager.modules.activity.entity;

/**
 * 活动报名数据
 * @author : xiaym
 * @date : 2015年6月23日 下午1:56:30
 * @version : 1.0
 * @table t_activity_signup
 */
public class ActSignup {
	/**
	 * 报名ID
	 */
	private Integer id;
	/**
	 * 报名用户ID
	 */
	private Integer userId;
	/**
	 * 活动ID
	 */
	private Integer actId;
	/**
	 * 报名时间
	 */
	private long createTime;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 备注
	 */
	private String content;
	
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
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
