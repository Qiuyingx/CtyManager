package com.gen.util;

public class TypeUtil {

	public static String getJavaType(String dataType) {
		String jType = "";
		if ("VARCHAR".equals(dataType) || "CHAR".equals(dataType)) {
			jType = "String";
		} else if ("TIMESTAMP".equals(dataType)) {
			jType = "Date";
		} else if ("INTEGER".equals(dataType)) {
			jType = "Long";
		}  else if ("INT".equals(dataType)) {
			jType = "Integer";
		} else if ("BIT".equals(dataType)) {
			jType = "Boolean";
		} else if ("REAL".equals(dataType)) {
			jType = "Float";
		} else if ("DECIMAL".equals(dataType)) {
			jType = "BigDecimal";
		} else if ("TIMESTAMP".equals(dataType) || "DATE".equals(dataType) || "DATETIME".equals(dataType)) {
			jType = "Date";
		} else if("DOUBLE".equals(dataType)){
			jType = "Double";
		}
		Double s = 0.01;
		return jType;
	}
}
