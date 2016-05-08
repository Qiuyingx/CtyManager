package com.yard.manager.charts.user.action;

import org.apache.struts2.convention.annotation.Action;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.charts.user.service.UserService;


/**
 * 用户表统计
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 6262804266261497062L;
	private static final String NAMESPACE = "/charts/user";
	private static final UserService us = UserService.getInstance();
	
	/**
	 * 统计各个小区累计注册量（欢迎页统计图展示）
	 * @return
	 */
	@Action(NAMESPACE + "/showcountuserarea")
	public String showCountUserArea() {
		us.showCountUserArea(map);
		return MAP;
	}
	
	/**
	 * 统计当天的关键元素，如：注册量，发帖量，发帖人数，点赞数量，评论数量（用作欢迎页统计图暂时）
	 * @return
	 */
	@Action(NAMESPACE + "/showcurrnumber")
	public String showCurrNumber() {
		us.showCountCurrNumber(map);
		return MAP;
	}
}
