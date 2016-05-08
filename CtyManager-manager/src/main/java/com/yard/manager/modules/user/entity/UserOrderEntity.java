package com.yard.manager.modules.user.entity;

/**
 * 用户订单
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午5:14:43
 * @version : 1.0
 */
public class UserOrderEntity {
	/**
	 * 点单记录ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 填写姓名
	 */
	private String userName;
	/**
	 * 填写电话
	 */
	private String userMobile;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 商品ID
	 */
	private Integer goodsId;
	/**
	 * 购买数量
	 */
	private Integer count;
	/**
	 * 创建时间
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
