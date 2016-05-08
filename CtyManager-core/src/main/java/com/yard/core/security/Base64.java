package com.yard.core.security;

public class Base64 {
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */

	public static byte[] decodeBase64(String key)  {
		return org.apache.commons.codec.binary.Base64.decodeBase64(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */

	public static String encodeBase64String(byte[] key) {
		return org.apache.commons.codec.binary.Base64.encodeBase64String(key);
	}
}
