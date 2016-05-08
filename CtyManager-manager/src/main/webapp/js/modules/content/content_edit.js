var page=1;
var total=0;
$(function(){
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	/**
	 * 点击上传Banner图
	 */
	$("#chooseImgBtnBanner, #imgShowDivBanner").click(function() {
		$("#fileNameBanner").click();
	});
	
	ue.ready(function () {
		if(!Common.isNull(process) && process == "update") {
			if(!Common.isNull(id)) {
				// 获取学堂数据
				getTrainData();
				// 获取课程数据
				getCourseData();
				// 获取专题信息
				getContent(id);
			}else{
				layer.msg("无法获取原文章信息，禁止修改！");
			}
		}else{
			// 获取学堂数据
			getTrainData();
			// 获取课程数据
			getCourseData();
		}
	});
	
	// 打开学堂选择面板
	$("#topAddBtn").click(function(){

		var courseIds = getChoosedCourseIds()+"";
		
		if(!Common.isNull(courseIds)) {
			layer.msg("你已经推荐了课程，不能同时推荐学堂！");
		}else{
			$("#include-style-dialog-panel").show();
		}
		
	});
	
	// 打开课程推荐面板
	$("#courseAddBtn").click(function(){

		var trainIds = getChoosedTrainIds()+"";
		
		if(!Common.isNull(trainIds)) {
			layer.msg("你已经推荐学堂了，不能同时推荐课程！");
		}else{
			$("#course_include-style-dialog-panel").show();
		}
		
	});
	
	// 关键字搜索学堂
	$("#search_btn").click(function(){
		var keywords = $("#msgSearchInput").val();
		if(Common.isNull(keywords)) {
			resetFind();
			return false;
		}
		findTrains(keywords);
	});
	
	// 关键字搜索课程
	$("#search_course_btn").click(function(){
		var keywords = $("#course_msgSearchInput").val();
		if(Common.isNull(keywords)) {
			resetCourseFind();
			return false;
		}
		findCourse(keywords);
	});
	
	// 搜索框聚焦清除内容
	$("#msgSearchInput").click(function(){
		$(this).val("");
	});
	
	// 搜索框聚焦清除内容
	$("#course_msgSearchInput").click(function(){
		$(this).val("");
	});
});


/**获取学堂数据*/
function getTrainData() {
	// 推荐学堂
	var is_top_list = $("#is_top_list").val();
	if(is_top_list == 1) {
		getTrainList(1);
	}
}

/**获取课程数据*/
function getCourseData() {
	// 推荐学堂
	var is_course_list = $("#is_course_list").val();
	if(is_course_list == 1) {
		getCourseList(1);
	}
}

/**获取所有的学堂ID*/
function getChoosedTrainIds() {
	var divs = $("#topList > div:visible");
	var ids = "";
	for(var i=0; i<divs.length; i++) {
		var trainIds = $("#topList > div:visible:eq("+i+")").prop("id");
		if(i==0) {
			ids += trainIds.replace("appmsg_item_", "");
		}else{
			ids += trainIds.replace("appmsg_item_", ",");
		}
	}
	return ids;
}

/**获取所有的课程ID*/
function getChoosedCourseIds() {
	var divs = $("#courseList > div:visible");
	var ids = "";
	for(var i=0; i<divs.length; i++) {
		var trainIds = $("#courseList > div:visible:eq("+i+")").prop("id");
		if(i==0) {
			ids += trainIds.replace("course_appmsg_item_", "");
		}else{
			ids += trainIds.replace("course_appmsg_item_", ",");
		}
	}
	return ids;
}

/**根据关键字搜索学堂*/
function findTrains(keywords) {
	$("#loadingDiv").show();
	
	var getTrainIds = getChoosedTrainIds().split(",");
	var keyDivs = $("#dialog_datalist > div:contains("+keywords+")");
	$("#dialog_datalist > div").hide();
	
	for(var i=0; i<keyDivs.length; i++) {
		var obj = $("#dialog_datalist > div:contains("+keywords+"):eq("+i+")");
		var trainIds =  obj.show();
		
		var trainIds =  obj.prop("id");
		var intId = trainIds.replace("appmsg_col_", "");
		
		for(var j=0; j<getTrainIds.length; j++) {
			if(getTrainIds[j] == intId) {
				$("#dialog_datalist > div:contains("+keywords+"):eq("+i+")").hide();
				$("#loadingDiv").hide();
				return;
			}
		}
	}
	
	$("#loadingDiv").hide();
}

/**根据关键字搜索学堂*/
function findCourse(keywords) {
	$("#loadingDiv").show();
	
	var getCourseIds = getChoosedCourseIds().split(",");
	var keyDivs = $("#course_dialog_datalist > div:contains("+keywords+")");
	$("#course_dialog_datalist > div").hide();
	
	for(var i=0; i<keyDivs.length; i++) {
		var obj = $("#course_dialog_datalist > div:contains("+keywords+"):eq("+i+")");
		var courseIds =  obj.show();
		
		var courseIds =  obj.prop("id");
		var intId = courseIds.replace("course_appmsg_col_", "");
		
		for(var j=0; j<getCourseIds.length; j++) {
			if(getCourseIds[j] == intId) {
				$("#course_dialog_datalist > div:contains("+keywords+"):eq("+i+")").hide();
				$("#loadingDiv").hide();
				return;
			}
		}
	}
	
	$("#loadingDiv").hide();
}

/**重置学堂搜索*/
function resetFind() { 
	$("#loadingDiv").show();
	
	var getTrainIds = getChoosedTrainIds().split(",");
	var allDivs = $("#dialog_datalist > div");
	allDivs.show();
	
	for(var i=0; i<allDivs.length; i++) {
		var obj = $("#dialog_datalist > div:eq("+i+")");
		var trainIds =  obj.prop("id");
		var intId = trainIds.replace("appmsg_col_", "");
		for(var j=0; j<getTrainIds.length; j++) {
			if(getTrainIds[j] == intId) {
				$("#dialog_datalist > div:eq("+i+")").hide();
			}
		}
	}
	
	$("#loadingDiv").hide();
}

/**重置课堂搜索*/
function resetCourseFind() { 
	$("#loadingDiv").show();
	
	var getCourseIds = getChoosedCourseIds().split(",");
	var allDivs = $("#course_dialog_datalist > div");
	allDivs.show();
	
	for(var i=0; i<allDivs.length; i++) {
		var obj = $("#course_dialog_datalist > div:eq("+i+")");
		var courseIds =  obj.prop("id");
		var intId = courseIds.replace("course_appmsg_col_", "");
		for(var j=0; j<getCourseIds.length; j++) {
			if(getCourseIds[j] == intId) {
				$("#course_dialog_datalist > div:eq("+i+")").hide();
			}
		}
	}
	
	$("#loadingDiv").hide();
}

/**
 * 通过ID内容详情
 * @param id 活动ID
 */
function getContent(id) {
	$.ajax({
		url : "getcontentinfo",
		type : "post",
		data : {
			"id" : id
		},
		async: false,
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				info =  json.info;
				setFormValue(info);
			}else{
				layer.msg("不存在专题信息，或者已被管理员删除！");
			}
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**初始化表单值*/
function setFormValue(info) {
	$("#id").val(info.id);
	$("#title").textbox("setValue", info.title);
	$("#description").textbox("setValue", info.description);
	if(!Common.isNull(info.titleImg) && info.titleImg.length > 0) {
		$("#isPicShow").show();
	}else{
		$("#isPicShow").hide();
	}
	if(!Common.isNull(info.bannerImg) && info.bannerImg.length > 0) {
		$("#isPicShowBanner").show();
	}else{
		$("#isPicShowBanner").hide();
	}
	ue.setContent(info.content);
	$("#preview").html(info.content);
	$("#status").combobox("setValue", info.status);
	controlFaceImg(info);
	
	// 相关学堂推荐处理
	if(!Common.isNull(process) && process == "update") {
		var trainIds = info.trainIds;
		if(!Common.isNull(trainIds)){
			var trains = trainIds.split(",");
			for(var i=0; i<trains.length; i++) {
				$("#appmsg_col_btn_"+trains[i]).click();
			}
		}
	}
	
	// 相关课程推荐处理
	if(!Common.isNull(process) && process == "update") {
		var courseIds = info.courseIds;
		if(!Common.isNull(courseIds)){
			var courses = courseIds.split(",");
			for(var i=0; i<courses.length; i++) {
				$("#course_appmsg_col_btn_"+courses[i]).click();
			}
		}
	}

}

/**控制封面图显示（添加/编辑）*/
function controlFaceImg(info) {
	$("#imgArea").hide();
	$("#imgAreaBanner").hide();
	$("#uploadImgArea").show();
	$("#uploadImgAreaBanner").show();
	if(!Common.isNull(info.titleImg) && info.titleImg.length > 0) {
		$("#uploadImgArea").attr("src", imgServer+info.titleImg);
		$("#image").val(info.titleImg);
	}
	if(!Common.isNull(info.bannerImg) && info.bannerImg.length > 0) {
		$("#uploadImgAreaBanner").attr("src", imgServer+info.bannerImg);
		$("#imageBanner").val(info.bannerImg);
	}
}

/** 上传封面图，展示图片 */
function imageProcesser(imgPath, showDiv) {
	if(showDiv == "uploadImgArea") {
		$("#uploadImgArea").prop("src", imgServer + imgPath);
		$("#uploadImgArea").show();
		$("#imgArea").hide();
		$("#image").val(imgPath);
	}else if(showDiv == "uploadImgAreaBanner") {
		$("#uploadImgAreaBanner").prop("src", imgServer + imgPath);
		$("#uploadImgAreaBanner").show();
		$("#imgAreaBanner").hide();
		$("#imageBanner").val(imgPath);
	}
}


/**清空文本*/
function cleanWin() {
	$("#id").val("");
	$("#title").textbox("setValue", "");
	$("#description").textbox("setValue", "");
	$("#isPicShow").hide();
	$("#imageBanner").val("");
	$("#uploadImgAreaBanner").hide();
	$("#imgAreaBanner").show();
	$("#image").val("");
	$("#uploadImgArea").hide();
	$("#imgArea").show();
	$("#status").combobox("setValue", "1");
	ue.setContent("");
	
    ani(0);
}

/**重置修改*/
function cleanBtn() {
	if(!Common.isNull(process) && process == "update") {
		if(!Common.isNull(id)) {
			// 获取学堂数据
			getTrainData();
			// 获取专题信息
			getContent(id);
			layer.msg("重置成功！");
		}else{
			layer.msg("重置失败，内容失效，请退出重试！");
		}
	}else{
		cleanWin();
		layer.msg("重置成功！");
	}
	
}

/**
 * 提交信息
 */
function edit(url) {
	$.messager.confirm("提示", "请你发布前确认你填写的信息是否正确？", function(r) {
	  if (r) {
		var id = $("#id").val();
	
		var title = $("#title").textbox("getValue");
		if(Common.isNull(title)) {
			layer.msg("请输入专题名称");
			return false;
		}
		
		var description = $("#description").textbox("getValue");
		if(Common.isNull(description)) {
			layer.msg("请输入专题描述，用于分享（50字内）！");
			return false;
		}
	
		var titleImg = $("#image").val();
		if(Common.isNull(titleImg)) {
			layer.msg("请上传专题封面图！");
			return false;
		}
		
		var bannerImg = $("#imageBanner").val();
		
		var status = $("#status").combobox("getValue");
		if(Common.isNull(status)) {
			layer.msg("请选择专题发布状态！");
			return false;
		}
		
		var content = ue.getContent();
		if(Common.isNull(content)) {
			layer.msg("请编辑专题内容！");
			return false;
		}
	 	
		$("#main").mask("正在处理中，请稍候......");
		$.ajax({
			url : url,
			type : "post",
			data : {
				"entity.id" : id,
				"entity.title" : title,
				"entity.description" : description,
				"entity.titleImg" : titleImg,
				"entity.content" : content,
				"entity.channelId" : 1,
				"entity.status" : status,
				"entity.bannerImg" : bannerImg,
				"entity.trainIds" : getChoosedTrainIds(),
				"entity.courseIds" : getChoosedCourseIds()
			},
			dataType : "json",
			success : function(json) {
				$("#main").unmask();
	            layer.msg(json.msg);

	        	$.messager.confirm("提示", "操作成功，是否需要返回到列表？", function(r) {

		            if(json.isSuccess && process == "add") {
		            	if (r) {
		  	            	$("#btn_back").click();
		            	}else{
			            	cleanWin();
		            	}
		            }else{	
		                if (r) {
	  	            	    $("#btn_back").click();
		                }
		            }
	        	  
	        	});
			},
			error : function(json) {
				$("#main").unmask();
				layer.msg("服务器繁忙，请稍候再试！！");
			}
		});
	  }
	});
}

/**获取学堂列表*/
function getTrainList(page) {
	$.ajax({
		url : "../../train/traininfo/querylist",
		type : "post",
		data : {
			"page" : page,
			"rows" : 1000,
			"query.status" : 1
		},
		async: false,
		dataType : "json",
		success : function(json) {
			var list = json.rows;
			var temp = $("#include-style-dialog-col").html();
			var currContent = "";

			total = list.length;
				
			for(var i=0; i<list.length; i++) {
				var id = list[i].id;
				var title = list[i].title;
				var time = list[i].categoryName;
				var url = "#";
				var titleImg = list[i].bannerImg;
				var description = list[i].description;
				var eventClick = "sureEvent("+id+",'"+title+"','"+time+"','"+titleImg+"','"+description+"')";
				
				currContent += temp.replace("@!{url}", url)
								   .replace("@!{title}", title)
								   .replace("@!{time}", time)
								   .replace("@!{titleImg}", imgServer+titleImg)
								   .replace("@!{description}", description)
								   .replace("@!{eventClick}", eventClick)
								   .replace("@!{id}", id)
								   .replace("@!{eventid}", id);
			}
			$("#is_top_list").val(2);
			$("#dialog_datalist").html(currContent);
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
	
}

/**获取学堂列表*/
function getCourseList(page) {
	$.ajax({
		url : "../../train/course/querylist",
		type : "post",
		data : {
			"page" : page,
			"rows" : 1000,
			"query.status" : 0
		},
		async: false,
		dataType : "json",
		success : function(json) {
			var list = json.rows;
			var temp = $("#course_include-style-dialog-col").html();
			var currContent = "";

			total = list.length;
				
			for(var i=0; i<list.length; i++) {
				var id = list[i].id;
				var title = list[i].title;
				var time = classTypeStatus(list[i].classType);
				var url = "#";
				var titleImg = list[i].titleImg;
				var description = list[i].description;
				var eventClick = "sureCourseEvent("+id+",'"+title+"','"+time+"','"+titleImg+"','"+description+"')";
				
				currContent += temp.replace("@!{url}", url)
								   .replace("@!{title}", title)
								   .replace("@!{time}", time)
								   .replace("@!{titleImg}", imgServer+titleImg)
								   .replace("@!{description}", description)
								   .replace("@!{eventClick}", eventClick)
								   .replace("@!{id}", id)
								   .replace("@!{eventid}", id);
			}
			$("#is_course_list").val(2);
			$("#course_dialog_datalist").html(currContent);
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
	
}

/**确认学堂推荐*/
function sureEvent(id, title, time, titleImg, description) {
	$("#appmsg_col_"+id).hide();
	
	// 判断是否已经添加过，若已添加，则显示即可
	if($("#appmsg_item_"+id).length>0) {
		$("#appmsg_item_"+id).show();
	}else{
		var eventClick = "delEvent("+id+")";
		
		var showItem = $("#include_style_show_item_one").html()
				     .replace("@!{title}", title)
				     .replace("@!{time}", time)
				     .replace("@!{titleImg}", imgServer+titleImg)
				     .replace("@!{description}", description)
					 .replace("@!{eventClick}", eventClick)
					 .replace("@!{id}", id);
		
		$("#topList").append(showItem);
	}

	// layer.msg("学堂推荐成功！");
}

/**确认课程推荐*/
function sureCourseEvent(id, title, time, titleImg, description) {
	$("#course_appmsg_col_"+id).hide();
	
	// 判断是否已经添加过，若已添加，则显示即可
	if($("#course_appmsg_item_"+id).length>0) {
		$("#course_appmsg_item_"+id).show();
	}else{
		var eventClick = "delCourseEvent("+id+")";
		
		var showItem = $("#course_include_style_show_item_one").html()
				     .replace("@!{title}", title)
				     .replace("@!{time}", time)
				     .replace("@!{titleImg}", imgServer+titleImg)
				     .replace("@!{description}", description)
					 .replace("@!{eventClick}", eventClick)
					 .replace("@!{id}", id);
		
		$("#courseList").append(showItem);
	}

	// layer.msg("学堂推荐成功！");
}

/**删除学堂推荐*/
function delEvent(id) {
	$("#appmsg_col_"+id).show();
	$("#appmsg_item_"+id).hide();
	
	layer.msg("学堂推荐删除成功！");
}

/**删除课程推荐*/
function delCourseEvent(id) {
	$("#course_appmsg_col_"+id).show();
	$("#course_appmsg_item_"+id).hide();
	
	layer.msg("课程推荐删除成功！");
}
