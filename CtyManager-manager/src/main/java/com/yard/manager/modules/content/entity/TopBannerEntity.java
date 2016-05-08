package com.yard.manager.modules.content.entity;

/**
 * 推荐Banner
 * @author : xiaym
 * @date : 2015年9月10日 下午2:36:58
 * @version : 1.0
 */
public class TopBannerEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 对应文章ID
	 */
	private Integer contentId;
	/**
	 * 置顶类型 (1专题；2资讯；3课程)
	 */
	private Integer dtype;
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
	public Integer getDtype() {
		return dtype;
	}
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
