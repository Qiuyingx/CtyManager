$(function(){
	// 确认推送
	$("#submitBtn").click(function(){
		startPush();
	});
	
	// 重置
	$("#reloadBtn").click(function(){
		reload();
	});
	
	//进入公告列表
	$("#viewListBtn").click(function(){
		viewList();
	});
		
});

/**点击推送按钮*/
function startPush() {
	var content = $("#content").val();
	if(Common.isNull(content)) {
		layer.msg("请输入公告的内容！");
		return false;
	}
	$.messager.confirm("提示", "你确定要发布此公告吗？", function(r) {
		if (r) {
			$("#main").mask("正在发送中，请稍候......");
			$.ajax({
				url : "radiosend",
				type : "post",
				data : {
					"entity.content" : content
				},
				dataType : "json",
				success : function(json) {
					$("#main").unmask();
					 layer.msg(json.msg);
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**重置*/
function reload() {
	$("#content").textbox("setValue", "");
}

/**进入公告列表*/
function viewList() {
	window.location.href="noticeindex?query.noticeType=1&query.pushType=0&forwardPath=radioindex";
}