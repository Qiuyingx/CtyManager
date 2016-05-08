/**
 * 条件检索
 */
function query() {
	//获取参数

	var nickName = $("#nickNameSearch").textbox("getValue");
	var userMobile = $("#userMobileSearch").textbox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	var courtyardIds = $("#courtyardIdsSearch").combotree("getValues");
	var valiType = $("#valiTypeSearch").combotree("getValues");
	var valiStatus = $("#valiStatusSearch").combotree("getValues");

	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.nickName" : nickName,
		"query.userMobile" : userMobile,
		"query.startTime" : startTime,
		"query.endTime" : endTime,
		"query.courtyardIds" : courtyardIds+"",
		"query.valiType" : valiType+"",
		"query.valiStatus" : valiStatus+""
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 重置筛选条件
 */
function flush(){
	$("#nickNameSearch").textbox("setValue", "");
	$("#userMobileSearch").textbox("setValue", "");
	$("#startTimeSearch").datebox("setValue", "");
	$("#endTimeSearch").datebox("setValue", "");
	$("#courtyardIdsSearch").combotree("setValues", "");
	$("#valiTypeSearch").combotree("setValues", "");
	$("#valiStatusSearch").combotree("setValues", "");
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
		},
		onClickRow : function (rowIndex, rowData) {
			/*if(rowData.valiStatus != 0) {
				$("#btnYes").hide();
				$("#btnNo").hide();
			}else{
				$("#btnYes").show();
				$("#btnNo").show();
			}*/
		}
	});
});

/**查看凭证*/
function lookPics(httpUrl) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想浏览的数据！");
		return false;
	}
	//判断验证类型
	var valiType = row.valiType;
	var contents = row.append;
	if(valiType == 1) {
		if(Common.isNull(contents)) {
			layer.msg( "暂时无图片");
			return false;
		}
		Common.showPictures(httpUrl, contents);
	} else { 
		Common.showText(contents);
	}
}

/**审核状态变更*/
function changeStatus(boo) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var valiStatus = row.valiStatus;
	if(valiStatus != 0) {
		if(valiStatus == 1 || valiStatus == 2) {
			layer.msg("该申请已被处理！");
			return false;
		}else{
			layer.msg("无法获取申请状态，暂停处理！");
			return false;
		}
	}
	$.messager.confirm("提示", "你确定需要审核该数据吗？", function(r) {
		if (r) {
			var status = 0;
			if(boo) {
				status = 1; // 审核通过
			}else{
				status = 2; // 审核不通过
			}
			$("#main").mask("正在处理中，请稍候......");
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"query.id" : row.id,
					"query.valiStatus" : status,
					"query.userId" : row.userId,
					"query.courtyardId" : row.courtyardId,
					"query.inviteCode" : row.inviteCode,
					"query.courtyardName" : row.courtyardName,
					"query.nickName" : row.nickName
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"valiStatus" : status
						});
					}
					$("#main").unmask();
					layer.msg(json.msg);
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}