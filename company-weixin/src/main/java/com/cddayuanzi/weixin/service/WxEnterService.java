package com.cddayuanzi.weixin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cddayuanzi.weixin.manager.WxMainManager;
import com.cddayuanzi.wxsdk.sendmessage.SendMessageManager;

/**
 * 微信被动消息Service
 * 
 * @author jiangbo
 *
 */
public class WxEnterService {
	private static Logger logger = LoggerFactory.getLogger(WxEnterService.class);
	private static final WxEnterService cs = new WxEnterService();

	private WxEnterService() {

	}

	public static WxEnterService getInstance() {
		return cs;
	}

	public void enter(HttpServletResponse response, HttpServletRequest request, String signature, String timestamp, String nonce,
			String echostr) {
		response.setCharacterEncoding("utf-8");

		// 验证消息真实性
		if (!WxMainManager.verification("fdsadfsd45456sdf23ra", timestamp, nonce, signature)) {
			return;
		}

		WxMainManager.downVali(response, echostr);
/*		try {
			AcceptMessage acceptMsg = WxMainManager.getMessage(request);

			// 根据公众号信息得到公众号在平台的配置信息
			WxConfigViewEntity wxConfig = WxMainManager.getConfig(acceptMsg.getToUserName());
			WeiXinMsgType msgType = WeiXinMsgType.getWeiXinMsgType(acceptMsg.getMsgType());

			switch (msgType) {
			case TEXT:
				processText(response, acceptMsg, wxConfig);
				break;
			case EVENT:
				// 事件处理
				WeiXinEventType eventType = WeiXinEventType.getWeiXinEventType(acceptMsg.getEvent());
				switch (eventType) {
				case CLICK:
					processEnvent(response, acceptMsg, wxConfig);
					break;
				case SUBSCRIBE:
					processSubscribe(response, acceptMsg, wxConfig);
					break;
				case UNSUBSCRIBE:
					processUnsubscribe(response, acceptMsg, wxConfig);
					break;
				default:
					break;
				}
				break;
			case IMAGE:
			case VOICE:
			case VIDEO:
			case SHORTVIDEO:
			case LOCATION:
			case LINK:
			default:
				// 这几个暂不处理
				responseSuccess(response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 处理发送过来的文本消息
	 * 
	 * @param response
	 * @param acceptMsg
	 * @param wxConfig
	 * @throws Exception
	 */
/*	private void processText(HttpServletResponse response, AcceptMessage acceptMsg, WxConfigViewEntity wxConfig) throws Exception {
		String respMsg = "";
		if (acceptMsg.getContent().equals("@预览绑定")) {
			if (WxConfigService.getInstance().setViewOpenId(wxConfig.getWxconfigid(), acceptMsg.getFromUserName())) {
				respMsg = "恭喜您，绑定成功，您的微信号将具备预览功能";
			} else {
				respMsg = "绑定失败";
			}
			SendMessageManager.responseTextMessage(response, acceptMsg, respMsg);
		} else {
			// 其它处理（未做）
			responseSuccess(response);
		}
	}*/

	/**
	 * 处理事件
	 * @param response
	 * @param acceptMsg
	 * @param wxConfig
	 * @throws Exception 
	 */
/*	private void processEnvent(HttpServletResponse response, AcceptMessage acceptMsg, WxConfigViewEntity wxConfig) throws Exception {
		String msgTypeId = "";
		String answerMsgId = "";
		
		String eventKey = acceptMsg.getEventKey();
		if (eventKey.indexOf("-") > 0) {
			// 用户创建的菜单
			String[] eventKeys = eventKey.split("-");
			if (eventKeys.length == 2 && !StringUtils.isBlank(eventKeys[0]) && !StringUtils.isBlank(eventKeys[1])) {
				msgTypeId = eventKeys[0];
				answerMsgId = eventKeys[1];
			}
			responseMedia(response, wxConfig, msgTypeId, answerMsgId, acceptMsg);
		} else if (eventKey.indexOf("SYSMENU_") >= 0) {
			// 系统内置的菜单
			if (eventKey.equals(ManagerConstant.SYSMENU_SIGN)) {
				// 签到
				signEvent(response, acceptMsg);
			} else if (eventKey.equals(ManagerConstant.SYSMENU_JOIN)) {
				// 入会
				joinEvent(response, acceptMsg);
			} else if (eventKey.equals(ManagerConstant.SYSMENU_VCARD)) {
				// 微贺卡
				vCardEvent(response, acceptMsg);
			}
		} else {
			responseSuccess(response);
		}
	}*/
	
	/**
	 * 处理取消关注
	 * 
	 * @param response
	 * @param acceptMsg
	 * @param wxConfig
	 */
/*	private void processUnsubscribe(HttpServletResponse response, AcceptMessage acceptMsg, WxConfigViewEntity wxConfig) {
		WxUserService.getInstance().unsubscribe(wxConfig.getWxconfigid(), acceptMsg.getFromUserName());
	}*/

	/**
	 * 处理关注
	 * 
	 * @param config
	 * @param accept
	 * @return
	 * @throws Exception
	 */
	/*private void processSubscribe(HttpServletResponse response, AcceptMessage acceptMsg, WxConfigViewEntity wxConfig)
			throws Exception {
		String accessToken = AccessTokenUtil.getAccessToken(wxConfig.getWxconfigid());

		// 拉取用户详情
		UserInfoJsonRespEntity userInfo = UserManager.getUserInfo(acceptMsg.getFromUserName(), accessToken);
		if (userInfo.isSuccess()) {
			// 拉取成功
			WxUserViewEntity user = new WxUserViewEntity();

			user.setCity(userInfo.getCity());
			user.setCreatetime(new Date());
			user.setGroupid("");
			user.setHeadimgurl(userInfo.getHeadimgurl());
			user.setNickname(userInfo.getNickname());
			user.setOpenid(userInfo.getOpenid());
			user.setProvince(userInfo.getProvince());
			user.setSex(userInfo.getSex());
			user.setUnionid(userInfo.getUnionid());
			user.setWxconfigid(wxConfig.getWxconfigid());

			WxUserService.getInstance().subscribe(user);

			// 获取关注时的自动应答消息
			WxAutoanswerEntity answer = WxMainManager.getAutoAnswer(acceptMsg);
			if (null != answer) {
				logger.info("取到关注后回复信息，准备推送......");
				responseMedia(response, wxConfig, answer.getMsgtypeid(), answer.getAnswermsgid(), acceptMsg);
			} else {
				logger.info("未取到关注后回复信息，回复微信服务器空消息！");
				responseSuccess(response);
			}
		}
	}
*/
	/**
	 * 响应多媒体消息
	 * 
	 * @param msgType
	 * @param answerId
	 * @param acceptMsg
	 * @throws Exception
	 */
	/*private void responseMedia(HttpServletResponse response, WxConfigViewEntity wxConfig, String msgTypeId, String answerMsgId,
			AcceptMessage acceptMsg) throws Exception {
		if (msgTypeId.equals("text")) {
			// 响应一条文本消息
			WxResponseTextViewEntity textEntity = WxResponseTextService.getInstance().findById(answerMsgId);
			SendMessageManager.responseTextMessage(response, acceptMsg, textEntity.getContent());
		} else if (msgTypeId.equals("image")) {
			// 响应一条图片消息
			WxResponseImageViewEntity imageEntity = WxResponseImageService.getInstance().findById(answerMsgId);
			SendMessageManager.responseImageMessage(response, acceptMsg, imageEntity.getMediaId());
		} else if (msgTypeId.equals("news")) {
			// 响应一组图文消息
			List<WxResponseNewsItemViewEntity> list = WxResponseNewsItemService.getInstance().getResponseList(
					answerMsgId);

			if (null == list || list.size() <= 0) {
				responseSuccess(response);
			} else {
				List<ResponseNewsEntity> newsList = new ArrayList<ResponseNewsEntity>();
				for (WxResponseNewsItemViewEntity dbEntity : list) {
					ResponseNewsEntity newsEntity = new ResponseNewsEntity();
					newsEntity.setTitle(dbEntity.getTitle());
					newsEntity.setDescription(dbEntity.getDescription());
					newsEntity.setPicUrl(HttpUtils.valiUrl(dbEntity.getPicurl()));
					newsEntity.setContentUrl(HttpUtils.valiUrl(dbEntity.getUrl()));

					newsList.add(newsEntity);
				}
				logger.info("回复多媒体消息......");
				SendMessageManager.responseNewsMessage(response, acceptMsg, newsList);
			}
		} else {
			responseSuccess(response);
		}
	}*/

	/**
	 * 没有任何可向用户回复的，直接应答微信服务器success
	 * 
	 * @param response
	 * @param acceptMsg
	 * @param wxConfig
	 * @throws Exception
	 */
	private void responseSuccess(HttpServletResponse response) throws Exception {
		SendMessageManager.responseSuccess(response);
	}
	
	/**
	 * 签到处理
	 * 
	 * @param response
	 * @param accept
	 */
	/*private static void signEvent(HttpServletResponse response, AcceptMessage accept) {
		try {
			// 通过原始ID 获取 公众平台信息
			WxConfigService wf = WxConfigService.getInstance();
			WxConfigViewEntity wxconfig = wf.findByOldId(accept.getToUserName());
			// 通过公众平台ID 获取 签到配置信息
			WxSignConfigService wc = WxSignConfigService.getInstance();
			WxSignConfig signConfig = wc.getSignConfig(wxconfig.getWxconfigid());
			// 签到处理
			int signStatus = WxSignService.getInstance().sign(accept, signConfig);
			SignContentService scs = new SignContentService();
			SignService.news(response, accept, signStatus, scs.getNewsContent(accept, signConfig), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	
/*	private static void joinEvent(HttpServletResponse response, AcceptMessage accept) {
		try {
			SignContentService scs = new SignContentService();
			SignService.joinNews(response, accept, scs.getJoinNewsContent(accept));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void vCardEvent(HttpServletResponse response, AcceptMessage accept) {
		try {
			SignContentService scs = new SignContentService();
			SignService.vCardNews(response, accept, scs.getVCardNewsContent(accept));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
