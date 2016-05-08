package com.cddayuanzi.weixin.action;

import org.apache.struts2.convention.annotation.Action;

import com.cddayuanzi.font.base.action.BaseAction;
import com.cddayuanzi.weixin.service.WxEnterService;

/**
 * 微信服务器发送过来的消息
 * @author xiayuanming
 *
 */
public class WxEnterAction extends BaseAction {
	private static final long serialVersionUID = -8440704559187732794L;
	private static final String NAMESPACE = "/cddayuanzi/wx";
	private static final WxEnterService service = WxEnterService.getInstance();

	@Action(NAMESPACE + "/enter")
	public String enter() {
		service.enter(response, request, signature, timestamp, nonce, echostr);
		return MAP;
	}

	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

}
