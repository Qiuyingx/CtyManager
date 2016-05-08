package com.yard.manager.merchant.train.entity;

/**
 * 用户店铺资料完善
 * @author : xiaym
 * @date : 2015年8月14日 下午8:15:59
 * @version : 1.0
 */
public class TrainInfoEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	protected Integer userId;
	/**
	 * 后台管理账号ID
	 */
	protected Integer managerId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * banner图
	 */
	private String bannerImg;
	/**
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 营业时间
	 */
	private String businessHours;
	/**
	 * 电话号码
	 */
	private String tel;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 类别
	 */
	protected Integer category;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 资料完善时间
	 */
	private long createTime;
	/**
	 * 店铺状态0未完善资料 1开通  2下线（禁用）
	 */
	private Integer status;
	/**
	 * 银行代号
	 * {
        "id": 1,
        "text": "工商银行"
    },{
        "id": 2,
        "text": "建设银行"
    },{
        "id": 3,
        "text": "招商银行"
    },{
        "id": 4,
        "text": "农业银行"
    },{
        "id": 5,
        "text": "平安银行"
    }
	 */
	private Integer bankname;
	/**
	 * 银行卡号
	 */
	private String bankcardNo;
	/**
	 * 1 对私 2 对公
	 */
	private Integer accountType;
	/**
	 * 开户人姓名
	 */
	private String bankusername;
	/**
	 * 开户支行名称
	 */
	private String openbankname;
	/**
	 * 结算周期 1 按周 2 按月
	 */
	private Integer period;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBannerImg() {
		return bannerImg;
	}
	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBankname() {
		return bankname;
	}
	public void setBankname(Integer bankname) {
		this.bankname = bankname;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getBankusername() {
		return bankusername;
	}
	public void setBankusername(String bankusername) {
		this.bankusername = bankusername;
	}
	public String getOpenbankname() {
		return openbankname;
	}
	public void setOpenbankname(String openbankname) {
		this.openbankname = openbankname;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
}
