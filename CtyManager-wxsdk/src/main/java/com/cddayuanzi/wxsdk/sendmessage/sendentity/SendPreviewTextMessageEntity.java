package com.cddayuanzi.wxsdk.sendmessage.sendentity;

public class SendPreviewTextMessageEntity extends SendPreviewMessageBaseEntity {
	private Content text = new Content();

	public Content getText() {
		return text;
	}

	public void setText(Content text) {
		this.text = text;
	}
}
