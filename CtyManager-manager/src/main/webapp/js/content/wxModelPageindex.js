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
function openWxModelPageInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增模板详情页", 320, 180, "wxModelPageInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}
		
		var wxconfig = row.wxconfigid;
		if(Common.isNull(wxconfig)){
			$.messager.alert("提示","公用模板,不可编辑!");
			return false;
		}

		win = new Common().getWindow("修改模板详情页", 320, 180, "wxModelPageInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");

		$("#wxModelPageInfo_modelinfoid").val(row.modelinfoid);

		// 让select选中一个值
		$("#wxModelPageInfo_modelurl").textbox("setValue",row.modelurl);
		$("#wxModelPageInfo_modellookurl").textbox("setValue",row.modellookurl);
		$("#wxModelPageInfo_sort").textbox("setValue",row.sort);
	}
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#wxModelPageInfo_modelinfoid").val("");
	$("#wxModelPageInfo_modelurl").textbox("setValue", "");
	$("#wxModelPageInfo_modellookurl").textbox("setValue", "");
	$("#wxModelPageInfo_sort").textbox("setValue", "");
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

	var modelinfoid = $("#wxModelPageInfo_modelinfoid").val();

	var modelurl = $("#wxModelPageInfo_modelurl").val();
	if (Common.isNull(modelurl)) {
		$.messager.alert("提示", "请输入模板URL！");
		return false;
	}
	var modellookurl = $("#wxModelPageInfo_modellookurl").val();
	if (Common.isNull(modellookurl)) {
		$.messager.alert("提示", "请输入预览URL！");
		return false;
	}
	var sort = $("#wxModelPageInfo_sort").val();
	if (Common.isNull(sort)) {
		$.messager.alert("提示", "请输入排序！");
		return false;
	}

	$.ajax({
		url : url,
		type : "post",
		data : {
			"wxModelPage.modelinfoid" : modelinfoid,
			"wxModelPage.modelurl" : modelurl,
			"wxModelPage.modellookurl" : modellookurl,
			"wxModelPage.sort" : sort
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
						"modelurl" : modelurl,
						"modellookurl" : modellookurl,
						"sort" : sort
					});
				}

				// 关闭窗口
				Common.closeWin("wxModelPageInfo");
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
	
	var wxconfig = row.wxconfigid;
	if(Common.isNull(wxconfig)){
		$.messager.alert("提示","公用模板,不可删除!");
		return false;
	}
	
	$.messager.confirm("提示", "你确定删除该条记录吗？", function(r) {
		if (r) {
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"wxModelPage.modelinfoid" : row.modelinfoid
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