package com.yard.manager.modules.user.entity;

/**
 * 用户基础信息
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午2:54:36
 * @version : 1.0
 */
public class UserEntity {
	/**
	 * 用户ID
	 */
	private Integer id;
	/**
	 * 用户电话
	 */
	private String tel;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 职业ID
	 */
	private Integer careerId;
	/**
	 * 所属院子
	 */
	private Integer courtyardId;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 头像地址
	 */
	private String headIcon;
	/**
	 * 邀请码
	 */
	private Integer inviteCode;
	/**
	 * 最后登录时间
	 */
	private long lastLoginTime;
	/**
	 * 注册平台
	 */
	private Integer platform;
	/**
	 * 注册时间
	 */
	private long registerTime;
	/**
	 * 禁用时间
	 */
	private long banningTime;
	/**
	 * 经验
	 */
	private Integer exp;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 社区验证状态  值为4表示未提交验证 ；否则表示认证结果
	 */
	private Integer yardsValiStatus;
	/**
	 * 认证方式  验证方式 0 有合作的手机验证 1 无合作的上传图片 2 无合作的code礼包 3 邀请码验证
	 */
	private Integer validateType;
	/**
	 * 认证凭证
	 * @return
	 */
	private String append;
	/**
	 * 认证时间
	 */
	private long valiTime;
	/**
	 * 验证社区
	 */
	private String valiYards;
	/**
	 * 社区验证审核备注
	 */
	private String valiRemark;
	/**
	 * 认证ID
	 */
	private Integer valiId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getCareerId() {
		return careerId;
	}
	public void setCareerId(Integer careerId) {
		this.careerId = careerId;
	}
	public Integer getCourtyardId() {
		return courtyardId;
	}
	public void setCourtyardId(Integer courtyardId) {
		this.courtyardId = courtyardId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public Integer getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(Integer inviteCode) {
		this.inviteCode = inviteCode;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	
	public long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}
	public long getBanningTime() {
		return banningTime;
	}
	public void setBanningTime(long banningTime) {
		this.banningTime = banningTime;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getYardsValiStatus() {
		return yardsValiStatus;
	}
	public void setYardsValiStatus(Integer yardsValiStatus) {
		this.yardsValiStatus = yardsValiStatus;
	}
	public Integer getValidateType() {
		return validateType;
	}
	public void setValidateType(Integer validateType) {
		this.validateType = validateType;
	}
	public String getAppend() {
		return append;
	}
	public void setAppend(String append) {
		this.append = append;
	}
	public long getValiTime() {
		return valiTime;
	}
	public void setValiTime(long valiTime) {
		this.valiTime = valiTime;
	}
	public String getValiYards() {
		return valiYards;
	}
	public void setValiYards(String valiYards) {
		this.valiYards = valiYards;
	}
	public String getValiRemark() {
		return valiRemark;
	}
	public void setValiRemark(String valiRemark) {
		this.valiRemark = valiRemark;
	}
	public Integer getValiId() {
		return valiId;
	}
	public void setValiId(Integer valiId) {
		this.valiId = valiId;
	}
	
}
