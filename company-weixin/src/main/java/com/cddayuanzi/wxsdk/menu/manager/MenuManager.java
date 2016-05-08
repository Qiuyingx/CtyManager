package com.cddayuanzi.wxsdk.menu.manager;

import com.cddayuanzi.wxsdk.Constant;
import com.cddayuanzi.wxsdk.accesstoken.AccessTokenManager;
import com.cddayuanzi.wxsdk.httputil.SendRequest;
import com.cddayuanzi.wxsdk.result.MenuJsonRespEntity;

public class MenuManager {
	/**
	 * 创建菜单
	 * @param menu
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static boolean create(String json, String accessToken) throws Exception {
		String url = Constant.MENU_CREATE_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		//String json = new Gson().toJson(menu);
		
		MenuJsonRespEntity result = SendRequest.sendRequest2WeiXin4Json(url, json, MenuJsonRespEntity.class);
		
		return result.getErrcode() == MenuJsonRespEntity.SUCCESS_CODE;
	}
	
	public static void main(String[] args) {
		String json="{\"button\": [{\"type\": \"view\",\"name\": \"商城入口\",\"url\": \"http://www.cddayuanzi.com:9002/company-weixin/normal/index\"}]}";
		try {
			String accessToken = AccessTokenManager.getAccessToken("wxd67ec042daa98e13", "9a2863d9bb521e55f26a5880d4c67f95").getAccess_token();
			boolean s = create(json, accessToken);
			System.out.println("====result:"+s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除菜单
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static boolean delete(String accessToken) throws Exception {
		String url = Constant.MENU_DELETE_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		MenuJsonRespEntity result = SendRequest.sendRequest2WeiXin(url, MenuJsonRespEntity.class);
		return result.getErrcode() == MenuJsonRespEntity.SUCCESS_CODE;
	}
}
