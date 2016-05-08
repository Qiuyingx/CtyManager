package com.cddayuanzi.wxsdk.redpack.entity;

public class Share {
	// 分享文案
	private String share_content;
	// 分享链接
	private String share_url;
	// 分享的图片
	private String share_imgurl;

	public String getShare_content() {
		return share_content;
	}

	public void setShare_content(String share_content) {
		this.share_content = share_content;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public String getShare_imgurl() {
		return share_imgurl;
	}

	public void setShare_imgurl(String share_imgurl) {
		this.share_imgurl = share_imgurl;
	}
}
