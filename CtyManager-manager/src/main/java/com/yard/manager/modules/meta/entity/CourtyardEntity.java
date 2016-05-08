package com.yard.manager.modules.meta.entity;

/**
 * 院子管理
 * 
 * @author : xiaym
 * @date : 2015年7月9日 上午11:45:04
 * @version : 1.0
 */
public class CourtyardEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 院子名称
	 */
	private String courtyardName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCourtyardName() {
		return courtyardName;
	}

	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	
}
