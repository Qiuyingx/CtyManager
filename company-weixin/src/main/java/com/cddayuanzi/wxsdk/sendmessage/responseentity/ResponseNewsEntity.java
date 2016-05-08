package com.cddayuanzi.wxsdk.sendmessage.responseentity;

/**
 * 被动回复图文消息实体
 * @author jiangbo
 *
 */
public class ResponseNewsEntity {
	private String title; // 标题
	private String description; // 描述
	private String picUrl; // 图片地址
	private String contentUrl; // 文本地址

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
}
