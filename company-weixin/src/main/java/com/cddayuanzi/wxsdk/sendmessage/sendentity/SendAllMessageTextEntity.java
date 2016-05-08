package com.cddayuanzi.wxsdk.sendmessage.sendentity;

public class SendAllMessageTextEntity extends SendAllMessageBaseEntity {
	private Content text = new Content();

	public Content getText() {
		return text;
	}

	public void setText(Content text) {
		this.text = text;
	}
}
