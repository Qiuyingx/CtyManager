package com.cddayuanzi.wxsdk;

public enum WeiXinEventType {
	SUBSCRIBE("subscribe"), // 关注
	UNSUBSCRIBE("unsubscribe"), // 取消关注
	SCAN("SCAN"), // 已经关注
	LOCATION("LOCATION"), // 上报地理位置事件
	CLICK("CLICK"), // 菜单点击
	VIEW("VIEW"), // 菜单点击跳转连接
	SCANCODE_PUSH("scancode_push"), // 扫码推事件的事件推送
	SCANCODE_WAITMSG("scancode_waitmsg"), // 扫码推事件且弹出“消息接收中”提示框的事件推送
	PIC_SYSPHOTO("pic_sysphoto"), // 弹出系统拍照发图的事件推送
	PIC_PHOTO_OR_ALBUM("pic_photo_or_album"), // 弹出拍照或者相册发图的事件推送
	PIC_WEIXIN("pic_weixin"), // 弹出微信相册发图器的事件推送
	LOCATION_SELECT("location_select"); // 弹出地理位置选择器的事件推送

	public String eventTypeName;

	WeiXinEventType(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public static WeiXinEventType getWeiXinEventType(String eventTypeName) {
		for (WeiXinEventType e : WeiXinEventType.values()) {
			if (e.getEventTypeName().equals(eventTypeName)) {
				return e;
			}
		}

		return null;
	}
}
