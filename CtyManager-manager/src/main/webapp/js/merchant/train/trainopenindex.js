var currOption = "";
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

$(function(){
	/**
	 * 双击查看单元格内容
	 */
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});
});

/**是否打开账号绑定面板*/
function isBindNamePanel() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var userId = row.userId;
	if(userId == 0 || userId == null || userId == "null") {
		var win = new Common().getWindow("账号绑定面板", 350, 180, "bindNamePanel");
		win.window("open");
		return false;
	}else{
		return true;
	}
}

/**开始绑定商户账号*/
function startBindUser() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var tel = $("#usertel").textbox("getValue");
	if(Common.isNull(tel)) {
		layer.msg("请输入需要绑定的商户账号！");
		return false;
	}
	if(Common.isNull(tel.trim())) {
		layer.msg("请输入需要绑定的商户账号！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "binduser",
		type : "post",
		data : {
			"openId" : row.id,
			"tel" : tel
		},
		dataType : "json",
		async:false, 
		success : function(json) {
			if (json.isSuccess) {
				Common.refreshGrid("grid");
				Common.closeWin("bindNamePanel");
				Common.updateSelectedRowData("grid", {
					"userId" : json.msg
				});
				if(currOption == "1") {
					updateStatus(2, '通过');
				}else if(currOption == "2") {
					enterValiRemarkPanel();
				}
			}else{
				layer.msg(json.msg);
			}
			$("#main").unmask();
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**审核填写审核备注*/
function enterValiRemarkPanel() {
	currOption = "2";
	if(!isBindNamePanel()) {
		return false;
	}
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#textRemark").textbox("setValue", row.remark);
	var win = new Common().getWindow("审核备注面板", 350, 180, "valiRemarkPanel");
	win.window("open");
}

/**添加或修改备注*/
function addOrUpdateRemark(){
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var remark = $("#textRemark").textbox("getValue");
	if(Common.isNull(remark)) {
		layer.msg("请填写审核备注！");
		return false;
	}
	
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "addorupdateremark",
		type : "post",
		data : {
			"entity.id" : row.id,
			"entity.remark" : remark
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				Common.updateSelectedRowData("grid", {
					"remark" : remark
				});
				Common.closeWin("valiRemarkPanel");
			}
			$("#main").unmask();
			layer.msg(json.msg);
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

function updateRemark(id, remark){
	$.ajax({
		url : "addorupdateremark",
		type : "post",
		data : {
			"entity.id" : id,
			"entity.remark" : remark
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				Common.updateSelectedRowData("grid", {
					"remark" : remark
				});
			}
		},
		error : function(json) {
			layer.msg( "出现异常，备注 信息添加失败！！");
		}
	});
}

/**审核状态*/
function updateStatus(status, valiResult) {
	currOption = "1";
	if(Common.isNull(status) || (status != 1 && status != 2)) {
		layer.msg("无效状态，操作失败！");
		return false;
	}
	if(!isBindNamePanel()) {
		return false;
	}
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(row.passed != 0) {
		layer.msg("申请已被处理！");
		return false;
	}
	var remark = $("#textRemark").textbox("getValue");
	if(status == 1 && Common.isNull(remark)) {
		layer.msg("请填写审核不通过的原因！");
		return false;
	}
	
	$.messager.confirm("提示", "你确定审核"+valiResult+"吗？", function(r) {
		if (r) {
			$("#main").mask("正在处理，请稍后...");
			$.ajax({
				url : "udpatestatus",
				type : "post",
				data : {
					"entity.id" : row.id,
					"entity.passed" : status,
					"entity.tel" : row.tel,
					"entity.email" : row.email,
					"entity.userId" : row.userId,
					"entity.category" : row.category,
					"entity.remark" : remark
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						if(status == 1) {
							updateRemark(row.id, remark);
						}
						Common.updateSelectedRowData("grid", {
							"passed" : status
						});	
						
						if(status == 2) {
							var trainIds = json.msg;
							if(!Common.isNull(trainIds)) {
								$.messager.confirm("提示", "恭喜您，操作成功，是否需要现在完善学堂信息？", function(r) {
									if(r) {
										window.location.href="../../other/train/traininfo/traininfoindex?managerId="+trainIds;
									}
								});
							}
						}
					}else{
						layer.msg(json.msg);
					}
					$("#main").unmask();
					$("#textRemark").textbox("setValue","");
					Common.closeWin('valiRemarkPanel');
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**浏览图片*/
function lookPics(httpUrl) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想浏览的数据！");
		return false;
	}
	var images = row.imageNames;
	if(Common.isNull(images)) {
		layer.msg( "暂时无图片");
		return false;
	}
	Common.showPictures(httpUrl, images);
}

/**商家后台入驻*/
function trainBlackEnter() {
	var win = new Common().getWindow("商家后台入驻面板", 334, 290, "blackEnterPanel");
	win.window("open");
}


/**商家后台入驻 提交信息*/
function submitTrain() {
	var isCreated = "2"; // 不自动创建
	if($("#autoCreated").prop("checked")) {
		isCreated = "1"; // 自动创建
	}
	var username = $("#train_username").textbox("getValue");
	if(isCreated != "1") {
		if(Common.isNull(username)) {
			layer.msg("请填写商户账号信息！");
			return false;
		}
	}
	var tel = $("#train_tel").textbox("getValue");
	if(Common.isNull(tel)) {
		layer.msg("请填写商户手机号码！");
		return false;
	}
	if(isNaN(tel)) {
		layer.msg("手机号输入不合法，请确认输入！");
		return false;
	}
	if(tel.length != 11) {
		layer.msg("您输入的手机号码有误，请确认输入！");
		return false;
	}
	var email = $("#train_email").textbox("getValue");
	if(Common.isNull(email)) {
		layer.msg("请填写商户常用的邮箱地址！");
		return false;
	}
	$.messager.confirm("提示", "你确定入驻该商家吗？", function(r) {
		if (r) {
			$("#main").mask("正在处理，请稍后...");
			$.ajax({
				url : "submittrain",
				type : "post",
				data : {
					"entity.userName" : username,
					"entity.tel" : tel,
					"entity.email" :email,
					"entity.isCreated" : isCreated
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						$("#train_username").textbox("setValue", "");
						$("#train_tel").textbox("setValue", "");
						$("#train_email").textbox("setValue", "");
						Common.refreshGrid("grid");
						Common.closeWin('blackEnterPanel');
						
						var trainIds = json.msg;
						if(!Common.isNull(trainIds)) {
							$.messager.confirm("提示", "恭喜您，操作成功，是否需要现在完善学堂信息？", function(r) {
								if(r) {
									window.location.href="../../other/train/traininfo/traininfoindex?managerId="+trainIds;
								}
							});
						}
					}else{
						layer.msg(json.msg);
					}
					$("#main").unmask();
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}