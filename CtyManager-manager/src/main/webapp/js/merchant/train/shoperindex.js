
/**
 * 学堂条件检索
 */
function query() {
	//获取参数
	var title = $("#titleSearch").textbox("getValue");
	var status = $("#statusSearch").combobox("getValue");
	// 设置参数
	var queryParams = $("#grid").datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"query.title" : title,
		"query.status" : status+""
	});

	// 重新加载datagrid的数据
	$("#grid").datagrid("reload");
}

/**
 * 邻豆重置筛选条件
 */
function flush(){
	$("#titleSearch").textbox("setValue", "");
	$("#statusSearch").combobox("setValues", "");
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

	$("#titleSearch").textbox({
		onChange : function(newValue, oldValue) {
			query();
		}
	});

	$("#statusSearch").combobox({
		onSelect : function() {
			query();
		}
	});
});

/**
 * 禁用/启用店铺
 */
function updateStatus() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择想禁用/启用的学堂！");
		return false;
	}

	var status = row.status;
	var aftStatus = status == 2 ? 1 : 2;

	$.messager.confirm("提示", "你确定<span style=\"color:red\">" + (status == 2 ? "启用" : "禁用") + "</span>该店铺吗？", function(r) {
		if (r) {
			$.ajax({
				url : "updatestatus",
				type : "post",
				data : {
					"entity.id" : row.id,
					"entity.status" : aftStatus,
					"entity.userId" : row.userId
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.updateSelectedRowData("grid", {
							"status" : aftStatus
						});
					}

					layer.msg(json.msg);
				},
				error : function(json) {
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**编辑学堂*/
function updateTrainInfo() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择需要编辑的学堂！");
		return false;
	}
	if(row.status != 0 && row.status != 1 && row.status != 2) {
		layer.msg("学堂状态有问题，禁止修改！");
		return false;
	}
	if(row.status == 2) {
		layer.msg("该学堂已被禁用，请启用后才可编辑学堂信息~");
		return false;
	}
	window.location.href="../../other/train/traininfo/traininfoindex?managerId="+row.managerId;
}

/**查看学堂对应的所有课程*/
function lookAllCourse() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择需要操作的学堂！");
		return false;
	}
	window.location.href="../../other/train/traincourse/traincourseindex?trainId="+row.id;
}
/**
 * 查看该学堂的订单
 * @returns {Boolean}
 */
function getOrders(){
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择需要操作的学堂！");
		return false;
	}
	window.location.href="../../other/train/userorder/orderlist?trainId="+row.id;
}

function getAllOrders(){
	
	window.location.href="../../other/train/userorder/allorder";
}

/**添加课程*/
function addCourse() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg("请选择需要操作的学堂！");
		return false;
	}
	window.location.href="../../other/train/traincourse/content_edit?process=add&trainId="+row.id;
}

/**查看详情*/
function lookinfocontent() {
	var row = Common.getSelectedRow("grid");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	window.open(imgServer+"Miqu/share/shareTrain.do?trainId="+row.id ,'_blank');
}