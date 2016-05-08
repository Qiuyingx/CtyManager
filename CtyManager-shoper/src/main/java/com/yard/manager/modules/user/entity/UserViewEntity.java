package com.yard.manager.modules.user.entity;

public class UserViewEntity extends UserEntity {
	/**
	 * 所属院子名称
	 */
	private String courtyardName;
	/**
	 * 用户邻豆
	 */
	private Integer amount;

	public String getCourtyardName() {
		return courtyardName;
	}

	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
