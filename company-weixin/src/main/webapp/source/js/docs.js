// 倒计时初始时间
var time = 61;
// 是否需要自动登录
var isAutoLogin = true;
// 课程规格
var courseItems = new Array();
// 公众平台ID
var wxAppId = "wx87b4438a2a8abff3";
// 服务器访问地址
var demainUrl = "http%3a%2f%2fwww.cddayuanzi.com%3a9002%2f";

// 当前地图导航位置
var baiduMapPath = "";

// 分页加载

// 加载flag
var loading = false;
// 当前页号
var pageNum = 1;
// 每次加载数量
var rowsNum = 8;
// 当次加载数据条数（用户判断是否进行下一页加载）
var dataLength = 10;

// 保存当前列表第一个数据的ID
var beforeId = 0;

// cookie操作
function setCookie(name,value) 
{ 
    var Days = 30; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
}

function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]); 
    else 
        return null; 
}

function delCookie(name) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) 
        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 

// 全局方法

	//获取用户当前经度
	function getLongitude() {
		var long = getCookie("location.longitude");
		if(isNull(long)) {
			long = 0;
		}
		return long;
	}
	
	// 获取用户当前纬度
	function getLatitude() {
		var lat = getCookie("location.latitude");
		if(isNull(lat)) {
			lat = 0;
		}
		return lat;
	}

// 跳转
	// 延迟跳转
	function timeHref(path) {
		setTimeout("forward('"+path+"')",500);
	}

	// 倒计时
	function timeCount() {
		if(time <= 0) {
			// 倒计时结束
			$("#label_time_count").removeClass("bk_disabled");
			$("#register_submit_btn_div").removeClass("bk_disabled");
			$("#label_time_count").text("获取验证码");
			return;
		}else{
			// 继续倒计时
			time--;
			$("#label_time_count").text(time+"s");
			setTimeout("timeCount()",1000);
		}
	}
	
	// 跳转到百度导航
	function forwardGuide(mapPath) {
		if(is_weixin) {
			$.showPreloader("定位中，请稍后...");
			wxGetLocation();
		}else{
			$.toast("手机浏览器，暂不支持定位，请手动搜索乘车方案。");
			timeHref("http://map.baidu.com/mobile/webapp/index/index#index/index/qt=cur&wd=%E6%88%90%E9%83%BD%E5%B8%82&from=maponline&tn=m01&ie=utf-8=utf-8/tab=line");
		}
	}
	
	// 跳转地址
	function forward(path) {
		$.showIndicator();
		window.location.href = path;
	}
	
	// 登录验证
	function valiLogin(path) {
		var userId = getCookie("user.userId");
		if(isNull(userId)) {
			$.toast("请登录！");
			timeHref("enter_login");
			return false;
		}else{
			if(isNull(getCookie("user.nickname")) || isNull(getCookie("user.headIcon"))) {
				$.toast("请完善资料！");
				timeHref("enter_userhome");
			}else{
				window.location.href=path;
			}
		}
	}
	
	function valiLoginOpenId(path) {
		var userId = getCookie("user.userId");
		if(isNull(userId)) {
			$.toast("请登录！");
			timeHref("enter_login");
			return false;
		}else{
			if(isNull(getCookie("user.nickname")) || isNull(getCookie("user.headIcon"))) {
				$.toast("请完善资料！");
				timeHref("enter_userhome");
			}else{
				window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxAppId +
						"&redirect_uri="+demainUrl+"company-weixin%2fnormal%2f"+path +
						"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
			}
		}
	}
	
	// 拨打电话
	function click_call_phone(tel) {
		window.location.href="tel://"+tel;
	}
	
	// 课程详情
	function click_course_view(id) {
		window.location.href="course_view?courseId="+id;
	}
	
	// 专题资讯详情
	function click_content_view(id, contentType) {
		window.location.href="content_view?contentId="+id+"&contentType="+contentType;
	}
	
	// 学堂主页
	function click_train_view(id) {
		window.location.href="train_view?trainId="+id;
	}
	
	// 往期专题
	function click_history_content() {
		window.location.href="history_content";
	}
	
	// 通过课程分类ID查询课程
	function click_traintype_id(id, name) {
		window.location.href="course_search_list?trainTypeId="+id;
	}
	
	// 我的订单
	function click_order_owner() {
		// 是否已经授权判断
		var openId = getCookie("user.openId");
		if(isNull(openId)) {
			// 是否登录判断
			valiLoginOpenId("order_owner_open");
		}else{
			// 是否登录判断
			valiLogin("order_owner");
		}
	}
	
	// 我的店铺
	function click_shop_index() {
		valiLogin("shop_index");
	}
	
	// 跳转至完善资料
	function click_forward_userhome() {
		$.toast("请完善资料！");
		timeHref("enter_userhome");
	}
	
	// 订单详情
	function click_order_info(id, forwardFunction){
		window.location.href="order_pay_status?orderId="+id+"&forwardFunction="+forwardFunction;
	}
	
	// 消费验证成功，进入订单详情核销 remark：1 表示核销
	function click_order_info_use(id, forwardFunction){
		window.location.href="order_pay_status?orderId="+id+"&forwardFunction="+forwardFunction+"&remark=1";
	}
	
	// 进入商户主页
	function click_enter_shop_home() {
		
	}
	
	// 进入订单申请退款
	function click_order_refund(orderNo, price) {
		window.location.href="order_refund?orderNo="+orderNo+"&price="+price;
	}
	
	// 进入订单填写
	function click_order_write(id) {
		var openId = getCookie("user.openId");
		if(isNull(openId)) {
			$.toast("请前往微信公众平台【觅趣小镇】进行购买！");
			timeHref("index");
			return false;
		}
		window.location.href="order_write?courseId="+id;
	}
	
	// 进入订单填写，同时获取openId
	function click_order_write_openId(id) {
		var path = "order_write_openid?courseId="+id;
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxAppId +
		"&redirect_uri="+demainUrl+"company-weixin%2fnormal%2f"+path +
		"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
	}
	
	// 订单完成
	function click_order_down(id) {
		window.location.href="order_down?orderNo="+id;
	}
	
// 首页

// 判断是否为商户
function isShoper() {
	$.ajax({
		url : "../../../Miqu/game/main.do",
		type : "post",
		dataType : "json",
		success : function(json) {
			if(json.errorCode == 0) {
				var pass = json.pass;
				var myShopId = json.myShopId; //-1未申请 0申请未处理 1未通过 2已通过
				
				if(pass == 2) {
					$("#is_shoper_div").show();
					setCookie("user.myShopId", myShopId);
				}
				setCookie("user.pass", pass);
			}
		},
		error : function(json) {
		}
	});
}

// 获取推荐列表和banner数据
function getAllList(page, rows) {
    loading = true;
	$.ajax({
		url : "../../../Miqu/game/recommendList.do",
		type : "post", 
		async:false,
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows)
		},
		dataType : "json",
		success : function(json) {
			if(json.errorCode == 0) {
				pageNum += 1;
				processData(json);
			}else{
				$.toast(json.msg);
			}
	        loading = false;
		},
		error : function(json) {
			$.toast("获取数据失败！");
	        loading = false;
		}
	});
}

// 解析json数据（banner列表和推荐列表）
function processData(json) {
	if(json.errorCode != 0) {
		$.toast("网络异常，请稍后重试！");
		return false;
	}
	// banner列表
	var bannerJsonList = json.banner;
	var bannerList = "";
	var bannerbulletList = "";
	for(var i=0; i<bannerJsonList.length; i++) {
		var contentType = bannerJsonList[i].contentType; // 1专题 2资讯 3课程
		bannerList += "<div class=\"swiper-slide\" ";
		
		if(contentType == 1 || contentType == 2) {
			bannerList += "onclick='click_content_view("+bannerJsonList[i].contentId+","+contentType+")'";
		}else if(contentType == 3) {
			bannerList += "onclick='click_course_view("+bannerJsonList[i].contentId+","+contentType+")'";
		}
		
		bannerList += "><img src=\""+imgPath+bannerJsonList[i].img+"_750X450\" ></div>";

		
		bannerbulletList += "<span class=\"swiper-pagination-bullet ";
		if(i == 0 ){
			bannerbulletList += "swiper-pagination-bullet-active";
		}
		bannerbulletList += "\"></span>";
	} 
	$("#index_banner_list").html(bannerList);
	$("#index_banner_bullet").html(bannerbulletList);
	
	// 推荐列表
	var topJsonList = json.recommendList;
	var topList = "";
	
	// 获取模板
	var templ_content_item = $("#templ_content_item").html();
	// 获取模板
	var templ_course_item = $("#templ_course_item").html();
	
	dataLength = topJsonList.length;
	
	for(var j=0; j<topJsonList.length; j++) {
		if(j == 0) {
			beforeId = topJsonList[j].contentId;
		}
		var contentType = topJsonList[j].contentType;
		// 课程  / 活动
		if(contentType == 3) {
			var classType = topJsonList[j].classType;
			// 课程
			var className = "课程";
			if(classType == 2){
				className = "活动";
			}
			// 填充数据
			topList += templ_course_item.replace("@!{titleImg}", imgPath+topJsonList[j].titleImg+"_718X450")
										.replace("@!{logo}", imgPath+topJsonList[j].logo+"_100X100")
										.replace("@!{classType}", className)
										.replace("@!{title}", topJsonList[j].title)
										.replace("@!{address}", topJsonList[j].address)
										.replace("@!{id}", topJsonList[j].contentId)
										.replace("@!{classTypeId}", classType)
										.replace("@!{trainId}", topJsonList[j].trainId)
										 .replace("@!{faceItemId}", topJsonList[j].contentId);
		}
		// 专题资讯
		if(contentType == 1 || contentType == 2) {
			var contentName = "专题";
			if(contentType == 2) {
				contentName = "资讯";
			}
			// 填充数据
			topList += templ_content_item.replace("@!{titleImg}", imgPath+topJsonList[j].titleImg+"_718X450")
										 .replace("@!{contentType}", contentName)
										 .replace("@!{title}", topJsonList[j].title)
										 .replace("@!{id}", topJsonList[j].contentId)
										 .replace("@!{contentTypeId}", contentType)
										 .replace("@!{faceItemId}", topJsonList[j].contentId);
		}
	}
	$("#topList").append(topList);
	$.init();
}


//获取推荐列表（加载更多）
function getSecondAllList(page, rows) {
    loading = true;
	$.ajax({
		url : "../../../Miqu/game/recommendList.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows)
		},
		dataType : "json",
		success : function(json) {
	        loading = false;
			if(json.errorCode == 0) {
				processSecondData(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
	        loading = false;
			$.toast("获取数据失败！");
		}
	});
}

// 解析json数据(加载更多+下拉刷新)
function processSecondData(json, page) {
	// 推荐列表
	var topJsonList = json.recommendList;
	var topList = "";
	
	// 获取模板
	var templ_content_item = $("#templ_content_item").html();
	// 获取模板
	var templ_course_item = $("#templ_course_item").html();
	
	dataLength = topJsonList.length;
	
	if(dataLength <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var j=0; j<topJsonList.length; j++) {
		
		topList += first_process_data(topJsonList, templ_course_item, templ_content_item, j);
		
	}
	
	if(page == 1) {
		$("#topList").html(topList);
	}else{
		$("#topList").append(topList);
	}
}


function first_process_data(topJsonList, templ_course_item, templ_content_item, j){
	var contentType = topJsonList[j].contentType;
	// 课程  / 活动
	if(contentType == 3) {
		var classType = topJsonList[j].classType;
		// 课程
		var className = "课程";
		if(classType == 2){
			className = "活动";
		}
		// 填充数据
		topList = templ_course_item.replace("@!{titleImg}", imgPath+topJsonList[j].titleImg+"_718X450")
									.replace("@!{logo}", imgPath+topJsonList[j].logo+"_100X100")
									.replace("@!{classType}", className)
									.replace("@!{title}", topJsonList[j].title)
									.replace("@!{address}", topJsonList[j].address)
									.replace("@!{id}", topJsonList[j].contentId)
									.replace("@!{classTypeId}", classType)
									.replace("@!{trainId}", topJsonList[j].trainId)
									.replace("@!{faceItemId}", topJsonList[j].contentId);
	}
	// 专题资讯
	if(contentType == 1 || contentType == 2) {
		var contentName = "专题";
		if(contentType == 2) {
			contentName = "资讯";
		}
		// 填充数据
		topList = templ_content_item.replace("@!{titleImg}", imgPath+topJsonList[j].titleImg+"_718X450")
									 .replace("@!{contentType}", contentName)
									 .replace("@!{title}", topJsonList[j].title)
									 .replace("@!{id}", topJsonList[j].contentId)
									 .replace("@!{contentTypeId}", contentType)
									 .replace("@!{faceItemId}", topJsonList[j].contentId);
	}
	return topList;
}

// 附近列表
function getNearByCourseList(page, rows, lgt, lat) {
	loading = true;
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/nearbyCourse.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows),
			"lgt": lgt,
			"lat": lat
		},
		dataType : "json",
		success : function(json) {
			loading = false;
			$.hideIndicator();
			if(json.errorCode == 0) {
				processNearByCourseData(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			loading = false;
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processNearByCourseData(json, page) {
	// 推荐列表
	var coursesJsonList = json.courses;
	var topList = "";

	// 获取模板
	var templ_course_item = $("#templ_course_item_distance").html();
	
	if(coursesJsonList.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var j=0; j<coursesJsonList.length; j++) {
		var distance =  coursesJsonList[j].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item = templ_course_item.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item = templ_course_item.replace("@!{distance}", "");
		}
		// 填充数据
		topList += templ_course_item.replace("@!{faceItemId}", coursesJsonList[j].contentId)
									.replace("@!{titleImg}", imgPath+coursesJsonList[j].titleImg+"_718X450")
									.replace("@!{id}", coursesJsonList[j].contentId)
									.replace("@!{classTypeId}", coursesJsonList[j].classType)
									.replace("@!{title}", coursesJsonList[j].title)
									.replace("@!{address}", coursesJsonList[j].address);
		
	}
	if(page == 1) {
		$("#search_top_list").html(topList);
	}else{
		$("#search_top_list").append(topList);
	}
	echo.render();
}

// 搜索列表
function getSearchList(page, rows, key, lgt, lat) {
	loading = true;
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/searchCourse.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows),
			"key": key,
			"lgt": lgt,
			"lat": lat
		},
		dataType : "json",
		success : function(json) {
			loading = false;
			$.hideIndicator();
			if(json.errorCode == 0) {
				processSearchData(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			loading = false;
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

//解析json数据（banner列表和推荐列表）
function processSearchData(json, page) {
	if(json.errorCode != 0) {
		$.toast("网络异常，请稍后重试！");
		return false;
	}
	// 推荐列表
	var coursesJsonList = json.courses;
	var topList = "";

	// 获取模板
	var templ_content_item = $("#templ_content_item").html();
	// 获取模板
	var templ_course_item = $("#templ_course_item_distance").html();
	
	if(coursesJsonList.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var j=0; j<coursesJsonList.length; j++) {
		var contentType = coursesJsonList[j].contentType;
		// 课程  / 活动
		if(contentType == 3) {
			var distance =  coursesJsonList[j].distance;
			if(!isNull(distance) && distance != "0m") {
				templ_course_item = templ_course_item.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
			}else{
				templ_course_item = templ_course_item.replace("@!{distance}", "");
			}
			
			// 填充数据
			topList += templ_course_item.replace("@!{titleImg}", imgPath+coursesJsonList[j].titleImg+"_718X450")
												.replace("@!{error_titleImg}", imgPath+coursesJsonList[j].titleImg+"_718X450")
												.replace("@!{logo}", imgPath+coursesJsonList[j].logo+"_100X100")
												.replace("@!{title}", coursesJsonList[j].title)
												.replace("@!{address}", coursesJsonList[j].address)
												.replace("@!{id}", coursesJsonList[j].contentId)
												.replace("@!{classTypeId}", coursesJsonList[j].classType)
												.replace("@!{trainId}", coursesJsonList[j].trainId);
			
			/*var classType = coursesJsonList[j].classType;
			// 课程
			var className = "课程";
			if(classType == 2){
				className = "活动";
			}
			// 填充数据
			topList += templ_course_item.replace("@!{titleImg}", imgPath+coursesJsonList[j].titleImg+"_718X450")
										.replace("@!{logo}", imgPath+coursesJsonList[j].logo+"_100X100")
										.replace("@!{classType}", className)
										.replace("@!{title}", coursesJsonList[j].title)
										.replace("@!{address}", coursesJsonList[j].address)
										.replace("@!{id}", coursesJsonList[j].contentId)
										.replace("@!{classTypeId}", classType);*/
		}
		// 专题资讯
		if(contentType == 1 || contentType == 2) {
			var contentName = "专题";
			if(contentType == 2) {
				contentName = "资讯";
			}
			// 填充数据
			topList += templ_content_item.replace("@!{titleImg}", imgPath+coursesJsonList[j].titleImg+"_718X450")
										 .replace("@!{contentType}", contentName)
										 .replace("@!{title}", coursesJsonList[j].title)
										 .replace("@!{id}", coursesJsonList[j].contentId)
										 .replace("@!{contentTypeId}", contentType);
		}
		
	}
	if(page == 1) {
		$("#search_top_list").html(topList);
	}else{
		$("#search_top_list").append(topList);
	}
	echo.render();
}

//获取课程详情数据
function lookCourseInfo(courseId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/courseDetail.do",
		type : "post", 
		data : {
			"courseId": parseInt(courseId)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processCourseView(json);
				photoCaption();
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processCourseView(json) {
	// 活动课程
	var classTypeName = json.class_type;
	if(classTypeName == 2) {
		// 活动信息显示
		$("#act_course_div").show();
		$("#value_act_endTime").text(json.endTime);
		$("#value_act_count").text(json.copies);
		
		// 活动按钮显示
		var ended = json.ended;
		// 是否结束
		if(ended) {
			 //已经结束
			$("#order_act_end_btn").show();
			$("#order_write_buy_btn").hide();
		}else{
			// 未结束，判断是否已经报名
			var joined = json.joined;
			if(joined) {
				// 已经参与
				$("#order_act_already_btn").show();
				$("#order_write_buy_btn").hide();
			}else{
				$("#order_write_buy_btn").show();
			}
		}
	}else{
		$("#act_course_div").hide();
		$("#order_write_buy_btn").show();
	}
	
	// 课程信息
	$("#value_course_title").text(subStr(json.title, 12));
	$("#value_title_img").html("<img src='"+imgPath+json.title_img+"_750X500' />");
	$("#value_study_time").text(json.study_time);
	$("#value_copies").text(json.hours);
	$("#value_tel").text(json.tel);
	$("#call_href_a").attr("onClick", "javascript:click_call_phone('"+json.tel+"')");
	$("#value_address").text(json.address);
	$("#address_guide").attr("onclick", "javascript:forwardGuide()");
	baiduMapPath = "http://api.map.baidu.com/direction?origin=latlng:@!{location}|name:当前位置" +
		"&destination=latlng:"+json.latitude+","+json.longitude+"|name:"+json.address+"&mode=transit&region=成都&output=html&src=成都大院子科技|觅趣小镇";
	$("#value_content_text").html(json.content);
	//$("#value_nowprice").text(json.price);
	
	// 价格信息
	var items = json.items;
	if(isNull(items) && items.length>0) {
		$("#value_oldprice").text(items[0].origiPrice);
		$("#value_nowprice").text(items[0].price);
	}else{
		$("#value_oldprice").text(0);
		$("#value_nowprice").text(0);
	}
	
	// 培训室信息
	$("#value_train_logo").prop("src", imgPath+json.logo+"_100X100");
	$("#value_train_title").text(json.trainName);
	$("#value_train_description").text(json.trainDecription);
	$("#train_enter_div").bind("click", function(){
		click_train_view(json.trainId);
	});
	
	// 底部按钮
	$("#order_write_buy_btn").attr("onClick", "javascript:click_order_write_openId("+json.contentId+")");
	
	// 猜你喜欢

	var guestLikeJson = json.related;
	var dataList = "";
	// 获取模板
	var templ_course_item_noradius = $("#templ_course_item_noradius").html();
	
	for(var i=0; i<guestLikeJson.length; i++) {
		var distance =  guestLikeJson[i].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", "");
		}
		// 填充数据
		dataList += templ_course_item_noradius.replace("@!{titleImg}", imgPath+guestLikeJson[i].titleImg+"_718X450")
											.replace("@!{error_titleImg}", imgPath+guestLikeJson[i].titleImg)
											.replace("@!{logo}", imgPath+guestLikeJson[i].logo+"_100X100")
											.replace("@!{title}", guestLikeJson[i].title)
											.replace("@!{address}", guestLikeJson[i].address)
											.replace("@!{id}", guestLikeJson[i].contentId)
											.replace("@!{classTypeId}", guestLikeJson[i].classType);
	
	}
	$("#value_guestlike_list").html(dataList);
}


// 订单填写页获取课程新详情
function getCourseForOrderWrite(courseId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/courseDetail.do",
		type : "post", 
		data : {
			"courseId": parseInt(courseId)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processOrderWriteCourseView(json);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processOrderWriteCourseView(json) {
	// 活动课程
	var classTypeName = json.class_type;
	if(classTypeName == 2) {
	}else{
	}
	
	// 课程信息
	$("#value_course_title").text(json.title);
	$("#value_title_img").prop("src", imgPath+json.title_img+"_100X100");
	//$("#value_nowprice").text(json.price);
	
	// 规格
	
	var itemChoose = new Object();
	itemChoose.text = "选择规格";
	itemChoose.label = true;
	courseItems.push(itemChoose);
	
	var ctext = "<div class=\"list_div_item_action\" onClick=\"click_item('@!{item_name}', '@!{item_price}', @!{item_index})\"><div class=\"list_div_content_action\">@!{label_text}<span class=\"normal_price list_div_item_cancel\">¥@!{label_price}</span></div></div>";
	var items = json.items;
	for(var i=0; i<items.length; i++) {
		if(i == 0) {
			click_item(items[0].itemName, items[0].price, 0);
		}
		var item = new Object();
		item.text = ctext.replace("@!{label_text}",items[i].itemName)
						 .replace("@!{label_price}", items[i].price)
						 .replace("@!{item_name}",items[i].itemName)
						 .replace("@!{item_price}", items[i].price)
						 .replace("@!{item_index}", i);
		courseItems.push(item);
	}

	var itemCancel = new Object();
	itemCancel.text = "取消";
	courseItems.push(itemCancel);
	
	var tel = getCookie("user.tel");
	if(!isNull(tel)) {
		$("#tel").val(tel);
	}
}

function click_item(name, value, index) {
	$("#itemindex").val(index);
  	$("#ow_type").text(name);
    $("#ow_price").text(value*(parseInt($("#ow_count").text())));
    $("#value_nowprice").text(value);
}

// 生成本地订单
function createNativeOrder() {
	if(isNull(courseId)) {
		$.toast("无法获取课程信息！");
		return false;
	}
	var itemindex = $("#itemindex").val();
	if(isNull(itemindex)) {
		$.toast("请选择课程规格！");
		return false;
	}
	var buyAmount = $("#ow_count").text();
	if(isNull(buyAmount)) {
		$.toast("购买份数无效！");
		return false;
	}
	if(isNaN(buyAmount)) {
		$.toast("购买份数无效！");
		return false;
	}
	if(parseInt(buyAmount) <= 0) {
		$.toast("购买份数至少1份！");
		return false;
	}
	var name = $("#name").val();
	if(isNull(name)) {
		$.toast("请输入您的姓名！");
		return false;
	}
	if(name.length >= 8) {
		$.toast("姓名长度8个字符以内！");
		return false;
	}
	var tel = $("#tel").val();
	if(isNull(tel)) {
		$.toast("请输入您的手机号！");
		return false;
	}
	if(isNaN(tel)) {
		$.toast("您输入的手机号无效！");
		return false;
	}
	if(tel.length != 11) {
		$.toast("手机号无效，请确认输入！");
		return false;
	}

	// 手机号与登录账号不匹配则需要做登录验证
	var telOld = getCookie("user.tel");
	if(!isNull(telOld) && !isNull(tel) && tel != telOld) {
		$("#valiCode_div").show();
		$("#sendCode_div").show();
	}else{
		$("#valiCode_div").hide();
		$("#sendCode_div").hide();
	}
	
	var valiCode = 0;
	var userId = getCookie("user.userId");
	if(isNull(userId)) {
		valiCode = $("#valiCode").val();
		if(isNull(valiCode)) {
			$.toast("请输入验证码！");
			return false;
		}
		if(isNaN(valiCode)) {
			$.toast("您输入的验证码无效！");
			return false;
		}
	}
	var remark = $("#remark").val();
	$("#write_order_create_btn").text("订单创建中...");
	$("#write_order_create_btn").removeAttr("onclick");
    $.confirm('确定要提交订单吗?', '温馨提示', 
            function () {
		    	$.showIndicator();
		    	var timestamp = new Date().getTime();
		    	$.ajax({
		    		url : "../../../Miqu/game/generateOrder.do",
		    		type : "post", 
		    		data : {
		    			"courseId": parseInt(courseId),
		    			"itemindex": itemindex,
		    			"amount": buyAmount,
		    			"name": name,
		    			"tel": tel,
		    			"remark": remark,
		    			"valicode": valiCode,
		    			"platform": 1,
		    			"token": "wx"+timestamp
		    		},
		    		dataType : "json",
		    		success : function(json) {
		    			$.hideIndicator();
		    			$("#write_order_create_btn").text("提交订单");
		    			$("#write_order_create_btn").attr("onclick", "javascript:createNativeOrder()");
		    			
		    			if(json.errorCode == 0) {
		    				$.toast("订单创建成功！");
		    				$("#write_order_create_btn").text("支付请求中...");
		    				setTimeout("payOrderForWeixin('"+json.orderNo+"')",500);
		    			}else if(json.errorCode == 10) {
		    				$.toast("登录信息已过期，请重新登录！");
		    				timeHref("enter_login");
		    			}else if(json.errorCode == 20){
		    				$.toast(json.msg);
		    				$("#valiCode_div").show();
		    				$("#sendCode_div").show();
		    			}else{
		    				$.toast(json.msg);
		    			}
		    			
		    		},
		    		error : function(json) {
		    			$.hideIndicator();
		    			$.toast("操作异常，请稍后重试！");
		    			$("#write_order_create_btn").text("提交订单");
		    			$("#write_order_create_btn").attr("onclick", "javascript:createNativeOrder()");
		    		}
		    	});
            },
            function () {
            	$("#write_order_create_btn").text("提交订单");
            	$("#write_order_create_btn").attr("onclick", "javascript:createNativeOrder()");
            }
    );
    
	
}

//订单微信支付(创建微信预付订单)
function payOrderForWeixin(orderNo) {
	var openId = getCookie("user.openId");
	if(isNull(openId)) {
		$.toast("请前往微信公众平台【觅趣小镇】进行购买！");
		timeHref("index");
		return false;
	}
	if(isNull(orderNo)) {
		$.toast("无法获取订单信息，支付失败！");
		return false;
	}
	var openId = getCookie("user.openId");
	if(isNull(openId)) {
		$.toast("请前往微信公众平台【觅趣小镇】进行购买");
		return false;
	}
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/wxpageprepay.do",
		type : "post",
		data : {
			"orderNo": orderNo,
			"openId": openId
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				var timestamp = json.timestamp;
				var nonceStr = json.noncestr;
				var packageStr = json.packa;
				var signType = json.signType;
				var paySign = json.paySign;
				start_pay_wx(orderNo, wxAppId , timestamp, nonceStr, packageStr, signType, paySign);
			}else if(json.errorCode == 10) {
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else if(json.errorCode == 20){
				$("#btn_text_submit").text("再次购买");
				$("#btn_text_submit").attr("onClick", "");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("操作异常，请稍后重试！");
		}
	});
}

//获取专题详情数据
function lookContentInfo(contentId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/getContentDetail.do",
		type : "post", 
		data : {
			"contentId": parseInt(contentId)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processContentView(json);
				photoCaption();
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processContentView(json) {
	if(json.errorCode != 0) {
		$.toast("网络异常，请稍后重试！");
		return false;
	}
	
	var styleHead = "<style type=\"text/css\">h4, h5, h6,h1, h2, h3 {margin-top: 0;}ul, ol {margin: 0;}p {margin: 0; letter-spacing:1px; line-height: 21px;padding-top: 10px;}html, body{font-family: Microsoft YaHei;font-size: 14px;background:#e0e0e1;color: #313336;line-height: 21px;letter-spacing: 1.5px;}*{margin: 0px;padding: 0px;}a{text-decoration: none;color: #313336;}<style>";
	
	// 专题资讯信息
	$("#value_title").text(json.title);
	$("#value_time").text(json.create_time);
	$("#value_content").html((json.content).replace(styleHead, ""));
	$("#value_view_count").text(json.views);
	$("#value_like_count").text(json.laudAmount);
	
	// 相关推荐

	var dataList = "";
	var linkTopTrainJson = json.recommendTrainInfo;
	var linkTopCourseJson = json.recommendCourse;
	
	if(linkTopTrainJson.length>0 || linkTopCourseJson.length>0) {
		$("#show_top_content_div").show();
		dataList += "<table width=\"100%\" border=\"0\">";
		if(linkTopTrainJson.length >= linkTopCourseJson.length) {
			var templ_content_top_item = $("#templ_train_top_item").html();
			// 学堂推荐
			for(var i=0; i<linkTopTrainJson.length; i++) {
				if((i+1)%2 == 1) {
					dataList += "<tr><td width=\"50%\" align=\"left\">";
				}else{
					dataList += "<td width=\"50%\" align=\"right\">";
				}
				dataList += templ_content_top_item.replace("@!{titleImg}", imgPath+linkTopTrainJson[i].banner_img+"_718X450")
											  .replace("@!{title}", linkTopTrainJson[i].title)
											  .replace("@!{id}", linkTopTrainJson[i].trainId);
				if((i+1)%2 == 1) {
					dataList += "</td>";
				}else{
					dataList += "</td></tr>";
				}
				
			}
		}else{
			var templ_content_top_item = $("#templ_course_top_item").html();
			// 课程推荐
			for(var i=0; i<linkTopCourseJson.length; i++) {
				if((i+1)%2 == 1) {
					dataList += "<tr><td width=\"50%\" align=\"left\" valign=\"top\">";
				}else{
					dataList += "<td width=\"50%\" align=\"right\" valign=\"top\">";
				}
				dataList += templ_content_top_item.replace("@!{titleImg}", imgPath+linkTopCourseJson[i].titleImg+"_718X450")
											  .replace("@!{title}", linkTopCourseJson[i].title)
											  .replace("@!{id}", linkTopCourseJson[i].contentId)
											  .replace("@!{classTypeId}", linkTopCourseJson[i].classType)
				if((i+1)%2 == 1) {
					dataList += "</td>";
				}else{
					dataList += "</td></tr>";
				}
				
			}
		}
		dataList += "</table>";
	}
	// 获取模板
	
	$("#value_contetn_top_list").html(dataList);
}


// 获取资讯详情数据
function lookNewsInfo(contentId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/getContentDetail.do",
		type : "post", 
		data : {
			"contentId": parseInt(contentId)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processNewsView(json);
				photoCaption();
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processNewsView(json) {
	if(json.errorCode != 0) {
		$.toast("网络异常，请稍后重试！");
		return false;
	}
	
	var styleHead = "<style type=\"text/css\">h4, h5, h6,h1, h2, h3 {margin-top: 0;}ul, ol {margin: 0;}p {margin: 0; letter-spacing:1px; line-height: 21px;padding-top: 10px;}html, body{font-family: Microsoft YaHei;font-size: 14px;background:#e0e0e1;color: #313336;line-height: 21px;letter-spacing: 1.5px;}*{margin: 0px;padding: 0px;}a{text-decoration: none;color: #313336;}<style>";
	
	// 资讯信息
	$("#value_title").text(json.title);
	$("#value_time").text(json.create_time);
	$("#value_content").html((json.content).replace(styleHead, ""));
	$("#value_view_count").text(json.views);
	$("#value_like_count").text(json.laudAmount);
	
}


//获取学堂主页数据
function lookTrainInfo(trainId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/trainHome.do",
		type : "post", 
		data : {
			"trainId": parseInt(trainId),
			"lgt": getLongitude(),
			"lat": getLatitude()
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processTrainView(json);
				photoCaption();
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processTrainView(json) {
	// 学堂基础信息
	$("#value_train_title").text(subStr(json.title, 12));
	$("#value_train_titleImg").prop("src", imgPath+json.banner_img);
	$("#value_train_logo").prop("src", imgPath+json.logo+"_100X100");
	$("#value_train_description").text(json.description);
	$("#value_hours").text(json.businessHours);
	$("#value_tel").text(json.tel);
	$("#call_href_a").attr("onClick", "javascript:click_call_phone('"+json.tel+"')");
	$("#value_address").text(json.address);
	$("#address_guide").attr("onclick", "javascript:forwardGuide()");
	baiduMapPath = "http://api.map.baidu.com/direction?origin=latlng:@!{location}|name:当前位置" +
		"&destination=latlng:"+json.latitude+","+json.longitude+"|name:"+json.address+"&mode=transit&region=成都&output=html&src=成都大院子科技|觅趣小镇";
	$("#value_content_text").html((json.content));
	
	// 学堂课程列表

	var trainOfCourseJson = json.courses;
	var dataList = "";
	// 获取模板
	var templ_course_item_noradius = $("#templ_course_item_noradius").html();
	
	for(var i=0; i<trainOfCourseJson.length; i++) {
		var distance =  trainOfCourseJson[i].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", "");
		}
		// 填充数据
		dataList += templ_course_item_noradius.replace("@!{titleImg}", imgPath+trainOfCourseJson[i].titleImg+"_718X450")
											.replace("@!{error_titleImg}", imgPath+trainOfCourseJson[i].titleImg)
											.replace("@!{logo}", imgPath+trainOfCourseJson[i].logo+"_100X100")
											.replace("@!{title}", trainOfCourseJson[i].title)
											.replace("@!{address}", trainOfCourseJson[i].address)
											.replace("@!{id}", trainOfCourseJson[i].contentId)
											.replace("@!{classTypeId}", trainOfCourseJson[i].classType)
											.replace("@!{trainId}", trainOfCourseJson[i].trainId);
	
	}
	$("#value_train_of_course_list").html(dataList);
}


//获取商家主页数据
function lookShopHomeInfo(trainId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/trainHome.do",
		type : "post", 
		data : {
			"trainId": parseInt(trainId),
			"lgt": getLongitude(),
			"lat": getLatitude()
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processTrainHomeView(json);
				photoCaption();
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processTrainHomeView(json) {
	// 学堂基础信息
	$("#value_train_title").text(subStr(json.title, 12));
	$("#value_train_titleImg").prop("src", imgPath+json.banner_img+"_750X500");
	$("#value_train_logo").prop("src", imgPath+json.logo+"_100X100");
	$("#value_train_description").text(json.description);
	$("#value_hours").text(json.businessHours);
	$("#value_tel").text(json.tel);
	$("#call_href_a").attr("onClick", "javascript:click_call_phone('"+json.tel+"')");
	$("#value_address").text(json.address);

	$("#address_guide").attr("onclick", "javascript:forwardGuide()");
	baiduMapPath = "http://api.map.baidu.com/direction?origin=latlng:@!{location}|name:当前位置" +
		"&destination=latlng:"+json.latitude+","+json.longitude+"|name:"+json.address+"&mode=transit&region=成都&output=html&src=成都大院子科技|觅趣小镇";
	
	$("#value_content_text").html((json.content));
	
	// 学堂课程列表

	var trainOfCourseJson = json.courses;
	var dataList = "";
	// 获取模板
	var templ_course_item_noradius = $("#templ_course_item_noradius").html();
	
	for(var i=0; i<trainOfCourseJson.length; i++) {
		var distance =  trainOfCourseJson[i].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", "");
		}
		// 填充数据
		dataList += templ_course_item_noradius.replace("@!{titleImg}", imgPath+trainOfCourseJson[i].titleImg+"_718X450")
											.replace("@!{error_titleImg}", imgPath+trainOfCourseJson[i].titleImg)
											.replace("@!{logo}", imgPath+trainOfCourseJson[i].logo)
											.replace("@!{title}", trainOfCourseJson[i].title)
											.replace("@!{address}", trainOfCourseJson[i].address)
											.replace("@!{id}", trainOfCourseJson[i].contentId)
											.replace("@!{classTypeId}", trainOfCourseJson[i].classType)
											.replace("@!{trainId}", trainOfCourseJson[i].trainId);
	
	}
	$("#value_train_of_course_list").html(dataList);
}

// 分页获取商户主页课程信息
function getShopHomeCourse(page, rows, trainId) {
	$.showIndicator();
	loading = true;
	$.ajax({
		url : "../../../Miqu/game/trainHome.do",
		type : "post", 
		data : {
			"trainId": parseInt(trainId),
			"lgt": 0,
			"lat": 0,
			"current_page": parseInt(page),
			"max_num": parseInt(rows)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			loading = false;
			if(json.errorCode == 0) {
				processTrainHomeList(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			loading = false;
			$.toast("获取数据失败！");
		}
	});
}

function processTrainHomeList(json, page) {
	// 学堂课程列表
	var trainOfCourseJson = json.courses;
	var dataList = "";
	// 获取模板
	var templ_course_item_noradius = $("#templ_course_item_noradius").html();

	if(trainOfCourseJson.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var i=0; i<trainOfCourseJson.length; i++) {
		var distance =  trainOfCourseJson[i].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item_noradius = templ_course_item_noradius.replace("@!{distance}", "");
		}
		// 填充数据
		dataList += templ_course_item_noradius.replace("@!{titleImg}", imgPath+trainOfCourseJson[i].titleImg+"_718X450")
											.replace("@!{error_titleImg}", imgPath+trainOfCourseJson[i].titleImg)
											.replace("@!{logo}", imgPath+trainOfCourseJson[i].logo)
											.replace("@!{title}", trainOfCourseJson[i].title)
											.replace("@!{address}", trainOfCourseJson[i].address)
											.replace("@!{id}", trainOfCourseJson[i].contentId)
											.replace("@!{classTypeId}", trainOfCourseJson[i].classType)
											.replace("@!{trainId}", trainOfCourseJson[i].trainId);
	
	}
	
	if(page == 1) {
		$("#value_train_of_course_list").html(dataList);
	}else{
		$("#value_train_of_course_list").append(dataList);
	}
}

// 获取往期专题列表数据
function getContentHistoryList(page, rows) {
	$.showIndicator();
	loading = true;
	$.ajax({
		url : "../../../Miqu/game/getContentList.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows)
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			loading = false;
			if(json.errorCode == 0) {
				processContentHistoryData(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			loading = false;
			$.toast("获取数据失败！");
		}
	});
}

//解析json数据（banner列表和推荐列表）
function processContentHistoryData(json, page) {
	if(json.errorCode != 0) {
		$.toast("网络异常，请稍后重试！");
		return false;
	}
	// 推荐列表
	var contentJsonList = json.datas;
	var dataList = "";

	// 获取模板
	var templ_content_item = $("#templ_content_item").html();
	
	if(contentJsonList.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var j=0; j<contentJsonList.length; j++) {
		// 专题资讯
		var contentName = "专题";
		// 填充数据
		dataList += templ_content_item.replace("@!{titleImg}", imgPath+contentJsonList[j].image)
									 .replace("@!{error_titleImg}", imgPath+contentJsonList[j].image)
									 .replace("@!{contentType}", contentName)
									 .replace("@!{title}", contentJsonList[j].title)
									 .replace("@!{id}", contentJsonList[j].id)
									 .replace("@!{contentTypeId}", 1);
		
	}
	
	if(page == 1) {
		$("#content_history_list_div").html(dataList);
	}else{
		$("#content_history_list_div").append(dataList);
	}
	
}

// 通过课程分类ID查询课程
function getTrainOfIdList(page, rows, trainTypeId, lgt, lat) {
	$.showIndicator();
    loading = true;
	$.ajax({
		url : "../../../Miqu/game/getCourseByType.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows),
			"typeId": trainTypeId,
			"lgt": lgt,
			"lat": lat
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
		    loading = false;
			if(json.errorCode == 0) {
				processTrainOfIdData(json, page);
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
		    loading = false;
			$.toast("获取数据失败！");
		}
	});
}

//
function processTrainOfIdData(json, page) {
	// 学堂课程列表
	var trainOfCourseJson = json.courses;
	var dataList = "";
	// 获取模板
	var templ_course_item_distance = $("#templ_course_item_distance").html();
	
	if(trainOfCourseJson.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var i=0; i<trainOfCourseJson.length; i++) {
		var distance =  trainOfCourseJson[i].distance;
		if(!isNull(distance) && distance != "0m") {
			templ_course_item_distance = templ_course_item_distance.replace("@!{distance}", distance+"&nbsp;|&nbsp;");
		}else{
			templ_course_item_distance = templ_course_item_distance.replace("@!{distance}", "");
		}
		
		// 填充数据
		dataList += templ_course_item_distance.replace("@!{titleImg}", imgPath+trainOfCourseJson[i].titleImg+"_718X450")
											.replace("@!{error_titleImg}", imgPath+trainOfCourseJson[i].titleImg+"_718X450")
											.replace("@!{logo}", imgPath+trainOfCourseJson[i].logo+"_100X100")
											.replace("@!{title}", trainOfCourseJson[i].title)
											.replace("@!{address}", trainOfCourseJson[i].address)
											.replace("@!{id}", trainOfCourseJson[i].contentId)
											.replace("@!{classTypeId}", trainOfCourseJson[i].classType)
											.replace("@!{trainId}", trainOfCourseJson[i].trainId);
	
	}
	if(page == 1) {
		$("#value_train_of_course_list").html(dataList);
	}else{
		$("#value_train_of_course_list").append(dataList);
	}
	echo.render();
}


// 登录

function click_login_submit() {
	var tel = $("#tel").val();
	if(isNull(tel)) {
		$.toast("请输入手机号！");
		return false;
	}
	if(isNaN(tel)) {
		$.toast("手机号不合法!");
		return false;
	}
	if(tel.length != 11) {
		$.toast("手机号无效！");
		return false;
	}
	var password = $("#password").val();
	if(isNull(password)) {
		$.toast("请输入登录密码！");
		return false;
	}
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/login.do",
		type : "post",
		data : {
			"pf": 1,
			"externalType": 0,
			"tel": tel,
			"password": password
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				setUserInfoCookie(json);
				// 判断是否需要完善资料
				//================if(isNull(json.nickname) || isNull(json.headIcon)) {
				if(isNull(json.nickname)) {
					// 此时需要完善资料
					$.toast("请完善资料！");
					timeHref("enter_userhome");
				}else{
					// 登录成功
					$.toast("登录成功！");
					timeHref("index");
				}
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("出现异常，操作失败！");
		}
	});
}

// 修改个人资料(第二次完善，个人中心修改资料)
function modifyinfo() {
	var headIcons = $("#headIcons").val();
	if(isNull(headIcons)) {
		$.toast("请上传头像！");
		return false;
	}
	var nickName = $("#nickname_span").text();
	if(isNull(nickName)) {
		$.toast("请输入昵称！");
		return false;
	}
	var birthday = $("#birthday").val();
	if(isNull(birthday)) {
		$.toast("请选择生日！");
		return false;
	}
	var gender = $("#sex").val();
	if(isNull(gender)) {
		$.toast("请选择性别！");
		return false;
	}
	var interests = getUserInterestNameFormatter();
	if(isNull(interests)) {
		$.toast("请选择兴趣爱好！");
		return false;
	}
	var tel = $("#tel").val();
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/modifyinfo.do",
		type : "post",
		data : {
			"headPath": "1.jpg",
			"nickName": nickName,
			"gender": gender,
			"birthday": birthday,
			"interests": interests,
			"tel": tel
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				$.toast("信息完善成功！");
				setUserInfoCookie(json);
				timeHref("index");
			}else if(json.errorCode == 10) {
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("出现异常，操作失败！");
		}
	});
}


//添加个人资料(注册完善资料)
function addinfo() {
	var headIcons = $("#headIcons").val();
	if(isNull(headIcons)) {
		$.toast("请上传头像！");
		return false;
	}
	var nickName = $("#nickname_span").text();
	if(isNull(nickName)) {
		$.toast("请输入昵称！");
		return false;
	}
	var birthday = $("#birthday").val();
	if(isNull(birthday)) {
		$.toast("请选择生日！");
		return false;
	}
	var gender = $("#sex").val();
	if(isNull(gender)) {
		$.toast("请选择性别！");
		return false;
	}
	var interests = getUserInterestNameFormatter();
	if(isNull(interests)) {
		$.toast("请选择兴趣爱好！");
		return false;
	}
	var tel = $("#tel").val();
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/addinfo.do",
		type : "post",
		data : {
			"headPath": headIcons,
			"nickName": nickName,
			"gender": gender,
			"birthday": birthday,
			"interests": interests,
			"tel": tel
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				$.toast("信息完善成功！");
				setUserInfoCookie(json);
				timeHref("index");
			}else if(json.errorCode == 10) {
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("出现异常，操作失败！");
		}
	});
}

function setUserInfoCookie(json) {
	clearUserCookie();
	setCookie("user.age",json.age);
	setCookie("user.birthday", json.birthday);
	setCookie("user.gender", json.gender);
	//================setCookie("user.headIcon", json.headIcon);
	setCookie("user.headIcon", "../source/images/right_forward.png");
	var interests = json.interests;
	var interStr = "";
	for(var i=0; i<interests.length; i++) {
		interStr += interests[i].id+"|"+interests[i].interest+";";
	}
	setCookie("user.interests", interStr);
	setCookie("user.laud", json.laud);
	setCookie("user.message", json.message);
	setCookie("user.nickname",json.nickname);
	setCookie("user.reply", json.reply);
	setCookie("user.system", json.system);
	setCookie("user.tel", json.tel);
	setCookie("user.userId", json.userId);
}

function clearUserCookie() {
	setCookie("user.age","");
	setCookie("user.birthday", "");
	setCookie("user.gender", "");
	setCookie("user.headIcon", "");
	setCookie("user.interests", "");
	setCookie("user.laud", "");
	setCookie("user.message", "");
	setCookie("user.nickname","");
	setCookie("user.reply", "");
	setCookie("user.system", "");
	setCookie("user.tel", "");
	setCookie("user.userId", "");
	setCookie("location.longitude");
	setCookie("location.latitude");
}

function clearUserInfoCookie() {
	// 清除用户信息缓存
	clearUserCookie();
	// 未登录，提供登录入口
	$("#first_tool_icon").prop("href", "enter_login");
	$("#first_tool_icon").addClass("open-panel");
	// 用户展示信息置空
	$("#label_headimg").prop("src", "");
	$("#label_nickname").text("");
	$("#label_tel").text("");
	// 关闭侧边栏
	$.closePanel(".panel");
	$.toast("退出成功！");
}

// 退出登录
function exitLogin() {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/logoff.do",
		type : "post",
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			clearUserInfoCookie();
		},
		error : function(json) {
			$.hideIndicator();
			clearUserInfoCookie();
			$.toast("退出失败，请稍后重试！");
		}
	});
}

// 查看我的订单(用户)
function getOrderOwnerList(page, rows) {
	loading = true;
	$.ajax({
		url : "../../../Miqu/game/myOrders.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows)
		},
		dataType : "json",
		success : function(json) {
			loading = false;
			if(json.errorCode == 0) {
				processOrderOwnerData(json, page);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			loading = false;
			$.toast("获取数据失败！");
		}
	});
}

function processOrderOwnerData(json, page) {
	// 订单列表
	var orderOwnerListJson = json.datas;
	var dataList = "";
	// 获取模板(待支付)
	var templ_order_owner_item_pay = $("#templ_order_owner_item_pay").html();
	// 获取模板（已支付）
	var templ_order_owner_item_again = $("#templ_order_owner_item_again").html();

	if(orderOwnerListJson.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		pageNum += 1;
		$(".loading_text").hide();
	}
	
	for(var i=0; i<orderOwnerListJson.length; i++) {
		var orderStatus = orderOwnerListJson[i].orderStatus;
		// 除待付款状态，其余专题显示“再次购买按钮”
		// 填充数据
		if(orderStatus == 0) {
			// 待支付
			dataList += templ_order_owner_item_pay.replace("@!{userName}", orderOwnerListJson[i].buyerName)
												  .replace("@!{statusName}", orderStatusFormatter(0))
												  .replace("@!{courseImg}", imgPath+orderOwnerListJson[i].title_img+"_100X100")
												  .replace("@!{courseName}", orderOwnerListJson[i].title)
												  .replace("@!{address}", orderOwnerListJson[i].address)
												  .replace("@!{coursePrice}", orderOwnerListJson[i].totalFee)
												  .replace("@!{id}", orderOwnerListJson[i].orderNo)
												  .replace("@!{forwardFunction}", "click_order_owner")
												  .replace("@!{orderNo}", orderOwnerListJson[i].orderNo);
		}else{
			// 其他
			dataList += templ_order_owner_item_again.replace("@!{userName}", orderOwnerListJson[i].buyerName)
												  .replace("@!{statusName}", orderStatusFormatter(orderStatus))
												  .replace("@!{courseImg}", imgPath+orderOwnerListJson[i].title_img+"_100X100")
												  .replace("@!{courseName}", orderOwnerListJson[i].title)
												  .replace("@!{address}", orderOwnerListJson[i].address)
												  .replace("@!{coursePrice}", orderOwnerListJson[i].totalFee)
												  .replace("@!{id}", orderOwnerListJson[i].orderNo)
		  										  .replace("@!{forwardFunction}", "click_order_owner")
		  										  .replace("@!{courseId}", orderOwnerListJson[i].courseId);
		}
		
	
	}
	
	if(page == 1) {
		$("#order_owner_list_div").html(dataList);
	}else{
		$("#order_owner_list_div").append(dataList);
	}
	echo.render();
}

//查看我的订单（商户）
function getOrderShoperList(page, rows, orderType) {
	loading = true;
	$.ajax({
		url : "../../../Miqu/game/shopOrders.do",
		type : "post",
		data : {
			"current_page": parseInt(page),
			"max_num": parseInt(rows),
			"orderType": parseInt(orderType)
		},
		dataType : "json",
		success : function(json) {
			loading = false;
			if(json.errorCode == 0) {
				processOrderShoperData(json, orderType, page);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			loading = false;
			$.toast("获取数据失败！");
		}
	});
}

function processOrderShoperData(json, orderType, page) {
	$("#orderAmount").text(json.orderAmount);
	$("#totalSalesAmount").text(json.totalSalesAmount);
	
	// 订单列表
	var orderShoperListJson = json.datas;
	var dataList = "";
	// 获取模板
	var templ_order_shoper_item = $("#templ_order_shoper_item").html();
	
	if(orderShoperListJson.length <= 0) {
		loading = true;
		$('.infinite-scroll-preloader').remove();
		$(".loading_text").show();
	}else{
		setPageNum(parseInt(page)+1);
		$(".loading_text").hide();
	}
	
	for(var i=0; i<orderShoperListJson.length; i++) {
		var orderStatus = orderShoperListJson[i].orderStatus;
		// 填充数据
			// 其他
			dataList += templ_order_shoper_item.replace("@!{userName}", orderShoperListJson[i].buyerName)
												  .replace("@!{statusName}", orderStatusFormatter(orderStatus))
												  .replace("@!{courseImg}", imgPath+orderShoperListJson[i].title_img+"_100X100")
												  .replace("@!{courseName}", orderShoperListJson[i].title)
												  .replace("@!{buyCount}", orderShoperListJson[i].buyAmount)
												  .replace("@!{coursePrice}", orderShoperListJson[i].totalFee)
												  .replace("@!{id}", orderShoperListJson[i].orderNo)
		  										  .replace("@!{forwardFunction}", "click_shop_index");
	
	}
	
	if(page == 1) {
		$("#order_shoper_list_div_"+orderType).html(dataList);
	}else{
		$("#order_shoper_list_div_"+orderType).append(dataList);
	}
	echo.render();
}

// 获取验证码
function sendCode(type) {
	if(isNull(type)) {
		type = 0;
	}
	time = 61;
	// 判断是否允许发送手机短信
	var bkColor = $("#label_time_count").css("background-color");
	if(bkColor == "#d6d6d7" || bkColor == "rgb(214, 214, 215)") {
		// 不允许再次发送
		return false;
	}
	var tel = $("#tel").val();
	if(isNull(tel)) {
		$.toast("请输入手机号！");
		return false;
	}
	if(isNaN(tel)) {
		$.toast("手机号不合法!");
		return false;
	}
	if(tel.length != 11) {
		$.toast("手机号无效！");
		return false;
	}
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/sendCode.do",
		type : "post",
		data : {
			"type": parseInt(type),
			"tel": tel
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				timeCount();
				$("#label_time_count").addClass("bk_disabled");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("验证码发送失败，请稍后重试！");
		}
	});
}

// 注册
function register() {
	var tel = $("#tel").val();
	if(isNull(tel)) {
		$.toast("请输入手机号！");
		return false;
	}
	if(isNaN(tel)) {
		$.toast("手机号不合法!");
		return false;
	}
	if(tel.length != 11) {
		$.toast("手机号无效！");
		return false;
	}
	var valiCode = $("#valiCode").val();
	if(isNull(valiCode)) {
		$.toast("请输入验证码！");
		return false;
	}
	var password = $("#password").val();
	if(isNull(password)) {
		$.toast("请输入密码！");
		return false;
	}
	if(!isChinese(password)) {
		$.toast("密码不能含有中文！");
		return false;
	}
	
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/register.do",
		type : "post",
		data : {
			"pf": 1,
			"validateCode": valiCode,
			"password": password
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				window.location.href="enter_userhome";
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("注册失败，请稍后重试！");
		}
	});
}

//订单详情 type(1 表示用户 2表示商户)
function lookOrderInfo(type, orderId) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/orderDetail.do",
		type : "post",
		data : {
			"type": parseInt(type),
			"orderNo": orderId
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processOrderView(json, type);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
				setTimeout("click_order_owner()",500);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processOrderView(json, type) {
	var orderStatus = json.orderStatus;
	// 订单信息
	$("#label_order_statusName").text(orderStatusFormatter(orderStatus));
	$("#label_order_orderNo").text(json.orderNo);
	$("#label_order_price").text("¥"+json.totalFee);
	$("#label_order_time").text(json.createTime);
	$("#label_order_course_img").prop("src", imgPath+json.courseTitleImg+"_100X100");
	$("#label_order_course_title").text(json.courseTitle);
	$("#label_order_course_count").text("x"+json.buyAmount);
	$("#label_order_course_shopTotalFee").text(json.price);
	$("#order_buyName").text(json.buyername);
	$("#order_buyerTel").text(json.tel);
	$("#order_remark").text(json.remark);
	$("#click_course_div").attr("onClick", "javascript:click_course_view("+json.courseId+")");
	$("#label_order_item").text(json.itemName);
	// 申请退款  用户详情并且订单处于待支付状态则显示
	
	if(type == 1 && orderStatus == 0) {
		// 新订单 显示取消订单功能
		$("#click_cancel_div").show();
		$("#click_cancel_div").attr("onclick", "javascript:cancelOrderEnter('"+json.orderNo+"')");
	}else if(type == 1 && orderStatus == 1){
		// 已支付状态可以提交退款申请
		$("#click_refund_div").show();
		$("#click_refund_div").attr("onclick", "javascript:click_order_refund('"+json.orderNo+"','"+json.totalFee+"')");
	}else{
		$("#click_refund_div").hide();
		$("#pay_way_div").hide();
	}
	
	// 底部按钮
	if(type == 1) {
		if(orderStatus == 0) {
			$("#btn_text_submit").text("立即支付");
			$("#btn_text_submit").attr("onClick", "javascript:payOrderForWeixin('"+orderId+"')");
		}else{
			$("#btn_text_submit").text("再次购买");
			$("#btn_text_submit").attr("onclick", "javascript:click_order_write("+json.courseId+")");
		}
	}else if(type == 2) {
		if(orderStatus == 1 && !isNull(remark) && remark == "1"){
			$("#btn_text_submit").text("确认消费");
			$("#btn_text_submit").attr("onclick", "javascript:vali_order_down('"+json.orderNo+"')");
		}else{
			$("#btn_float_text").hide();
		}
	}
	
}

// 订单成功页面
function showSuccessOrderInfo(orderNo) {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/orderDetail.do",
		type : "post",
		data : {
			"type": parseInt(1),
			"orderNo": orderNo
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				processSuccessOrderView(json);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

function processSuccessOrderView(json) {
	var orderStatus = json.orderStatus;
	// 订单信息
	$("#od_head_title_price").text(json.totalFee);
	$("#od_head_time").text(json.createTime);
	$("#od_order_id").text(json.orderNo);
}

// 订单申请退款
function orderRefundEnter() {
	if(isNull(orderNo)) {
		$.toast("订单已失效，请重新进入！")
		timeHref("shop_order_list");
		return false;
	}
	var reason = $("#reason").val();
	if(isNull(reason)) {
		$.toast("请填写退款申请理由！");
		return false;
	}
	if(reason.length >= 40) {
		$.toast("退款理由40个字符内！");
		return false;
	}
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/refund.do",
		type : "post",
		data : {
			"reason": reason,
			"orderNo": orderNo
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				$.toast("退款申请已提交，请等候后台审核！");
				setTimeout("click_order_owner()",500);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

//取消订单
function cancelOrderEnter(orderNo) {
	if(isNull(orderNo)) {
		$.toast("订单已失效，请重新进入！")
		timeHref("shop_order_list");
		return false;
	}
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/cancelOrder.do",
		type : "post",
		data : {
			"orderNo": orderNo
		},
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				$.toast("操作成功！");
				setTimeout("click_order_owner()",500);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}

// 验证订单是否有效，有效则进入订单详情
function vali_order_info() {
	var valiCode = $("#valiCode").val();
	if(isNull(valiCode)) {
		$.toast("请填写验证码！");
		return false;
	}
	$.showPreloader("消费码验证中，请稍后...");
	$.ajax({
		url : "../../../Miqu/game/relate.do",
		type : "post",
		data : {
			"validateCode": valiCode
		},
		dataType : "json",
		success : function(json) {
			$.hidePreloader();
			if(json.errorCode == 0) {
				setCookie("shop.order.valiCode", valiCode);
				var orderNo = json.orderNo;
				setTimeout("click_order_info_use('"+orderNo+"','click_shop_index')");
			}else if(json.errorCode == 20) {
				$.toast(json.msg);
			}else if(json.errorCode == 10){
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
			$("#valiCode").val("");
		},
		error : function(json) {
			$.hidePreloader();
			$.toast("出现异常，操作失败！");
		}
	});
}

// 订单消费
function vali_order_down(orderNo) {
	if(isNull(orderNo)) {
		$.toast("无法获取订单信息，请重新验证！");
		click_shop_index();
		return false;
	}
	var valiCode = getCookie("shop.order.valiCode");
	if(isNull(valiCode)) {
		$.toast("验证码已失效，请重新验证！");
		click_shop_index();
		return false;
	}
    $.confirm('确认进行消费吗?', '温馨提示', 
            function () {
		    	$.showPreloader("订单核销中，请稍后...");
		    	$.ajax({
		    		url : "../../../Miqu/game/confirmUse.do",
		    		type : "post",
		    		data : {
		    			"validateCode": valiCode
		    		},
		    		dataType : "json",
		    		success : function(json) {
		    			$.hidePreloader();
		    			if(json.errorCode == 0) {
		    				$.toast("验证码有效,消费成功！");
		    				setTimeout("click_order_info('"+orderNo+"','click_shop_index')");
		    			}else if(json.errorCode == 20) {
		    				$.toast(json.msg);
		    			}else if(json.errorCode == 10){
		    				$.toast("登录信息已过期，请重新登录！");
		    				timeHref("enter_login");
		    			}else{
		    				$.toast(json.msg);
		    			}
		    			$("#valiCode").val("");
		    		},
		    		error : function(json) {
		    			$.hidePreloader();
		    			$.toast("出现异常，操作失败！");
		    		}
		    	});
            }
     );
}

function record_tab_order(id) {
	setCookie("shop.order.list.currTab", id);
	var currHtml = $("#order_shoper_list_div_"+id).html();
	if(isNull(currHtml)) {
		getOrderShoperList(pageNum, rowsNum, id);
	}
}


// 我的店铺index
function viewMyShopInfo() {
	$.showIndicator();
	$.ajax({
		url : "../../../Miqu/game/getMyTrain.do",
		type : "post",
		dataType : "json",
		success : function(json) {
			$.hideIndicator();
			if(json.errorCode == 0) {
				$("#orderAmount").text(json.orderAmount);
				$("#totalsales").text(json.totalsales);
				$("#income").text(json.income);
				$("#balance").text(json.balance);
			}else if(json.errorCode == 10) {
				$.toast("登录信息已过期，请重新登录！");
				timeHref("enter_login");
			}else{
				$.toast(json.msg);
			}
		},
		error : function(json) {
			$.hideIndicator();
			$.toast("获取数据失败！");
		}
	});
}


$(function() {
// 首页
	
	// 侧边栏事件
/*	$(".index_page").css("overflow", "auto");
 * $(document).on('opened','.panel', function () {
		$(".index_page").css("overflow", "hidden");
	});
	$(document).on('closed','.panel', function () {
		$(".index_page").css("overflow", "auto");
	});*/
	
	// 关键词搜索
	$("#search").change(function(){
		var key = $("#search").val();
		getSearchList(1, 1000, key, getLongitude(), getLatitude());
	});
	$("#search_label").click(function(){
		var key = $("#search").val();
		getSearchList(1, 1000, key, getLongitude(), getLatitude());
	});
	
	
	
	
//  个人资料

	// 头像上传
	$("#user_icon_div").click(function(){
		$("#userIconInput").click();
	});
	
	// 年龄选择
	try {
		var d = new Date();
		var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		$("#birthday_div").calendar({
		      dateFormat: "yy-mm-dd",
		      maxDate:str,
		      onChange: function(p, v, d) {
		      	var s = mills2DateTime(v);
		       $("#birthday").val(s);
		       $("#age_span").text(getAge(s)+1);
		       $.closeModal(".popup-birthday");
		      }
		});
	} catch (e) {
		
	}

  	// 昵称选择
  	$("#nickname_submit_btn").click(function(){
  		var nickname = $("#nickname_text").val();
  		if(isNull(nickname)) {
  			$.toast("请输入昵称");
  			return false;
  		}
  		if(nickname.length<=1) {
  			$.toast("昵称长度范围：2~10个字符");
  			return false;
  		}
  		if(nickname.length>10) {
  			$.toast("昵称长度范围：2~10个字符");
  			return false;
  		}
  		$("#nickname_span").text(nickname);
  		$.closeModal('.popup-nickname');	
	  	});

	$(document).on('click','.open-nickname', function () {
	  $.popup('.popup-nickname');
	});

	$(document).on('click','.open-birthday', function () {
	  $.popup('.popup-birthday');
	});
	 
	$(document).on('click','.open-interest', function () {
	  	$.popup('.popup-interest');	
	});	
	$(document).on('click','.close-interest', function () {
	  	$.closeModal('.popup-interest');	
	    // 兴趣选择 
	  	$("#interest_span").text(getUserInterestName());
	});

	// 性别选择
	$(document).on('click','.create-sex', function () {
	     var buttons1 = [
	        {
	          text: '请选择',
	          label: true
	        },
	        {
	          text: '男',
	          bold: true,
	          color: 'danger',
	          onClick: function() {
	          	$("#sex_span").text("男");
	          	$("#sex").val(1);
	          }
	        },
	        {
	          text: '女',
	          onClick: function() {
	          	$("#sex_span").text("女");
	          	$("#sex").val(2);
	          }
	        }
	      ];
	      var buttons2 = [
	        {
	          text: '取消',
	          bg: 'danger'
	        }
	      ];
	      var groups = [buttons1, buttons2];
	      $.actions(groups);
	  });

// 注册

	// 查看密码
	$(".valiCode_div").click(function(){
		var passType = $("#password").attr("type");
		if(passType == "text") {
			$("#password").prop("type", "password");
		}else{
			$("#password").prop("type", "text");
		}
	});


// 申请退款
	
	// 提交退款申请
	$("#order_refund_div_sbt").click(function(){
		orderRefundEnter();
	});
	
	// 首次影藏加载
	$('.infinite-scroll-preloader').remove();
	
	$.init();
	
	$(".content").scroll(function(){
		echo.render();
	});
	
});

	
