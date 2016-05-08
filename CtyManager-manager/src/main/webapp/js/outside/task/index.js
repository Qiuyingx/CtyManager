/**
 * 运营部发帖量统计下载
 */
function downTaskCount() {

	var time = $("#time").datebox("getValue");

	$("#main").mask("正在统计中，请稍候......");
	$.ajax({
		url : "task/count",
		type : "post",
		data : {
			"time" : time
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
        	$("#mframe").prop("src","../../../excel/outside/task/"+json.msg+".xls");
		},
		error : function(json) {
			$("#main").unmask();
			alert( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 * 用户活跃量统计 
 */
function downPostCount() {
	$("#main").mask("正在统计中，请稍候......");
	$.ajax({
		url : "userpost/count",
		type : "post",
		data : {
			"courtyardIds" : $("#yardids").combotree("getValues")+""
		},
		dataType : "json",
		success : function(json) {
			$("#main").unmask();
        	$("#mframe").prop("src","../../../excel/outside/userpost/"+json.msg+".xls");
		},
		error : function(json) {
			$("#main").unmask();
			alert( "服务器繁忙，请稍候再试！！");
		}
	});
}
