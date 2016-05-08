/**
 * 列表双击事件
 */
$(function() {
	var options = $("#grid").datagrid("options");
	options.onDblClickRow = function(index, row) {
		openChannelInfoWin(false);
	};
	options.onClickRow = function(index, row) {
		$("#channelInfo_channelName").textbox("setValue", row.channelName);
	}
});

/**
 * 查询按钮
 */
function query() {
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"condition.startDate" : $("#startDate").datebox("getValue"),
		"condition.endDate" : $("#endDate").datebox("getValue")
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}


/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#channelInfo_channelId").val("");
	$("#channelInfo_channelName").textbox("setValue", "");
	$("#channelInfo_oldChannelName").val("");
}

/**
 * 提交
 * 
 * @returns
 */
function submit(isAdd) {
	var url;

	if (isAdd == true) {
		url = "add";
	} else {
		url = "update";
	}

	var channelId = $("#channelInfo_channelId").val();
	var channelName = $("#channelInfo_channelName").textbox("getValue");

	if (Common.isNull(channelName)) {
		$.messager.alert("提示", "请输入栏目名称！");
		return false;
	}
	
	if(url == "update"){
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}
		channelId = row.channelId;
	}


	$.ajax({
		url : url,
		type : "post",
		data : {
			"channel.channelId" : channelId,
			"channel.channelName" : channelName
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				if (isAdd) {
					// 刷新列表
					Common.refreshGrid("grid");
				} else {
					// 更新列表
					Common.updateSelectedRowData("grid", {
						channelName : channelName
					});
				}

				// 关闭窗口
				Common.closeWin("channelInfo");
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
		$.messager.alert("提示", "请选择想删除的数据！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除栏目【" + row.channelName + "】吗？", function(r) {
		if (r) {
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"channel.channelId" : row.channelId
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