package com.yard.manager.modules.user.entity;

public class UserOrderViewEntity extends UserOrderEntity {
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 所属院子名称
	 */
	private String courtyardName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	
}
