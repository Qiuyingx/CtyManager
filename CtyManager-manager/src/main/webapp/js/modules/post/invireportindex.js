/**
 * 条件检索
 */
function query() {
	//获取参数
	var title = $("#titleSearch").textbox("getValue");
	var nickname = $("#nicknameSearch").textbox("getValue");
	var yardids = $("#yardidsSearch").combotree("getValues") + "";
	var tel = $("#telSearch").textbox("getValue");
	var isAccept = $("#isAcceptSearch").is(':checked') + "";
	var isHarry = $("#isHarrySearch").is(":checked") + "";
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.startDate" : $("#startDate").datebox("getValue"),
		"query.endDate" : $("#endDate").datebox("getValue"),
		"query.title" : title,
		"query.nickname" : nickname,
		"query.yardids" : yardids,
		"query.tel" :  tel,
		"query.isAccept" : isAccept,
		"query.isHarry" : isHarry
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
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
