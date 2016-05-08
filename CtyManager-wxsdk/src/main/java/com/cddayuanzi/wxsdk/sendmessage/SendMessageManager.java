package com.cddayuanzi.wxsdk.sendmessage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cddayuanzi.wxsdk.Constant;
import com.cddayuanzi.wxsdk.httputil.SendRequest;
import com.cddayuanzi.wxsdk.result.SendMessageJsonRespEntity;
import com.cddayuanzi.wxsdk.sendmessage.responseentity.AcceptMessage;
import com.cddayuanzi.wxsdk.sendmessage.responseentity.ResponseNewsEntity;
import com.cddayuanzi.wxsdk.sendmessage.sendentity.SendAllMessageBaseEntity;
import com.cddayuanzi.wxsdk.sendmessage.sendentity.SendPreviewMessageBaseEntity;
import com.google.gson.Gson;

/**
 * 发送消息
 * 
 * @author jiangbo
 *
 */
public class SendMessageManager {
	/**
	 * 群发消息
	 * 
	 * @param msgEntity
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static SendMessageJsonRespEntity sendAllMessage(SendAllMessageBaseEntity msgEntity, String accessToken)
			throws Exception {
		String url = Constant.MSG_SENDALL_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = new Gson().toJson(msgEntity);

		return SendRequest.sendRequest2WeiXin4Json(url, json, SendMessageJsonRespEntity.class);
	}

	/**
	 * 给指定用户发送预览
	 * @param msgEntity
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static SendMessageJsonRespEntity sendPreviewMessage(SendPreviewMessageBaseEntity msgEntity, String accessToken) throws Exception {
		String url = Constant.MSG_PREVIEW_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = new Gson().toJson(msgEntity);

		return SendRequest.sendRequest2WeiXin4Json(url, json, SendMessageJsonRespEntity.class);
	}
	
	/**
	 * 回复文本消息
	 * 
	 * @param response
	 * @param accept
	 * @param message
	 * @throws Exception
	 */
	public static void responseTextMessage(HttpServletResponse response, AcceptMessage accept, String message) throws Exception {
		String messageBack = "<xml><ToUserName><![CDATA[" + accept.getFromUserName() + "]]></ToUserName><FromUserName><![CDATA["
				+ accept.getToUserName() + "]]></FromUserName><CreateTime>" + new Date()
				+ "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + message
				+ "]]></Content><FuncFlag>0</FuncFlag></xml> ";

		print(response, messageBack);
	}

	/**
	 * 回复图片消息
	 * 
	 * @param response
	 * @param accept
	 * @param mediaId
	 * @throws Exception
	 */
	public static void responseImageMessage(HttpServletResponse response, AcceptMessage accept, String mediaId) throws Exception {
		String messageBack = "<xml>" + "<ToUserName><![CDATA[" + accept.getFromUserName() + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + accept.getToUserName() + "]]></FromUserName>" + "<CreateTime>" + new Date()
				+ "</CreateTime>" + " <MsgType><![CDATA[image]]></MsgType>" + "<Image>" + "<MediaId><![CDATA[" + mediaId
				+ "]]></MediaId>" + "</Image></xml>";

		print(response, messageBack);
	}

	/**
	 * 回复音乐消息
	 * 
	 * @param response
	 * @param accept
	 * @param title
	 * @param description
	 * @param voiceUrl
	 * @throws Exception
	 */
	public static void responseMusicMessage(HttpServletResponse response, AcceptMessage accept, String title, String description,
			String musicUrl) throws Exception {

		String messageBack = "<xml>" + "<ToUserName><![CDATA[" + accept.getFromUserName() + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + accept.getToUserName() + "]]></FromUserName>" + "<CreateTime>" + new Date()
				+ "</CreateTime>" + " <MsgType><![CDATA[music]]></MsgType>" + "<Music>" + " <Title><![CDATA[" + title
				+ "]]></Title>" + " <Description><![CDATA[" + description + "]]></Description>" + "<MusicUrl><![CDATA["
				+ musicUrl + "]]></MusicUrl>" + " <HQMusicUrl><![CDATA[" + musicUrl + "]]></HQMusicUrl>" + "  </Music>"
				+ " <FuncFlag>0</FuncFlag>" + "</xml>";

		print(response, messageBack);
	}
	
	/**
	 * 没有任何可向用户回复的，直接应答微信服务器success
	 * @param response
	 * @throws Exception
	 */
	public static void responseSuccess(HttpServletResponse response) throws Exception {
		print(response, "success");
	}

	/**
	 * 回复图文消息
	 * 
	 * @param response
	 * @param accept
	 * @param item
	 */
	public static void responseNewsMessage(HttpServletResponse response, AcceptMessage accept, ResponseNewsEntity news) throws Exception {
		List<ResponseNewsEntity> newsList = new ArrayList<ResponseNewsEntity>();
		newsList.add(news);
		
		responseNewsMessage(response, accept, newsList);
	}
	
	/**
	 * 回复图文消息
	 * 
	 * @param response
	 * @param accept
	 * @param item
	 */
	public static void responseNewsMessage(HttpServletResponse response, AcceptMessage accept, List<ResponseNewsEntity> newsList)
			throws Exception {
		if (newsList.size() > 10) {
			throw new Exception("最多只能回复10条消息");
		}

		String messageBack = "<xml>" + "<ToUserName><![CDATA[" + accept.getFromUserName() + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + accept.getToUserName() + "]]></FromUserName>" + "<CreateTime>" + new Date()
				+ "</CreateTime>" + "<MsgType><![CDATA[news]]></MsgType>" + "<ArticleCount>" + newsList.size()
				+ "</ArticleCount>" + "<Articles>";

		for (ResponseNewsEntity news : newsList) {
			messageBack += "<item><Title><![CDATA[" + news.getTitle() + "]]></Title>" + "<Description><![CDATA["
					+ news.getDescription() + "]]></Description>" + "<PicUrl><![CDATA[" + news.getPicUrl() + "]]></PicUrl>"
					+ "<Url><![CDATA[" + news.getContentUrl() + "]]></Url>" + "</item>";

		}
		
		messageBack += "</Articles></xml>";

		print(response, messageBack);
	}

	private static void print(HttpServletResponse response, String message) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter w = response.getWriter();
		w.write(message);
		w.close();
		response.flushBuffer();
	}
}
