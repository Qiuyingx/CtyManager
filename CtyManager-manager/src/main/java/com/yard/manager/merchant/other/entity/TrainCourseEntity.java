package com.yard.manager.merchant.other.entity;

/**
 * 培训室课程管理
 * @author : xiaym
 * @date : 2015年9月7日 下午4:45:20
 * @version : 1.0
 */
public class TrainCourseEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 培训室ID
	 */
	private Integer trainId;
	/**
	 * 课程一级分类ID
	 */
	private Integer typeParent;
	/**
	 * 课程分类ID
	 */
	private Integer typeId;
	/**
	 * 课程分类名称
	 */
	private String typeName;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 课程名称
	 */
	private String title;
	/**
	 * 课程描述（用作分享描述）
	 */
	private String description;
	/**
	 * 课程封面图
	 */
	private String titleImg;
	/**
	 * 课程Banner图
	 */
	private String bannerImg;
	/**
	 * 课时
	 */
	private String hours;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 图文介绍
	 */
	private String content;
	/**
	 * 地址信息
	 */
	private String address;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 价格
	 */
	private double price;
	/**
	 * 价格单位
	 */
	private String priceunit;
	/**
	 * 推荐数
	 */
	private Integer topCount;
	/**
	 * 浏览数
	 */
	private Integer views;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 上课时间
	 */
	private String studyTime;
	/**
	 * 课程状态（0 正常； 1下架）
	 */
	private Integer status;
	/**
	 * 活动开始时间
	 */
	private long startTime;
	private String startTimeStr;
	/**
	 * 活动结束时间
	 */
	private long endTime;
	private String endTimeStr;
	/**
	 * 课程种类（1普通课程 2活动类课程）
	 */
	private Integer classType;
	/**
	 * 名额限制
	 */
	private Integer copies;
	/**
	 * 有赞商城链接
	 */
	private String courseUrl;
	/**
	 * 0 未消费随时退 1 不可退 2 提前几天可退
	 */
	private int refundType;
	/**
	 * 退款比例
	 */
	private double refundRatio;
	/**
	 * 当refundType=2时，设置提前几天可退
	 */
	private int refundDays;
	/**
	 * 规格设置，规格之间用‘|’分隔
	 */
	private String items;
	/**
	 * 觅趣价格，与规格对应，之间用'|'分隔
	 */
	private String prices;
	/**
	 * 学堂价格，与规格对应，之间用‘|’分隔
	 */
	private String shopPrices;
	/**
	 * 觅趣原价，与规格对应，之间‘|’分隔
	 */
	private String origi_prices;
	
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
	public Integer getTypeParent() {
		return typeParent;
	}
	public void setTypeParent(Integer typeParent) {
		this.typeParent = typeParent;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getBannerImg() {
		return bannerImg;
	}
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getTopCount() {
		return topCount;
	}
	public void setTopCount(Integer topCount) {
		this.topCount = topCount;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getStudyTime() {
		return studyTime;
	}
	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public Integer getClassType() {
		return classType;
	}
	public void setClassType(Integer classType) {
		this.classType = classType;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getPriceunit() {
		return priceunit;
	}
	public void setPriceunit(String priceunit) {
		this.priceunit = priceunit;
	}
	public String getCourseUrl() {
		return courseUrl;
	}
	public void setCourseUrl(String courseUrl) {
		this.courseUrl = courseUrl;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public double getRefundRatio() {
		return refundRatio;
	}
	public void setRefundRatio(double refundRatio) {
		this.refundRatio = refundRatio;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
	public String getShopPrices() {
		return shopPrices;
	}
	public void setShopPrices(String shopPrices) {
		this.shopPrices = shopPrices;
	}
	public String getOrigi_prices() {
		return origi_prices;
	}
	public void setOrigi_prices(String origi_prices) {
		this.origi_prices = origi_prices;
	}
	public int getRefundDays() {
		return refundDays;
	}
	public void setRefundDays(int refundDays) {
		this.refundDays = refundDays;
	}
	
}
