package com.yard.manager.base.entity;

import java.io.Serializable;

/**
 * 查询条件实体
 * @author jiangbo
 *
 */
public class ConditionEntity implements Serializable {
	private static final long serialVersionUID = 8973324134596598321L;

	private String startDate;
	private String endDate;
	private String dataName;
	
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
