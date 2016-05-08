package com.cddayuanzi.wxsdk.redpack.entity;

public class RedPack extends PackPram {
	// 随机字符串
	private String nonce_str;
	// 签名
	private String sign;

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
