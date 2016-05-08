package com.yard.manager.merchant.train.entity;

/**
 * 培训室标签
 * @author : xiaym
 * @date : 2015年9月7日 下午3:44:16
 * @version : 1.0
 */
public class TrainTagEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 培训室ID
	 */
	private Integer trainId;
	/**
	 * 标签ID
	 */
	private Integer tagId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	
}
