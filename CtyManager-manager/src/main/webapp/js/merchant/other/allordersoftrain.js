
/**
 * 学堂条件检索
 */
function queryorders() {
	//获取参数
	var orderStatus = $("#statusSearch").combobox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.orderStatus" : orderStatus,
		"query.startTime" : startTime,
		"query.endTime" : endTime,
		"query.orderNo" : $("#orderNo").textbox("getValue"),
		"query.tel" : $("#tel").textbox("getValue"),
		"query.userName" : $("#userName").textbox("getValue")
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function flushorders(){
	$("#statusSearch").combobox("setValues","");
	$("#startTimeSearch").datebox("getValue","");
	$("#endTimeSearch").datebox("getValue","");
	queryorders();
	queryTotalFee();
}

/**
 * 查询订单总额
 */
function queryTotalFee(){
	//获取参数
	var orderStatus = $("#statusSearch").combobox("getValue");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	$.ajax({
		url : "queryTotalFee",
		type : "post",
		data : {
			"query.orderStatus" : orderStatus,
			"query.startTime" : startTime,
			"query.endTime" : endTime
		},
		dataType : "json",
		success : function(json) {
			$("#totalFee").text(json.totalFees.totalFee);
			$("#shopTotalFee").text(json.totalFees.shopTotalFee);
		},
		error : function(json) {
			layer.msg("服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 * 同意退款
 */
function changeOrderStatus(action){
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想操作的订单！");
		return false;
	}
	var orderStatus = row.orderStatus;
	var statusName;
	if(action==4){
		if(orderStatus!=3){
			$.messager.alert("提示", "该订单不是申请退款状态！");
			return false;
		}
		statusName = '退款中';
	}else if(action==5){
		if(orderStatus!=4){
			$.messager.alert("提示", "该订单不是退款中状态！");
			return false;
		}
		statusName = '已退款';
	}else if(action==-2){
		if(orderStatus!=0){
			$.messager.alert("提示", "该订单不是新订单状态！");
			return false;
		}
		statusName = '系统取消';
	}else{
		return false;
	}

	
	$.messager.confirm("提示", "你确定将该订单变为<span style=\"color:red\">" + statusName + "</span>状态吗？", function(r) {
		if (r) {
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"orderNo" : row.orderNo,
					"status" : action
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"orderStatus" : action
						});
					}
					layer.msg(json.msg);
				},
				error : function(json) {
					$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
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
//		onDblClickCell : function (rowIndex, field, value) {
//			Common.showText(value);
//		},
		onDblClickRow: function (rowIndex, rowData) {
            //双击开启编辑行
//			Common.showContent(rowData.orderNo+'>>>'+rowData.payTradeNo);
			$("#dorderNo").text(rowData.orderNo);
			$("#orderStatus").text(orderStatusformat(rowData.orderStatus));
			$("#payType").text(paytypeformat(rowData.payType));
			$("#totalFeeBefore").text(rowData.totalFeeBefore);
			$("#shopTotalFee").text(rowData.shopTotalFee);
			$("#nickname").text(rowData.nickname);
			$("#buyername").text(rowData.buyername);
			$("#tel").text(rowData.tel);
			$("#remark").text(rowData.remark);
			$("#trainName").text(rowData.trainName);
			$("#goodsName").text(rowData.goodsName);
			$("#address").text(rowData.address);
			$("#itemName").text(rowData.itemName);
			$("#goodsAmount").text(rowData.goodsAmount);
			$("#payTradeNo").text(rowData.payTradeNo);
			$("#createTime").text(mills2DateTime(rowData.createTime));
			$("#payTime").text(mills2DateTime(rowData.payTime));
			$("#refundFee").text(rowData.refundFee);
			$("#refundReason").text(rowData.refundReason);
			var win = $("#orderDetailwin").window({
				shadow : true,
				modal : true, // 模态窗口
				closed : true,
				minimizable : false,
				maximizable : false,
				collapsible : false
			});
			win.window("open");
        }
	});

	$("#statusSearch").combobox({
		onSelect : function() {
			queryorders();
			queryTotalFee();
		}
	});
	
	queryTotalFee();
});
