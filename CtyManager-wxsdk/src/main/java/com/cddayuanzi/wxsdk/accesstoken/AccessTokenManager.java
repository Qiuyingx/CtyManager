package com.cddayuanzi.wxsdk.accesstoken;

import com.cddayuanzi.wxsdk.Constant;
import com.cddayuanzi.wxsdk.httputil.SendRequest;
import com.cddayuanzi.wxsdk.result.AccessTokenJsonRespEntity;
import com.cddayuanzi.wxsdk.result.JsApiTicketJsonRespEntity;

/**
 * Token
 * @author jiangbo
 *
 */
public class AccessTokenManager {
	/**
	 * 获取access_token
	 * @param appId
	 * @param appSecuret
	 * @return
	 * @throws Exception
	 */
	public static AccessTokenJsonRespEntity getAccessToken(String appId, String appSecuret) throws Exception {
		String url = Constant.ACCESS_TOKEN_URL.replaceAll(Constant.PARAM_APPID, appId).replaceAll(Constant.PARAM_APPSECRET, appSecuret);
		
		return SendRequest.sendRequest2WeiXin(url, AccessTokenJsonRespEntity.class);
	}
	
	/**
	 * 获取jsapi_ticket
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static JsApiTicketJsonRespEntity getJsApiTicket(String accessToken) throws Exception {
		String url = Constant.JSAPI_TICKET_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		
		return SendRequest.sendRequest2WeiXin(url, JsApiTicketJsonRespEntity.class);
	}
}
