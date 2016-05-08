package com.yard.manager.merchant.other.entity.query;

/**
 * 
 * @author : leihc
 * @date : 2015年10月16日
 * @version : 1.0
 */
public class UserOrderQueryEntity {

	/**
	 * 培训室ID
	 */
	private Integer trainId;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 截止时间
	 */
	private String endTime;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 手机号
	 */
	private String tel;
	/**
	 * 姓名
	 */
	private String userName;
	
	
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
