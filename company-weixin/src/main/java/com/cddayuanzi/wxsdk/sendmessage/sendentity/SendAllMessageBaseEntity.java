package com.cddayuanzi.wxsdk.sendmessage.sendentity;


/**
 * 群发消息实体
 * @author jiangbo
 *
 */
public class SendAllMessageBaseEntity {
	public static final String MSGTYPE_NEWS = "mpnews";
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_IMAGE = "image";
	
	private Filter filter = new Filter(); // 过滤器
	private String msgtype;

	public class Filter {
		private boolean is_to_all;
		private String group_id;

		public boolean isIs_to_all() {
			return is_to_all;
		}

		public void setIs_to_all(boolean is_to_all) {
			this.is_to_all = is_to_all;
		}

		public String getGroup_id() {
			return group_id;
		}

		public void setGroup_id(String group_id) {
			this.group_id = group_id;
		}
	}

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
	
	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
