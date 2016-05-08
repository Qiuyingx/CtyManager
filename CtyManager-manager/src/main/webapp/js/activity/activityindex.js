/**
 * 列表双击事件
 */
$(function() {
	var options = $("#grid").datagrid("options");
	options.onDblClickRow = function(index, row) {
		openActivityInfoWin(false);
	};

	// 文章分类combo绑定选中事件
	var channelOptions = $("#activityInfo_channel").combobox("options");
	channelOptions.onSelect = function(record) {
		var union_url = $("#activityInfo_channel").attr("union_url");
		// 加载文章
		$("#activityInfo_content").combobox("setValue", "");
		$("#activityInfo_content").combobox("reload", union_url + "/" + record.id);
	};
	
	var gameTypeOptions = $("#activityInfo_gameType").combobox("options");
	gameTypeOptions.onSelect = function(record) {
		var union_url = $("#activityInfo_gameType").attr("union_url");
		// 加载文章
		$("#activityInfo_game").combobox("setValue", "");
		$("#activityInfo_game").combobox("reload", union_url + "/" + record.id);
	};
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
 * 查看栏目信息
 */
function openActivityInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增活动", 500, 290, "activityInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}

		win = new Common().getWindow("修改活动", 500, 290, "activityInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");

		setWin(row);
	}
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#activityInfo_activityId").val("");
	$("#activityInfo_oldActivityNo").val("");
	
	$("#activityInfo_activityNo").textbox("setValue", "");
	$("#activityInfo_activityName").textbox("setValue", "");
	$("#activityInfo_activityDescript").textbox("setValue", "");
	$("#activityInfo_activityCategory").combobox("setValue", "");
	$("#activityInfo_channel").combobox("setValue", "");
	$("#activityInfo_content").combobox("setValue", "");
	$("#activityInfo_gameType").combobox("setValue", "");
	$("#activityInfo_game").combobox("setValue", "");
	$("#activityInfo_peopleCount").numberspinner("setValue", "0");
	$("#activityInfo_startTime").datebox("setValue", "");
	$("#activityInfo_endTime").datebox("setValue", "");
	
	$("#activityInfo_isRegIdcode").attr("checked", false);
	$("#activityInfo_isRegName").attr("checked", false);
	$("#activityInfo_isRegTel").attr("checked", false);
	$("#activityInfo_isRegAddr").attr("checked", false);
}

/**
 * 设置窗口中的域
 */
function setWin(row) {
	$("#activityInfo_activityId").val(row.activityId);
	$("#activityInfo_oldActivityNo").val(row.activityNo);
	
	$("#activityInfo_activityNo").textbox("setValue", row.activityNo);
	$("#activityInfo_activityName").textbox("setValue", row.activityName);
	$("#activityInfo_activityDescript").textbox("setValue", row.activityDescript);
	$("#activityInfo_activityCategory").combobox("select", row.activityCategoryId);
	$("#activityInfo_channel").combobox("select", row.channelId);
	$("#activityInfo_content").combobox("select", row.contentId);
	$("#activityInfo_gameType").combobox("select", row.gameTypeId);
	$("#activityInfo_game").combobox("select", row.gameId);
	$("#activityInfo_peopleCount").numberspinner("setValue", row.peopleCount);
	$("#activityInfo_startTime").datebox("setValue", row.startTime);
	$("#activityInfo_endTime").datebox("setValue", row.endTime);
	
	$("#activityInfo_isRegIdcode").prop("checked", row.isRegIdcode == 1);
	$("#activityInfo_isRegName").attr("checked", row.isRegName == 1);
	$("#activityInfo_isRegTel").attr("checked", row.isRegTel == 1);
	$("#activityInfo_isRegAddr").attr("checked", row.isRegAddr == 1);
}

/**
 * 提交
 * 
 * @returns
 */
function submit() {
	var url;
	var isAdd;

	if ($("#submitBtn").attr("value") == "新增") {
		url = "add";
		isAdd = true;
	} else {
		url = "update";
		isAdd = false;
	}

	var activityId = $("#activityInfo_activityId").val();
	var oldActivityNo = $("#activityInfo_oldActivityNo").val();
	var activityNo = $("#activityInfo_activityNo").textbox("getValue");
	var activityName = $("#activityInfo_activityName").textbox("getValue");
	var activityDescript = $("#activityInfo_activityDescript").textbox("getValue");
	var activityCategoryId = $("#activityInfo_activityCategory").combobox("getValue");
	var contentId = $("#activityInfo_content").combobox("getValue");
	var gameId = $("#activityInfo_game").combobox("getValue");
	var peopleCount = $("#activityInfo_peopleCount").numberspinner("getValue");
	var startTime = $("#activityInfo_startTime").datebox("getValue");
	var endTime = $("#activityInfo_endTime").datebox("getValue");
	var isRegIdcode = $("#activityInfo_isRegIdcode").prop("checked") ? 1 : 0;
	var isRegName = $("#activityInfo_isRegName").prop("checked") ? 1 : 0;
	var isRegTel = $("#activityInfo_isRegTel").prop("checked") ? 1 : 0;
	var isRegAddr = $("#activityInfo_isRegAddr").prop("checked") ? 1 : 0;

	if (Common.isNull(activityNo)) {
		$.messager.alert("提示", "请输入活动编号名称！");
		return false;
	}

	if (Common.isNull(activityName)) {
		$.messager.alert("提示", "请输入活动名称！");
		return false;
	}
	
	if (Common.isNull(activityCategoryId)) {
		$.messager.alert("提示", "请选择活动分类！");
		return false;
	}
	
	if (Common.isNull(contentId)) {
		$.messager.alert("提示", "请选择活动文章！");
		return false;
	}
	
	if (Common.isNull(startTime)) {
		$.messager.alert("提示", "请选择活动起始时间！");
		return false;
	}
	
	if (Common.isNull(endTime)) {
		$.messager.alert("提示", "请选择活动结束时间！");
		return false;
	}

	if (!Common.compareDate(startTime, endTime)) {
		$.messager.alert("提示", "结束日期必须大于起始日期！");
		return false;
	}
	
	$.ajax({
		url : url,
		type : "post",
		data : {
			"form.activityId" : activityId,
			"form.oldActivityNo" : oldActivityNo,
			"form.activityNo" : activityNo,
			"form.activityName" : activityName,
			"form.activityDescript" : activityDescript,
			"form.contentId" : contentId,
			"form.activityCategoryId" : activityCategoryId,
			"form.gameId" : gameId,
			"form.isRegIdcode" : isRegIdcode,
			"form.isRegName" : isRegName,
			"form.isRegTel" : isRegTel,
			"form.isRegAddr" : isRegAddr,
			"form.peopleCount" : peopleCount,
			"form.startTime" : startTime,
			"form.endTime" : endTime
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				// 刷新列表
				Common.refreshGrid("grid");

				// 关闭窗口
				Common.closeWin("activityInfo");
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