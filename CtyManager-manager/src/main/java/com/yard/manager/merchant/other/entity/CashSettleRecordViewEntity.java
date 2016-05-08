package com.yard.manager.merchant.other.entity;

/**
 * 
 * @author : leihc
 * @date : 2015年10月22日
 * @version : 1.0
 */
public class CashSettleRecordViewEntity {

	/**
	 * 学堂名称
	 */
	private String trainName;
	/**
	 * 结算金额
	 */
	private double withdrawcash;
	/**
	 * 操作者名称
	 */
	private String managerName;
	/**
	 * 操作时间
	 */
	private long createTime;
	
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public double getWithdrawcash() {
		return withdrawcash;
	}
	public void setWithdrawcash(double withdrawcash) {
		this.withdrawcash = withdrawcash;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
