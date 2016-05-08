package com.cddayuanzi.wxsdk.sendmessage.sendentity;

public class SendAllMessageImageEntity extends SendAllMessageBaseEntity {
	private Media image = new Media();

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}
}
