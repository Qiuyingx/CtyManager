/**
 * 条件检索
 */
function query() {
	//获取参数
	//var title = $("#titleSearch").textbox("getValue");
	var nickname = $("#nicknameSearch").textbox("getValue");
	var yardids = $("#yardidsSearch").combotree("getValues") + "";
	var tel = $("#telSearch").textbox("getValue");
	var isAccept = $("#isAcceptSearch").is(':checked') + "";
	var isHarry = $("#isHarrySearch").is(":checked") + "";
	var isTop = $("#isTopSearch").prop("checked");
	
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.startDate" : $("#startDate").datebox("getValue"),
		"query.endDate" : $("#endDate").datebox("getValue"),
		"query.nickname" : nickname,
		"query.yardids" : yardids,
		"query.tel" :  tel,
		"query.isAccept" : isAccept,
		"query.isHarry" : isHarry,
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
	 * 是否紧急
	 */
	$("#isHarrySearch").change(function(){
		query();
	});
	
	/**
	 * 是否已经采纳评论
	 */
	$("#isAcceptSearch").change(function(){
		query();
	});

	/**
	 * 双击查看单元格内容
	 */
	$("#grid").datagrid({
		onDblClickCell : function (rowIndex, field, value) {
			Common.showText(value);
		}
	});
	// 勾选推送至所有用户
	$("#isAllCity").change(function(){
		var isAllCity = $("#isAllCity").prop("checked");
		if(isAllCity) {
			$("#city").textbox({
				disabled:true
			});
			$("#yards").textbox({
				disabled:true
			});
			$("#isAllYards").prop("disabled", true);
			$("#city").combotree("setValues", "");
		}else{
			$("#city").textbox({
				disabled:false
			});
			$("#yards").textbox({
				disabled:true
			});
			$("#isAllYards").prop("disabled", true);
		}
	});
	
	// 勾选推送所选城市下院子
	$("#isAllYards").change(function(){
		var isAllYards = $("#isAllYards").prop("checked");
		if(isAllYards) {
			$("#yards").textbox({
				disabled:true
			});
		}else{
			$("#yards").textbox({
				disabled:false
			});
		}
	});
	
	// 选择城市之后
	$("#city").combotree({
		onChange : function(record){
			var cityIds = $("#city").combotree("getValues");
			if(!Common.isNull(cityIds)) {
				$("#yards").combotree({
					url : "../../common/getyardbycityids?cityIds="+cityIds
				});
				$("#isAllYards").prop("disabled", false);
				$("#isAllYards").prop("checked", false);
				$("#yards").textbox({
					disabled:false
				});
			}else{
				$("#yards").combotree({
					url : "../../js/modules/data/json/null.js"
				});
			}
			$("#isAllYards").prop("disabled", false);
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
	$("#isHarrySearch").prop("checked", false);
	$("#isAcceptSearch").prop("checked", false);
}

/**
 * 删除帮帮
 */
function deletePost() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择想删除的帮帮！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除帮帮吗？", function(r) {
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
		layer.msg( "请选择帮帮！");
		return false;
	}
	window.location.href="../reply/replyindex?postId="+row.id+"&contentType=2";
}

/**进入审核面板*/
function valiHelp(httpUrl) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	var status = row.valiStatus;
	if(status == 1 || status == 2) {
		layer.msg("此紧急求助已经处理！");
		return false;
	}
	if(status != 1 && status != 2 && status != 0) {
		layer.msg("此状态禁止审核！");
		return false;
	}
	var win = new Common().getWindow("紧急求助审核面板", 500, 210, "valiInfo");
	win.window("open");
}

/**审核处理  true:认证成功； false 认证失败*/
function valiDown(isResult) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	$.messager.confirm("提示", "你确定需要审核该数据吗？", function(r) {
		if (r) {
			var status = 0;
			if(isResult) {
				status = 1; // 审核通过
			}else{
				status = 2; // 审核不通过
			}
			var isCity = $("#isAllCity").prop("checked");
			var isAllCity = 0;
			var cityIds = "";
			var isAllYards = 0;
			var yardIds = "";
			if(!isCity) {
				cityIds = $("#city").combotree("getValues");
				if(Common.isNull(cityIds)) {
					layer.msg("请选择需要推送的城市");
					return false;
				}
				var isYards = $("#isAllYards").prop("checked");
				if(!isYards) {
					yardIds = $("#yards").combotree("getValues");
					if(Common.isNull(yardIds)) {
						layer.msg("请选择需要推送的院子！");
						return false;
					}
					isAllYards = 2;
				}else{
					isAllYards = 1;
				}
				isAllCity = 2;
			}else{
				isAllCity = 1;
			}
			$("#main").mask("正在处理中，请稍候......");
			$.ajax({
				url : "changevalistatus",
				type : "post",
				data : {
					"post.id" : row.id,
					"post.valiStatus" : status,
					"post.cityIds" : cityIds + "",
					"post.isAllCity" : isAllCity,
					"post.yardIds" : yardIds + "",
					"post.isAllYards" : isAllYards,
					"post.nickName" : row.nickName,
					"post.content" : row.content,
					"post.courtyardName" : row.courtyardName
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"valiStatus" : status
						});
					}
					Common.closeWin("valiInfo");
					$("#main").unmask();
					layer.msg(json.msg);
				},
				error : function(json) {
					layer.msg( "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
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