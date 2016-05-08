/**
 * 条件检索
 */
function query() {
	//获取参数
	var nickName = $("#nicknameSearch").textbox("getValue");
	var tel = $("#telSearch").textbox("getValue");
	var startDate = $("#startDate").datebox("getValue");
	var endDate = $("#endDate").datebox("getValue");
	
	var gender = $("#genderSearch").combobox("getValue");
	var platform = $("#platformSearch").combobox("getValue");
	
	var lastStartTime = $("#lastStartTime").datebox("getValue");
	var lastEndTime = $("#lastEndTime").datebox("getValue");

	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.nickName" : nickName,
		"query.tel" : tel,
		"query.startTime" : startDate,
		"query.endTime" : endDate,
		"query.gender" : gender,
		"query.platform" : platform,
		"query.lastLoginStartTime" : lastStartTime,
		"query.lastLoginEndTime" : lastEndTime
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 重置筛选条件
 */
function flush(){
	$("#telSearch").textbox("setValue", "");
	$("#nicknameSearch").textbox("setValue", "");
	$("#startDate").datebox("setValue", "");
	$("#endDate").datebox("setValue", "");
	$("#statusSearch").val("");
	$("#genderSearch").combobox("setValue", "");
	$("#platformSearch").combobox("setValue", "");
	$("#lastStartTime").datebox("setValue", "");
	$("#lastEndTime").datebox("setValue", "");
	query();
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
	
	// 点击自动筛选
	$("#searchButton").click(function(){
		query();
		Common.openQueryPanel(470,320).window("close");
	});
	
	$("#nicknameSearch, #telSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});
	
	$("#startDate, #endDate, #lastStartTime, #lastEndTime").datebox({
		onSelect : function(date) {
			query();
		}
	});
	
	$("#genderSearch, #platformSearch").combobox({
		onSelect : function(record) {
			query();
		}
	});
});

/**查看大图*/
function  lookPics(httpUrl, images, index) {
	Common.showPictures(httpUrl, images, index);
}

/**进入用户空间*/
function lookUserHome() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择用户信息！");
		return false;
	}
	window.location.href="userhomeindex?id="+row.id;
}

/**条件筛选*/
function enterQueryPanel() {
	Common.openQueryPanel(470,320).window("open");
}