package com.cddayuanzi.wxsdk.redpack.entity;

public class PackPram extends Shops {
	// 付款金额(是)
	private int total_amount;
	// 最小红包金额(是)
	private int min_value;
	// 最大红包金额(是)
	private int max_value;
	// 红包发放总人数(是)
	private int total_num;
	// 红包祝福语(是)
	private String wishing;

	// 商户订单号(是)--
	private String mch_billno;
	// 用户openid(是)
	private String re_openid;
	// Ip地址(是)
	private String client_ip;
	// 活动名称(是)
	private String act_name;
	// 备注(是)
	private String remark;

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	public int getMin_value() {
		return min_value;
	}

	public void setMin_value(int min_value) {
		this.min_value = min_value;
	}

	public int getMax_value() {
		return max_value;
	}

	public void setMax_value(int max_value) {
		this.max_value = max_value;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getMch_billno() {
		return mch_billno;
	}

	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}

	public String getRe_openid() {
		return re_openid;
	}

	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
