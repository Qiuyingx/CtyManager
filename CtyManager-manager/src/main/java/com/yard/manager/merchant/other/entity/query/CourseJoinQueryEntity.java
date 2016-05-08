package com.yard.manager.merchant.other.entity.query;

public class CourseJoinQueryEntity {
	/**
	 * 姓名搜索
	 */
	private String name;
	/**
	 * 电话搜索
	 */
	private String tel;
	/**
	 * 报名时间（起）
	 */
	private String startTime;
	/**
	 * 报名时间（止）
	 */
	private String endTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
