package com.yard.manager.base.entity;

/**
 * 公众平台令牌实体
 * @author jiangbo
 *
 */
public class AccessTokenEntity {
	private String accessToken; // 接口令牌
	private String jsApiTicket; // JS票证
	private long tokenExpiresTime; // 到期时间（时间戳）
	private String wxConfigId; // 所属公众号

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public long getTokenExpiresTime() {
		return tokenExpiresTime;
	}

	public void setTokenExpiresTime(long tokenExpiresTime) {
		this.tokenExpiresTime = tokenExpiresTime;
	}

	public String getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(String wxConfigId) {
		this.wxConfigId = wxConfigId;
	}
}
