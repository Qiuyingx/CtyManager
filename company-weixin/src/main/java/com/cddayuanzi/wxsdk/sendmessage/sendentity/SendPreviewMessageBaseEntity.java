package com.cddayuanzi.wxsdk.sendmessage.sendentity;


/**
 * 预览消息实体
 * @author jiangbo
 *
 */
public class SendPreviewMessageBaseEntity {
	public static final String MSGTYPE_NEWS = "mpnews";
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_IMAGE = "image";
	
	private String touser; // 发给谁
	private String msgtype;

	public class Media {
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
	}
	
	public class Content {
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
	
	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}
}
