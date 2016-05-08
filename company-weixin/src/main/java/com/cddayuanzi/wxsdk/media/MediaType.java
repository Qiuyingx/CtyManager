package com.cddayuanzi.wxsdk.media;

/**
 * 多媒体类型枚举类
 * @author jiangbo
 *
 */
public enum MediaType {
	IMAGE("image", "image/jpeg"), VOICE("voice", "audio/mpeg");//, VIDEO("video", ""), THUMB("thumb", "");
	
	public String mediaType;
	public String htmlContentType;
	
	MediaType(String mediaType, String htmlContentType) {
		this.mediaType = mediaType;
		this.htmlContentType = htmlContentType;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getHtmlContentType() {
		return htmlContentType;
	}

	public void setHtmlContentType(String htmlContentType) {
		this.htmlContentType = htmlContentType;
	}
}
