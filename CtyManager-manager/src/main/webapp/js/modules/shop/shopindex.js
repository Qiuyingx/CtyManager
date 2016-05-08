/**
 * 条件检索
 */
function query() {
	//获取参数
	var goodsName = $("#titleSearch").textbox("getValue");
	var status = $("#statusSearch").textbox("getValue");
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.goodsName" : goodsName,
		"query.status" : status
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 重置筛选条件
 */
function flush(){
	$("#titleSearch").textbox("setValue","");
	$("#statusSearch").textbox("setValue","");
}

/**
 * 删除一条记录
 */
function del() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择想删除的数据！");
		return false;
	}
	$.messager.confirm("提示", "你确定删除该条记录吗？", function(r) {
		if (r) {
			$("#main").mask("正在处理，请稍后...");
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"entity.id" : row.id
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.deleteSelectedRow("grid");
					}
					$("#main").unmask();
                    layer.msg("操作成功！");
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg("服务器繁忙，请稍候再试！！");
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

/**在售/下架*/
function changeStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想在售/下架的商品！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 0 ? "下架" : "上线") + "</span>该商品吗？", function(r) {
		if (r) {
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"query.id" : row.id
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

/**编辑预览*/
function preView() {
	var content = ue.getContent();
 	if(Common.isNull(content)) {
		layer.msg( "请编辑商品介绍内容！");
		return false;
	}
 	Common.showContent(content);
}


/**添加商品*/
function addShop() {
	window.location.href="../../shop/info/shop_edit?process=add";
}

/**编辑商品*/
function updateShop() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.location.href="../../shop/info/shop_edit?process=update&id="+row.id;
}
