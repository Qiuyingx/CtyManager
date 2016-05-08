$(function(){
	var optionsWxConfig = $("#contentInfo_wxConfigId").combobox("options");
	optionsWxConfig.onSelect = function(record) {
		// 加载栏目
		$("#contentInfo_channelId").combobox("reload", path + "/common/getchannelcombo/" + record.id);
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
function openContentInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增内容", 680, 540, "channelInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}

		win = new Common().getWindow("修改内容", 680, 540, "channelInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");
		
		setWin(row);
	}
	
	win = new Common().getWindow("内容", 680, 540, "contentInfo");
	win.window("open");
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#contentInfo_channelId").combobox("setValue", "");
	$("#contentInfo_title").textbox("setValue", "");
	$("#contentInfo_descript").textbox("setValue", "");
	ue.execCommand("clearlocaldata"); // 清空草稿箱
	ue.setContent(""); // 清空编辑框
	$("#uploadMsg").html("*");
	$("#contentInfo_oldTitle").val("");
}

/**
 * 设置窗口中的域
 * @param row
 */
function setWin(row) {
	$("#contentInfo_channelId").combobox("select", row.channelId);
	$("#contentInfo_title").textbox("setValue", row.title);
	$("#contentInfo_descript").textbox("setValue", row.descript);
	ue.execCommand("clearlocaldata"); // 清空草稿箱
	ue.setContent(row.contentText); // 清空编辑框
	$("#contentInfo_contentId").val(row.contentId);
	$("#contentInfo_oldTitle").val(row.title);
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

	var contentId = $("#contentInfo_contentId").val();
	var channelId = $("#contentInfo_channelId").combobox("getValue");
	var title = $("#contentInfo_title").textbox("getValue");
	var descript = $("#contentInfo_descript").textbox("getValue");
	var contentText = ue.getContent();
	var oldTitle = $("#contentInfo_oldTitle").val();
	
	if (Common.isNull(channelId)) {
		$.messager.alert("提示", "请选择栏目名称！");
		return false;
	}
	
	if (Common.isNull(title)) {
		$.messager.alert("提示", "请输入内容标题名称！");
		return false;
	}
	
	if (Common.isNull(descript)) {
		$.messager.alert("提示", "请输入内容描述！");
		return false;
	}

	if (!ue.hasContents()) {
		$.messager.alert("提示", "请输入内容正文！");
		return false;
	}

	$.ajax({
		url : url,
		type : "post",
		data : {
			"content.contentId" : contentId,
			"content.channelId" : channelId,
			"content.title" : title,
			"content.descript" : descript,
			"content.contentText" : contentText,
			"content.oldTitle" : oldTitle
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				if (isAdd) {
					// 刷新列表
					Common.refreshGrid("grid");
				} else {
					// 更新列表
					var channelName = $("#contentInfo_channelId").combobox("getText");
					
					Common.updateSelectedRowData("grid", {
						channelId : channelId,
						channelName : channelName,
						descript : descript,
						title : title,
						contentText : contentText
					});
				}

				// 关闭窗口
				Common.closeWin("contentInfo");
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

	$.messager.confirm("提示", "你确定删除内容【" + row.title + "】吗？", function(r) {
		if (r) {
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"content.contentId" : row.contentId
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

/**
 * 禁用/启用一条记录
 */
function updateStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想禁用/启用的文章！");
		return false;
	}

	var status = row.status;

	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 0 ? "启用" : "禁用") + "</span>该文章吗？", function(r) {
		if (r) {
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"content.contentId" : row.contentId
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"status" : Math.abs(status - 1)
						});
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