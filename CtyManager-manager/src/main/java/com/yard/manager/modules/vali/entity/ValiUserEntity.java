package com.yard.manager.modules.vali.entity;

/**
 * 用户社区验证
 * @author : xiaym
 * @date : 2015年6月29日 下午3:36:44
 * @version : 1.0
 */
public class ValiUserEntity {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 用户Id
	 */
	private Integer userId;
	/**
	 * 验证时间
	 */
	private long createTime;
	/**
	 * 验证方式
	 */
	private Integer valiType;
	/**
	 * 图片验证方式
	 */
	private String append;
	/**
	 * 验证状态
	 */
	private Integer valiStatus;
	/**
	 * 用户填写的邀请码
	 */
	private Integer inviteCode;
	
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getValiType() {
		return valiType;
	}
	public void setValiType(Integer valiType) {
		this.valiType = valiType;
	}
	public String getAppend() {
		return append;
	}
	public void setAppend(String append) {
		this.append = append;
	}
	public Integer getValiStatus() {
		return valiStatus;
	}
	public void setValiStatus(Integer valiStatus) {
		this.valiStatus = valiStatus;
	}
	public Integer getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(Integer inviteCode) {
		this.inviteCode = inviteCode;
	}

}
