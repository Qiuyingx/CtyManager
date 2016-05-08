package com.yard.manager.modules.content.action;

import org.apache.struts2.convention.annotation.Action;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.content.service.TopBannerService;

/**
 * banner推荐
 * @author : xiaym
 * @date : 2015年9月10日 下午3:04:35
 * @version : 1.0
 */
public class TopBannerAction extends BaseAction {
	private static final long serialVersionUID = 3653123098876483947L;
	private static final String NAMESPACE = "/content/bannertop";
	private static final TopBannerService tbs = TopBannerService.getInstance();
	
	/**
	 * 推荐Banner
	 * @return
	 */
	@Action(NAMESPACE + "/addtop")
	public String addTop() {
		tbs.addTop(contentId, dtype, map);
		return MAP;
	}
	
	/**
	 * 删除Banner推荐
	 * @return
	 */
	@Action(NAMESPACE + "/deltop")
	public String delTop() {
		tbs.delTop(contentId, dtype, map);
		return MAP;
	}
	
	private Integer contentId; // 文章ID
	private Integer dtype; // 文章类型 1专题 2资讯 3课程

	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getDtype() {
		return dtype;
	}
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
}
