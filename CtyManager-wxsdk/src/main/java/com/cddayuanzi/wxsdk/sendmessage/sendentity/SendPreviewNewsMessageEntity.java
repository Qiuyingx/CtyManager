package com.cddayuanzi.wxsdk.sendmessage.sendentity;

public class SendPreviewNewsMessageEntity extends SendPreviewMessageBaseEntity {
	private Media mpnews = new Media();

	public Media getMpnews() {
		return mpnews;
	}

	public void setMpnews(Media mpnews) {
		this.mpnews = mpnews;
	}
}
