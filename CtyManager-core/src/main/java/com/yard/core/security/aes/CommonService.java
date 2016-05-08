package com.yard.core.security.aes;

public interface CommonService {
	// 加密
	public String Encrypt(String sSrc) throws Exception;

	// 解密
	public String Decrypt(String sSrc) throws Exception;
}
