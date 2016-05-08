package com.yard.manager.merchant.train.entity;

/**
 * 商家申请开店
 * @author : xiaym
 * @date : 2015年8月13日 下午3:59:20
 * @version : 1.0
 */
public class TrainOpenEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 院子ID
	 */
	private Integer courtyardId;
	/**
	 * 类别
	 */
	private String category;
	/**
	 * 介绍
	 */
	private String introduction;
	/**
	 * 手机号
	 */
	private String tel;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 审核状态  0已申请 1未通过 2已通过
	 */
	private Integer passed;
	/**
	 * 申请时间
	 */
	private long createTime;
	/**
	 * 审核时间
	 */
	private long valiTime;
	/**
	 * 审核备注
	 */
	private String remark;
	/**
	 * 图片
	 */
	private String imageNames;
	/**
	 * 申请渠道（1APP申请 2网站申请 3后台入驻） 
	 */
	private Integer source;
	/**
	 * 申请人 
	 */
	private String name;
	
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
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPassed() {
		return passed;
	}
	public void setPassed(Integer passed) {
		this.passed = passed;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getValiTime() {
		return valiTime;
	}
	public void setValiTime(long valiTime) {
		this.valiTime = valiTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImageNames() {
		return imageNames;
	}
	public void setImageNames(String imageNames) {
		this.imageNames = imageNames;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
