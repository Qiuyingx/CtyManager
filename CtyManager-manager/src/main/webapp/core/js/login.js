/**
 * 登录页面js
 */
$(function() {
	$("#kaptcha").click(function() {
		$(this).attr("src", "kaptcha.jpg?" + Math.floor(Math.random() * 100));
	});
	$("#loginButton").click(function() {
		var account = $("#account").val();
		var pwd = $("#pwd").val();
		var code = $("#code").val();
		
		if (typeof (account) == "undefined" || null == account || "" == account) {
			layer.msg("请输入登录账号！");
			return false;
		}
		 /* || "" == account.trim()*/

		if (typeof (pwd) == "undefined" || null == pwd || "" == pwd) {
			layer.msg("请输入登录密码！");
			return false;
		}

	/*	if (typeof (code) == "undefined" || null == code || "" == code) {
			layer.msg("请输入验证码！");
			return false;
		}*/

		$.ajax({
			url : "login",
			data : {
				account : account,
				pwd : pwd
			},
			type : "post",
			dataType : "json",
			success : function(json) {
				if (json.result.state) {
					location.href = "center";
				} else {
					layer.msg(json.result.msg);
					return false;
				}
			},
			error : function() {
				alert("登录失败！");
			}
		});
	});
	
	$("#account").keydown(function(event) {
		switch (event.keyCode) {
		case 13:
			$("#pwd").focus();
			break;
		default:
			break;
		}
	});
	
/*	$("#pwd").keydown(function(event) {
		switch (event.keyCode) {
		case 13:
			$("#code").focus();
			break;
		default:
			break;
		}
	});*/
	
	$("#pwd, #loginButton").keydown(function(event) {
		switch (event.keyCode) {
		case 13:
			$("#loginButton").click();
			break;
		default:
			break;
		}
	});
});