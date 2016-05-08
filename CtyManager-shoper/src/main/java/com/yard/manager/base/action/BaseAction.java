package com.yard.manager.base.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.core.struts.action.JsonServletAction;
import com.yard.manager.base.constant.UploadFilePath;
import com.yard.manager.base.entity.ConditionEntity;
import com.yard.manager.base.entity.TreeNodeEntity;
import com.yard.manager.base.util.HttpUtils;

@Results({ @Result(type = "json", name = "TREENODE", params = { "root", "treeNode" }) })
public abstract class BaseAction extends JsonServletAction {
	private static final long serialVersionUID = -842489292342484165L;

	protected static final String INDEX = "index";
	protected static final String EDIT = "edit";
	protected static final String JASPERREPORT = "jasperreport";
	protected static final String TREENODE = "TREENODE";
	
	protected String imgHttpRequestUrl = UploadFilePath.HTTP_REQUEST;
	protected long page; // 页码
	protected long rows; // 单页条数
	protected String order; // 排序方法
	protected String sort; // 排序字段
	protected ConditionEntity condition; // 查询条件
	protected List<TreeNodeEntity> treeNode;

	protected String httpUrl=  HttpUtils.ServerUrl;//服务器请求地址
	protected String httpBlackUrl = HttpUtils.ServerBlackUrl; // 后台地址
	protected String configName = "";//公众平台名称
	protected String id; // 公用ID，用于页面传任何ID回来
	protected String jsApiTicket; // JSAPI票证
	
	public String getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getImgHttpRequestUrl() {
		return imgHttpRequestUrl;
	}

	public void setImgHttpRequestUrl(String imgHttpRequestUrl) {
		this.imgHttpRequestUrl = imgHttpRequestUrl;
	}

	public List<TreeNodeEntity> getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(List<TreeNodeEntity> treeNode) {
		this.treeNode = treeNode;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getHttpBlackUrl() {
		return httpBlackUrl;
	}

	public void setHttpBlackUrl(String httpBlackUrl) {
		this.httpBlackUrl = httpBlackUrl;
	}
	
}
