package com.yard.core.util;

import java.security.NoSuchAlgorithmException;

public class SHA1 {
	public static String digest(String str) {
		try {
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance("SHA-1");
			alga.update(str.getBytes());
			return byte2hex(alga.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toLowerCase();
	}
}
