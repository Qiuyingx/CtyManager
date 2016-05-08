/**
 * 定义一个Common类，存放一些常用的方法
 * 
 * @author jiangbo
 */
var Common = function() {

};

// 需实例化的方法new Common().getWindow();
Common.prototype = {
	/**
	 * 将一个easyui-window以窗口的形式展现出来
	 * 
	 * @param title //
	 *            窗口标题
	 * @param width //
	 *            窗口宽度
	 * @param height //
	 *            窗口高度
	 * @param divId //
	 *            层ID
	 * @returns
	 */
	getWindow : function(title, width, height, divId) {
		return $("#" + divId).window({
			title : title,
			width : width,
			height : height,
			// 居中打开
			top : ($(window).height() - height) * 0.5,
			left : ($(window).width() - width) * 0.5,
			shadow : true,
			modal : true, // 模态窗口
			iconCls : "icon-add",
			closed : true,
			minimizable : true,
			maximizable : true,
			collapsible : false
		});
	},
	method1 : function() {
		alert("xxxxxxxxxxxxxxxxx");
	}
	
};


//得到当前时间字符串，格式为：YYYY-MM-DD HH:MM:SS    
Common.curentTime = function(){   
    var now = new Date();  
         
    var year = now.getFullYear();       //年  
    var month = now.getMonth() + 1;     //月  
    var day = now.getDate();            //日  
         
    var hh = now.getHours();            //时  
    var mm = now.getMinutes();          //分  
    var ss=now.getSeconds();            //秒  
         
    var clock = year + "-";  
         
    if(month < 10) clock += "0";         
    clock += month + "-";  
         
    if(day < 10) clock += "0";   
    clock += day + " ";  
         
    if(hh < 10) clock += "0";  
    clock += hh + ":";  
  
    if (mm < 10) clock += '0';   
    clock += mm+ ":";  
          
    if (ss < 10) clock += '0';   
    clock += ss;  
  
    return(clock);   
}  

// 查询面板
Common.openQueryPanel = function(width, height) {
	return $("#queryPanel").window({
		title : "筛选面板",
		width : width,
		height : height,
		// 居中打开
		top : ($(window).height() - height) * 0.5,
		left : ($(window).width() - width) * 0.5,
		shadow : true,
		modal : true, // 模态窗口
		closed : true,
		iconCls : "icon-search",
		minimizable : false,
		maximizable : false,
		collapsible : false,
		resizable : false
	});
}


// 静太方法写这里
Common.staticFnExample1 = function() {
	alert("method1 is static");
}

// 窗口显示文本信息（layer） text：需要展示的文本内容
Common.showText = function(text) {
	layer.open({
	    type: 1,
	    skin: 'layui-layer-demo', //样式类名
	    closeBtn: false, //不显示关闭按钮
	    shift: 2,
	    shadeClose: true, //开启遮罩关闭
	    content: "<div style=\"padding:20px;\">"+faceIcon(text)+"</div>"
	});
}

// 窗口显示图文信息 （layer） text：需要显示的图文内容
Common.showContent = function(text) {
	layer.open({
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['250px', '400px'], //宽高
	    shadeClose: true, //开启遮罩关闭
	    content: "<div style=\"padding:5px;\">"+text+"</div>"
	});
}

// 多图浏览  httpUrl 服务器地址，对应“${httpUrl}” ; images:多图的拼接地址，如：imagePath|imagePath2|
Common.showPictures = function(httpUrl, images, index) {
	if(Common.isNull(images)) {
		images = "";
	}
	if(images.length>0) {
		if(images.charAt(images.length-1) == "|") {
			images = images.substring(0, images.length-1);
		}
	}
	var imgSplit = new Array();
		imgSplit = images.split("|");
	var showData = new Array();
	var imgs = "";
	for(var i=0; i<imgSplit.length; i++) {
		imgs = httpUrl + imgSplit[i];
		var ob = new Object();
		ob.alt = ""+i;
		ob.src= imgs;
		showData = showData.concat(ob);
	}

	viewPics(showData, index);
}

/**获取多图路径数组*/
Common.getPicsPath = function(httpUrl, images) {
	if(Common.isNull(images)) {
		images = "";
	}
	if(images.length>0) {
		if(images.charAt(images.length-1) == "|") {
			images = images.substring(0, images.length-1);
		}
	}
	var imgSplit = new Array();
		imgSplit = images.split("|");
	var showData = new Array();
	var imgs = "";
	for(var i=0; i<imgSplit.length; i++) {
		imgs = httpUrl + imgSplit[i];
		var ob = new Object();
		ob.alt = ""+i;
		ob.src= imgs;
		showData = showData.concat(ob);
	}
	
	return showData;
}

//单图浏览 httpUrl 服务器地址，对应“${httpUrl}” ; image : 单个图片的地址
Common.showPicture = function(httpUrl, image) {
	var data =  [   
			        {
			            "alt": "图片名",
			            "src": httpUrl+image
			        }
			    ];
	viewPics(data);
}

// 左下角消息提示
Common.msgslide = function(msg) {
	$.messager.show({
		title : '操作提示',
		msg : msg,
		timeout : 3000,
		showType : 'slide'
	});

}
Common.msgwait = function() {
	$.messager.show({
		title : '',
		msg : "正在处理当中，请稍后...",
		timeout : 500,
		showType : 'fade'
	});

}
/**
 * 获取data-grid列表选中的行
 * 
 * @returns
 */
Common.getSelectedRow = function(divId) {
	return $("#" + divId).datagrid("getSelected");
}

Common.getAllSelectedRow = function(divId) {
	return $("#" + divId).datagrid("getSelections");
}

Common.closeWin = function(divId) {
	$("#" + divId).window("close");
}

Common.updateSelectedRowData = function(divId, rowData) {
	// 直接更新行数据
	var jqObj = $("#" + divId);
	var row = jqObj.datagrid("getSelected");
	var index = jqObj.datagrid("getRowIndex", row);
	jqObj.datagrid("updateRow", {
		index : index,
		row : rowData
	});
}

Common.deleteSelectedRow = function(divId) {
	// 直接更新行数据
	var jqObj = $("#" + divId);
	var row = jqObj.datagrid("getSelected");
	var index = jqObj.datagrid("getRowIndex", row);
	jqObj.datagrid("deleteRow", index);
}

Common.isNull = function(value) {
	return typeof (value) == "undefined" || value == "" || value == null;
}

Common.refreshGrid = function(divId, isCleanParam) {
	// 重置查询条件
	if (!isCleanParam) {
		$("#" + divId).datagrid("options").queryParams = {};
	}
	$("#" + divId).datagrid("reload");
}

Common.reloadByParentId = function(divId, parentId) {
	var queryParams = $("#" + divId).datagrid("options").queryParams;
	queryParams = $.extend(queryParams, {
		"parentId" : parentId,
		"name" : ""
	});

	$("#" + divId).datagrid("reload");
	$("#" + divId).datagrid("unselectAll");
}

Common.queryGrid = function(divId, params) {
	// 设置参数
	var queryParams = $("#" + divId).datagrid("options").queryParams;
	queryParams = $.extend(queryParams, params);

	// 重新加载datagrid的数据
	$("#" + divId).datagrid("reload");
}

/**
 * 返回字符串长度，中文算两个
 * @param s
 * @returns {Number}
 */
Common.getStrLen = function(s) {
	var len = 0;
	for (var i = 0; i < s.length; i++) {
		if (s.charCodeAt(i) > 127 || s.charCodeAt(i) == 94) {
			len += 2;
		} else {
			len++;
		}
	}
	return len;
}

Common.compareDate = function(start, end) {
	start = new Date(start.replace(/\-/g,'/'));
	end = new Date(end.replace(/\-/g,'/'));
	
	return end > start;
}
