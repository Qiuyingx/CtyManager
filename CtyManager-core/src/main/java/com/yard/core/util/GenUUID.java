package com.yard.core.util;

import java.util.UUID;

public class GenUUID {
	public static String uuid() {
		return uuid("");
	}

	public static String uuid(boolean b) {
		if (b)
			return UUID.randomUUID().toString();
		else
			return uuid();
	}

	public static String uuid(String replacement) {
		if (replacement == null) {
			return uuid();
		}
		return UUID.randomUUID().toString().replaceAll("-", replacement);

	}
}
