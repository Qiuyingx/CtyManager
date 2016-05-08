package com.yard.manager.modules.activity.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.activity.entity.ActSignupViewEntity;
import com.yard.manager.modules.activity.entity.query.ActQueryEntity;
import com.yard.manager.modules.activity.service.ActSignupService;
/**
 * 活动报名
 * @author : xiaym
 * @date : 2015年6月23日 下午2:37:41
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "actsignupindex", location="/WEB-INF/content/modules/act/actsignupindex.html")
})
public class ActSignupAction extends BaseAction {
	private static final long serialVersionUID = -2170789104855964439L;
	private static final String NAMESPACE = "/act/signup";
	private static final ActSignupService ass = ActSignupService.getInstance();
	
	/**
	 * 进入活动列表页
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "actsignupindex";
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", ass.queryCount(actId));
			map.put("rows", ass.queryList(page, rows, actId));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<ActSignupViewEntity>());
		}
		return MAP;
	}
	
	private ActQueryEntity query;
	private Integer actId;

	public ActQueryEntity getQuery() {
		return query;
	}

	public void setQuery(ActQueryEntity query) {
		this.query = query;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
	
} 
