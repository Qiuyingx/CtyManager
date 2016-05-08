$(function(){
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	/**
	 * 多图上传
	 */
	$("#chooseImgBtnMore, #imgShowDivMore").click(function() {
		upImage();
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
	$("#goodsName").textbox("setValue", info.goodsName);
	$("#price").numberspinner("setValue", info.price);
	$("#stockLimit").numberspinner("setValue", info.stockLimit);
	$("#status").combobox("setValue", info.status+"");
	$("#image").val(info.image);
	$("#imageMore").val(info.listImage);
	ue.setContent(info.remark);
	controlFaceImg(info);
}

/**控制封面图显示（添加/编辑）*/
function controlFaceImg(info) {
	$("#imgArea").hide();
	$("#uploadImgArea").show();
	$("#imgShowDivMore").hide();
	$("#uploadImgAreaMore").show();
	$("#uploadImgArea").attr("src", imgServer+info.image);
	moreImagesProcess(info.listImage);
}

/** 上传封面图，展示图片 */
function imageProcesser(imgPath, showDiv) {
	if(showDiv == "uploadImgArea") {
		$("#uploadImgArea").prop("src", imgServer + imgPath);
		$("#uploadImgArea").show();
		$("#imgArea").hide();
		$("#image").val(imgPath);
	}
}

/**多图解析显示*/
function uploadImagesProcess(images) {
	moreImagesProcess(images);
	$("#imageMore").val(images);
	$("#imgShowDivMore").hide();
	$("#uploadImgAreaMore").show();
}

function moreImagesProcess(images) {
	var arrayImages = Common.getPicsPath(imgServer, images);
	var imgTags = "";
	if(arrayImages.length>0) {
		for(var i=0; i<arrayImages.length;i++){
			imgTags += "<img src=\""+arrayImages[i].src+"\" style=\"width:320px; height:160px; margin-left:5px;\"/>";
		}
	}
	$("#uploadImgAreaMore").html(imgTags);
}

/**清空文本*/
function cleanWin() {
	$("#id").val("");
	$("#goodsName").textbox("setValue", "");
	$("#price").numberspinner("setValue", 0);
	$("#stockLimit").numberspinner("setValue", 0);
	$("#status").combobox("setValue", "");
	$("#image").val("");
	$("#imageMore").html("");
	ue.setContent("");
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

	var goodsName = $("#goodsName").textbox("getValue");
	if(Common.isNull(goodsName)) {
		layer.msg("请输入商品名称！");
		return false;
	}
	var price = $("#price").numberspinner("getValue");
	if(Common.isNull(price)) {
		layer.msg("请输入商品价格！");
		return false;
	}
	var stockLimit = $("#stockLimit").numberspinner("getValue");
	if(Common.isNull(stockLimit)) {
		layer.msg("请输入库存量！");
		return false;
	}
	var status = $("#status").combobox("getValue");
	if(Common.isNull(status)) {
		layer.msg("请确认商品的状态！");
		return false;
	}
	var image = $("#image").val();
	if(Common.isNull(image)) {
		layer.msg("请上传商品封面图！");
		return false;
	}
	var listImage = $("#imageMore").val();
	if(Common.isNull(listImage)) {
		layer.msg("请上传商品的图组！");
		return false;
	}
	var content = ue.getContent();
	if(Common.isNull(content)) {
		layer.msg("请编辑活动内容！");
		return false;
	}
	$("#main").mask("正在处理中，请稍候......");
	$.ajax({
		url : url,
		type : "post",
		data : {
			"entity.id" : id,
			"entity.goodsName" : goodsName,
			"entity.price" : price,
			"entity.stockLimit" : stockLimit,
			"entity.image" : image,
			"entity.remark" : content,
			"entity.status" : status,
			"entity.listImage" : listImage
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
			layer.msg("服务器繁忙，请稍候再试！！");
		}
	});
	
}
