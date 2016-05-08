package com.cddayuanzi.wxsdk.result;

public class JsApiTicketJsonRespEntity extends BaseJsonRespEntity {
	private static final long serialVersionUID = -7546964192481503825L;

	private String ticket; // jsapiticket
	private int expires_in; // 有效期（秒）

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
