//======================条件筛选重置===============================================

/**
 * 专题条件检索
 */
function query() {
	//获取参数
	var title = $("#titleSearch").textbox("getValue", "");
	var startTime = $("#startTimeSearch").datebox("getValue");
	var endTime = $("#endTimeSearch").datebox("getValue");
	var status = $("#statusSearch").combobox("getValue");
	var isBanner = "";
	
	if($("#topBanner").prop("checked")) {
		isBanner = "1";
	}
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.title" : title,
		"query.startTime" : startTime,
		"query.endTime" : endTime,
		"query.isBannerTop" : isBanner,
		"query.status" : status
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function flush(){
	$("#titleSearch").textbox("setValue", "");
	$("#startTimeSearch").datebox("setValue", "");
	$("#endTimeSearch").datebox("setValue", "");
	$("#statusSearch").combobox("setValue", "");
	$("#topBanner").prop("checked", false);
	$("#toTopBanner").show();
	$("#delTopBanner").hide();
	query();
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

                    layer.msg("操作成功！");
				},
				error : function(json) {
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

$(function(){
	/**双击查看单元格内容*/
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});
	
	// 推荐Banner
	$("#topBanner").change(function() {
		var isChecked = $(this).prop("checked")
		if(isChecked) {
			$("#topList").prop("checked", false);
			$("#delTopBanner").show();
			$("#toTopBanner").hide();
			$("#delTopList").hide();
			$("#toTopList").show();
		}else{
			$("#delTopBanner").hide();
			$("#toTopBanner").show();
		}
		query();
	});

	$("#titleSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});
	
	$("#startTimeSearch, #endTimeSearch").datebox({
		onSelect : function(date) {
			query();
		}
	});

	$("#statusSearch").combobox({
		onSelect : function() {
			query();
		}
	});
});

/**草稿/发布*/
function changeStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		$.messager.alert("提示", "请选择想操作的资讯！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定将该资讯变为<span style=\"color:red\">" + (status == 0 ? "发布" : "草稿") + "</span>状态吗？", function(r) {
		if (r) {
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"entity.id" : row.id
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"status" : Math.abs(status - 1)
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

/**添加资讯*/
function addContent() {
	window.location.href="../../content/info/news_edit?process=add";
}

/**编辑资讯*/
function updateContent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.location.href="../../content/info/news_edit?process=update&id="+row.id;
}

/**推至Banner*/
function toTopBanner() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(Common.isNull(row.bannerImg)) {
		layer.msg("快去上传一张banner图，再做推荐吧~");
		return false;
	}
	if(row.status != 1) {
		layer.msg("资讯必须处于发布状态才可以推荐！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/bannertop/addtop",
		type : "post",
		data : {
			"contentId" : row.id,
			"dtype" : 2
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

/**取消推荐Banner*/
function delTopBanner() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/bannertop/deltop",
		type : "post",
		data : {
			"contentId" : row.id,
			"dtype" : 2
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

/**查看详情*/
function lookinfocontent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.open(imgServer+"Miqu/share/shareContent.do?contentId="+row.id ,'_blank');
}