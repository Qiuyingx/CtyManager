package com.yard.manager.staticpage.outside.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.service.CommonService;

/**
 * 预览处理
 * @author : xiaym
 * @date : 2015年10月6日 上午10:42:02
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "index", location="/WEB-INF/content/outside/task/index.html")
})
public class CountDownAction extends BaseAction {
	private static final long serialVersionUID = 3592900065691737005L;
	private static final String NAMESPACE = "/static/page/outside";

	
	@Action(NAMESPACE + "/centent_view")
	public String contentView() {
		return "index";
	}
	
	// 标题
	private String title;
	// 简述
	private String description;
	// 封面图
	private String titleImg;
	// 内容
	private String content;
	// 预览地址
	private String preUrl;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPreUrl() {
		return preUrl;
	}
	public void setPreUrl(String preUrl) {
		this.preUrl = preUrl;
	}
	
	
}
