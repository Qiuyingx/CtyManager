$(function() {
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
});

/** 上传封面图，展示图片 */
function imageProcesser(imgPath) {
	$("#uploadImgArea").prop("src", imgServer + imgPath);
	$("#uploadImgArea").show();
	$("#imgArea").hide();
	$("#image").val(imgPath);
}

function complete(uri) {
	var id = $("#id").val();

	var title = $("#title").textbox("getValue");
	if (Common.isNull(title)) {
		layer.msg("请输入培训室的名称！");
		return false;
	}
	var description = $("#description").textbox("getValue");
	if (Common.isNull(description)) {
		layer.msg("请对培训室做简要介绍！");
		return false;
	}
	var businessHours = $("#businessHours").textbox("getValue");
	if (Common.isNull(businessHours)) {
		layer.msg("请输入培训室的培训时间！");
		return false;
	}
	var tel = $("#tel").textbox("getValue");
	if (Common.isNull(tel)) {
		layer.msg("请输入培训室的联系方式！");
		return false;
	}
	var address = $("#address").textbox("getValue");
	if (Common.isNull(address)) {
		layer.msg("请输入培训室的详细地址！");
		return false;
	}
	var longitude = $("#longitude").textbox("getValue");
	if (Common.isNull(longitude)) {
		layer.msg("请输入培训室的经度，可在百度上获取！");
		return false;
	}
	var latitude = $("#latitude").textbox("getValue");
	if (Common.isNull(latitude)) {
		layer.msg("请输入培训室的纬度，可在百度上获取！");
		return false;
	}
	var bannerImg = $("#image").val();
	if (Common.isNull(bannerImg)) {
		layer.msg("请上传培训室的封面图！");
		return false;
	}
	var content = ue.getContent();
	if (Common.isNull(content)) {
		layer.msg("请编辑培训室的详细介绍！");
		return false;
	}

	$("#main").mask("正在处理中，请稍候......");
	$.ajax({
		url : uri,
		type : "post",
		data : {
			"entity.id" : id,
			"entity.title" : title,
			"entity.description" : description,
			"entity.businessHours" : businessHours,
			"entity.tel" : tel,
			"entity.address" : address,
			"entity.longitude" : longitude,
			"entity.latitude" : latitude,
			"entity.bannerImg" : bannerImg,
			"entity.content" : content
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
			layer.msg(json.msg);
			if (json.isSuccess && uri == "complete") {
				$("#submit_open").text("确认修改");
				$("#submit_open").attr("onclick",
						"javascript:complete('update')");
			}
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg("服务器繁忙，请稍候再试！！");
		}
	});
}

/**查看详情*/
function lookinfocontent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.open(imgServer+"Miqu/share/shareTrain.do?trainId="+row.id ,'_blank');
}