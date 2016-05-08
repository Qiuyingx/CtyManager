/**
 * 条件检索
 */
function query() {
	//获取参数
	var nickName = $("#nickNameSearch").textbox("getValue");
	var userMobile = $("#userMobileSearch").textbox("getValue");
	var courtyardIds = $("#courtyardIdsSearch").combotree("getValues");
	var inviTypeIds = $("#inviTypeIdsSearch").combotree("getValues");
	var toStartTime = $("#toStartTimeSearch").datebox("getValue");
	var toEndTime = $("#toEndTimeSearch").datebox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.nickName" : nickName,
		"query.userMobile" : userMobile,
		"query.courtyardIds" : courtyardIds+"",
		"query.inviTypeIds" : inviTypeIds+"",
		"query.toStartTime" : toStartTime,
		"query.toEndTime" : toEndTime,
		"query.startTime" : startTime,
		"query.endTime" : endTime
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}
/**
 * 筛选条件重置 
 */
function flush() {
	$("#nickNameSearch").textbox("setValue", "");
	$("#userMobileSearch").textbox("setValue", "");
	$("#courtyardIdsSearch").combotree("setValues", "");
	$("#inviTypeIdsSearch").combotree("setValues", "");
	$("#toStartTimeSearch").datebox("setValue", "");
	$("#toEndTimeSearch").datebox("setValue", "");
	$("#startTimeSearch").datebox("setValue", "");
	$("#endTimeSearch").datebox("setValue", "");
}

/**
 * 删除一条记录
 */
function del() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想删除的数据！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除该条记录吗？", function(r) {
		if (r) {
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"post.id" : row.id
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.deleteSelectedRow("grid");
					}

                    Common.msgslide(json.msg);
				},
				error : function(json) {
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

$(function(){
	/**
	 * 双击查看单元格内容
	 */
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});	
});

/**浏览图片*/
function lookPics(httpUrl) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想浏览的数据！");
		return false;
	}
	var images = row.imageNames;
	if(Common.isNull(images)) {
		layer.msg( "暂时无图片");
		return false;
	}
	Common.showPictures(httpUrl, images);
}

/**查看评论详情*/
function lookSignup() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择邀约！");
		return false;
	}
	window.location.href="../reply/replyindex?postId="+row.id+"&contentType=1";
}