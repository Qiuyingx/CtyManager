/**
 * 条件检索
 */
function query() {
	//获取参数
	var nickname = $("#nickNameSearch").textbox("getValue");
	var tel = $("#telSearch").textbox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	var yardids = $("#yardidsSearch").combotree("getValues");
	var reportType = $("#reportTypeSearch").combotree("getValues");
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.nickname" : nickname,
		"query.tel" : tel,
		"query.startTime" : startTime,
		"query.endTime" : endTime,
		"query.yardids" : yardids+"",
		"query.reportType" : reportType+""
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

function flush() {
	$("#nickNameSearch").textbox("setValue", "");
	$("#telSearch").textbox("setValue", "");
	$("#startTimeSearch").datebox("setValue", "");
	$("#endTimeSearch").datebox("setValue", "");
	$("#yardidsSearch").combotree("setValues", "");
	$("#reportTypeSearch").combotree("setValues", "");
}

/**
 * 查看帖子详情
 */
function lookInfo() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要查看详情的帖子！");
		return false;
	}
	
	$.ajax({
		url : "getpostinfo",
		type : "post",
		data : {
			"query.postId" : row.targetId
		},
		dataType : "json",
		success : function(json) {
		  alert("---->>"+json);
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
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

