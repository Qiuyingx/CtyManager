package com.yard.manager.base.tag;

import java.util.Map;

import com.yard.manager.base.constant.ManagerConstant;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

public class MenuTypeTags extends BaseRenderTag {

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) {
		String constant = getParam(params, "type");
		String str = "";
		if (constant.equals("push")) {
			str = ManagerConstant.WEIXIN_MENUTYPE_PUSH + "";
		} else if (constant.equals("link")) {
			str = ManagerConstant.WEIXIN_MENUTYPE_LINK + "";
		} else {
			str = "";
		}
		
		try {
			renderString(env, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
