package com.yard.core.struts.manager;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.yard.core.shiro.freemarkertags.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * struts2+freemarker框中使用shiro标签
 * 配置到struts.xml文件中
 * <struts>
 *     <constant name="struts.freemarker.manager.classname" value="com.xxx.ShiroFreemarkerManager" />  
 * </struts>
 * @author jiangbo
 *
 */
public class ShiroFreemarkerManager extends FreemarkerManager {
	@Override
	protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
		Configuration cfg = super.createConfiguration(servletContext);
		cfg.setSharedVariable("shiro", new ShiroTags());
		return cfg;
	}
}
