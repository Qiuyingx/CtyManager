package com.yard.manager.modules.goods.entity.query;

/**
 * 邻豆商城筛选
 * @author : xiaym
 * @date : 2015年6月23日 下午5:23:33
 * @version : 1.0
 */
public class ShopQueryEntity {
	/**
	 * 商品ID
	 */
	private Integer id;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品状态
	 */
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
