package com.yard.manager.modules.util;

import java.io.File;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;
import com.yard.manager.modules.notice.entity.PushType;
import com.yard.manager.modules.user.entity.UserPush;

/**
 * 
 * @author : leihc
 * @date : 2015年5月25日
 * @version : 1.0
 */
public class ApnsUtil {

	private static final ApnsUtil instance = new ApnsUtil();

	private ApnsService apnsService;

	private ApnsUtil() {
		File file = new File(PropertiesFactory.getCONFIG_PUSH_PATH());
		try{
			ApnsServiceBuilder serviceBuilder = APNS.newService().withCert(
					file.getPath(), PropertiesFactory.getCONFIG_PUSH_PASS());
			serviceBuilder.withAppleDestination(PropertiesFactory.getCONFIG_PUSH_ISPRODUCT());
			serviceBuilder.asQueued();
			apnsService = serviceBuilder.build();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static ApnsUtil getInstance() {
		return instance;
	}

	public ApnsService getApnsService() {
		return apnsService;
	}

	public void send(UserPush target, String content) {
		if (target.isIosUser()) {
			String payload = APNS.newPayload().sound("default").badge(1)
					.alertBody(content).build();
			apnsService.push(target.getLastToken(), payload);
		}
	}

	public void send(UserPush target) {
		if (target.isIosUser()) {
			String payload = APNS.newPayload().badge(1).sound("default")
					.build();
			apnsService.push(target.getLastToken(), payload);
		}
	}

	/**
	 * 推送内容和icon计数
	 * 
	 * @param target
	 * @param noticeType
	 * @param content
	 * @param badge
	 */
	public void send(UserPush target, PushType type, String content, int badge) {
		if (target.isIosUser()) {
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder.customField("type", type.ordinal());
			payloadBuilder.alertBody(content).sound("default");
			if (badge > 0) {
				payloadBuilder.badge(badge);
			}
			apnsService.push(target.getLastToken(), payloadBuilder.build());
		}
	}
	
	/**
	 * 推送内容和icon计数
	 * 
	 * @param target
	 * @param noticeType
	 * @param content
	 * @param badge
	 */
	public void send(UserPush target, String content, int badge) {
		if (target.isIosUser()) {
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder.alertBody(content).sound("default");
			if (badge > 0) {
				payloadBuilder.badge(badge);
			}
			apnsService.push(target.getLastToken(), payloadBuilder.build());
		}
	}

	/**
	 * 推送内容和icon计数，可以查看详情，比如活动，新闻等
	 * 
	 * @param target
	 * @param type
	 * @param content
	 * @param append
	 *            活动，新闻，专题的数据库ID
	 * @param badge
	 */
	public void send(UserPush target, PushType type, String content,
			long append, int badge) {
		if (target.isIosUser()) {
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder.customField("type", type.ordinal());
			payloadBuilder.customField("append", append);
			payloadBuilder.alertBody(content).sound("default");
			if (badge > 0) {
				payloadBuilder.badge(badge);
			}
			apnsService.push(target.getLastToken(), payloadBuilder.build());
		}
	}

	public static void main(String[] args) {

		UserPush user = new UserPush();
		user.setLastPlatform(2);
		user.setLastToken("75d567e4b987fd8b8e31c02349a19d4dc108089315d51427dc8918aacd159c18");

		ApnsUtil ap = new ApnsUtil();
		/*ap.send(user, 
				"推送配置测试 ");*/
		ap.send(user, PushType.活动,
				"竟发现床上躺着一个肉丸子！新郎大惊，忙问新娘在哪？肉丸子害羞的说：讨厌，人家脱了衣服你就不认识啦！", 2, 2);

	}
}
