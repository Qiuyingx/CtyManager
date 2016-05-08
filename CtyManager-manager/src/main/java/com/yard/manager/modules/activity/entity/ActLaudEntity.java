package com.yard.manager.modules.activity.entity;

/**
 * 互动点赞表
 * @author : xiaym
 * @date : 2015年6月23日 下午12:11:52
 * @version : 1.0
 * @table t_activity_laud
 */
public class ActLaudEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 活动ID
	 */
	private Integer actId;
	/**
	 * 点赞用户ID
	 */
	private Integer userId;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 点赞时间
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
}
