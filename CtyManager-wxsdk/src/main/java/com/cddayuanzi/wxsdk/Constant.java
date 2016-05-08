package com.cddayuanzi.wxsdk;

/**
 * 微信接口地址静态变量
 * 
 * @author Administrator
 *
 */
public class Constant {
	public final static String PARAM_APPID = "APPID";
	public final static String PARAM_APPSECRET = "APPSECRET";
	public final static String PARAM_ACCESS_TOKEN = "ACCESS_TOKEN";
	public final static String PARAM_OPENID = "OPENID";
	public final static String PARAM_TYPE = "TYPE";
	public final static String PARAM_MEDIA_ID = "MEDIA_ID";
	public final static String PARAM_CODE = "CODE";
	public final static String PARAM_SECRET = "SECRET";

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// JSAPI_TICKET获取
	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/******************************************** 菜单操作 *****************************************/
	// 菜单创建（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 清除菜单
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/******************************************** 菜单操作 *****************************************/

	/******************************************** 多媒体操作 ***************************************/
	// 上传永久多媒体文件
	public final static String UPLOAD_MEDIA_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	// 上传临时多媒体文件（3天）
	public final static String UPLOAD_MEDIA_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// 删除永久多媒体文件
	public final static String DELETE_MEDIA_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	// 新增永久图文素材
	public final static String UPLOAD_NEWS_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	// 新增临时图文素材
	public final static String UPLOAD_NEWS_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	// 下载多媒体素材
	public final static String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	/******************************************** 多媒体操作 ***************************************/

	/******************************************** 发送消息 *****************************************/
	// 群发消息
	public static final String MSG_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	// 发送客服消息接口
	public static final String msg_server_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 预览
	public static final String MSG_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	/******************************************** 发送消息 *****************************************/

	/**
	 * OAuth2.0网页授权
	 */
	// 用户同意授权，获取code
	public static final String get_oauth2_code_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	// 通过code获取授权access_token
	public static final String get_accessToken_byCode_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 刷新access_token
	public static final String refresh_accessToken_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	// 拉取用户信息(需scope为 snsapi_userinfo)
	public static final String get_userInfo_byToken_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/******************************************** 微信用户管理 *****************************************/
	// 获取关注者列表
	public static final String get_allUser_list_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	// 查询所有分组
	public static final String get_allGroup_list_url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	// 创建分组
	public static final String create_group_url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	// 移动分组
	public static final String move_group_url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	// 获取用户所在组
	public static final String get_user_group_url = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	// 获取单个用户信息
	public static final String USER_GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 通过code换取网页授权access_token
	public static final String USER_GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	/******************************************** 微信用户管理 *****************************************/
	
	/******************************************** 微信消息类型 *****************************************/
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_IMAGE = "image";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_VIDEO = "video";
	public static final String MSGTYPE_SHORTVIDEO = "shortvideo"; // 小视屏
	public static final String MSGTYPE_LOCATION = "location"; // 位置信息
	public static final String MSGTYPE_LINK = "link"; // 链接
	public static final String MSGTYPE_EVENT = "event"; // 事件
	/******************************************** 微信消息类型 *****************************************/
	
	/******************************************** 微信事件类型 *****************************************/
	public static final String EVENTTYPE_SUBSCRIBE = "subscribe"; // 关注
	public static final String EVENTTYPE_UNSUBSCRIBE = "unsubscribe"; // 取消关注
	public static final String EVENTTYPE_SCAN = "SCAN"; // 已经关注
	public static final String EVENTTYPE_LOCATION = "LOCATION"; // 上报地理位置事件
	public static final String EVENTTYPE_CLICK = "CLICK"; // 菜单点击
	public static final String EVENTTYPE_VIEW = "VIEW"; // 菜单点击跳转连接
	public static final String EVENTTYPE_SCANCODE_PUSH = "scancode_push"; // 扫码推事件的事件推送
	public static final String EVENTTYPE_SCANCODE_WAITMSG = "scancode_waitmsg"; // 扫码推事件且弹出“消息接收中”提示框的事件推送
	public static final String EVENTTYPE_PIC_SYSPHOTO = "pic_sysphoto"; // 弹出系统拍照发图的事件推送
	public static final String EVENTTYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album"; // 弹出拍照或者相册发图的事件推送
	public static final String EVENTTYPE_PIC_WEIXIN = "pic_weixin"; // 弹出微信相册发图器的事件推送
	public static final String EVENTTYPE_LOCATION_SELECT = "location_select"; // 弹出地理位置选择器的事件推送
	/******************************************** 微信事件类型 *****************************************/
}
