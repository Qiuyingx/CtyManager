/**
 * 条件检索
 */
function query() {
	//获取参数
	var title = $("#titleSearch").textbox("getValue");
	var nickName = $("#nicknameSearch").textbox("getValue");
	var startDate = $("#startDate").datebox("getValue");
	var endDate = $("#endDate").datebox("getValue");
	var status = $("#statusSearch").combobox("getValue");

	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.actTitle" : title,
		"query.nickName" : nickName,
		"query.startTime" : startDate,
		"query.endTime" : endDate,
		"query.isDisable" : status
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 重置筛选条件
 */
function flush(){
	$("#titleSearch").textbox("setValue", "");
	$("#nicknameSearch").textbox("setValue", "");
	$("#startDate").datebox("setValue", "");
	$("#endDate").datebox("setValue", "");
	$("#statusSearch").val("");
	//$("#city").combobox("setValue", "");
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
				url : "deleteact",
				type : "post",
				data : {
					"entity.id" : row.id
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.deleteSelectedRow("grid");
					}

                    layer.msg(json.msg);
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

/**查看活动报名*/
function lookSignup() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择活动！");
		return false;
	}
	window.location.href="../signup/index?actId="+row.id;
}

/**查看评论详情*/
function lookReply() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择活动！");
		return false;
	}
	window.location.href="../reply/replyindex?actId="+row.id;
}

/**编辑预览*/
function preView() {
	var content = ue.getContent();
 	if(Common.isNull(content)) {
		layer.msg( "请编辑活动内容！");
		return false;
	}
 	Common.showContent(content);
}

/**修改直接添加@关联关系*/
function onlineAddRelations(id, nickName) {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "操作失败！");
		return false;
	}
	 $.ajax({
		url : "../../attarget/info/add",
		type : "post",
		data : {
			"entity.atTargetId" : id,
			"entity.atNickName" : nickName,
			"entity.scene" : 4,
			"entity.append" : row.id
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
		},
		error : function(json) {
			layer.msg( "服务器繁忙，操作失败！！");
		}
	});
}

/**添加后台处理@关联关系  保存@对象列表  格式如下：id|nickName;nickName*/
function otherAddRelations(id, nickName) {
	var atList = $("#atTargetUrl").val();
	var str = id+"|"+nickName+";";

	// 判断是否已经存在
	if(atList.indexOf(str) == -1) {
		atList += str;
	}
	
	$("#atTargetUrl").val(atList);
}

/**添加@关联关系*/
function addRelations(id, nickName) {

	if ($("#submitBtn").attr("value") == "新增") {
		otherAddRelations(id, nickName);
	}else{
		onlineAddRelations(id, nickName);
	}
	
}

/**管理--修改@关联关系*/
function managerUpdateAt() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "操作失败！");
		return false;
	}
	$("#main").mask("正在搜索，请稍后...");
	var atList = "";
	$.ajax({
		url : "../../attarget/info/query",
		type : "post",
		data : {
			"entity.scene" : 4,
			"entity.append" : row.id
		},
		async: false,
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
			var data = json.data;
			if(data.length <= 0) {
				layer.msg("已不存在@对象了！");
				return false;
			}
			
			for(var i=0; i<data.length; i++) {
				var nickName = data[i].atNickName;
				var id = data[i].atTargetId;
				var scene = data[i].scene;
				var append = data[i].append;
				
				atList += "<div style=\"margin-left:10px;\">";
				atList += "<span style=\"margin-left:3px\">"+nickName+"</span>";
				atList += "<span style=\"color:green; float:right; cursor:pointer; margin-top:0px; margin-right:15px;\" onclick=\"delAtTarget("+id+",'"+nickName+"',"+scene+","+append+")\">删除</span>"
				atList += "</div><br/>";
			}
		},
		error : function(json) {
			layer.msg( "服务器繁忙，搜索失败！！");
		}
	});
	return atList;
}

/**管理--添加@关联关系*/
function managerAddAt() {
	var atList = $("#atTargetUrl").val();
	if(Common.isNull(atList)) {
		layer.msg("已不存在@对象了！");
		return false;
	}
	var atObject = new Array();
	atObject = atList.split(";");
	var showList = "";
	
	for(var i=0;i<atObject.length;i++) {
		var attrs = new Array();
		attrs = atObject[i].split("|");
		if(!Common.isNull(attrs[0])) {
			showList += "<div style=\"margin-left:10px;\">";
			showList += "<span style=\"margin-left:3px\">"+attrs[1]+"</span>";
			showList += "<span style=\"color:green; float:right; cursor:pointer; margin-top:0px; margin-right:15px;\" onclick=\"managerDelAt("+attrs[0]+",'"+attrs[1]+"')\">删除</span>"
			showList += "</div><br/>";
		}
	}
	return showList;
}

/**管理--删除@关联关系*/
function managerDelAt(id, nickName) {
	var atList = $("#atTargetUrl").val();
	if(Common.isNull(atList)) {
		layer.msg("已不存在@对象了！");
		return false;
	}
	if(!Common.isNull(id) && !Common.isNull(nickName)) {
		var reg=new RegExp(id+"\\|"+nickName+";","g");
		$("#atTargetUrl").val(atList.replace(reg,""));
		$("#atManagerList").html(managerAddAt());

		var reg=new RegExp("@"+nickName,"g");
		ue.setContent(ue.getContent().replace(reg,""));
		
		layer.msg("删除成功！");
		return true;
	}
	layer.msg("删除失败！");
}

/**管理@选项卡*/
function adminAtTarget(){

	if ($("#submitBtn").attr("value") == "新增") {
		return managerAddAt();
	}else{
		return managerUpdateAt();
	}
	
}

/**添加活动*/
function addAct() {
	window.location.href="../../act/info/act_edit?process=add";
}

/**编辑活动*/
function updateAct() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.location.href="../../act/info/act_edit?process=update&id="+row.id;
}