package com.yard.manager.modules.notice.entity;

public class NoticeViewEntity extends NoticeEntity {
	/**
	 * 院子名称
	 */
	private String courtyardName;
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	public NoticeViewEntity() {
		
	}
	/**
	 * 推送制定用户
	 * @param userId 用户ID
	 * @param content 推送内容
	 */
	public NoticeViewEntity(Integer userId, String pushContent, String content) {
		super.userId = userId;
		super.pushContent = pushContent;
		super.content = content;
	}
	
	/**
	 * 推送所有城市所有院子
	 * @param content 推送内容
	 */
	public NoticeViewEntity(String pushContent, String content) {
		super.pushContent = pushContent;
		super.content = content;
		super.isAllCity = 1;
	}
	
	/**
	 * 推送所选城市所有院子
	 * @param content 推送内容
	 * @param cityIds 城市IDs 
	 */
	public NoticeViewEntity(String pushContent, String content, String cityIds) {
		super.pushContent = pushContent;
		super.content = content;
		super.isAllYards = 1;
		super.cityIds = cityIds;
	}
	
	/**
	 * 推送类型 有传入参数决定
	 * @param content 推送内容
	 * @param isAllYards 是推送至所有城市
	 * @param cityIds 城市IDs 
	 * @param isAllYards 是推送至所有院子
	 * @param yardIds 院子IDs
	 */
	public NoticeViewEntity(String pushContent, String content, Integer isAllCity, String cityIds, Integer isAllYards, String yardIds) {
		super.pushContent = pushContent;
		super.content = content;
		super.cityIds = cityIds;
		super.isAllCity = isAllCity;
		super.yardIds = yardIds;
		super.isAllYards = isAllYards;
	}
	
	/**
	 * 推送所选城市所选院子
	 * @param content 推送内容
	 * @param cityIds 城市IDs 
	 * @param yardIds 院子IDs
	 */
	public NoticeViewEntity(String pushContent, String content, String cityIds, String yardIds) {
		super.pushContent = pushContent;
		super.content = content;
		super.cityIds = "2";
		super.yardIds = yardIds;
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
	
}
