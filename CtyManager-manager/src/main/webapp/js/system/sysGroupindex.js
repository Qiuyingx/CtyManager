/**
 * 
 */
$(function() {
	// 列表绑定点击事件，选中角色后，显示该用户组拥有的角色和全部权限
	var options = $("#grid").datagrid("options");
	options.onClickRow = function(index, row) {
		// 选中拥有的角色
		$.ajax({
			url : "getgrouprole",
			type : "post",
			data : {
				groupId : row.sysGroupId
			},
			dateType : "json",
			success : function(json) {
				$("#groupRoleGrid").datagrid("unselectAll");

				for (var i = 0; i < json.length; i++) {
					$("#groupRoleGrid").datagrid("selectRecord", json[i].sysRoleId);
				}
			}
		});

		reloadTree(row);
	};
});

function reloadTree(row) {
	var treeObj = $("#permissionTree");
	var rootNode = treeObj.tree("find", "0");
	var queryParams = treeObj.tree("options").queryParams;
	queryParams = $.extend(queryParams, {
		"nodeId" : "0",
		"groupId" : row.sysGroupId
	});
	treeObj.tree("reload");
}

function save() {
	var row = Common.getSelectedRow("grid");
	var roleRows = Common.getAllSelectedRow("groupRoleGrid");

	if (!row) {
		$.messager.alert("提示", "请选择你想保存权限的用户组！");
		return false;
	}

	var roleStr = "";
	if (roleRows.length > 0) {
		for (var i = 0; i < roleRows.length; i++) {
			roleStr += roleRows[i].sysRoleId + ",";
		}
	}

	// 保存权限
	$.ajax({
		url : "save",
		type : "post",
		data : {
			groupId : row.sysGroupId,
			roleRows : roleStr
		},
		dataType : "json",
		success : function(json) {
			Common.msgslide(json.msg);
			reloadTree(row);
		},
		error : function() {
			$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
		}
	});
}
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
function openSysGroupInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增用户分组", 260, 140, "sysGroupInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}

		win = new Common().getWindow("修改用户分组", 260, 140, "sysGroupInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");

		$("#sysGroupInfo_sysgroupid").val(row.sysGroupId);

		// 让select选中一个值
		$("#sysGroupInfo_sysgroupname").textbox("setValue", row.sysGroupName);
	}
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#sysGroupInfo_sysgroupid").val("");
	$("#sysGroupInfo_sysgroupname").textbox("setValue", "");
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

	var sysgroupid = $("#sysGroupInfo_sysgroupid").val();

	var sysgroupname = $("#sysGroupInfo_sysgroupname").val();
	if (Common.isNull(sysgroupname)) {
		$.messager.alert("提示", "请输入分组名称！");
		return false;
	}

	$.ajax({
		url : url,
		type : "post",
		data : {
			"sysGroup.sysGroupId" : sysgroupid,
			"sysGroup.sysGroupName" : sysgroupname
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
						"sysGroupName" : sysgroupname
					});
				}

				// 关闭窗口
				Common.closeWin("sysGroupInfo");
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
		$.messager.alert("提示", "请选择想禁用/启用的分组！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 0 ? "启用" : "禁用") + "</span>该分组吗？", function(r) {
		if (r) {
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"sysGroup.sysGroupId" : row.sysGroupId
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