package com.yard.manager.merchant.train.entity;

/**
 * 课程报名
 * @author : xiaym
 * @date : 2015年9月9日 下午3:08:50
 * @version : 1.0
 */
public class CourseJoinEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 课程ID
	 */
	private Integer courseId;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 价格
	 */
	private double price;
	/**
	 * 价格单位
	 */
	private String priceunit;
	/**
	 * 课时
	 */
	private String hours;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPriceunit() {
		return priceunit;
	}
	public void setPriceunit(String priceunit) {
		this.priceunit = priceunit;
	}
	
}
