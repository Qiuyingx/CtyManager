package com.gen.entity;

import com.gen.util.TypeUtil;

public class Type {

	private String d; // 数据类型
	private String j; // JAVA类型

	public boolean checkType(String type) {
		return j.equals(type);
	}

	public Type(String code) {
		this.d = code;
		j = TypeUtil.getJavaType(d);
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
		j = TypeUtil.getJavaType(d);
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}
}
