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

/**技能认证*/
function updateStatus(status, valiResult) {
	if(Common.isNull(status) || (status != 1 && status != 2)) {
		layer.msg("无效状态，操作失败！");
		return false;
	}
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(row.status != 0) {
		layer.msg("申请已被处理！");
		return false;
	}
	var remark = $("#textRemark").textbox("getValue");
	if(status == 2 && Common.isNull(remark)) {
		layer.msg("请填写审核不通过的原因！");
		return false;
	}
	$.messager.confirm("提示", "你确定审核"+valiResult+"吗？", function(r) {
		if (r) {
			$("#main").mask("正在处理，请稍后...");
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"entity.id" : row.id,
					"entity.status" : status,
					"entity.userId" : row.userId,
					"entity.remark" : remark
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"status" : status
						});
					}
					$("#main").unmask();
					layer.msg(json.msg);
					$("#textRemark").textbox("setValue","");
					Common.closeWin('valiRemarkPanel');
				},
				error : function(json) {
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**审核填写审核备注*/
function enterValiRemarkPanel() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var win = new Common().getWindow("审核备注面板", 350, 180, "valiRemarkPanel");
	win.window("open");
}

/**浏览封面图*/
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