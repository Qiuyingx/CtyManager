package com.yard.manager.modules.detail.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.detail.entity.ExpViewDetail;
import com.yard.manager.modules.detail.entity.LindouViewDetail;
import com.yard.manager.modules.detail.entity.query.ExpQueryDetail;
import com.yard.manager.modules.detail.entity.query.LindouQueryDetail;
import com.yard.manager.modules.detail.service.ExpDetailService;
import com.yard.manager.modules.detail.service.LindouDetailService;

/**
 * 日志记录Action
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
public class DetailAction extends BaseAction {
	private static final long serialVersionUID = 5044411513754625635L;
	private static final String NAMESPACE = "/detail/info";
	private static final LindouDetailService lindouService = LindouDetailService.getInstance();
	private static final ExpDetailService expService = ExpDetailService.getInstance();
	
	/**
	 * 邻豆明细
	 */
	@Action(NAMESPACE + "/querylindoulist")
	public String queryLindouList() {
		try {
			map.put("total", lindouService.queryCount(lindou, userId));
			map.put("rows", lindouService.queryList(lindou, page, rows, userId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<LindouViewDetail>());
		}
		return MAP;
	}
	
	/**
	 * 经验明细
	 */
	@Action(NAMESPACE + "/queryexplist")
	public String queryExpList() {
		try {
			map.put("total", expService.queryCount(exp, userId));
			map.put("rows", expService.queryList(exp, page, rows, userId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<ExpViewDetail>());
		}
		return MAP;
	}
	
	private LindouQueryDetail lindou;
	private ExpQueryDetail exp;
	private Integer userId;

	public LindouQueryDetail getLindou() {
		return lindou;
	}
	public void setLindou(LindouQueryDetail lindou) {
		this.lindou = lindou;
	}
	public ExpQueryDetail getExp() {
		return exp;
	}
	public void setExp(ExpQueryDetail exp) {
		this.exp = exp;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
