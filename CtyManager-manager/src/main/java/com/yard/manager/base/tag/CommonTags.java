package com.yard.manager.base.tag;

import freemarker.template.SimpleHash;

/**
 * freemarker自定义指令注册类
 * @author jiangbo
 *
 */
public class CommonTags extends SimpleHash {
    /**
	 * 
	 */
	private static final long serialVersionUID = -771012624601548405L;

	public CommonTags() {
        put("isSysUser", new IsSysUserTag()); // 判断当前用户是不是系统内置账户
        put("menuType", new MenuTypeTags()); // 微信菜单类型常量获取
    }
}