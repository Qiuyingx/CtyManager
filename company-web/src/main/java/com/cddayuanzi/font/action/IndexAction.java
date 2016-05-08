package com.cddayuanzi.font.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.font.base.action.BaseAction;

/**
 * 网站入口基础处理
 * 
 * @author xiayuanming
 * 
 */
@Results({ @Result(type = "freemarker", name = "index", location = "/WEB-INF/web/index.html") })
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -7535629533795069592L;
	private static final String NAMESPACE = "/web";

	/**
	 * 进入大院子官网
	 */
	@Action(NAMESPACE + "/index")
	public String contentIndex() {
		return "index";
	}
	
}
