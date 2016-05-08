$(function() {
	var win;
	//文章选择
	$("#chooseContentBtn").click(function() {
		win = new Common().getWindow("文章选择面板", 820, 520, "openResponseContentChooseWin");
		win.window("open");
	});

	//栏目分组查询
	var optionsChannel = $("#gridchooseChannel").datagrid("options");
	optionsChannel.onClickRow = function(index, row) {
		var channelId = row.channelId;
		var queryParams = $("#gridchooseContent").datagrid("options").queryParams;
		queryParams = $.extend(queryParams, {
			"channelId" : channelId
		});
		$('#gridchooseContent').datagrid('reload');
	};

	//填充选中的文章
	var optionsContent = $("#gridchooseContent").datagrid("options");
	optionsContent.onClickRow = function(index, row) {
		$("#chooseContentShow").textbox("setValue", row.title);
		$("#choosedcontentid").val(row.contentId);
		$("#choosedcontenttitle").val(row.title);
		$("#choosedcontentdescription").val(row.descript);
	};

	//填充选中的模板
	var optionsModelPage = $("#gridchooseModelPage").datagrid("options");
	optionsModelPage.onClickRow = function(index, row) {
		$("#chooseModelPageShow").textbox("setValue", row.modelurl);
		$("#choosedmodelurl").val(row.modelurl);
	};
	//确认
	$("#contentBtn").click(function() {
		var content = $("#choosedcontentid").val();
		if (Common.isNull(content)) {
			$.messager.alert("提示", "请选择需要显示的文章内容！");
			return false;
		}
		var modelurl = $("#choosedmodelurl").val();
		if (Common.isNull(modelurl)) {
			$.messager.alert("提示", "请选择展示模板！");
			return false;
		}
		$("#wxResponseNewsItemInfo_url").textbox("setValue", modelurl + content);
		Common.closeWin("openResponseContentChooseWin");
		clickContentBtn();
	});
});