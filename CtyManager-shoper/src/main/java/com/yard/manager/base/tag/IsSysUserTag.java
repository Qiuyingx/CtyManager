package com.yard.manager.base.tag;

import java.util.Map;

import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.platform.shiro.ShiroUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

public class IsSysUserTag extends BaseRenderTag {

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) {
		if (ShiroUtil.getUser().getSysUserNo().equals(ManagerConstant.SYSTEM_USER)) {
			try {
				renderBody(env, body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
