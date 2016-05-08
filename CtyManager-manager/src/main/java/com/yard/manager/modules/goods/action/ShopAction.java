package com.yard.manager.modules.goods.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.goods.entity.ShopViewEntity;
import com.yard.manager.modules.goods.entity.query.ShopQueryEntity;
import com.yard.manager.modules.goods.service.ShopService;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.platform.shiro.ShiroUtil;
/**
 * 邻豆商城
 * @author : xiaym
 * @date : 2015年6月23日 下午2:37:41
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "shopindex", location="/WEB-INF/content/modules/shop/shopindex.html"),
	@Result(type = "freemarker", name = "shop_edit", location="/WEB-INF/content/modules/shop/shop_edit.html")
})
public class ShopAction extends BaseAction {
	private static final long serialVersionUID = -2170789104855964439L;
	private static final String NAMESPACE = "/shop/info";
	private static final ShopService so = ShopService.getInstance();
	
	/**
	 * 进入商品编辑页面
	 */
	@Action(NAMESPACE + "/shop_edit")
	public String shopEdit() {
		return "shop_edit";
	}
	
	/**
	 * 进入活动列表页
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "shopindex";
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", so.queryCount(query, courtyardIds));
			map.put("rows", so.queryList(query, page, rows, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 商品添加
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		so.addGoods(entity, map);
		return MAP;
	}
	
	/**
	 * 商品编辑
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		so.updateGoods(entity, map);
		return MAP;
	}
	
	/**
	 * 删除商品
	 */
	@Action(NAMESPACE + "/del")
	public String delete() {
		so.deleteGoods(entity, map);
		return MAP;
	}
	
	/**
	 * 在售/下架商品
	 */
	@Action(NAMESPACE + "/changestatus") 
	public String changeStatus() {
	  	so.changeStatus(query, map);
	  	return MAP;
	}
	
	/**
	 * 根据ID获取内容详情
	 * @return
	 */
	@Action(NAMESPACE + "/getcontentinfo")
	public String getContentInfo() {
		so.getContentInfo(id, map);
		return MAP;
	}
	
	private ShopQueryEntity query;
	private ShopViewEntity entity;
	private Integer id;
	private String process; //操作类型  update 修改 add 添加（默认）

	public ShopQueryEntity getQuery() {
		return query;
	}

	public void setQuery(ShopQueryEntity query) {
		this.query = query;
	}

	public ShopViewEntity getEntity() {
		return entity;
	}

	public void setEntity(ShopViewEntity entity) {
		this.entity = entity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

} 
