$(function(){
	//城市 院子联动选择
	$("#city").combobox({
		onSelect : function(record) {
			var id = record.id;
			getYards(id);
		}
	});
	//活动报名人数控制
	$("#status").combobox({
		onSelect : function(record) {
			var id = record.id;
			if(id == 1) {
				//不可报名
				$("#countLimit").numberspinner("disable");
			}else{
				//可报名
				$("#countLimit").numberspinner("enable");
			}
		}
	});

	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	// 活动是否面向所有社区
	$("#isAllYards").change(function(){
		isFaceAll();
	});

	ue.ready(function () {
		if(!Common.isNull(process) && process == "update") {
			if(!Common.isNull(id)) {
				getContent(id);
			}else{
				layer.msg("无法获取原文章信息，禁止修改！");
			}
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
	$("#id").val(info.id);
    $("#actTitle").textbox("setValue",info.actTitle);
	$("#description").textbox("setValue", info.description);
	if(info.isDisable == 1){
		//不可报名
		$("#countLimit").numberspinner("disable");
	}else{
		//可报名
		$("#countLimit").numberspinner("enable");
	}
	$("#countLimit").numberspinner("setValue",info.countLimit);
	$("#status").combobox("setValue", info.isDisable+"");
	$("#startTime").datetimebox("setValue", info.startTime);
	$("#endTime").datetimebox("setValue",info.endTime);
	controlFaceImg(info);
	ue.setContent(info.content);
	$("#image").val(info.image);
	var isAlls = info.isAllYards;
	if(isAlls == 1) {
		// 面向所有社区
		$("#isAllYards").prop("checked", true);
	}else{
		// 面向指定社区
		$("#isAllYards").prop("checked", false);
	}
	isFaceAll();
	$("#city").combobox("setValue", info.cityId);
	getYards(info.cityId);
	setTimeout("setYs('"+info.courtyardIds+"')", 100);
}

/**控制封面图显示（添加/编辑）*/
function controlFaceImg(info) {
	$("#uploadImgArea").show();
	$("#uploadImgArea").attr("src", imgServer+info.image);
	$("#imgArea").hide();
}

/** 上传封面图，展示图片 */
function imageProcesser(imgPath) {
	$("#uploadImgArea").prop("src", imgServer + imgPath);
	$("#uploadImgArea").show();
	$("#imgArea").hide();
	$("#image").val(imgPath);
}

/**是否面向所有社区*/
function isFaceAll() {
	var isCheck = $("#isAllYards").prop("checked");
	if(isCheck){
		$("#city").combobox({
			disabled:true
		});
		$("#yards").combotree({
			disabled:true
		});
	}else{
		$("#city").combobox({
			disabled:false
		});
		$("#yards").combotree({
			disabled:false
		});
	}
}

/**通过城市Id填充院子列表*/
function getYards(id){
	if(!Common.isNull(id)) {
		$("#yards").combotree({
			url : "../../common/getyardcombobycity?cityId="+id
		});
	}else{
		$("#yards").combotree({
			url : "../../js/modules/data/json/null.js"
		});
	}
}

function setYs(ids){
	$("#yards").combotree("setValues", ids);
}

/**清空文本*/
function cleanWin() {
	$("#id").val("");
    $("#actTitle").textbox("setValue","");
	$("#city").combobox("setValue", "");
	$("#startTime").datetimebox("setValue","");
	$("#endTime").datetimebox("setValue","");
	$("#status").combobox("setValue","");
	$("#countLimit").numberspinner("setValue","");
	$("#image").val("");
	$("#uploadImgArea").hide();
	$("#imgArea").show();
	ue.setContent("");
	$("#isAllYards").prop("checked", true);
	isFaceAll();
	$("#description").textbox("setValue", "");
}

/**重置修改*/
function cleanBtn() {
	if(!Common.isNull(process) && process == "update") {
		if(!Common.isNull(id)) {
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
	var id = $("#id").val();

	var actTitle = $("#actTitle").textbox("getValue");
	if(Common.isNull(actTitle)){
		layer.msg( "请输入活动标题！");
		return false;
	}
	
	var description = $("#description").textbox("getValue");
	if(Common.isNull(description)) {
		layer.msg("请输入活动描述 ，用于分享（50字内）！");
		return false;
	}
	
	var yards = $("#yards").combotree("getValues");
	var yardsName = "面向所有院子";
	var cityId = $("#city").combobox("getValue");
	var isAllYards = 1;
	var isPushAll = 2;
	
	var isCheck = $("#isAllYards").prop("checked");
	if(isCheck){
	}else{
		if(Common.isNull(cityId)) {
			layer.msg("请选择城市！");
			return false;
		}
		if (Common.isNull(yards)) {
			layer.msg( "请选择院子！");
			return false;
		}
		isAllYards = 0;
		yardsName = $("#yards").combotree("getText");
	}
	var isPushCheck = $("#isPushAll").prop("checked");
	if(isPushCheck) {
		isPushAll = 1;
	}
	var startTime = $("#startTime").datetimebox("getValue");
	if(Common.isNull(startTime)) {
		layer.msg("请选择活动开始时间！");
		return false;
	}
	var endTime = $("#endTime").datetimebox("getValue");
	if(Common.isNull(endTime)) {
		layer.msg("请选择活动结束时间！");
		return false;
	}
	if (!Common.compareDate(startTime, endTime)) {
		layer.msg("结束日期必须大于起始日期！");
		return false;
	}
	var status = $("#status").combobox("getValue");
	if(Common.isNull(status)) {
		layer.msg( "请选择活动的报名状态！");
		return false;
	}
	var countLimit = $("#countLimit").numberspinner("getValue");
	if(status == 0) {
		if(Common.isNull(countLimit)) {
			layer.msg( "请输入活动报名总人数限制！");
			return false;
		}
	}
	var image = $("#image").val();
	if(Common.isNull(image)) {
		layer.msg( "请上传活动封面图！");
		return false;
	}
	var content = ue.getContent();
 	if(Common.isNull(content)) {
		layer.msg( "请编辑活动内容！");
		return false;
	}
 	var atTarget = $("#atTargetUrl").val();
 	
	$("#main").mask("正在处理中，请稍候......");
	$.ajax({
		url : url,
		type : "post",
		data : {
			"entity.id" : id,
			"entity.courtyardId" : 0,
			"entity.actTitle" : actTitle,
			"entity.startTime" : startTime,
			"entity.endTime" : endTime,
			"entity.content" : content,
			"entity.image" : image,
			"entity.countLimit" : countLimit,
			"entity.isDisable" : status,
			"entity.isAllYards" : isAllYards,
			"entity.courtyardIds" : yards+"",
			"entity.courtyardNames" : yardsName,
			"entity.cityId" : cityId,
			"entity.isPushAll" : 1,
			"entity.description" : description,
			"entity.atTarget" : atTarget
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
            layer.msg(json.msg);
            if(json.isSuccess && process == "add") {
            	cleanWin();
            }
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}


/**修改直接添加@关联关系*/
function onlineAddRelations(ids, nickName) {
	 $.ajax({
		url : "../../attarget/info/add",
		type : "post",
		data : {
			"entity.atTargetId" : ids,
			"entity.atNickName" : nickName,
			"entity.scene" : 4,
			"entity.append" : id
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
		},
		error : function(json) {
			layer.msg( "服务器繁忙，操作失败！！");
		}
	});
}

/**添加后台处理@关联关系  保存@对象列表  格式如下：id|nickName;nickName*/
function otherAddRelations(id, nickName) {
	var atList = $("#atTargetUrl").val();
	var str = id+"|"+nickName+";";

	// 判断是否已经存在
	if(atList.indexOf(str) == -1) {
		atList += str;
	}
	
	$("#atTargetUrl").val(atList);
}

/**添加@关联关系*/
function addRelations(id, nickName) {

	if (process == "add") {
		otherAddRelations(id, nickName);
	}else{
		onlineAddRelations(id, nickName);
	}
	
}

/**管理--修改@关联关系*/
function managerUpdateAt() {
	$("#main").mask("正在搜索，请稍后...");
	var atList = "";
	$.ajax({
		url : "../../attarget/info/query",
		type : "post",
		data : {
			"entity.scene" : 4,
			"entity.append" : id
		},
		async: false,
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
			var data = json.data;
			if(data.length <= 0) {
				layer.msg("已不存在@对象了！");
				return false;
			}
			
			for(var i=0; i<data.length; i++) {
				var nickName = data[i].atNickName;
				var id = data[i].atTargetId;
				var scene = data[i].scene;
				var append = data[i].append;
				
				atList += "<div style=\"margin-left:10px;\">";
				atList += "<span style=\"margin-left:3px\">"+nickName+"</span>";
				atList += "<span style=\"color:green; float:right; cursor:pointer; margin-top:0px; margin-right:15px;\" onclick=\"delAtTarget("+id+",'"+nickName+"',"+scene+","+append+")\">删除</span>"
				atList += "</div><br/>";
			}
		},
		error : function(json) {
			layer.msg( "服务器繁忙，搜索失败！！");
		}
	});
	return atList;
}

/**管理--添加@关联关系*/
function managerAddAt() {
	var atList = $("#atTargetUrl").val();
	if(Common.isNull(atList)) {
		layer.msg("已不存在@对象了！");
		return false;
	}
	var atObject = new Array();
	atObject = atList.split(";");
	var showList = "";
	
	for(var i=0;i<atObject.length;i++) {
		var attrs = new Array();
		attrs = atObject[i].split("|");
		if(!Common.isNull(attrs[0])) {
			showList += "<div style=\"margin-left:10px;\">";
			showList += "<span style=\"margin-left:3px\">"+attrs[1]+"</span>";
			showList += "<span style=\"color:green; float:right; cursor:pointer; margin-top:0px; margin-right:15px;\" onclick=\"managerDelAt("+attrs[0]+",'"+attrs[1]+"')\">删除</span>"
			showList += "</div><br/>";
		}
	}
	return showList;
}

/**管理--删除@关联关系*/
function managerDelAt(id, nickName) {
	var atList = $("#atTargetUrl").val();
	if(Common.isNull(atList)) {
		layer.msg("已不存在@对象了！");
		return false;
	}
	if(!Common.isNull(id) && !Common.isNull(nickName)) {
		var reg=new RegExp(id+"\\|"+nickName+";","g");
		$("#atTargetUrl").val(atList.replace(reg,""));
		$("#atManagerList").html(managerAddAt());

		var reg=new RegExp("@"+nickName,"g");
		ue.setContent(ue.getContent().replace(reg,""));
		
		layer.msg("删除成功！");
		return true;
	}
	layer.msg("删除失败！");
}

/**管理@选项卡*/
function adminAtTarget(){

	if (process == "add") {
		return managerAddAt();
	}else{
		return managerUpdateAt();
	}
	
}