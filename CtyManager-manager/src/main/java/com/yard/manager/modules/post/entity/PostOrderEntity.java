package com.yard.manager.modules.post.entity;

/**
 * 发帖活跃
 * @author : xiaym
 * @date : 2015年7月14日 下午2:43:35
 * @version : 1.0
 */
public class PostOrderEntity {
	/**
	 * 发帖用户ID
	 */
	private Integer userId;
	/**
	 * 发帖总数
	 */
	private Integer counts;
	/**
	 * 发帖人昵称
	 */
	private String nickName;
	/**
	 * 发帖人电话
	 */
	private String tel;
	/**
	 * 发帖人所属院子
	 */
	private String courtyardName;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCourtyardName() {
		return courtyardName;
	}
	public void setCourtyardName(String courtyardName) {
		this.courtyardName = courtyardName;
	}
	
}
