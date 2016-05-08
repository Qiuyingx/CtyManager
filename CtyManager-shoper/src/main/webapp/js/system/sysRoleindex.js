/**
 * 
 */
$(function() {
	// 列表绑定点击事件，选中角色后，显示该角色的权限
	var options = $("#grid").datagrid("options");
	options.onClickRow = function(index, row) {
		var treeObj = $("#permissionTree");
		// 清空树结构所有选项
		var rootNode = treeObj.tree("find", "0");
		treeObj.tree("uncheck", rootNode.target);

		// 重新得到树结构应有选项
		$.ajax({
			url : "getrolepermission",
			type : "post",
			data : {
				roleId : row.sysRoleId
			},
			dateType : "json",
			success : function(json) {
				for (var i = 0; i < json.length; i++) {
					var nodeId = json[i].sysMenuId;
					var node = treeObj.tree("find", nodeId);
					if (treeObj.tree("isLeaf", node.target)) {
						// 只有叶子节点进行设置，其它的通过叶子联动
						treeObj.tree("check", node.target);
					}
				}
			}
		});
	};
	
	// 用于选中一个节点时，也打上勾勾，不用去点那个框框
	var treeOptions = $("#permissionTree").tree("options");
	treeOptions.onSelect = function(node) {
		$("#permissionTree").tree("check", node.target);
	}
});

function save() {
	var row = Common.getSelectedRow("grid");

	var treeObj = $("#permissionTree");

	// 得到角色所选中的权限（打勾的和打方块的），打方块的称为不确定选中，在这里就是选中
	var nodes = treeObj.tree("getChecked", [ "checked", "indeterminate" ]);

	var nodeStr = "";
	if (nodes.length > 0) {
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].id == "0")
				continue; // 根结点，不算权限
			nodeStr += nodes[i].id + ",";
		}
	}

	// 保存权限
	$.ajax({
		url : "save",
		type : "post",
		data : {
			menuIds : nodeStr,
			roleId : row.sysRoleId
		},
		dataType : "json",
		success : function(json) {
			Common.msgslide(json.msg);
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
function openSysRoleInfoWin(isAdd) {
	// 打开窗口
	var win;
	if (isAdd) {
		// 清空win内所有域
		cleanWin();

		win = new Common().getWindow("新增用户角色管理", 260, 140, "sysRoleInfo");
		win.window("open");
		$("#submitBtn").attr("value", "新增");
	} else {
		var row = Common.getSelectedRow("grid");
		if (!row) {
			$.messager.alert("提示", "请选择想修改的数据！");
			return false;
		}

		win = new Common().getWindow("修改用户角色管理", 260, 140, "sysRoleInfo");
		win.window("open");
		$("#submitBtn").attr("value", "修改");

		$("#sysRoleInfo_sysroleid").val(row.sysRoleId);

		// 让select选中一个值
		$("#sysRoleInfo_sysrolename").textbox("setValue", row.sysRoleName);
	}
}

/**
 * 清空win里面的域
 */
function cleanWin() {
	$("#sysRoleInfo_sysroleid").val("");
	$("#sysRoleInfo_sysrolename").textbox("setValue", "");
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

	var sysroleid = $("#sysRoleInfo_sysroleid").val();

	var sysrolename = $("#sysRoleInfo_sysrolename").val();
	if (Common.isNull(sysrolename)) {
		$.messager.alert("提示", "请输入角色名称！");
		return false;
	}

	$.ajax({
		url : url,
		type : "post",
		data : {
			"sysRole.sysRoleId" : sysroleid,
			"sysRole.sysRoleName" : sysrolename
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
						"sysRoleName" : sysrolename
					});
				}

				// 关闭窗口
				Common.closeWin("sysRoleInfo");
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
		$.messager.alert("提示", "请选择想禁用/启用的角色！");
		return false;
	}

	var status = row.status;

	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 0 ? "启用" : "禁用") + "</span>该角色吗？", function(r) {
		if (r) {
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"sysRole.sysRoleId" : row.sysRoleId
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

function dispAll(obj) {
	Common.queryGrid("grid", {
		"isDispAll" : obj.checked
	});
}