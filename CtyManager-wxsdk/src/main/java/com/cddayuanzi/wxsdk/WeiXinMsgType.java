package com.cddayuanzi.wxsdk;

/**
 * 被动消息类型枚举类
 * @author jiangbo
 *
 */
public enum WeiXinMsgType {
	TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), SHORTVIDEO("shortvideo"), // 小视屏
	LOCATION("location"), // 位置信息
	LINK("link"), // 链接
	EVENT("event"); // 事件

	private String msgTypeName;

	WeiXinMsgType(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	public String getMsgTypeName() {
		return msgTypeName;
	}

	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	public static WeiXinMsgType getWeiXinMsgType(String msgTypeName) {
		for (WeiXinMsgType e : WeiXinMsgType.values()) {
			if (e.getMsgTypeName().equals(msgTypeName)) {
				return e;
			}
		}

		return null;
	}
}
