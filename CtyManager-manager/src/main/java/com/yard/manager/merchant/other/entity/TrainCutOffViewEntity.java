package com.yard.manager.merchant.other.entity;

/**
 * 商户结算视图 
 * @author : leihc
 * @date : 2015年10月20日
 * @version : 1.0
 */
public class TrainCutOffViewEntity {

	/**
	 * 学堂ID
	 */
	private Integer trainId;
	/**
	 * 学堂名称
	 */
	private String trainName;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 结算类型 1 按周 2 按月
	 */
	private Integer period;
	/**
	 * 当前结算周期的截止时间
	 */
	private String endTime;
	/**
	 * 截止当前结算周期销售额，觅趣价格
	 */
	private Double totalSales;
	/**
	 * 到当前销售额，觅趣价格
	 */
	private Double currentTotalSales;
	/**
	 * 截止当前结算周期的销售额，觅趣与商家谈定的价格
	 * 订单状态为已支付、已消费、已申请退款
	 */
	private Double shopTotalSales;
	/**
	 * 到当前销售额，觅趣与商家谈定的价格
	 */
	private Double currentShopTotalSales;
	/**
	 * 截止当前结算周期总收益
	 * 订单状态为已消费
	 */
	private Double shopTotalIncome;
	/**
	 * 到当前总收益，订单状态为已消费
	 */
	private Double currentShopTotalIncome;
	/**
	 * 历次结算额的总金额
	 */
	private Double shopCutOffSales;
	/**
	 * 当前结算周期结算金额
	 * 为0时表示当前结算周期还未结算
	 */
	private Double currentCuttoff;
	/**
	 * 可结算收益=shopTotalIncome-shopCutOffSales
	 */
	private Double remain;
	
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}
	public Double getShopTotalSales() {
		return shopTotalSales;
	}
	public void setShopTotalSales(Double shopTotalSales) {
		this.shopTotalSales = shopTotalSales;
	}
	public Double getShopCutOffSales() {
		return shopCutOffSales;
	}
	public void setShopCutOffSales(Double shopCutOffSales) {
		this.shopCutOffSales = shopCutOffSales;
	}
	public Double getShopTotalIncome() {
		return shopTotalIncome;
	}
	public void setShopTotalIncome(Double shopTotalIncome) {
		this.shopTotalIncome = shopTotalIncome;
	}
	public Double getCurrentCuttoff() {
		return currentCuttoff;
	}
	public void setCurrentCuttoff(Double currentCuttoff) {
		this.currentCuttoff = currentCuttoff;
	}
	public Double getRemain() {
		return remain;
	}
	public void setRemain(Double remain) {
		this.remain = remain;
	}
	public Double getCurrentTotalSales() {
		return currentTotalSales;
	}
	public void setCurrentTotalSales(Double currentTotalSales) {
		this.currentTotalSales = currentTotalSales;
	}
	public Double getCurrentShopTotalSales() {
		return currentShopTotalSales;
	}
	public void setCurrentShopTotalSales(Double currentShopTotalSales) {
		this.currentShopTotalSales = currentShopTotalSales;
	}
	public Double getCurrentShopTotalIncome() {
		return currentShopTotalIncome;
	}
	public void setCurrentShopTotalIncome(Double currentShopTotalIncome) {
		this.currentShopTotalIncome = currentShopTotalIncome;
	}
	
}
