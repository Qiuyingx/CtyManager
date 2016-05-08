$(function(){
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
		layer.msg("请输入群发的内容！");
		return false;
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
	
	$.messager.confirm("提示", "你确定要开始群发消息吗？", function(r) {
		if (r) {
			$("#main").mask("正在群发中，请稍候......");
			$.ajax({
				url : "send",
				type : "post",
				data : {
					"entity.content" : content,
					"entity.userId" : 0,
					"entity.cityIds" : cityIds + "",
					"entity.isAllCity" : isAllCity,
					"entity.yardIds" : yardIds + "",
					"entity.isAllYards" : isAllYards
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
	$("#city").textbox({
		disabled:true
	});
	$("#yards").textbox({
		disabled:true
	});
	$("#city").combotree("setValues", "");
	$("#yards").combotree("setValues", "");
	$("#isAllYards").prop("checked", true);
	$("#isAllCity").prop("checked", true);
}

/**进入群发列表*/
function viewList() {
	window.location.href="noticeindex?query.noticeType=0&query.pushType=6&forwardPath=index";
}