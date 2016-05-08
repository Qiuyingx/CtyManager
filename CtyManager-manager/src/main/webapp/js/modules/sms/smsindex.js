/**
 * 短信发送
 */
function sendSms() {
	var mobiles = $("#mobiles").textbox("getValue");
	if(Common.isNull(mobiles)) {
		layer.msg("手机号码至少为一个！");
		return false;
	}
	var content = $("#content").textbox("getValue");
	if(Common.isNull(content)) {
		layer.msg("请输入短信内容！");
		return false;
	}
	$.ajax({
		url : "sendSms",
		type : "post",
		data : {
			"mobiles" : mobiles,
			"content" : content
		},
		async: false,
		dataType : "json",
		success : function(json) {
			alert(json.msg);
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}