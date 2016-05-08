/**
 * 条件检索
 */
function query() {
	//获取参数
	//var title = $("#titleSearch").textbox("getValue");
	var nickname = $("#nicknameSearch").textbox("getValue");
	var yardids = $("#yardidsSearch").combotree("getValues") + "";
	var tel = $("#telSearch").textbox("getValue");
	var specialTitle = $("#specialTitleSearch").textbox("getValue");
	var isTop = $("#isTopSearch").prop("checked");
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.startDate" : $("#startDate").datebox("getValue"),
		"query.endDate" : $("#endDate").datebox("getValue"),
		"query.nickname" : nickname,
		"query.yardids" : yardids,
		"query.tel" :  tel,
		"query.specialTitle" : specialTitle,
		"query.isTop" : isTop
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

$(function(){
	/**
	 * 标题所搜
	 */
	$("#titleSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});
	/**
	 * 发帖人搜索
	 */
	$("#nicknameSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	})
	/**
	 * 开始时间所搜
	 */
	$("#startDate").datebox({
		onSelect : function(date) {
			query();
		}
	});
	/**
	 * 结束时间搜索
	 */
	$("#endDate").datebox({
		onSelect : function(date) {
			query();
		}
	});
	/**
	 * 院子筛选
	 */
	$("#yardidsSearch").combotree({
		onCheck : function(node, checked) {
			query();
		}
	});
	/**
	 * 发帖人联系电话
	 */
	$("#telSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});
	
	/**
	 * 双击查看单元格内容
	 */
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});
	
	/**
	 * 筛选置顶帖
	 */
	$("#isTopSearch").change(function(){
		var isTop = $("#isTopSearch").prop("checked");
		if(isTop) {
			$("#cancelTop").show();
		}else{
			$("#cancelTop").hide();
		}
		query();
	});
});

/**
 * 重置筛选条件
 */
function flush(){
	$("#nicknameSearch").textbox("setValue","");
	$("#startDate").textbox("setValue","");
	$("#endDate").textbox("setValue","");
	$("#yardidsSearch").combotree("setValues","");
	$("#telSearch").textbox("setValue","");
	$("#specialTitleSearch").textbox("setValue", "");
}

/**
 * 删除话题
 */
function deletePost() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想删除的话题！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除该话题吗？", function(r) {
		if (r) {
			$("#main").mask("正在处理，请稍后...");
			$.ajax({
				url : "deletepost",
				type : "post",
				data : {
					"id" : row.id
				},
				dataType : "json",
				success : function(json) {
					$("#main").unmask();
					if (json.isSuccess) {
						Common.deleteSelectedRow("grid");
					}

                    layer.msg(json.msg);
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

/**查看评论详情*/
function lookSignup() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择话题！");
		return false;
	}
	window.location.href="../reply/replyindex?postId="+row.id+"&contentType=3";
}

/**进入帖子置顶选择面板*/
function toTopPostPanel() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	getTopPostInfo(row.id);
	var win = new Common().getWindow("帖子置顶面板", 350, 150, "toTopPostInfo");
	win.window("open");
}

/**确认置顶帖子*/
function addTopPost() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var topType = $("input[name='topType']:checked").val();
	if(Common.isNull(topType)) {
		layer.msg("请选择置顶类型！");
		return false;
	}
	var priority = $("#priority").combobox("getValue");
	if(Common.isNull(priority)) {
		layer.msg("请选择置顶级别！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "totoppost",
		type : "post",
		data : {
			"topPost.postId" : row.id,
			"topPost.courtyardId" : row.courtyardId,
			"topPost.topType" : topType,
			"topPost.priority" : priority
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
			Common.closeWin("toTopPostInfo");
            layer.msg(json.msg);
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**取消帖子置顶*/
function cancelTopPost() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$("#main").mask("正在处理，请稍后...");
	$.ajax({
		url : "canceltoppost",
		type : "post",
		data : {
			"id" : row.id
		},
		dataType : "json",
		success : function(json) {
			if(json.isSuccess) {
				Common.deleteSelectedRow("grid");
			}
			$("#main").unmask();
	        layer.msg(json.msg);
		},
		error : function(json) {
			$("#main").unmask();
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**获取置顶信息*/
function getTopPostInfo(id) {
	if(Common.isNull(id)) {
		layer.msg("无法获取帖子信息！");
		return false;
	}
	$.ajax({
		url : "gettoppostinfo",
		type : "post",
		async : false,
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(json) {
			if(!Common.isNull(json.data.id)) {
				$("input[value='"+json.data.topType+"']").prop("checked", true);
				$("#priority").combobox("setValue", json.data.priority);
			}else{
				$("input[value='2']").prop("checked", true);
				$("#priority").combobox("setValue", 2);
			}
		},
		error : function(json) {
		}
	});
}