package com.yard.manager.merchant.train.entity.query;

/**
 * 学堂查询条件筛选
 * @author : xiaym
 * @date : 2015年9月29日 下午3:53:31
 * @version : 1.0
 */
public class TrainInfoQueryEntity {
	/**
	 * 学堂标题
	 */
	private String title;
	/**
	 * 学堂状态
	 */
	private Integer status;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
