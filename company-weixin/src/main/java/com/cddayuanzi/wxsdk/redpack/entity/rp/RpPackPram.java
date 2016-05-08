package com.cddayuanzi.wxsdk.redpack.entity.rp;

public class RpPackPram {

	// 付款金额(是)
	private int total_amount;
	// 红包祝福语(是)
	private String wishing;

	// 用户openid(是)
	private String re_openid;

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getRe_openid() {
		return re_openid;
	}

	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
}
