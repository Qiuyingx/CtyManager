package com.yard.manager.merchant.train.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.merchant.train.entity.TrainInfoViewEntity;
import com.yard.manager.merchant.train.entity.query.TrainInfoQueryEntity;
import com.yard.manager.merchant.train.service.TrainInfoService;

/**
 * 店铺资料完善
 * @author : xiaym
 * @date : 2015年8月13日 下午4:53:17
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "traininfoindex", location = "/WEB-INF/content/merchant/train/traininfoindex.html"),
	@Result(type = "freemarker", name = "shoperindex", location = "/WEB-INF/content/merchant/train/shoperindex.html"),
	@Result(type = "freemarker", name = "linkshoperindex", location = "/WEB-INF/content/merchant/train/linkshoperindex.html")
})
public class TrainInfoAction extends BaseAction {
	private static final long serialVersionUID = -3719531580009240976L;
	private static final String NAMESPACE = "/train/traininfo";
	private static final TrainInfoService tis = TrainInfoService.getInstance();
	
	/**
	 * 进入商家管理界面
	 */
	@Action(NAMESPACE + "/shoperindex")
	public String shoperIndex() {
		return "shoperindex";
	}
	
	/**
	 * 进入专题学堂推荐
	 * @return
	 */
	@Action(NAMESPACE + "/linkshoperindex")
	public String linkShoperIndex() {
		return "linkshoperindex";
	}
	
	/**
	 * 获取商家数据
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", tis.queryCount(query));
			map.put("rows", tis.queryList(page, rows, query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<TrainInfoViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 专题相关学堂推荐列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylinklist")
	public String queryLinkList() {
		try {
			map.put("total", tis.queryLinkCount(contentId));
			map.put("rows", tis.queryLinkList(page, rows, contentId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<TrainInfoViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 进入开店审核页面
	 */
	@Action(NAMESPACE + "/traininfoindex")
	public String index() {
		entity = tis.findById();
		if(entity == null) {
			entity = new TrainInfoViewEntity();
		}
		return "traininfoindex";
	}
	
	/**
	 * 店铺管理(启用、禁用)
	 */
	@Action(NAMESPACE + "/updatestatus")
	public String updateStatus() {
		tis.updateStatus(entity, map);
		return MAP;
	}
	
	private TrainInfoViewEntity entity;
	private TrainInfoQueryEntity query;
	private Integer contentId; // 专题ID

	public TrainInfoViewEntity getEntity() {
		return entity;
	}

	public void setEntity(TrainInfoViewEntity entity) {
		this.entity = entity;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public TrainInfoQueryEntity getQuery() {
		return query;
	}

	public void setQuery(TrainInfoQueryEntity query) {
		this.query = query;
	}
	
}
