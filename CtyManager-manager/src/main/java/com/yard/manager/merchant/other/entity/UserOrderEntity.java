package com.yard.manager.merchant.other.entity;


/**
 * 用户订单
 * 
 * @author : leihc
 * @date : 2015年9月23日
 * @version : 1.0
 */
public class UserOrderEntity {

	private long id;
	/**
	 * 下单用户ID
	 */
	private long userId;
	/**
	 * 订单no,由时间“yyyyMMddHHmmss”+四位随机数组成
	 */
	private String orderNo;
	/**
	 * 支付类型 1 支付宝支付  2 微信支付
	 */
	private int payType;
	/**
	 * 店家用户ID
	 */
	private long ownerId;
	/**
	 * 店家ID
	 */
	private long trainId;
	/**
	 * 购买的商品ID
	 */
	private long goodsId;
	/**
	 * 购买的课程地址
	 */
	private String address;
	/**
	 * 商品名
	 */
	private String goodsName;
	/**
	 * 商品描述，支付时传给支付平台的
	 */
	private String goodsDescription;
	/**
	 * 商品详细，觅趣平台上展示的内容
	 */
	private String goodsDetail;
	/**
	 * 课程封面图
	 */
	private String title_img;
	/**
	 * 规格
	 */
	private String itemName;
	/**
	 * 商品单价，单位元，保留两位小数
	 * 用户支付的单价
	 */
	private double goodsPrice;
	/**
	 * 课程价格，平台与商户商定的价格
	 */
	private double shopPrice;
	/**
	 * 购买商品数量
	 */
	private int goodsAmount;
	/**
	 * 应付总金额goodsPrice*goodsAmount
	 * 单位元，保留小数两位
	 */
	private double totalFeeBefore;
	/**
	 * 总金额，不是通过goodsPrice*goodsAmount算出
	 * 是从支付平台异步通知结果获取，初始为0，实际付款额
	 * 扣除手续费后的金额？不确定
	 */
	private double totalFee;
	/**
	 * 按平台和商户商定的价格，该订单的总金额
	 * shopPrice*goodsAmount
	 */
	private double shopTotalFee;
	/**
	 * 折扣
	 */
	private double discount;
	/**
	 * 订单状态
	 * -2 系统取消订单
	 * -1 用户取消订单 
	 * 0 新订单 
	 * 1 已支付 ，可退款
	 * 2 已消费，不可退款 
	 * 3 申请退款 
	 * 4 退款中
	 * 5 已退款 
	 */
	private int orderStatus;
	/**
	 * 支付平台交易号
	 */
	private String payTradeNo;
	/**
	 * 买主姓名
	 */
	private String buyername;
	/**
	 * 买主电话
	 */
	private String tel;
	/**
	 * 支付平台买家用户号
	 */
	private String buyerId;
	/**
	 * 支付平台买家账号
	 * 支付宝账号，微信用户的openid
	 */
	private String buyerEmail;
	/**
	 * 支付平台用户号
	 * 支付宝用户号，微信支付公众号ID
	 */
	private String sellerId;
	/**
	 * 支付平台账号，觅趣官方账号
	 * 支付宝账号，微信支付商户号
	 */
	private String sellerEmail;
	/**
	 * 用户付款时间
	 */
	private long payTime;
	/**
	 * 订单生成时间
	 */
	private long createTime;
	/**
	 * 下单时用户备注
	 */
	private String remark;
	/**
	 * 申请退款原因
	 */
	private String refundReason;
	/**
	 * 生成订单时生成的短信验证码，发送给用户
	 */
	private String validateCode;
	/**
	 * 可退款数
	 */
	private double refundFee;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getTrainId() {
		return trainId;
	}

	public void setTrainId(long trainId) {
		this.trainId = trainId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(int goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public long getPayTime() {
		return payTime;
	}

	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}

	public String getBuyername() {
		return buyername;
	}

	public void setBuyername(String buyername) {
		this.buyername = buyername;
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

	public String getGoodsDescription() {
		return goodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getTitle_img() {
		return title_img;
	}

	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotalFeeBefore() {
		return totalFeeBefore;
	}

	public void setTotalFeeBefore(double totalFeeBefore) {
		this.totalFeeBefore = totalFeeBefore;
	}

	public double getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(double refundFee) {
		this.refundFee = refundFee;
	}

	public double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}

	public double getShopTotalFee() {
		return shopTotalFee;
	}

	public void setShopTotalFee(double shopTotalFee) {
		this.shopTotalFee = shopTotalFee;
	}
	
	
}
