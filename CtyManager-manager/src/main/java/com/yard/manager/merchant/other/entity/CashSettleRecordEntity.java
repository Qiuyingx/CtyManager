package com.yard.manager.merchant.other.entity;

/**
 * 商家结算记录
 * @author : leihc
 * @date : 2015年10月14日
 * @version : 1.0
 */
public class CashSettleRecordEntity {

	/**
	 * 
	 */
	private long id;
	/**
	 * 商户ID
	 */
	private long trainId;
	/**
	 * 提现总额
	 */
	private double withdrawcash;
	/**
	 * 操作人ID
	 */
	private int managerId;
	/**
	 * 结算截止时间
	 */
	private long endTime;
	/**
	 * 操作时间
	 */
	private long createTime;
	
	public long getTrainId() {
		return trainId;
	}
	public void setTrainId(long trainId) {
		this.trainId = trainId;
	}
	public double getWithdrawcash() {
		return withdrawcash;
	}
	public void setWithdrawcash(double withdrawcash) {
		this.withdrawcash = withdrawcash;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
}
