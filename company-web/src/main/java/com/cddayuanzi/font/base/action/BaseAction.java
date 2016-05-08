package com.cddayuanzi.font.base.action;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.font.base.entity.ConditionEntity;
import com.cddayuanzi.font.util.HttpUtils;
import com.yard.core.struts.action.JsonServletAction;

/**
 * 
 * @author xiayuanming
 *
 */
@Results({ @Result(type = "json", name = "TREENODE", params = { "root", "treeNode" }) })
public abstract class BaseAction extends JsonServletAction {
	private static final long serialVersionUID = -842489292342484165L;

	protected static final String INDEX = "index";
	protected static final String EDIT = "edit";
	protected static final String JASPERREPORT = "jasperreport";
	protected static final String TREENODE = "TREENODE";

	protected long page; // 页码
	protected long rows; // 单页条数
	protected String order; // 排序方法
	protected String sort; // 排序字段
	protected ConditionEntity condition; // 查询条件

	private String httpUrl = HttpUtils.ServerUrl;

	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}

	public ConditionEntity getCondition() {
		return condition;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

}
