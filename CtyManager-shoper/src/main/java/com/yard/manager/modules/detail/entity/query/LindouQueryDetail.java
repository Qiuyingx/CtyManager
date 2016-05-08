package com.yard.manager.modules.detail.entity.query;

/**
 * 邻豆筛选
 * @author : xiaym
 * @date : 2015年6月30日 下午2:29:24
 * @version : 1.0
 */
public class LindouQueryDetail {
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
