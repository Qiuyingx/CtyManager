package com.cddayuanzi.wxsdk.menu.manager;

import com.cddayuanzi.wxsdk.Constant;
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
		String json="{\"button\": [{\"type\": \"view\",\"name\": \"我要报修\",\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7fc15b80cad19e20&redirect_uri=http://www.cdvanke.com/app/weixin/repeat/enter.jspx&response_type=CODE&scope=snsapi_base&state=sdfsde&connect_redirect=1#wechat_redirect\"},{\"type\": \"view\",\"name\": \"报修列表\",\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7fc15b80cad19e20&redirect_uri=http://www.cdvanke.com/app/weixin/repeat/mydata.jspx&response_type=CODE&scope=snsapi_base&state=sdfsde&connect_redirect=1#wechat_redirect\"},{\"type\": \"view\",\"name\": \"APP下载\",\"url\": \"http://www.hiibox.com:8080/files/vk/xz.html\"}]}";
		try {
			create(json, "-jrGAeTyMzQNkJKuxJ0sAK8S9T8OrKKwaP7kyx7F6AapkAFJ8OdD4kvtu5DhYGqcQdPx3_udYYCN4lcLscNvW98NqBxZIPVzE0vGhKhnhxs");
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
