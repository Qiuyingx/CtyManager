package com.yard.manager.modules.vali.entity.query;

/**
 * 社区验证用户筛选 
 * @author : xiaym
 * @date : 2015年6月29日 下午3:45:07
 * @version : 1.0
 */
public class ValiUserQueryEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户电话
	 */
	private String userMobile;
	/**
	 * 用户所属院子ID
	 */
	private Integer courtyardId;
	/**
	 * 用户所属院子名称
	 */
	private String courtyardName;
	/**
	 * 所属社区
	 */
	private String courtyardIds;
	/**
	 * 验证方式
	 */
	private String valiType;
	/**
	 * 验证状态 0 提交审核 1 审核通过 2审核不通过
	 */
	private String valiStatus;
	/**
	 * 提交开始时间
	 */
	private String startTime;
	/**
	 * 提交结束时间
	 */
	private String endTime;
	/**
	 * 审核备注
	 */
	private String remark;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getCourtyardIds() {
		return courtyardIds;
	}
	public void setCourtyardIds(String courtyardIds) {
		this.courtyardIds = courtyardIds;
	}
	public String getValiType() {
		return valiType;
	}
	public void setValiType(String valiType) {
		this.valiType = valiType;
	}
	public String getValiStatus() {
		return valiStatus;
	}
	public void setValiStatus(String valiStatus) {
		this.valiStatus = valiStatus;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public Integer getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(Integer inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	
}
