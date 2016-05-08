package com.cddayuanzi.wxsdk.user.manager;

import com.cddayuanzi.wxsdk.Constant;
import com.cddayuanzi.wxsdk.httputil.SendRequest;
import com.cddayuanzi.wxsdk.result.UserInfoJsonRespEntity;
import com.cddayuanzi.wxsdk.user.entity.WebCodeInfo;

/**
 * 微信用户管理接口类
 * 
 * @author jiangbo
 *
 */
public class UserManager {
	/**
	 * 获取单个用户信息
	 * @param openId
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static UserInfoJsonRespEntity getUserInfo(String openId, String accessToken) throws Exception {
		String url = Constant.USER_GET_USERINFO_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken).replaceAll(
				Constant.PARAM_OPENID, openId);

		return SendRequest.sendRequest2WeiXin(url, UserInfoJsonRespEntity.class);
	}
	
	/**
	 * 网页授权
	 *   通过code 换取access_token,openid等信息
	 * 
	 * @param appid 公众号的唯一标识
	 * @param secret 公众号的appsecret
	 * @param code 填写第一步获取的code参数
	 * @return
	 * @throws Exception
	 */
	public static WebCodeInfo getUserWebInfo(String appid, String secret, String code) throws Exception {
		String url = Constant.USER_GET_ACCESSTOKEN_URL.replace(Constant.PARAM_APPID, appid)
				.replaceAll(Constant.PARAM_SECRET, secret).replaceAll(Constant.PARAM_CODE, code);

		return SendRequest.sendRequest2WeiXin(url, WebCodeInfo.class);
	}

}
