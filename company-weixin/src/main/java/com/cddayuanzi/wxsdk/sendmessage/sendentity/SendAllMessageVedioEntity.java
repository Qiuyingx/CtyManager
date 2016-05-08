package com.cddayuanzi.wxsdk.sendmessage.sendentity;

public class SendAllMessageVedioEntity extends SendAllMessageBaseEntity {
	private Media voice = new Media();

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}
}
