package com.yard.manager.base.constant;

import com.yard.manager.platform.entity.User;

/**
 * 系统常量（全局）
 * 
 * @author jiangbo
 *
 */
public class ManagerConstant {
	/**
	 * 启用状态
	 */
	public static final short ENABLED = 1;// 启用
	/**
	 * 禁用状态
	 */
	public static final short DISABLED = 0;// 禁用

	/**
	 * 分组类型
	 * 
	 */
	// 文本分组
	public static final short BASE_GROUP_TEXT = 1;
	// 命令配置分组
	public static final short BASE_GROUP_AUTOANSWER = 7;

	// 是否是方法菜单
	public static final short ISFUNCTION_YES = 1;
	public static final short ISFUNCTION_NO = 0;

	// JSON字段名称
	public static final String JSON_COL_ISSUCCESS = "isSuccess";
	public static final String JSON_COL_MSG = "msg";
	public static final String JSON_COL_IMGURL = "imgUrl";
	public static final String JSON_COL_TOTAL = "total";
	public static final String JSON_COL_ROWS = "rows";

	// 系统内置用户
	public static final String SYSTEM_USER = "ctyAdmin";//角色管理员
	public static final String SYSTEM_SUPPER = "ctySupper";//权限管理员
	public static final String SYSTEM_PASSWORD = "sysAdmin@";
	public static final String SYSTEM_PERMISSION = "99%";
	
	//分组固定ID
	public static final String GROUP_SUPPER = "ebdd094f1b5b48bea4593bed01bb9115"; //超级管理组
	public static final String GROUP_TEST = "d2a83773df2142138d9653f083ee9060";//120测试分组
	public static final String GROUP_AREA = "a8774b792f994a8c81e2b812794cf69e";//片区经理组
	public static final String GROUP_INNER = "6499717b976b4f559b747921cf17696d"; //内置账号组

	// 系统内置用户属性
	public static User systemUser = new User() {
		{
			setSysUserId(SYSTEM_USER);
			setSysUserNo(SYSTEM_USER);
			setStatus(1);
			setSysUserName("系统内置账户");
			setCreateTime("1970-01-01");
		}
	};

	// 存用户实体的session字段
	public static final String SESSION_USER = "loginUser";

	// 微信菜单类型
	public static final int WEIXIN_MENUTYPE_PUSH = 1;
	public static final int WEIXIN_MENUTYPE_LINK = 2;

	// 签到相关
	public static final int SIGN_STATIC_999 = -1; // 签到失败
	public static final int SIGN_STATIC_0 = 0; // 签到成功 且 成功生成签到记录
	public static final int SIGN_STATIC_1 = 1; // 重复签到
	public static final int SIGN_STATIC_2 = 2; // 未签到
	public static final int SIGN_STATIC_3 = 3; // 签到成功 且 生成签到记录失败

	// 内置菜单
	public static final String SYSMENU_SIGN = "SYSMENU_SIGN";// 签到
	public static final String SYSMENU_JOIN = "SYSMENU_JOIN";// 入会
	public static final String SYSMENU_VCARD = "SYSMENU_VCARD";// 微贺卡
	
	// 群发任务状态
	public static final int TASK_STATUS_1 = 1; // 未发送
	public static final int TASK_STATUS_2 = 2; // 已经发出（未反馈状态）
	public static final int TASK_STATUS_3 = 3; // 发送成功
	public static final int TASK_STATUS_4 = 4; // 发送失败
	public static final int TASK_STATUS_5 = 5; // 取消发送
	
	// 举报贴状态
	public static final int REPORT_STATUS_0 = 0;// 举报
	public static final int REPORT_STATUS_1 = 1;// 处理并删除
	
	// 技能认证状态
	public static final int USER_STAR_STATUS_0 = 0; // 已申请
	public static final int USER_STAR_STATUS_1 = 1; // 已通过
	public static final int USER_STAR_STATUS_2 = 2; // 未通过
	
	// 帖子类型
	public static final int CONTENT_TYPE_1 = 1;// 邀约
	public static final int CONTENT_TYPE_2 = 2;// 帮帮
	public static final int CONTENT_TYPE_3 = 3;// 话题
	public static final int CONTENT_TYPE_4 = 4;// 活动
	public static final int CONTENT_TYPE_5 = 5;// 专题
	public static final int CONTENT_TYPE_6 = 6;// 帖子评论
	public static final int CONTENT_TYPE_7 = 7;// 活动评论
	public static final int CONTENT_TYPE_8 = 8;// 专题评论
	
	// 推送类型
	public static final int NOTICE_NORMAL = 0;// 普通通知
	public static final int NOTICE_RADIO = 1;// 社区公告
	public static final int NOTICE_HELP = 3;// 求助通知
	public static final int NOTICE_ACT = 4;// 官方活动通知
	public static final int NOTICE_VALIDATE = 7; //验证通知
	
	// 设备类型
	public static final Integer DEVICE_ANDROID = 1;// android
	public static final Integer DEVICE_IOS = 2;// ios
	
	//消息未读数
	public static final String NOTICE_MSG_COUNT = "MSG_COUNT"; //消息未读数
	// 消息种类
	public static final String NOT_READ_COUNT = "NOTICE";

	// =========================  推送文字提示       ============================================
	public static final String SHOW_PUSH_TEXT_ACT = "【活动通知】${actTitle}"; // 活动发布通知  ${actTitle} 活动的标题
	public static final String SHOW_PUSH_TEXT_ACT_INFO = "【新闻资讯】${actInfoTitle}"; // 活动资讯通知 ${actInfoTitle} 活动资讯标题
	public static final String SHOW_PUSH_TEXT_RADIO = "【社区公告】${radioContent}"; // 社区公告通知 ${radioContent} 公告内容（20字以内）
	public static final String SHOW_PUSH_TEXT_MASS = "【系统通知】${massContent}"; // 群发通知 ${massContent} 群发内容 （20字以内）
	public static final String SHOW_PUSH_TEXT_SUBJECT = "【邻聚专题】${subjectTitle}"; // 专题发布通知 ${subjectTitle} 专题标题
	public static final String SHOW_PUSH_TEXT_HELP = "你收到一条新的紧急求助通知，看看你是否能帮上忙？"; //后台审核紧急求助，文字推送提醒
	public static final String SHOW_PUSH_TEXT_YARDS_SUCCESS = "你的住址认证成功，请尽快发个话题给大家打个招呼吧"; // 社区审核验证通过
	public static final String SHOW_PUSH_TEXT_YARDS_FAILED = "你的住址认证资料被拒绝，请重新到”我的社区“提交认证资料"; // 社区审核验证不通过
	public static final String SHOW_PUSH_TEXT_VALI_TRAIN_SUCCESS = "你好！恭喜你，你的开店申请已经审核通过。"; // 开店申请通过审核
	public static final String SHOW_PUSH_TEXT_VALI_TRAIN_FAILED = "你好！你的开店申请未通过审核，点击查看具体原因。"; // 开店申请审核不通过
	
	// =========================  文字提示       ============================================
	public static final String SHOW_RECORD_TEXT_ACT = "平台发起了“${actTitle}”的活动，大家赶快积极参与中"; // 活动内容发布   ${actTitle} 活动的标题
	public static final String SHOW_RECORD_TEXT_ACT_INFO = "平台发起了“${actInfoTitle}”的资讯，邻居们快来围观吧！"; // 资讯发布内容 ${actInfoTitle} 活动资讯标题
	public static final String SHOW_RECORD_TEXT_RADIO = "${radioContent}"; // 公告内容 ${radioContent} 公告内容
	public static final String SHOW_RECORD_TEXT_MASS = "${massContent}"; // 系统通知内容  ${massContent} 群发内容 
	public static final String SHOW_RECORD_TEXT_SUBJECT = "平台发布了“${subjectTitle}”的专题，邻居们快来围观吧！"; // 专题发布内容  ${subjectTitle} 专题标题
	public static final String SHOW_RECORD_TEXT_HELP = "${courtyardName}社区的“${nickName}”发出紧急求助：“${helpContent}”"; // ${courtyardName}社区名称 帮帮内容 ${nickName} 帮帮发起人昵称  ${helpContent} 帮帮内容
	public static final String SHOW_RECORD_TEXT_YARDS_SUCCESS = "恭喜你！验证成功，请尽快发个话题给大家打个招呼吧。获得${expCount}个经验值，${lindouCount}个邻豆"; // 社区审核验证通过 ${expCount} 赠送经验数量  ${lindouCount} 赠送邻豆数量
	public static final String SHOW_RECORD_TEXT_YARDS_FAILED = "你的住址认证资料被拒绝，请重新到”我的社区“提交认证资料"; // 社区审核验证不通过
	public static final String SHOW_RECORD_TEXT_VALI_TRAIN_SUCCESS = "你好！恭喜你，你的开店申请已经审核通过，请点击查看详情完善后续步骤。"; // 开店申请通过审核
	public static final String SHOW_RECORD_TEXT_VALI_TRAIN_FAILED = "你好！你的开店申请未通过审核，具体原因是：${detailReasons}"; // 开店申请审核不通过 ${detailReasons} 具体原因（后台填写）

	// =========================  文字提示       ============================================
	public static final String SHOW_POST_HELLO_TITLE = "大家好"; // 认证发帖say Hello 标题
	public static final String SHOW_POST_HELLO_CONTENT = "${courtyardName}的邻居们，大家好！我叫${nickName},很高兴认识大家。"; // 认证通过 自动发帖

	// =========================  开店审核状态      ============================================
	public static final String VALI_YARDS_USER_SUBMIT = "0"; // 提交审核
	public static final String VALI_YARDS_USER_PASS = "1";	// 审核通过
	public static final String VALI_YARDS_USER_NOTPASS = "2"; // 审核不通过

	// =========================  商户中心      ============================================

	// 店铺状态
	public static final int STATUS_TRAIN_INFO_DEFAULT = 0; // 具有开店资格，未完善店铺资料
	public static final int STATUS_TRAIN_INFO_UP = 1; // 已经开通
	public static final int STATUS_TRAIN_INFO_DOWN = 2; // 店铺下线
	
	// 后台账号
	public static final String TRAIN_INFO_DEFAULT_PWD = "111111"; // 后台登录初始密码
	public static final String TRAIN_INFO_SYS_GROUP_ID = "031c3e1560604a8ab85f8960cff437e4"; // 后台账号所属分组ID
	public static final String TRAIN_INFO_SYS_CREATOR_ID = "3634"; // 后台账号创建者ID
	public static final String TRAIN_INFO_SYS_CREATOR_NAME = "ctySupper"; // 创建者名称
}
