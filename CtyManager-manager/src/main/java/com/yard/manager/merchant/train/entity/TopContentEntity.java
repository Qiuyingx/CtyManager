package com.yard.manager.merchant.train.entity;

/**
 * 专题相关推荐
 * @author : xiaym
 * @date : 2015年9月10日 下午2:36:58
 * @version : 1.0
 */
public class TopContentEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 专题ID
	 */
	private Integer contentId;
	/**
	 * 学堂ID
	 */
	private Integer trainId;
	/**
	 * 推荐时间
	 */
	private long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
