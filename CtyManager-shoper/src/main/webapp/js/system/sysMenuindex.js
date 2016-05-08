var urls;
var isRoot = false;
function openSysMenuInfoWin(isAdd, node) {
	// 打开窗口
	var win;
	if (isAdd) {
		urls = "add";
		// 清空win内所有域
		cleanWin(node);
		//按钮显隐控制
		adShow();

		win = new Common().getWindow("新增系统菜单管理", 330, 370, "sysMenuInfo");
		win.window("open");

	} else {
		win = new Common().getWindow("修改系统菜单管理", 330, 370, "sysMenuInfo");
		win.window("open");

		urls = "update";
		//按钮显隐控制
		upShow();
		
        if(!node){
        	upForChange();
        }else{
        	upInintValue(node);
        }
	}
}

/**
 * 添加面板样式控制
 */
function adShow(){
	$("#"+urls+"_submitBtn").attr("onclick", "javascript:submit('add')");
	$("#upBt").show();
	$("#addBt").hide();
}
/**
 * 修改面板样式控制
 */
function upShow(){
	$("#"+urls+"_submitBtn").attr("onclick", "javascript:submit('update')");
	$("#upBt").hide();
	$("#addBt").show();
}

/**
 * 清空win里面的域
 */
function cleanWin(node,url) {
	$("#"+urls+"_sysMenuInfo_sysmenuname").textbox("setValue", "");
	$("#sysMenuInfo_parentmenuid").val($("#sysMenuInfo_sysmenuid").val());
	$("#"+urls+"_sysMenuInfo_parentmenuname").text($("#hi_sysmenuname").val());
	$("#"+urls+"_sysMenuInfo_url").textbox("setValue", "");
	$("#"+urls+"_status_1").prop("checked", "checked");
	$("#"+urls+"_isfunction_0").prop("checked", "checked");
	$("#"+urls+"_sysMenuInfo_sort").textbox("setValue", "1");
	$("#add_sysMenuInfo_sysmenuname").textbox("setValue","");
	if(isRoot==true){
		$("#hi_parentmenuid").val("0");
		$("#sysMenuInfo_sysmenuid").val("0");
		$("#hi_sysmenuname").val("所有权限");
		$("#hi_parentmenuname").val("所有权限");
		$("#add_sysMenuInfo_parentmenuname").text("所有权限");
	}
}
/**
 * 修改初始赋值 
 */
function upInintValue(node,url){
	$("#sysMenuInfo_sysmenuid").val(node.id);

	$("#"+urls+"_sysMenuInfo_sysmenuname").textbox("setValue", node.text);
	$("#hi_sysmenuname").val(node.text);
	$("#"+urls+"_sysMenuInfo_parentmenuid").val($("#permissionTree").tree("getParent", node.target).id);
	$("#hi_parentmenuid").val($("#permissionTree").tree("getParent", node.target).id);
	$("#"+urls+"_sysMenuInfo_parentmenuname").html($("#permissionTree").tree("getParent", node.target).text);
	$("#hi_parentmenuname").val($("#permissionTree").tree("getParent", node.target).text);
	$("#"+urls+"_sysMenuInfo_url").textbox("setValue", node.attributes.url);
	$("#hi_url").val(node.attributes.url);
	$("#"+urls+"_status_" + node.attributes.status).prop("checked", "checked");
	$("#hi_status").val(node.attributes.status);
	$("#"+urls+"_isfunction_" + node.attributes.isfunction).prop("checked", "checked");
	$("#hi_isfunction").val(node.attributes.isfunction);
	$("#"+urls+"_sysMenuInfo_sort").textbox("setValue", node.attributes.sort);
	$("#hi_sort").val(node.attributes.sort);
}
/**
 * 按钮切换 修改面板
 */
function upForChange(url){
	var stat = $("#hi_status").val();
	var func = $("#hi_isfunction").val();
	$("#"+urls+"_sysMenuInfo_sysmenuname").textbox("setValue", $("#hi_sysmenuname").val());
	$("#sysMenuInfo_parentmenuid").val($("#hi_parentmenuid").val());
	$("#"+urls+"_sysMenuInfo_url").textbox("setValue", $("#hi_url").val());
	$("#"+urls+"_status_"+stat).prop("checked", "checked");
	$("#"+urls+"_isfunction_"+func).prop("checked", "checked");
	$("#"+urls+"_sysMenuInfo_sort").textbox("setValue", $("#hi_sort").val());
}
/**
 * 提交
 * 
 * @returns
 */
function submit(url) {
	var isAdd;
	var parentmenuid;
	
	if (url == "add") {
		parentmenuid = $("#sysMenuInfo_sysmenuid").val();
		isAdd = true;
	} else {
		parentmenuid = $("#hi_parentmenuid").val();
		isAdd = false;
	}

	var sysmenuid = $("#sysMenuInfo_sysmenuid").val();

	var sysmenuname = $("#"+url+"_sysMenuInfo_sysmenuname").val();
	if (Common.isNull(sysmenuname)) {
		$.messager.alert("提示", "请输入菜单名称！");
		return false;
	}
	if (Common.isNull(parentmenuid)) {
		$.messager.alert("提示", "请输入上级菜单ID！");
		return false;
	}
	var urlStr = $("#"+url+"_sysMenuInfo_url").val();
	if (Common.isNull(url)) {
		$.messager.alert("提示", "请输入菜单url！");
		return false;
	}
	var isfunction = $("input[name='"+url+"_isfunction']:checked").val();
	if (Common.isNull(isfunction)) {
		$.messager.alert("提示", "请输入是否功能菜单！");
		return false;
	}
	var status = $("input[name='"+url+"_status']:checked").val();
	if (Common.isNull(status)) {
		$.messager.alert("提示", "请输入状态！");
		return false;
	}
	var sort = $("#"+url+"_sysMenuInfo_sort").val();
	if (Common.isNull(sort)) {
		$.messager.alert("提示", "请输入排序！");
		return false;
	}


	$.ajax({
		url : url,
		type : "post",
		data : {
			"sysMenu.sysmenuid" : sysmenuid,
			"sysMenu.sysmenuname" : sysmenuname,
			"sysMenu.parentmenuid" : parentmenuid,
			"sysMenu.url" : urlStr,
			"sysMenu.isfunction" : isfunction,
			"sysMenu.status" : status,
			"sysMenu.sort" : sort
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				if (isAdd) {
					// 刷新列表
				} else {
					// 更新列表

		            Common.msgslide("操作成功！");

				}

				// 关闭窗口
				Common.closeWin("sysMenuInfo");
			}

            Common.msgslide(json.msg);
		},
		error : function(json) {
			$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 *  tab页 基础选中操作
 *      
 *      操作菜单为 "顶级菜单"，进行如下操作：
 *          1：隐藏修改选项卡，打开添加选项卡
 *          2：打开系统菜单操作面板
 *          3：标示操作的菜单为顶级元素  isRoot = true;
 *          4：移除 删除工具
 *          
 *      操作菜单为 "非顶级菜单"，进行如下操作：
 *          1：显示添加
 */
$(function() {
	$('#permissionTree').tree({
		onClick : function(node) {
			if(node.text  != "所有权限"){
				showUpdateTab();
				openSysMenuInfoWin(false, node);
				isRoot = false;
				$("#sysmenutabs").tabs("select", 0);
				addTabsTool();
			}else{
				justAddPanelShow();
				isRoot = true;
				$("#sysmenutabs").tabs("select", 1);
				moveTabsTool();
			}
		},
		onDrop : function(target, source, point){
			Common.closeWin("sysMenuInfo");
		}
	});
	/**
	*  onSelect :  tab页切换
	*  tools :  工具（删除）
	**/
	$('#sysmenutabs').tabs({
		onSelect: function(title,index){
			if(index == 0){
				updatePanelShow();
			}else if(index == 1){
				addPanelShow();
			}
	    }
	});

});

/**
 * 子级菜单 添加删除工具
 */
function addTabsTool(){
	$('#sysmenutabs').tabs({
		tools:[{
			iconCls:'icon-remove',
			handler:function(){
				del();
			}
		}]
	});
}

/**
 * 顶级菜单 屏蔽删除工具 
 */
function moveTabsTool(){
	$('#sysmenutabs').tabs({tools:''});
}

/**
 * 修改选项卡默认展示
 */
function updatePanelShow(){
	$("#addPanel div").hide();
	$("#updatePanel div").show();
	openSysMenuInfoWin(false);
}

/**
 * 添加选项卡默认展示
 */
function addPanelShow(){
	$("#updatePanel div").hide();
	$("#addPanel div").show();
	openSysMenuInfoWin(true);
}

/**
 * 仅展示添加选项卡
 */
function justAddPanelShow(){
	hideUpdateTab();
	$("#addPanel div").show();
	openSysMenuInfoWin(true);
}

/**
 * 显示修改选项卡
 */
function showUpdateTab(){
	tab_option = $('#sysmenutabs').tabs('getTab',0 ).panel('options').tab;  
	tab_option.show();  
}

/**
 * 隐藏修改选项卡
 */
function hideUpdateTab(){
	tab_option = $('#sysmenutabs').tabs('getTab',0).panel('options').tab;  
	tab_option.hide();
}

/**
 * 删除一条记录
 */
function del() {
	var sysmenuid = $("#sysMenuInfo_sysmenuid").val();
	if (Common.isNull(sysmenuid)) {
		$.messager.alert("提示", "无法完成删除操作！");
		return false;
	}

	$.messager.confirm("提示", "你确定删除该条记录吗？", function(r) {
		if (r) {
			$.ajax({
				url : "del",
				type : "post",
				data : {
					"sysMenu.sysmenuid" : sysmenuid
				},
				dataType : "json",
				success : function(json) {
					if (json.isSuccess) {
						Common.closeWin("sysMenuInfo");
					}

		            Common.msgslide(json.msg);
				},
				error : function(json) {
					$.messager.alert("提示", "服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}

/**
 * 页面打开时加载loadmask
 */
$(function() {
	$("#main").mask("正在加载数据请稍候......");
})

function hideMask() {
	$("#main").unmask();
}

 