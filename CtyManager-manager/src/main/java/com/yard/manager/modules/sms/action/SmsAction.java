package com.yard.manager.modules.sms.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.platform.sms.SmsProcess;


@Results({
	@Result(type = "freemarker", name = "index", location="/WEB-INF/content/modules/sms/smsindex.html")
})
public class SmsAction extends BaseAction{
	private static final long serialVersionUID = 8802478339851067512L;
	private static final String NAMESPACE = "/sms/enter";
	
	/**
	 * 进入短信发送页面
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "index";
	}
	
	@Action(NAMESPACE + "/sendSms")
	public String sendSms() {
		if(StringUtils.isBlank(mobiles)) {
			JsonResult.toJson(map, false, "请输入电话号码！");
			return MAP;
		}
		if(StringUtils.isBlank(content)) {
			JsonResult.toJson(map, false, "请输入短信内容！");
			return MAP;
		}
		String[] mobileStr = mobiles.split(",");
		
		for(String mobile : mobileStr) {
			if(mobile.length() == 11) {
				System.out.println("==============正在发送短信==================："+mobile);
				new SmsProcess().sendMsg(mobile, content);
			}
		}
		JsonResult.toJson(map, true, "短信已经发送，请在短信后台查看发送是否成功！");
		return MAP;
	}
	
	private String mobiles;
	private String content;

	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
