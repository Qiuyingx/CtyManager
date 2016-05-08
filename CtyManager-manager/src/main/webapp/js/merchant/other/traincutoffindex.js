

$(function(){
	
	$("#periodSearch").combobox({
		onSelect : function() {
			//获取参数
			var periodSearch = $("#periodSearch").combobox("getValue");
			// 设置参数
			var queryParams = $("#grid").datagrid("options").queryParams;
			queryParams.temp = periodSearch;

			// 重新加载datagrid的数据
			$("#grid").datagrid("load");
		}
	});
	
	$("#grid").datagrid({
		onLoadSuccess : function (data) {
			$("#endTime").text(data.endTime);
		}
	});
});

/**
 * 关闭结算窗口
 */
function concelcutoff(){
	Common.closeWin("cutoffinput");
}

/**
 * 提交结算
 * @returns {Boolean}
 */
function submitcutoff(){
	var trainId = $("#trainId").val();
	var cutoffamount = $("#cutoffamount").textbox("getValue");
	if(isNaN(cutoffamount) || cutoffamount==''){
		layer.msg( "请输入正确的结算金额！");
		return false;
	}
	if(cutoffamount==0){
		layer.msg( "结算金额必须大于0！");
		return false;
	}
	var row = Common.getSelectedRow("grid");
	if(row.remain<cutoffamount){
		layer.msg( "结算金额不能大于可结算金额！");
		return false;
	}
	
	$.messager.confirm("提示", "你确定要结算？", function(r) {
		if (r) {
			$.ajax({
				url : "cutofftrain",
				type : "post",
				data : {
					"trainId" : trainId,
					"cutoffamount" : cutoffamount
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						// 更新行数据
						Common.updateSelectedRowData("grid", {
							"shopCutOffSales" : json.info.shopCutOffSales,
							"remain" : json.info.remain
						});
					}
					Common.closeWin("cutoffinput");
					layer.msg(json.msg);
				},
				error : function(json) {
					$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**
 * 结算
 */
function cutofftrain(){
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(row.remain<=0){
		layer.msg( "没可结算金额，无法结算！");
		return false;
	}
	var trainId = row.trainId;
	var win = $("#cutoffinput").window({
		title : "商家结算",
		shadow : true,
		modal : true, // 模态窗口
		closed : true,
		minimizable : false,
		maximizable : false,
		collapsible : false
	});
	win.window("open");
	$("#trainId").val(trainId);
	$("#cutoffamount").textbox("setValue", row.remain);
}

/**
 * 查看结算记录
 */
function getcutoffrecord(){
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var havedata = true;
	$('#recordgrid').datagrid({
		url : 'cutoffrecord',    
		queryParams :
		{
			trainId : row.trainId
		},
		striped : true,
		rownumbers : true,
		pagination : true,
		singleSelect : true,
		onBeforeLoad : function(param) {
			
		},
		onLoadSuccess : function(data) {
			if(data.total==0){
				Common.closeWin("cutoffrecord");
				layer.msg( "没有结算记录！");
			}else{
				var win = $("#cutoffrecord").window({
					shadow : true,
					modal : true, // 模态窗口
					closed : true,
					minimizable : false,
					maximizable : false,
					collapsible : false
				});
				win.window("open");
			}
		}
	});
	
	
}