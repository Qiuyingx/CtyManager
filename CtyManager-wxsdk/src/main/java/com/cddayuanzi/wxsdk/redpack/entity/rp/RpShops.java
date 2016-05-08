package com.cddayuanzi.wxsdk.redpack.entity.rp;

public class RpShops {

	// 商户号
	private String mch_id;
	// 公众账号appid
	private String wxappid;
	// 提供方名称
	private String nick_name;
	// 商户名称
	private String send_name;

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getWxappid() {
		return wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getSend_name() {
		return send_name;
	}

	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
}
