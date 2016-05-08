$(function(){
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtnBanner, #imgShowDivBanner").click(function() {
		$("#fileNameBanner").click();
	});

	ue.ready(function () {
		if(!Common.isNull(process) && process == "update") {
			if(!Common.isNull(id)) {
				getContent(id);
			}else{
				layer.msg("无法获取原资讯信息，禁止修改！");
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
				layer.msg("不存在资讯信息，或者已被管理员删除！");
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
	$("#status").combobox("setValue", info.status);
	controlFaceImg(info);
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
			layer.msg("请输入资讯标题");
			return false;
		}
		
		var description = $("#description").textbox("getValue");
		if(Common.isNull(description)) {
			layer.msg("请输入咨资讯描述，用于分享（50字内）！");
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
			layer.msg("请选择资讯发布状态！");
			return false;
		}
		
		var content = ue.getContent();
		if(Common.isNull(content)) {
			layer.msg("请编辑资讯内容！");
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
				"entity.channelId" : 2,
				"entity.status" : status,
				"entity.bannerImg" : bannerImg
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
