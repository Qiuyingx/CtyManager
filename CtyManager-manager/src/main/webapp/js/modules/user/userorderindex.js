/**
 * 条件检索
 */
function query() {
	//获取参数
	var orderId = $("#orderIdSearch").textbox("getValue");
	var userName = $("#userNameSearch").textbox("getValue");
	var userMobile = $("#userMobileSearch").textbox("getValue");
	var nickName = $("#nickNameSearch").textbox("getValue");
	var goodsName = $("#goodsNameSearch").textbox("getValue");
	var buyStartTime = $("#buyStartTimeSearch").datebox("getValue");
	var buyEndTime = $("#buyEndTimeSearch").datebox("getValue");
	var courtyardIds = $("#courtyardIdsSearch").combotree("getValues");

	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.orderId" : orderId,
		"query.userName" : userName,
		"query.userMobile" : userMobile,
		"query.nickName" : nickName,
		"query.goodsName" : goodsName,
		"query.buyStartTime" : buyStartTime,
		"query.buyEndTime" : buyEndTime,
		"query.courtyardIds" : courtyardIds+""
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 重置筛选条件
 */
function flush(){
	$("#orderIdSearch").textbox("setValue", "");
	$("#userNameSearch").textbox("setValue", "");
	$("#userMobileSearch").textbox("setValue", "");
	$("#nickNameSearch").textbox("setValue", "");
	$("#goodsNameSearch").textbox("setValue", "");
	$("#buyStartTimeSearch").datebox("setValue", "");
	$("#buyEndTimeSearch").datebox("setValue", "");
	$("#courtyardIdsSearch").combotree("setValues", "");
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
		}
	});
});