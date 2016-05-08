/**
 * 列表双击事件
 */
$(function() {
	var options = $("#grid").datagrid("options");
	options.onClickRow = function(index, row) {
		$("#activityCategoryName").textbox("setValue", row.activityCategoryName);
		$("#oldActivityCategoryName").val(row.activityCategoryName);
		$("#activityCategoryId").val(row.activityCategoryId);
	};
});

/**
 * 查询按钮
 */
function query() {
	Common.queryGrid("grid", {
		"condition.dataName" : $("#activityCategoryName").textbox("getValue")
	});
}

/**
 * 提交
 * 
 * @returns
 */
function add() {
	var activityCategoryName = $("#activityCategoryName").textbox("getValue");
	if (Common.isNull(activityCategoryName)) {
		$.messager.alert("提示", "请填写想新增的分类名称！");
		return false;
	}
	
	$.ajax({
		url : "add",
		type : "post",
		data : {
			"form.activityCategoryName" : activityCategoryName
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				// 刷新列表
				Common.refreshGrid("grid");
				// 清空输入框
				$("#activityCategoryName").textbox("setValue", "");
			}

			Common.msgslide(json.msg);
		},
		error : function(json) {
			$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
		}
	});
}

function update() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想修改的活动分类名称！");
		return false;
	}
	
	var activityCategoryId = $("#activityCategoryId").val();
	var activityCategoryName = $("#activityCategoryName").textbox("getValue");
	if (Common.isNull(activityCategoryName)) {
		$.messager.alert("提示", "请填写修改后的分类名称！");
		return false;
	}
	
	var oldActivityCategoryName = $("#oldActivityCategoryName").val();
	if (oldActivityCategoryName == activityCategoryName) {
		$.messager.alert("提示", "请填写修改后的分类名称，不能与原名称相同！");
		return false;
	}
	
	$.ajax({
		url : "update",
		type : "post",
		data : {
			"form.activityCategoryName" : activityCategoryName,
			"form.activityCategoryId" : activityCategoryId
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				// 更新列表
				Common.updateSelectedRowData("grid", {
					activityCategoryName : activityCategoryName
				});
				
				// 清空输入框
				$("#activityCategoryName").textbox("setValue", "");
				$("#activityCategoryId").val("");
				$("#oldActivityCategoryName").val("");
			}

			Common.msgslide(json.msg);
		},
		error : function(json) {
			$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 * 删除一条记录
 */
function del() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想删除的活动分类名称！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除活动分类【" + row.activityCategoryName + "】吗？", function(r) {
		if (r) {
			// 清空输入框
			$("#activityCategoryName").textbox("setValue", "");
			$("#activityCategoryId").val("");
			$("#oldActivityCategoryName").val("");
			
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"form.activityCategoryId" : row.activityCategoryId,
					"form.activityCategoryName" : row.activityCategoryName
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.deleteSelectedRow("grid");
					}

					Common.msgslide(json.msg);
				},
				error : function(json) {
					$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}