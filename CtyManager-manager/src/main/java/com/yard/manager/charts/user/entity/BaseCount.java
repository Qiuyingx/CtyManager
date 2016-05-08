package com.yard.manager.charts.user.entity;

public class BaseCount {
	/**
	 * 用户累计注册量
	 */
	private Long totalUserCount;
	/**
	 * 当天用户注册量
	 */
	private Long currUserCount;
	/**
	 * 累计发帖人数
	 */
	private Long totalPostUserCount;
	/**
	 * 当天发帖人数
	 */
	private Long currPostUserCount;
	/**
	 * 累计帖子数
	 */
	private Long totalPostCount;
	/**
	 * 当天帖子数
	 */
	private Long currPostCount;
	/**
	 * 累计帖子评论数
	 */
	private Long totalPostReplyCount;
	/**
	 * 当天帖子评论数
	 */
	private Long currPostReplyCount;
	/**
	 * 累计送出的感谢
	 */
	private Long totalPostLaudCount;
	/**
	 * 当天送出的感谢
	 */
	private Long currPostLaudCount;
	/**
	 * 当天登录用户数
	 */
	private Long currLoginUserCount;
	
	public Long getTotalUserCount() {
		return totalUserCount;
	}
	public void setTotalUserCount(Long totalUserCount) {
		this.totalUserCount = totalUserCount;
	}
	public Long getCurrUserCount() {
		return currUserCount;
	}
	public void setCurrUserCount(Long currUserCount) {
		this.currUserCount = currUserCount;
	}
	public Long getTotalPostUserCount() {
		return totalPostUserCount;
	}
	public void setTotalPostUserCount(Long totalPostUserCount) {
		this.totalPostUserCount = totalPostUserCount;
	}
	public Long getCurrPostUserCount() {
		return currPostUserCount;
	}
	public void setCurrPostUserCount(Long currPostUserCount) {
		this.currPostUserCount = currPostUserCount;
	}
	public Long getTotalPostCount() {
		return totalPostCount;
	}
	public void setTotalPostCount(Long totalPostCount) {
		this.totalPostCount = totalPostCount;
	}
	public Long getCurrPostCount() {
		return currPostCount;
	}
	public void setCurrPostCount(Long currPostCount) {
		this.currPostCount = currPostCount;
	}
	public Long getTotalPostReplyCount() {
		return totalPostReplyCount;
	}
	public void setTotalPostReplyCount(Long totalPostReplyCount) {
		this.totalPostReplyCount = totalPostReplyCount;
	}
	public Long getCurrPostReplyCount() {
		return currPostReplyCount;
	}
	public void setCurrPostReplyCount(Long currPostReplyCount) {
		this.currPostReplyCount = currPostReplyCount;
	}
	public Long getTotalPostLaudCount() {
		return totalPostLaudCount;
	}
	public void setTotalPostLaudCount(Long totalPostLaudCount) {
		this.totalPostLaudCount = totalPostLaudCount;
	}
	public Long getCurrPostLaudCount() {
		return currPostLaudCount;
	}
	public void setCurrPostLaudCount(Long currPostLaudCount) {
		this.currPostLaudCount = currPostLaudCount;
	}
	public Long getCurrLoginUserCount() {
		return currLoginUserCount;
	}
	public void setCurrLoginUserCount(Long currLoginUserCount) {
		this.currLoginUserCount = currLoginUserCount;
	}
	
}
