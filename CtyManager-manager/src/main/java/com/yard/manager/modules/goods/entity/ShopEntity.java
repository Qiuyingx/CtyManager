package com.yard.manager.modules.goods.entity;

/**
 * 邻豆商城
 * @author : xiaym
 * @date : 2015年6月23日 下午3:43:59
 * @version : 1.0
 * @table t_shop
 */
public class ShopEntity {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 商品民称
	 */
	private String goodsName;
	/**
	 * 价格
	 */
	private Integer price;
	/**
	 * 库存
	 */
	private Integer stockLimit;
	/**
	 * 封面图
	 */
	private String image;
	/**
	 * 商品描述
	 */
	private String remark;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 多图
	 */
	private String listImage;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStockLimit() {
		return stockLimit;
	}
	public void setStockLimit(Integer stockLimit) {
		this.stockLimit = stockLimit;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getListImage() {
		return listImage;
	}
	public void setListImage(String listImage) {
		this.listImage = listImage;
	}
	
}
