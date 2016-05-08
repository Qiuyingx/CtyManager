package com.cddayuanzi.font.modules.normal.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.font.base.action.BaseAction;
import com.cddayuanzi.wxsdk.user.entity.WebCodeInfo;
import com.cddayuanzi.wxsdk.user.manager.UserManager;

@Results({
	@Result(type = "freemarker", name = "getopenidbypage", location = "/WEB-INF/page/order/order_owner.html"),
	@Result(type = "freemarker", name = "order_write_openid", location = "/WEB-INF/page/order/order_write.html")
})
public class WxAction extends BaseAction {
	private static final long serialVersionUID = 1100570219961032499L;
	private static final String NAMESPACE = "/normal";
	private String appId = "wx87b4438a2a8abff3";
	private String appSecret = "f50ac37191b828a71ee20721411611ce";

	public void getOpenIdProcess(){
		try {
			WebCodeInfo info = null;
			try {
				info = UserManager.getUserWebInfo(appId, appSecret, code);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(info != null && !StringUtils.isEmpty(info.getOpenid())) {
				openId = info.getOpenid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
			
	/**
	 * 获取openId, (跳转到订单详情)
	 */
	@Action(NAMESPACE + "/order_owner_open")
	public String getOpenIdByPage() {
		getOpenIdProcess();
		return "getopenidbypage";
	}
	
	/**
	 * 进入填单填写, 同时获取openId
	 */
	@Action(NAMESPACE + "/order_write_openid")
	public String orderWrite() {
		getOpenIdProcess();
		return "order_write_openid";
	}	
	
	
	private String code;
	private String state;
	private String orderId;
	private String openId;
	private Integer courseId;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	
}
