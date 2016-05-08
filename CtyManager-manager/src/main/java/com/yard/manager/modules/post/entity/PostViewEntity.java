package com.yard.manager.modules.post.entity;

/**
 * 话题帮帮扩展字段
 * 
 * @author : xiaym
 * @date : 2015年6月18日 上午1:19:10
 * @version : 1.0
 */
public class PostViewEntity extends PostEntity{
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 发帖人昵称
	 */
	private String nickName;
	/**
	 * 邻居帮帮的标签名称
	 */
	private String tagName;
	/**
	 * 感谢次数
	 */
	private Integer laudCount;
	/**
	 * 评论次数
	 */
	private Integer replyCount;
	/**
	 * 被采纳者ID
	 */
	private Integer cUserId;
	/**
	 * 被采纳者昵称
	 */
	private String cNickName;
	/**
	 * 被采纳者头像
	 */
	private String cFaceImage;
	/**
	 * 被采纳者评论时间
	 */
	private long cTime;
	/**
	 * 被采纳者评论内容
	 */
	private String cContent;
	/**
	 * 次数
	 */
	private Integer count;
	/**
	 * 是否周边可见  1可见（true） 0不可见（false）
	 */
	private Integer showAround;
	
	public PostViewEntity() {
		
	}
	
	/**
	 *  自我介绍
	 * @param courtyardId 院子ID
	 * @param userId 发帖人ID
	 * @param title 帖子标题
	 * @param content 帖子内容
	 */
	public PostViewEntity(Integer courtyardId, Integer userId, String title, String content) {
		super.courtyardId = courtyardId;
		super.userId = userId;
		super.title = title;
		super.content = content;
		super.contentType = 3;
		super.myself = 1;
		super.priority = 0;
	}
	
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getLaudCount() {
		return laudCount;
	}
	public void setLaudCount(Integer laudCount) {
		this.laudCount = laudCount;
	}
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public String getcNickName() {
		return cNickName;
	}

	public void setcNickName(String cNickName) {
		this.cNickName = cNickName;
	}

	public String getcFaceImage() {
		return cFaceImage;
	}

	public void setcFaceImage(String cFaceImage) {
		this.cFaceImage = cFaceImage;
	}

	public long getcTime() {
		return cTime;
	}

	public void setcTime(long cTime) {
		this.cTime = cTime;
	}

	public String getcContent() {
		return cContent;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public Integer getcUserId() {
		return cUserId;
	}

	public void setcUserId(Integer cUserId) {
		this.cUserId = cUserId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getShowAround() {
		return showAround;
	}

	public void setShowAround(Integer showAround) {
		this.showAround = showAround;
	}
	
}
