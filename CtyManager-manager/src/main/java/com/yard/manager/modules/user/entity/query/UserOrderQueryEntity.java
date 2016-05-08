package com.yard.manager.modules.user.entity.query;

/**
 * 订单筛选
 * @author : xiaym
 * @date : 2015年6月28日 下午12:33:15
 * @version : 1.0
 */
public class UserOrderQueryEntity {
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 电话
	 */
	private String userMobile;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 所属院子s
	 */
	private String courtyardIds;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 购买时间（开始时间）
	 */
	private String buyStartTime;
	/**
	 * 购买结束时间
	 */
	private String buyEndTime;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCourtyardIds() {
		return courtyardIds;
	}
	public void setCourtyardIds(String courtyardIds) {
		this.courtyardIds = courtyardIds;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getBuyStartTime() {
		return buyStartTime;
	}
	public void setBuyStartTime(String buyStartTime) {
		this.buyStartTime = buyStartTime;
	}
	public String getBuyEndTime() {
		return buyEndTime;
	}
	public void setBuyEndTime(String buyEndTime) {
		this.buyEndTime = buyEndTime;
	}
	
}
