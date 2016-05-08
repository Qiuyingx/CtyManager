package com.yard.manager.modules.relation.entity;

public class AtRelationsViewEntity extends AtRelationsEntity {
	/**
	 * 
	 * @param atTargetId 艾特对象的ID
	 * @param atNickName 艾特对象的昵称
	 * @param scene 发艾特的场景，{@link ContentType}
	 * @param append 附加信息，可能是话题，邀约等的ID
	 */
	public AtRelationsViewEntity(Integer atTargetId, String atNickName, Integer scene, Integer append) {
		super.setAtTargetId(atTargetId);
		super.setAtNickName(atNickName);
		super.setScene(scene);
		super.setAppend(append);
	}
	
	public AtRelationsViewEntity() {
		
	}
}
