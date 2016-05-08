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
function openSysUserInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增系统用户", 500, 190, "sysUserInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
		
		$("#tipMsg").hide();
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}

		win = new Common().getWindow("修改系统用户", 500, 190, "sysUserInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");

		// 设置文本框的值
		$("#sysUserInfo_sysuserid").val(row.sysUserId);
		$("#sysUserInfo_createtime").val(row.createTime);
		$("#sysUserInfo_sysuserpwd").textbox("setValue", "");
		$("#sysUserInfo_sysuserno").textbox("setValue", row.sysUserNo);
		$("#sysUserInfo_sysusername").textbox("setValue", row.sysUserName);
		$("#sysUserInfo_sysgroupid").combobox("setValue", row.sysGroupId);
		$("#sysUserInfo_yardIds").combotree("setValues",row.yardids.split(","));
		
		$("#tipMsg").show();
	}
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#sysUserInfo_sysuserid").val("");
	$("#sysUserInfo_sysuserno").textbox("setValue", "");
	$("#sysUserInfo_sysusername").textbox("setValue", "");
	$("#sysUserInfo_sysuserpwd").textbox("setValue", "");
	$("#sysUserInfo_sysgroupid").combobox("clear");
	$("#sysUserInfo_sysgroupid").combobox("reload");
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

	var yardids = $("#sysUserInfo_yardIds").combotree("getValues");
	var sysuserid = $("#sysUserInfo_sysuserid").val();

	var sysuserno = $("#sysUserInfo_sysuserno").textbox("getValue");
	if (Common.isNull(sysuserno)) {
		$.messager.alert("提示", "请输入用户编号！");
		return false;
	}
	var sysusername = $("#sysUserInfo_sysusername").textbox("getValue");
	if (Common.isNull(sysusername)) {
		$.messager.alert("提示", "请输入用户名称！");
		return false;
	}
	var sysuserpwd = $("#sysUserInfo_sysuserpwd").textbox("getValue");
	if(url == "add") {
		if (Common.isNull(sysuserpwd)) {
			$.messager.alert("提示", "请输入用户密码！");
			return false;
		}
	}
	var sysgroupid = $("#sysUserInfo_sysgroupid").combobox("getValue");
	if (Common.isNull(sysgroupid)) {
		$.messager.alert("提示", "请选择用户分组！");
		return false;
	}
	
	$.ajax({
		url : url,
		type : "post",
		data : {
			"sysUser.sysUserId" : sysuserid,
			"sysUser.sysUserNo" : sysuserno,
			"sysUser.sysUserName" : sysusername,
			"sysUser.sysUserPwd" : sysuserpwd,
			"sysUser.sysGroupId" : sysgroupid,
			"sysUser.yardids" : yardids + ""
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
						"sysUserNo" : sysuserno,
						"sysUserName" : sysusername,
						"sysUserPwd" : sysuserpwd,
						"sysGroupId" : sysgroupid,
						"status" : status,
						"yardids" : yardids + ""
					});
				}

				// 关闭窗口
				Common.closeWin("sysUserInfo");
			}

			Common.msgslide(json.msg);
		},
		error : function(json) {
			$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 * 禁用/启用一条记录
 */
function updateStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想禁用/启用的用户！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 0 ? "启用" : "禁用") + "</span>该用户吗？", function(r) {
		if (r) {
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"sysUser.sysUserId" : row.sysUserId
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