//======================条件筛选重置===============================================

/**
 * 课程条件检索
 */
function query() {
	//获取参数
	var title = $("#titleSearch").textbox("getValue");
	var status = $("#statusSearch").combobox("getValue");
	var isBanner = "";
	var isList = "";
	
	if($("#topBanner").prop("checked")) {
		isBanner = "1";
	}
	
	if($("#topList").prop("checked")) {
		isList = "1";
	}
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.title" : title,
		"query.status" : status+"",
		"query.isBannerTop" : isBanner,
		"query.isListTop" : isList
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function flush(){
	$("#titleSearch").textbox("setValue", "");
	$("#statusSearch").combobox("setValues", "");
	$("#topBanner").prop("checked", false);
	$("#topList").prop("checked", false);
	$("#toTopBanner").show();
	$("#toTopList").show();
	$("#delTopBanner").hide();
	$("#delTopList").hide();
	query();
}


$(function(){
	/**双击查看单元格内容*/
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});

	$("#titleSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});

	$("#statusSearch").combobox({
		onSelect : function() {
			query();
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
	
	// 推荐列表
	$("#topList").change(function() {
		var isChecked = $(this).prop("checked")
		if(isChecked) {
			$("#topBanner").prop("checked", false);
			$("#delTopList").show();
			$("#toTopList").hide();
			$("#delTopBanner").hide();
			$("#toTopBanner").show();
		}else{
			$("#delTopList").hide();
			$("#toTopList").show();
		}
		query();
	});
});

/**草稿/发布*/
function changeStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择想要操作的课程！");
		return false;
	}

	var status = row.status;
	
	$.messager.confirm("提示", "你确定将该课程变为<span style=\"color:red\">" + (status == 0 ? "草稿" : "发布") + "</span>状态吗？", function(r) {
		if (r) {
			$.ajax({
				url : "changestatus",
				type : "post",
				data : {
					"id" : row.id,
					"status" : Math.abs(status - 1)
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

/**查看课程报名*/
function lookSignup() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择课程信息！");
		return false;
	}
	window.location.href="../../course/join/coursejoinindex?courseId="+row.id;
}


/**推至Banner*/
function toTopBanner() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(row.status != 0) {
		layer.msg("课程必须处于发布专题才可推荐！");
		return false;
	}
	if(Common.isNull(row.bannerImg)) {
		layer.msg("快去上传一张banner图，再做推荐吧~");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/bannertop/addtop",
		type : "post",
		data : {
			"contentId" : row.id,
			"dtype" : 3
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
			"dtype" : 3
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

/**推至列表*/
function toTopList() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	if(row.status != 0) {
		layer.msg("课程必须处于发布专题才可推荐！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/listtop/addtop",
		type : "post",
		data : {
			"contentId" : row.id,
			"dtype" : 3,
			"releaseTime" : row.createTime
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
function delTopList() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "../../content/listtop/deltop",
		type : "post",
		data : {
			"contentId" : row.id,
			"dtype" : 3
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

/**编辑课程*/
function updateCourse() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.location.href="../../other/train/traincourse/content_edit?process=update&id="+row.id+"&trainId="+trainId;
}


/**查看详情*/
function lookinfocontent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.open(imgServer+"Miqu/share/shareCourse.do?courseId="+row.id ,'_blank');
}