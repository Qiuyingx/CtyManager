package com.yard.manager.base.action;

import org.apache.struts2.convention.annotation.Action;

import com.yard.manager.base.service.CommonService;
import com.yard.manager.platform.shiro.ShiroUtil;

public class CommonAction extends BaseAction {
	private static final long serialVersionUID = -6812583670163183470L;

	private static final String COMMON = "/common";

	@Action(COMMON + "/getsysgroupcombo")
	public String getSysGroupCombo() {
		array = new CommonService().getSysGroupCombo(ShiroUtil.getUser());
		return ARRAY;
	}
	
	@Action(COMMON + "/getcontentcombo/{id}")
	public String getContentCombo() {
		array = new CommonService().getContentCombo(id);
		return ARRAY;
	}
	
	/**
	 * 查询所有的院子列表
	 * @return
	 */
	@Action(COMMON + "/getyardcombo")
	public String getYardCombo() {
		array = new CommonService().getYardCombo(null);
		return ARRAY;
	}
	
	/**
	 * 根据城市查询院子
	 */
	@Action(COMMON + "/getyardcombobycity")
	public String getYardComboByCity() {
		String id = request.getParameter("cityId");
		
		array = new CommonService().getYardCombo(Integer.parseInt(id));
		return ARRAY;
	}
	
	/**
	 * 根据城市Ids查询院子
	 */
	@Action(COMMON + "/getyardbycityids")
	public String getYardComboByCityIds() {
		String id = request.getParameter("cityIds");
		
		array = new CommonService().getYardComboByCityIds(id);
		return ARRAY;
	}
}
