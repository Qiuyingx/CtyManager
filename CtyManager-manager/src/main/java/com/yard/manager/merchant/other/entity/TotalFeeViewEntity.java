package com.yard.manager.merchant.other.entity;

/**
 * 查询订单总金额结果
 * @author : leihc
 * @date : 2015年10月16日
 * @version : 1.0
 */
public class TotalFeeViewEntity {

	/**
	 * 总金额
	 */
	private double totalFee;
	/**
	 * 与商户商定的总金额
	 */
	private double shopTotalFee;
	
	
	public double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}
	public double getShopTotalFee() {
		return shopTotalFee;
	}
	public void setShopTotalFee(double shopTotalFee) {
		this.shopTotalFee = shopTotalFee;
	}
	
	
}
