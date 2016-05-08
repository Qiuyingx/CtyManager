package com.cddayuanzi.wxsdk.result;

import java.io.Serializable;

/**
 * 微信返回数据基础包（JSON）
 * @author jiangbo
 *
 */
public class BaseJsonRespEntity implements Serializable {
	private static final long serialVersionUID = 8547091935824655136L;
	public static final long SUCCESS_CODE = 0;
	
	private long errcode = SUCCESS_CODE; // 错误码
	private String errmsg; // 错误信息
	private String respStr = "";
	
	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public long getErrcode() {
		return errcode;
	}

	public void setErrcode(long errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public boolean isSuccess() {
		return errcode == SUCCESS_CODE;
	}
}
