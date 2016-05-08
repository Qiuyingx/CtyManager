$(function() {
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	/**
	 * 类型选择
	 */
/*	$("#typeParent").combobox({
		onChange : function(newValue, oldValue) {
			var comUrl = "../../js/data/json/classType_qinzi.json";
			if(newValue == 2) {
				comUrl = "../../js/data/json/classType_pinzhi.json";
			}
			$("#typeId").combobox({
				url : comUrl
			});
		}
	});*/
	
	ue.ready(function () {
		if(!Common.isNull(process) && process == "update") {
			if(!Common.isNull(id)) {
				getContent(id);
			}else{
				layer.msg("无法获取原文章信息，禁止修改！");
			}
		}
	});
	
	$("#isAct").change(function() {
		var isChecked = $(this).prop("checked")
		if(isChecked) {
			$("#startTr").show();
			$("#endTr").show();
		}else{
			$("#startTr").hide();
			$("#endTr").hide();
		}
	});
});
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
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				info =  json.info;
				setFormValue(info);
			}else{
				layer.msg("不存在活动信息，或者已被管理员删除！");
			}
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**初始化表单值*/
function setFormValue(info) {
	$("#title").textbox("setValue", info.title);
	$("#description").textbox("setValue", info.description);
	$("#hours").textbox("setValue", info.hours);
	$("#studyTime").textbox("setValue", info.studyTime);
	$("#tel").textbox("setValue", info.tel);
	$("#price").numberspinner("setValue", info.price);
	/*$("#priceunit").textbox("setValue", info.priceunit);*/
	$("#copies").numberspinner("setValue", info.copies);
	$("#address").textbox("setValue", info.address);
	$("#longitude").textbox("setValue", info.longitude);
	$("#latitude").textbox("setValue", info.latitude);
	
	$("#cityId").combobox("setValue", info.cityId);
	/*$("#typeParent").combobox("setValue", info.typeParent);*/
	$("#typeId").combobox("setValue", info.typeId);

	$("#uploadImgArea").attr("src", imgServer+info.titleImg);
	$("#image").val(info.titleImg);
	
	ue.setContent(info.content);
	
	if(info.classType == "2") {
		$("#isAct").prop("checked", true);
		$("#startTr").show();
		$("#endTr").show();
		$("#startTime").datetimebox("setValue", mills2DateTime(info.startTime));
		$("#endTime").datetimebox("setValue", mills2DateTime(info.endTime));
	}else{
		$("#startTr").hide();
		$("#endTr").hide();
		$("#isAct").prop("checked", false);
	}
}


/** 上传封面图，展示图片 */
function imageProcesser(imgPath, isPicShow) {
	if(isPicShow == "logos") {
		$("#uploadImgLogo").prop("src", imgServer + imgPath);
		$("#uploadImgLogo").show();
		$("#imgLogo").hide();
		$("#imageLogo").val(imgPath);
	}else if(isPicShow == "faceImgs") {
		$("#uploadImgArea").prop("src", imgServer + imgPath);
		$("#uploadImgArea").show();
		$("#imgArea").hide();
		$("#image").val(imgPath);
	}
}

/** 清空文本 */
function cleanWin() {
	$("#title").textbox("setValue", "");
	$("#description").textbox("setValue", "");
	$("#hours").textbox("setValue", "");
	$("#studyTime").textbox("setValue", "");
	$("#tel").textbox("setValue", "");
	$("#price").numberspinner("setValue", "");
	$("#copies").numberspinner("setValue", "");
	$("#address").textbox("setValue", "");
	$("#longitude").textbox("setValue", "");
	$("#latitude").textbox("setValue", "");
	/*$("#priceunit").textbox("setValue", "");*/

	$("#image").val("");

	$("#uploadImgArea").hide();
	$("#imgArea").show();
	
	ue.setContent("");
	$("#startTr").hide();
	$("#endTr").hide();
	$("#isAct").prop("checked", false);
	
	ani(0);
}

function edit(url) {
	$.messager.confirm("提示", "请你发布前确认你填写课程的价格、时间等信息是否正确？", function(r) {
		if(r) {
			if(url == "update") {
				if(Common.isNull(id)) {
					layer.msg("无法获取课程信息！");
					return false;
				}
			}
				
			var title = $("#title").textbox("getValue");
			if (Common.isNull(title)) {
				layer.msg("请输入课程的名称！");
				return false;
			}
			var description = $("#description").textbox("getValue");
			if(Common.isNull(description)) {
				layer.msg("请输入课程描述，用作课程分享！");
				return false;
			}
		/*	var typeParent = $("#typeParent").combobox("getValue");
			if(Common.isNull(typeParent)) {
				layer.msg("请选择课程一级类别！");
				return false;
			}*/
			var typeId = $("#typeId").combobox("getValue");
			if(Common.isNull(typeId)) {
				layer.msg("请选择培训室的类型！");
				return false;
			}
			var typeName = $("#typeId").combobox("getText");
			
			var hours = $("#hours").textbox("getValue");
			if (Common.isNull(hours)) {
				layer.msg("请输入课时！");
				return false;
			}
			var studyTime = $("#studyTime").textbox("getValue");
			if(Common.isNull(studyTime)) {
				layer.msg("请输入课程时间！");
				return false;
			}
			var tel = $("#tel").textbox("getValue");
			if (Common.isNull(tel)) {
				layer.msg("请输入联系方式！");
				return false;
			}
			if(isNaN(tel)) {
				layer.msg("手机号输入不合法，请确认输入！");
				return false;
			}
			if(tel.length != 11) {
				layer.msg("您输入的手机号码有误，请确认输入！");
				return false;
			}
			var price = $("#price").numberspinner("getValue");
			if(Common.isNull(price)) {
				layer.msg("请输入课程的价格！");
				return false;
			}
/*			var priceunit = $("#priceunit").textbox("getValue");
			if(Common.isNull(priceunit)) {
				layer.msg("请输入价格单位！");
				return false;
			}*/
			var copies = $("#copies").numberspinner("getValue");
			if(Common.isNull(copies)) {
				layer.msg("请输入课程的报名名额！");
				return false;
			}
			var cityId = $("#cityId").combobox("getValue");
			if(Common.isNull(cityId)) {
				layer.msg("请选择所在城市！");
				return false;
			}
			var address = $("#address").textbox("getValue");
			if (Common.isNull(address)) {
				layer.msg("请输入详细地址！");
				return false;
			}
			var isAct = 1;
			var startTime = "";
			var endTime = "";
		
			var isChecked = $("#isAct").prop("checked");
			if(isChecked) {
				isAct = 2;
				startTime = $("#startTime").datetimebox("getValue");
				endTime = $("#endTime").datetimebox("getValue");
				if(Common.isNull(startTime)) {
					layer.msg("请选择活动开始时间！");
					return false;
				}
				if(Common.isNull(endTime)) {
					layer.msg("请选择活动结束时间！");
					return false;
				}
			}
			
			var longitude = $("#longitude").textbox("getValue");
			if (Common.isNull(longitude)) {
				layer.msg("请输入培训室的经度，可在百度上获取！");
				return false;
			}
			if(isNaN(longitude)) {
				layer.msg("经度输入不合法，请确认输入！");
				return false;
			}
			if(longitude < 0 || longitude > 180) {
				layer.msg("您输入的经度有误，请确认输入！");
				return false;
			}
			var latitude = $("#latitude").textbox("getValue");
			if (Common.isNull(latitude)) {
				layer.msg("请输入培训室的纬度，可在百度上获取！");
				return false;
			}
			if(isNaN(latitude)) {
				layer.msg("纬度输入不合法，请确认输入！");
				return false;
			}
			if(latitude < 0 || latitude > 90) {
				layer.msg("您输入的纬度有误，请确认输入！");
				return false;
			}
			var titleImg = $("#image").val();
			if (Common.isNull(titleImg)) {
				layer.msg("请上传课程的封面图！");
				return false;
			}
			var content = ue.getContent();
			if (Common.isNull(content)) {
				layer.msg("请编辑课程的详细介绍！");
				return false;
			}
		
			$("#main").mask("正在处理中，请稍候......");
			$.ajax({
				url : url,
				type : "post",
				data : {
					"entity.id" : id,
					"entity.trainId" : trainId,
					"entity.typeParent" : 1,
					"entity.typeId" : typeId,
					"entity.typeName" : typeName,
					"entity.cityId" : cityId,
					"entity.title" : title,
					"entity.description" : description,
					"entity.titleImg" : titleImg,
					"entity.hours" : hours,
					"entity.tel" : tel,
					"entity.content" : content,
					"entity.address" : address,
					"entity.longitude" : longitude,
					"entity.latitude" : latitude,
					"entity.price" : price,
					"entity.priceunit" : "",
					"entity.studyTime" : studyTime,
					"entity.copies" : copies,
					"entity.classType" : isAct,
					"entity.startTimeStr" : startTime,
					"entity.endTimeStr" : endTime
				},
				dataType : "json",
				success : function(json) {
					$("#main").unmask();
					layer.msg(json.msg);
		            if(json.isSuccess && process == "add") {
		            	cleanWin();
		            }

		            if(json.isSuccess && process == "update") {
		            	$("#btn_back").click();
		            }
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}