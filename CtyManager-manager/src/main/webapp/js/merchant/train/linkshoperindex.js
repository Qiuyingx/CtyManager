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

/**推至列表*/
function addTopContent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/contenttop/addtop",
		type : "post",
		data : {
			"contentId" : contentId,
			"trainId" : row.id
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
            layer.msg(json.msg);
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**取消推荐列表*/
function delTopContent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/contenttop/deltop",
		type : "post",
		data : {
			"contentId" : contentId,
			"trainId" : row.id
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
            layer.msg(json.msg);
            if(json.isSuccess) {
				Common.deleteSelectedRow("grid");
            }
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}