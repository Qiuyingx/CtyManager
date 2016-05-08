package com.cddayuanzi.wxsdk.result;

/**
 * 上传多媒体素材返回实体
 * @author jiangbo
 *
 */
public class MediaJsonRespEntity extends BaseJsonRespEntity {
	private static final long serialVersionUID = -5866149507139785000L;

	private String media_id;
	private String type;
	private String created_at;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
