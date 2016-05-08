package com.yard.manager.platform.entity;

import java.util.List;

public class CenterMenu extends Menu {
	private List<Menu> modules;

	public List<Menu> getModules() {
		return modules;
	}

	public void setModules(List<Menu> modules) {
		this.modules = modules;
	}
}
