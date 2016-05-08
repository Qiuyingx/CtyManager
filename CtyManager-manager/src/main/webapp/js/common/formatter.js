/**
 * test
 */
function showTest(value) {
	alert(value);
}

/**
 *  [================tools===============]
 */

function formate(d){
	return d>9?d:'0'+d;
}

/**将好描述转化为日期时间类型*/
function mills2DateTime(value) {
	if(value == 0) {
		return "";
	}
	var d = new Date(value);
	
	var year=d.getFullYear();
	var day=d.getDate();
	var month=+d.getMonth()+1;
	var hour=d.getHours();
	var minute=d.getMinutes();
	var second=d.getSeconds();
	
	var f=year+"-"+formate(month)+"-"+formate(day)+" "+formate(hour)+":"+formate(minute)+":"+formate(second);
	//  dt.toLocaleString( ); 搜狗浏览器显示：Wed Aug 05 2015 15:08:00 GMT+0800 (中国标准时间)
	return f;
}

function dateFormatter(value) {
	if(value==null || value==""){
		return "";
	}
	return value.substring(0, 10);
}

function dateTimeFormatter(value) {
	return value.substring(0, 19);
}

/**
 *  [================[service]===============]
 */

/**
 *  [////[格式化]////]
 */

/**本地图片格式化 */
function imgFormatter(value) {
	return "<img src=\"../../" + value + "\" width=\"40px\" height=\"40px\" style=\"border:solid 1px #989898;margin-top:5px\" onerror=\"this.src='../../image/noImg.png'\"/>";
}

/**远程图片格式化 直接访问**/
function userImgFormatter(value) {
	return "<img src=\"" + imagePath+value + "\" width=\"40px\" height=\"40px\" style=\"border:solid 1px #989898;margin-top:5px\"  onerror=\"this.src='../../image/noImg.png'\"/>";
}

/**帮帮是否紧急状态*/
function isHarry(value) {
	if(value==1) {
		return "<img src=\"../../image/modules/harry.png\" height=\"28px\" />";
	}
	return "";
}

/**显示邻豆图标*/
function showLinDouIcon(value) {
	return value+"<img src=\"../../image/modules/dou.png\" height=\"20px\" />";
}

/**显示图标*/
function isAcceptComments(value) {
	if (value > 0) {
		return "<img src=\"../../image/modules/accept.png\" height=\"28px\" />";
	}
	return "";
}

/**表情库图片替换*/
function faceIcon(value) {
/*
 * 旧版表情符号对应关系
 * 	var faceIcon = {
			"[):]":"ee_1","[:D]":"ee_2","[;)]":"ee_3","[:-o]":"ee_4","[:p]":"ee_5",
			"[(H)]":"ee_6","[:@]":"ee_7","[:s]":"ee_8","[:$]":"ee_9","[:(]":"ee_10","[:'(]":"ee_11",
			"[:|]":"ee_12","[(a)]":"ee_13","[8o|]":"ee_14","[8-|]":"ee_15","[+o(]":"ee_16","[<o)]":"ee_17",
			"[|-)]":"ee_18","[*-)]":"ee_19","[:-#]":"ee_20","[:-*]":"ee_21","[^o)]":"ee_22","[8-)]":"ee_23",
			"[(|)]":"ee_24","[(u)]":"ee_25","[(S)]":"ee_26","[(*)]":"ee_27","[(#)]":"ee_28","[(R)]":"ee_29",
			"[({)]":"ee_30","[(})]":"ee_31","[(k)]":"ee_32","[(F)]":"ee_33","[(W)]":"ee_34","[(D)]":"ee_35"
	}*/
	var faceIcon = {
			"[):]":"ee_1","[:D]":"ee_2","[;)]":"ee_3","[:-o]":"ee_4","[:p]":"ee_5",
			"[(H)]":"ee_6","[:@]":"ee_7","[:s]":"ee_8","[:$]":"ee_9","[:(]":"ee_10","[:'(]":"ee_11",
			"[:|]":"ee_12","[(a)]":"ee_13","[8o|]":"ee_14","[8-|]":"ee_15","[+o(]":"ee_16","[<o)]":"ee_17",
			"[|-)]":"ee_18","[*-)]":"ee_19","[:-#]":"ee_20","[:-*]":"ee_21","[^o)]":"ee_22","[8-)]":"ee_23",
			"[(|)]":"ee_24","[(u)]":"ee_25","[(S)]":"ee_26","[(*)]":"ee_27","[(#)]":"ee_28","[(R)]":"ee_29",
			"[({)]":"ee_30","[(})]":"ee_31","[(k)]":"ee_32","[(F)]":"ee_33","[(W)]":"ee_34","[(D)]":"ee_35",
			
			"[):]":"ee_1","[:D]":"ee_2","[)]":"ee_3","[:-o]":"ee_4","[:p]":"ee_5","[(H)]":"ee_6","[:@]":"ee_7",
			"[:s]":"ee_8","[:$]":"ee_9","[:(]":"ee_10","[:'(]":"ee_11","[:|]:":"ee_12","[(a)]":"ee_13","[8o|]":"ee_14",
			"[8-|]":"ee_15","[+o(]":"ee_16","[<o)]":"ee_17","[|-)]":"ee_18","[*-)]":"ee_19","[:-#]":"ee_20","[:-*]":"ee_21",
			"[^o)]":"ee_22","[8-)]":"ee_23","[({)]":"ee_24","[(})]":"ee_25","[jie]":"ee_26","[jk]":"ee_27","[ll]":"ee_28",
			"[bz]":"ee_29","[shui]":"ee_30","[fd]":"ee_31","[zhm]":"ee_32","[yiw]":"ee_33","[xu]":"ee_34","[yun]":"ee_35",
			"[zhem]":"ee_36","[shuai]":"ee_37","[kl]":"ee_38","[qiao]":"ee_39","[zj]":"ee_40","[ch]":"ee_41","[gg]":"ee_42",
			"[gz]":"ee_43","[qd]":"ee_44","[zk]":"ee_45","[zhh]":"ee_46","[yhh]":"ee_47","[tuu]":"ee_48","[bs]":"ee_49",
			"[wq]":"ee_50","[kk]":"ee_51","[yx]":"ee_52","[tx]":"ee_53","[xia]":"ee_54","[kel]":"ee_55","[cd]":"ee_56",
			"[xig]":"ee_57","[pj]":"ee_58","[lq]":"ee_59","[pp]":"ee_60","[kf]":"ee_61","[fan]":"ee_62","[zt]":"ee_63",
			"[(F)]":"ee_64","[(W)]":"ee_65","[(k)]":"ee_66","[(|)]":"ee_67","[(u)]":"ee_68","[dg]":"ee_69","[(*)]":"ee_70",
			"[zhd]":"ee_71","[dao]":"ee_72","[zq]":"ee_73","[pch]":"ee_74","[bb]":"ee_75","[(S)]":"ee_76","[(#)]":"ee_77",
			"[lw]":"ee_78","[(R)]":"ee_79","[yb]":"ee_80","[(D)]":"ee_81","[ruo]":"ee_82","[ws]":"ee_83","[shl]":"ee_84",
			"[bq]":"ee_85","[gy]":"ee_86","[qt]":"ee_87","[cj]":"ee_88","[aini]":"ee_89","[NO]":"ee_90","[OK]":"ee_91",
			"[aiq]":"ee_92","[fw]":"ee_93","[tiao]":"ee_94","[fad]":"ee_95","[oh]":"ee_96","[zhq]":"ee_97","[kt]":"ee_98",
			"[ht]":"ee_99","[tsh]":"ee_100","[hsh]":"ee_101","[jd]":"ee_102","[jw]":"ee_103","[xw]":"ee_104",
			"[ztj]":"ee_105","[ytj]":"ee_106"
	}
	var reg = /\[.+?\]/g;  
	value = (value+"").replace(reg,function(a,b){  
		return "<img src=\"../../image/modules/faceIcon/"+ faceIcon[a] + ".png\" height=\"13px\" />";  

	});
	return value;
}

/**
 *  [////[标签]////]
 */

/**帮帮标签**/
function tagFormatter(value) {
	switch (value) {
	case 1:
		return "<span style=\"color:#96ca63\">#二手</span>";
	case 2:
		return "<span style=\"color:#96ca63\">#租售</span>";
	case 3:
		return "<span style=\"color:#96ca63\">#丢失</span>";
	case 4:
		return "<span style=\"color:#96ca63\">#询问</span>";
	case 5:
		return "<span style=\"color:#96ca63\">#借东西</span>";
	case 6:
		return "<span style=\"color:#96ca63\">#其他</span>";
	default:
		return "<span style=\"color:#96ca63\">未知</span>";
	}
}

/**帖子标签*/
function topicFormatter(value) {
	switch (value) {
	case 1:
		return "<span style=\"color:#96ca63\">#旅游摄影</span>";
	case 2:
		return "<span style=\"color:#96ca63\">#美食烘培</span>";
	case 3:
		return "<span style=\"color:#96ca63\">#运动健身</span>";
	case 4:
		return "<span style=\"color:#96ca63\">#亲子教育</span>";
	case 5:
		return "<span style=\"color:#96ca63\">#小区新闻</span>";
	case 6:
		return "<span style=\"color:#96ca63\">#丢失发现</span>";
	case 7:
		return "<span style=\"color:#96ca63\">#便民信息</span>";
	case 8:
		return "<span style=\"color:#96ca63\">#其他</span>";
	default:
		return "<span style=\"color:#96ca63\">未知</span>";
	}
}

/**举报贴标签*/
function tagReportPost(value) {
	switch (value) {
	case 1:
		return "<span style=\"color:#96ca63\">#垃圾营销</span>";
	case 2:
		return "<span style=\"color:#96ca63\">#淫秽色情</span>";
	case 3:
		return "<span style=\"color:#96ca63\">#不实信息</span>";
	case 4:
		return "<span style=\"color:#96ca63\">#敏感信息</span>";
	case 5:
		return "<span style=\"color:#96ca63\">#抄袭内容</span>";
	case 6:
		return "<span style=\"color:#96ca63\">#骚扰我</span>";
	case 7:
		return "<span style=\"color:#96ca63\">#虚假中奖</span>";
	default:
		return "<span style=\"color:#96ca63\">未知</span>";
	}
}

/**邀约类型*/
function inviType(value) {
	switch (value) {
	case 1:
		return "<span style=\"color:#96ca63\">#运动健身</span>";
	case 2:
		return "<span style=\"color:#96ca63\">#亲子活动</span>";
	case 3:
		return "<span style=\"color:#96ca63\">#社区聚会</span>";
	case 4:
		return "<span style=\"color:#96ca63\">#拼车</span>";
	case 5:
		return "<span style=\"color:#96ca63\">#户外活动</span>";
	default:
		return "<span style=\"color:#96ca63\">#未知</span>";
	}
}

/**用户职业**/
function careerId(value) {
	switch (value) {
	case 1:
		return "IT(计算机/互联网/通信)";
	case 2:
		return "制造(生产/工艺/制造)";
	case 3:
		return "医疗(医疗/护理/制药)";
	case 4:
		return "金融(金融/银行/投资/保险)";
	case 5:
		return "商业(销售/公关/媒体/服务业/个体户)";
	case 6:
		return "文化(文化/广告/传媒/文化)";
	case 7:
		return "艺术(娱乐/艺术/表演)";
	case 8:
		return "法律(娱乐/艺术/表演)";
	case 9:
		return "教育(教育/培训)";
	case 10:
		return "市政(公务员/行政/事业单位)";
	case 11:
		return "房地产(房地产/设计/工程/建筑)";
	case 12:
		return "公益类(NGO/环保/公益)";
	case 13:
		return "自由职业(全职妈妈/个人工作室/手工艺人)";
	default:
		return "未知";
	} 
}

/**开店类别标签*/
function tagTrain(value) {
	value = parseInt(value);
	switch (value) {
	case 1:
		return "钢琴";
	case 2:
		return "美术";
	case 3:
		return "舞蹈";
	case 4:
		return "英语";
	case 5:
		return "瑜伽";
	case 6:
		return "烘培";
	case 7:
		return "摄影";
	case 8:
		return "咖啡";
	case 9:
		return "钢琴";
	case 10:
		return "手工DIY";
	case 11:
		return "花艺";
	case 12:
		return "茶艺";
	case 13:
		return "陶瓷";
	case 14:
		return "击剑";
	case 15:
		return "跆拳道";
	case 16:
		return "射箭";
	case 17:
		return "健身";
	default:
		return "未知";
	}
}

/**
 *  [////[状态]////]
 */

/**是否启用状态*/
function statusFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:red\">禁用</span>";
	case 1:
		return "<span style=\"color:blue\">启用</span>";
	default:
		return "未知状态";
	}
}

/**文章状态*/
function contentStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "草稿";
	case 1:
		return "发布";
	case 2:
		return "删除";
	}
}

function yesOrNoFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:red\">否</span>";
	case 1:
		return "<span style=\"color:blue\">是</span>";
	default:
		return "未知";
	}
}

function mainModeFormatter(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:red\">否</span>";
	case 1:
		return "<span style=\"color:blue\">是</span>";
	default:
		return "未知模式";
	}
}

/**活动是否可报名状态*/
function isActSub(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "可报名";
	case 1:
		return "不可报名";
	default:
		return "未知";
	}
}

/**邻豆商品状态*/
function goodsStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "在售";
	case 1:
		return "已下架";
	default:
		return "未知";
	}
}

/**用户社区验证--验证状态*/
function valiCourtyardStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "<span style=\"color:blue\">提交验证</span>";
	case 1:
		return "<span style=\"color:green\">认证成功</span>";
	case 2:
		return "<span style=\"color:red\">认证失败</span>";
	case 4:
		return "还未申请";
	default:
		return "未知";
	}
}

/**紧急求助 审核状态*/
function valiPostStatus(value) {
	switch (value) {
	case 0:
		return "<span style=\"color:blue\">提交审核</span>";
	case 1:
		return "<span style=\"color:green\">通过审核</span>";
	case 2:
		return "<span style=\"color:red\">拒绝通过</span>";
	default:
		return "未知";
	}
}

/**开店申请审核状态*/
function valiTrainOpenStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "<span style=\"color:blue\">已申请</span>";
	case 2:
		return "<span style=\"color:green\">已通过</span>";
	case 1:
		return "<span style=\"color:red\">未通过</span>";
	default:
		return "未知";
	}
}

/**店铺状态*/
function trainInfoStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "<span style=\"color:green\">未完善资料</span>";
	case 1:
		return "<span style=\"color:blue\">已启用</span>";
	case 2:
		return "<span style=\"color:red\">已禁用</span>";
	default:
		return "未知";
	}
}

/**技能认证状态*/
function userStarStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "<span style=\"color:green\">已申请</span>";
	case 1:
		return "<span style=\"color:blue\">已通过</span>";
	case 2:
		return "<span style=\"color:red\">未通过</span>";
	default:
		return "未知";
	}
}

/**课程状态*/
function courseStatus(value){
	value = parseInt(value);
	 switch (value) {
	 case 0:
		 return "<span style=\"color:green\">发布</span>";
	 case 1:
		 return "<span style=\"color:red\">草稿</span>";
	 default:
		 return "未知";
   }
} 

/**入驻申请商户账号绑定状态*/
function isBindName(value) {
	if(value <= 0) {
		 return "<span style=\"color:red\">未绑定</span>";
	}else{
		 return "<span style=\"color:green\">已绑定</span>";
	}
}

/**入驻申请渠道*/
function sourceTrainStatus(value) {
	if(value == 1) {
		return "APP申请";
	}else if(value == 2) {
		return "网站申请";
	}else if(value == 3) {
		return "后台入驻";
	}else{
		return "未知";
	}
}

/**课程类型，活动开始时间不为空则为活动课程*/
function courseTypeStatus(value) {
	if(value == 2) {
		return "活动课程";
	}else{
		return "普通课程";
	}
}

/**课程分类*/
function classTypeStatus(value) {
	value = parseInt(value);
	switch (value) {
	case 1:
		return "早教";
	case 2:
		return "音乐";
	case 3:
		return "舞蹈";
	case 4:
		return "游泳";
	case 5:
		return "画画";
	case 6:
		return "钢琴";
	case 7:
		return "瑜伽";
	case 8:
		return "烘焙";
	case 9:
		return "手工DIY";
	case 10:
		return "花艺";
	case 11:
		return "摄影";
	case 12:
		return "多肉";
	default:
		return "未知";
	}
}

/**
 * 退款类型
 */
function refundTypeText(value){
	value = parseInt(value);
	switch (value) {
	case 0:
		return "未消费随时退";
	case 1:
		return "不可退";
	case 2:
		return "提前几天可退";
	default:
		return "未知";
	}
}

/**
 *  [////[替换判断]////]
 */

/**评论对象 如果为空 则为正对发帖人的评论*/
function targetName(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
		return "针对帖子评论";
	}
	return value;
}

/**评论对象 如果为空 则正对活动本身评论*/
function targetActName(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
		return "针对活动评论";
	}
	return value;
}

/**是否针对所有社区验证*/
function isAllCourtyardVali(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
		return "<span style=\"color:red;\">针对所有社区</span>";
	}
	return value;
}

/**是否针对所有用户*/
function isAllNickNameVali(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
		return "<span style=\"color:red;\">针对所有用户</span>";
	}
	return value;
}

/**是否已经推荐Banner*/
function toBannerStatus(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
	}else{
		return "已推荐";
	}
}

/**是否已经推荐列表*/
function toListStatus(value) {
	if(typeof (value) == "undefined" || value == "" || value == null) {
		
	}else{
		return "已推荐";
	}
}

/**
 *  [////[类型]////]
 */

/**帖子类型 2帮帮 3 话题**/
function contentType(value) {
	switch (value) {
	case 2: 
		return "<span style=\"color:red\">《帮帮》</span>";
	case 3:
		return "<span style=\"color:blue\">《话题》</span>";
	default:
		return "未知";
	}
}

/**注册平台*/
function platform(value) {
	switch (value) {
	case 1:
		return "Android";
	case 2:
		return "Ios";
	default:
		return "未知";
	}
}

/**用户性别*/
function gender(value) {
	switch (value) {
	case 1:
		return "男";
	case 2:
		return "女";
	default:
		return "未知";
	}
}

/**用户社区验证--验证方式*/
function valiCourtyardType(value) {
	switch (value) {
	case 0:
		return "手机验证";
	case 1:
		return "图片验证";
	case 2:
		return "code礼包";
	case 3:
		return "邀请码验证";
	case 4:
		return "位置认证";
	default:
		return "未知";
	}
}

/**所属城市*/
function cityFormatter(value) {
	value = parseInt(value);
	switch (value) {
	case 0:
		return "所有城市";
	case 1:
		return "成都";
	default:
		return "未知";
	}
}

/**性别*/
function sexFormatter(value){
	switch (value) {
	case 1:
		 return "男";
	case 2:
		 return "女";
	default:
		 return "未知";
	}
}
/**
 * 银行的格式化
 * @param value
 * @returns {String}
 */
function banknameformatter(value){
	switch (value) {
	case 1:
		 return "工商银行";
	case 2:
		 return "建设银行";
	case 3:
		 return "招商银行";
	case 4:
		 return "农业银行";
	case 5:
		 return "平安银行";
	default:
		 return "未知";
	}
}
/**
 * 银行账户类型
 * @param value
 * @returns {String}
 */
function accounttypeformatter(value){
	switch (value) {
	case 1:
		 return "对私";
	case 2:
		 return "对公";
	default:
		 return "未知";
	}
}

/**
 * 结算周期
 * @param value
 * @returns {String}
 */
function periodformatter(value){
	switch (value) {
	case 1:
		 return "按周计算";
	case 2:
		 return "按月计算";
	default:
		 return "未知";
	}
}

/**
 * 判断是否结算
 * @param value
 * @returns {String}
 */
function cutoff(value){
	if(value!=0){
		return '已结算';
	}else{
		return '未结算';
	}
}