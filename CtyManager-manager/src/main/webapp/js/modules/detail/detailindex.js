//======================条件筛选重置===============================================

/**
 * 邻豆条件检索
 */
function lindouquery() {
	//获取参数
	var remark = $("#remarkLindou").combotree("getValues");
	var startTime = $("#startTimeLindou").datebox("getValue");
	var endTime = $("#endTimeLindou").datebox("getValue");
	// 设置参数
	var queryParams = $("#lindougrid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"lindou.remark" : remark+"",
		"lindou.startTime" : startTime,
		"lindou.endTime" : endTime
	});

	// 重新加载datagrid的数据
	$("#lindougrid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function lindouflush(){
	$("#remarkLindou").combotree("setValues", "");
	$("#startTimeLindou").datebox("setValue", "");
	$("#endTimeLindou").datebox("setValue", "");
}

/**
 * 经验条件检索
 */
function expquery() {
	//获取参数
	var remark = $("#remarkExp").combotree("getValues");
	var startTime = $("#startTimeExp").datebox("getValue");
	var endTime = $("#endTimeExp").datebox("getValue");
	// 设置参数
	var queryParams = $("#expgrid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"exp.remark" : remark+"",
		"exp.startTime" : startTime,
		"exp.endTime" : endTime
	});

	// 重新加载datagrid的数据
	$("#expgrid").datagrid("reload");
}

/**
 * 经验重置筛选条件
 */
function expflush(){
	$("#remarkExp").combotree("setValues", "");
	$("#startTimeExp").datebox("setValue", "");
	$("#endTimeExp").datebox("setValue", "");
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

/**浏览封面图*/
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