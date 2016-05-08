package com.yard.manager.modules.user.entity;

/**
 * 用户技能认证
 * @author : xiaym
 * @date : 2015年8月17日 下午7:10:35
 * @version : 1.0
 */
public class UserStarEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 电话号码
	 */
	private String tel;
	/**
	 * 认证说明
	 */
	private String content;
	/**
	 * 认证图片
	 */
	private String imageNames;
	/**
	 * 技能标签
	 */
	private String tagName;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 申请时间
	 */
	private long createTime;
	/**
	 * 认证备注
	 */
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageNames() {
		return imageNames;
	}
	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 
}
