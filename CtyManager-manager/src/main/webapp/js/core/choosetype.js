$(function(){
	//回复消息选择
	$("#chooseMsgContentBtn").click(function(){
			var msgtypeid = $('#msgtypeid').combobox("getValue");
		   	if (Common.isNull(msgtypeid)) {
				$.messager.alert("提示", "请选择消息类型！");
				return false;
			}

			var win;
		        
			if(msgtypeid=="text"){//文本回复
				responseText();
				win = new Common().getWindow("文本回复选择面板", 820, 520, "openResponseTextChooseWin");
			}else if(msgtypeid == "image"){//图片回复
				responseImage();
			}else if(msgtypeid == "news"){//图文回复
				responseNews();
				win = new Common().getWindow("图文回复选择面板", 820, 520, "openResponseNewsChooseWin");
			}else{
				$.messager.alert("提示", "该回复类型暂时还未实现选择功能");
			}
			if(win){
		        win.window("open");
			}
	        
	});
});
//文本回复选择
function responseText(){	
	/**
	 * 文本回复 
	 */
	$(function(){
		//文本分组显示
		var options = $("#gridgrouptextchoose").datagrid("options");
		options.onClickRow = function(index, row) {
		   var groupid = row.groupid;
		   var queryParams = $("#gridtextchoose").datagrid("options").queryParams;
			queryParams = $.extend(queryParams, {
				"groupId" : groupid
			});
		    $('#gridtextchoose').datagrid('reload');
		};
		//填充选中的文本
		var options = $("#gridtextchoose").datagrid("options");
		options.onClickRow = function(index, row) {
		   $("#chooseTextShow").textbox("setValue",row.content);
		   $("#chooseIdHidden").val(row.textid);
		   
		   $("#chooseHidden").val(row.content);
		};
		//点击确认 关闭窗口
		$("#textBtn").click(function(){
			Common.closeWin("openResponseTextChooseWin");
		});
	});
}
//图片回复选择
function responseImage(){
	
}
//图文回复选择
function responseNews(){
	//图文分组
	var options = $("#gridgroupnewschoose").datagrid("options");
	options.onClickRow = function(index, row) {
	   var groupid = row.groupid;
	var queryParams = $("#gridnewschoose").datagrid("options").queryParams;
		queryParams = $.extend(queryParams, {
			"groupId" : groupid
		});
	    $('#gridnewschoose').datagrid('reload');
	};
	
	//填充选中的文本
	var options = $("#gridnewschoose").datagrid("options");
	options.onClickRow = function(index, row) {
	   $("#chooseNewsShow").textbox("setValue",row.newsname);
	   $("#chooseIdHidden").val(row.newsid);
	   
	   $("#chooseHidden").val(row.newsname);
	};
	//点击确认 关闭窗口
	$("#newsBtn").click(function(){
		Common.closeWin("openResponseNewsChooseWin");
	});
}