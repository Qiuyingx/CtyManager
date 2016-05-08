package com.yard.manager.modules.content.entity;

/**
 * 推荐列表管理
 * @author : xiaym
 * @date : 2015年9月10日 下午2:39:21
 * @version : 1.0
 */
public class TopListEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 文章ID
	 */
	private Integer contentId;
	/**
	 * 推荐类型（1专题；3课程）
	 */
	private Integer dtype;
	/**
	 * 推荐时间
	 */
	private long creatTime;
	/**
	 * 文章发布时间
	 */
	private long releaseTime;
	
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
	public Integer getDtype() {
		return dtype;
	}
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
	public long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}
	public long getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}
	 
}
