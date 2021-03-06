//======================条件筛选重置===============================================

/**
 * 专题条件检索
 */
function query() {
	//获取参数
	var name = $("#nameSearch").textbox("getValue");
	var tel = $("#telSearch").textbox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.name" : name,
		"query.tel" : tel,
		"query.startTime" : startTime,
		"query.endTime" : endTime
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function flush(){
	$("#nameSearch").textbox("setValue", "");
	$("#telSearch").textbox("setValue", "");
	$("#startTimeSearch").datebox("setValue", "");
	$("#endTimeSearch").datebox("setValue", "");
}


$(function(){
	/**双击查看单元格内容*/
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});

	$("#nameSearch, #telSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});

	$("#startTime, #endTime").datebox({
		onSelect : function(date) {
			query();
		}
	});
});

/**草稿/发布*/
function changeStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想操作的课程！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定要变更为<span style=\"color:red\">" + (status == 0 ? "草稿" : "发布") + "</span>状态吗？", function(r) {
		if (r) {
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"id" : row.id,
					"status" : Math.abs(status - 1)
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"status" : Math.abs(status - 1)
						});
					}
					layer.msg(json.msg);
				},
				error : function(json) {
					$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**添加专题*/
function addContent() {
	window.location.href="../../train/traincourse/content_edit?process=add&trainId="+trainId;
}

/**编辑专题*/
function updateContent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.location.href="../../train/traincourse/content_edit?process=update&id="+row.id+"&trainId="+trainId;
}

/**数据导出*/
function downData() {
	$("#main").mask("正在统计中，请稍候......");
	$.ajax({
		url : "downdata",
		type : "post",
		data : {
			"courseId" : courseId
		},
		dataType : "json",
		success : function(json) {
			if(json.isSuccess) {
	        	$("#mframe").prop("src","../.."+json.msg);
			}
			$("#main").unmask();
		},
		error : function(json) {
			$("#main").unmask();
			alert( "服务器繁忙，请稍候再试！！");
		}
	});
}