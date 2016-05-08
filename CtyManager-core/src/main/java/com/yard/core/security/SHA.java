package com.yard.core.security;

import java.security.MessageDigest;

/**
 * SHA，安全散列算法
 * 
 * @author Administrator
 * 
 */
public class SHA {
	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(data);

		return sha.digest();

	}
}
