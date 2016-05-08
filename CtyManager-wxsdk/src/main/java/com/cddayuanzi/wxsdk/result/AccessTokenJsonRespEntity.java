package com.cddayuanzi.wxsdk.result;

/**
 * 请求access_token时微信返回信息
 * @author jiangbo
 *
 */
public class AccessTokenJsonRespEntity extends BaseJsonRespEntity {
	private static final long serialVersionUID = 1L;

	private String access_token; // access_token
	private int expires_in; // 有效期（秒）

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
