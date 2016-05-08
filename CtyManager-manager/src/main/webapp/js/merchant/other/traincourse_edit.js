$(function() {
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});

	/**
	 * 点击上传Banner图
	 */
	$("#chooseImgBtnBanner, #imgShowDivBanner").click(function() {
		$("#fileNameBanner").click();
	});
	
	/**
	 * 类型选择
	 */
/*	$("#typeParent").combobox({
		onChange : function(newValue, oldValue) {
			var comUrl = "../../js/data/json/classType_qinzi.json";
			if(newValue == 2) {
				comUrl = "../../js/data/json/classType_pinzhi.json";
			}
			$("#typeId").combobox({
				url : comUrl
			});
		}
	});*/
	
	ue.ready(function () {
		if(!Common.isNull(process) && process == "update") {
			if(!Common.isNull(id)) {
				getContent(id);
			}else{
				layer.msg("无法获取原文章信息，禁止修改！");
			}
		}
	});
	
	$("#isAct").change(function() {
		var isChecked = $(this).prop("checked")
		if(isChecked) {
			$("#startTr").show();
			$("#endTr").show();
		}else{
			$("#startTr").hide();
			$("#endTr").hide();
		}
	});
	
	
	$("#refundType").combobox({
		onSelect : function() {
			var v = $("#refundType").combobox("getValue");
			if(v==2){
				$("#refunddaystr").show();
			}else{
				$("#refunddaystr").hide();
			}
		}
	});
});
/**
 * 通过ID内容详情
 * @param id 活动ID
 */
function getContent(id) {
	$.ajax({
		url : "getcontentinfo",
		type : "post",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(json) {
			if (json.isSuccess) {
				info =  json.info;
				setFormValue(info);
			}else{
				layer.msg("不存在活动信息，或者已被管理员删除！");
			}
		},
		error : function(json) {
			layer.msg( "服务器繁忙，请稍候再试！！");
		}
	});
}

/**
 * 规格表添加新行
 */
function addItem(){
	$('#itemstable').datagrid('appendRow',
		 {}
	);
}

/**
 * 删除规格表格选定行
 * @returns {Boolean}
 */
function removeItem() {
	var row = Common.getSelectedRow("itemstable");
	if (!row) {
		layer.msg( "请选择需要操作的数据！");
		return false;
	}
	Common.deleteSelectedRow("itemstable");
	//$('#itemstable').datagrid('beginEdit', 1);
}

function updateActions(index){
	$('#itemstable').datagrid('updateRow',{
		index: index,
		row:{}
	});
}



/**初始化表单值*/
function setFormValue(info) {
	$("#title").textbox("setValue", info.title);
	$("#description").textbox("setValue", info.description);
	$("#hours").textbox("setValue", info.hours);
	$("#studyTime").textbox("setValue", info.studyTime);
	$("#tel").textbox("setValue", info.tel);
	//$("#price").textbox("setValue", info.price);
	/*$("#priceunit").textbox("setValue", info.priceunit);*/
	$("#copies").numberspinner("setValue", info.copies);
	$("#address").textbox("setValue", info.address);
	$("#longitude").textbox("setValue", info.longitude);
	$("#latitude").textbox("setValue", info.latitude);
	
	$("#cityId").combobox("setValue", info.cityId);
	/*$("#typeParent").combobox("setValue", info.typeParent);*/
	$("#typeId").combobox("setValue", info.typeId);
	$("#refundType").combobox("setValue", info.refundType);
	$("#refundRatio").textbox("setValue", info.refundRatio);
	if(info.refundType==2){
		$("#refundDays").textbox("setValue", info.refundDays);
		$("#refunddaystr").show();
	}else{
		$("#refundDays").textbox("setValue", 0);
	}

	$("#uploadImgArea").attr("src", imgServer+info.titleImg);
	$("#image").val(info.titleImg);
	$("#courseUrl").textbox("setValue", info.courseUrl);

	if(!Common.isNull(info.bannerImg) && info.bannerImg.length > 0) {
		$("#imageBanner").val(info.bannerImg);
		$("#uploadImgAreaBanner").prop("src", imgServer+info.bannerImg);
		$("#uploadImgAreaBanner").show();
	}else{
		$("#imgAreaBanner").show();
		$("#uploadImgAreaBanner").hide();
	}
	if(!Common.isNull(info.origi_prices)&&!Common.isNull(info.items)&&!Common.isNull(info.prices)&&!Common.isNull(info.shopPrices)){
		var items = info.items.split('|');
		var prices = info.prices.split('|');
		var origi_prices = info.origi_prices.split('|');
		var shopPrices = info.shopPrices.split('|');
		var datas = new Array();
		for(var i=0;i<items.length;i++){
			if(Common.isNull(items[i])){
				continue;
			}
			datas[i]={'itemName':items[i],'price':prices[i],'origiprice':origi_prices[i],'trainprice':shopPrices[i]};
		}
		var obj = {'total':items.length,'rows':datas};
		$('#itemstable').datagrid('loadData',obj);
	}
	
	ue.setContent(info.content);
	
	if(info.classType == "2") {
		$("#isAct").prop("checked", true);
		$("#startTr").show();
		$("#endTr").show();
		$("#startTime").datetimebox("setValue", mills2DateTime(info.startTime));
		$("#endTime").datetimebox("setValue", mills2DateTime(info.endTime));
	}else{
		$("#startTr").hide();
		$("#endTr").hide();
		$("#isAct").prop("checked", false);
	}
}

/** 上传封面图，展示图片 */
function imageProcesser(imgPath, isPicShow) {
	if(isPicShow == "logos") {
		$("#uploadImgLogo").prop("src", imgServer + imgPath);
		$("#uploadImgLogo").show();
		$("#imgLogo").hide();
		$("#imageLogo").val(imgPath);
	}else if(isPicShow == "faceImgs") {
		$("#uploadImgArea").prop("src", imgServer + imgPath);
		$("#uploadImgArea").show();
		$("#imgArea").hide();
		$("#image").val(imgPath);
	}else if(isPicShow == "uploadImgAreaBanner") {
		$("#uploadImgAreaBanner").prop("src", imgServer + imgPath);
		$("#uploadImgAreaBanner").show();
		$("#imgAreaBanner").hide();
		$("#imageBanner").val(imgPath);
	}
}

/** 清空文本 */
function cleanWin() {
	$("#title").textbox("setValue", "");
	$("#description").textbox("setValue", "");
	$("#hours").textbox("setValue", "");
	$("#studyTime").textbox("setValue", "");
	$("#tel").textbox("setValue", "");
//	$("#price").textbox("setValue", "");
	$("#copies").numberspinner("setValue", "");
	$("#address").textbox("setValue", "");
	$("#longitude").textbox("setValue", "");
	$("#latitude").textbox("setValue", "");
	$("#courseUrl").textbox("setValue", "");
	/*$("#priceunit").textbox("setValue", "");*/

	$("#image").val("");
	$("#uploadImgArea").hide();
	$("#imgArea").show();

	$("#imageBanner").val("");
	$("#uploadImgAreaBanner").hide();
	$("#imgAreaBanner").show();
	
	ue.setContent("");
	$("#startTr").hide();
	$("#endTr").hide();
	$("#isAct").prop("checked", false);
	
	ani(0);
}

function edit(url) {
	$.messager.confirm("提示", "请你发布前确认你填写课程的价格、时间等信息是否正确？", function(r) {
		if(r) {
			if(url == "update") {
				if(Common.isNull(id)) {
					layer.msg("无法获取课程信息！");
					return false;
				}
			}
				
			var title = $("#title").textbox("getValue");
			if (Common.isNull(title)) {
				layer.msg("请输入课程的名称！");
				return false;
			}
			var courseUrl = $("#courseUrl").textbox("getValue");
			if(Common.isNull(courseUrl)) {
				layer.msg("请输入有赞商城的链接！");
				return false;
			}
			var description = $("#description").textbox("getValue");
			if(Common.isNull(description)) {
				layer.msg("请输入课程描述，用作课程分享！");
				return false;
			}
		/*	var typeParent = $("#typeParent").combobox("getValue");
			if(Common.isNull(typeParent)) {
				layer.msg("请选择课程一级类别！");
				return false;
			}*/
			var typeId = $("#typeId").combobox("getValue");
			if(Common.isNull(typeId)) {
				layer.msg("请选择培训室的类型！");
				return false;
			}
			var typeName = $("#typeId").combobox("getText");
			
			var hours = $("#hours").textbox("getValue");
			if (Common.isNull(hours)) {
				layer.msg("请输入课时！");
				return false;
			}
			var studyTime = $("#studyTime").textbox("getValue");
			if(Common.isNull(studyTime)) {
				layer.msg("请输入课程时间！");
				return false;
			}
			var tel = $("#tel").textbox("getValue");
			if (Common.isNull(tel)) {
				layer.msg("请输入联系方式！");
				return false;
			}
/*			if(isNaN(tel)) {
				layer.msg("手机号输入不合法，请确认输入！");
				return false;
			}
			if(tel.length != 11) {
				layer.msg("您输入的手机号码有误，请确认输入！");
				return false;
			}*/
//			var price = $("#price").textbox("getValue");
//			if(Common.isNull(price)) {
//				layer.msg("请输入课程的价格！");
//				return false;
//			}
//			if(isNaN(price) || price < 0) {
//				layer.msg("价格输入不合法，请确认输入！");
//				return false;
//			}
			var refundType = $("#refundType").combobox("getValue");
			if(Common.isNull(refundType)){
				layer.msg("请选择退款类型");
				return false;
			}
			var refundDays = $("#refundDays").textbox("getValue");
			if(refundType==2){
				if(Common.isNull(refundDays)){
					layer.msg("请输入提前几天退款的天数");
					return false;
				}
				if(isNaN(refundDays) || refundDays==''){
					layer.msg("请输入提前几天退款的天数");
					return false;
				}
				if(refundDays < 0){
					layer.msg("提前几天退款的天数不合法，需要大于等于0");
					return false;
				}
			}else{
				refundDays = 0;
			}
			var refundRatio = $("#refundRatio").textbox("getValue");
			if(isNaN(refundRatio) || refundRatio==''){
				layer.msg("请输入退款率！");
				return false;
			}
			if(refundRatio<0 || refundRatio>1){
				layer.msg("退款率只能取值0-1.0！");
				return false;
			}
/*			var priceunit = $("#priceunit").textbox("getValue");
			if(Common.isNull(priceunit)) {
				layer.msg("请输入价格单位！");
				return false;
			}*/
			var copies = $("#copies").numberspinner("getValue");
			if(Common.isNull(copies)) {
				layer.msg("请输入课程的报名名额！");
				return false;
			}
			var cityId = $("#cityId").combobox("getValue");
			if(Common.isNull(cityId)) {
				layer.msg("请选择所在城市！");
				return false;
			}
			var address = $("#address").textbox("getValue");
			if (Common.isNull(address)) {
				layer.msg("请输入详细地址！");
				return false;
			}
			var isAct = 1;
			var startTime = "";
			var endTime = "";
		
			var isChecked = $("#isAct").prop("checked");
			if(isChecked) {
				isAct = 2;
				startTime = $("#startTime").datetimebox("getValue");
				endTime = $("#endTime").datetimebox("getValue");
				if(Common.isNull(startTime)) {
					layer.msg("请选择活动开始时间！");
					return false;
				}
				if(Common.isNull(endTime)) {
					layer.msg("请选择活动结束时间！");
					return false;
				}
			}
			
			var longitude = $("#longitude").textbox("getValue");
			if (Common.isNull(longitude)) {
				layer.msg("请输入培训室的经度，可在百度上获取！");
				return false;
			}
			if(isNaN(longitude)) {
				layer.msg("经度输入不合法，请确认输入！");
				return false;
			}
			if(longitude < 0 || longitude > 180) {
				layer.msg("您输入的经度有误，请确认输入！");
				return false;
			}
			var latitude = $("#latitude").textbox("getValue");
			if (Common.isNull(latitude)) {
				layer.msg("请输入培训室的纬度，可在百度上获取！");
				return false;
			}
			if(isNaN(latitude)) {
				layer.msg("纬度输入不合法，请确认输入！");
				return false;
			}
			if(latitude < 0 || latitude > 90) {
				layer.msg("您输入的纬度有误，请确认输入！");
				return false;
			}
			var titleImg = $("#image").val();
			if (Common.isNull(titleImg)) {
				layer.msg("请上传课程的封面图！");
				return false;
			}
			var bannerImg = $("#imageBanner").val();

			var content = ue.getContent();
			if (Common.isNull(content)) {
				layer.msg("请编辑课程的详细介绍！");
				return false;
			}
			var items='';
			var prices='';
			var origi_prices='';
			var shopPrices='';
			var rows = $("#itemstable").datagrid("getRows");
			if(rows.length==0){
				layer.msg("请输入规格！");
				return false;
			}
			for(var i=0;i<rows.length;i++){
				if(Common.isNull(rows[i].itemName)){
					layer.msg("规格名不能为空！");
					return false;
				}
				if(Common.isNull(rows[i].price)){
					layer.msg("觅趣价格不能为空！");
					return false;
				}
				if(Common.isNull(rows[i].origiprice)){
					layer.msg("觅趣原价不能为空！");
					return false;
				}
				if(Common.isNull(rows[i].trainprice)){
					layer.msg("学堂价不能为空！");
					return false;
				}
				var validate=/^\d+(\.\d+)?$/;
				if(validate.test(rows[i].price)==false || rows[i].price<=0){
					layer.msg("请输入合法的价格！");
					return false;
				}
				if(validate.test(rows[i].origiprice)==false || rows[i].origiprice<=0){
					layer.msg("请输入合法的价格！");
					return false;
				}
				if(validate.test(rows[i].trainprice)==false || rows[i].trainprice<=0){
					layer.msg("请输入合法的价格！");
					return false;
				}
				items = items+rows[i].itemName+'|';
				prices = prices+rows[i].price+'|';
				origi_prices = origi_prices+rows[i].trainprice+'|';
				shopPrices = shopPrices+rows[i].trainprice+'|';
			}
		
			$("#main").mask("正在处理中，请稍候......");
			$.ajax({
				url : url,
				type : "post",
				data : {
					"entity.id" : id,
					"entity.trainId" : trainId,
					"entity.typeParent" : 1,
					"entity.typeId" : typeId,
					"entity.typeName" : typeName,
					"entity.cityId" : cityId,
					"entity.title" : title,
					"entity.courseUrl" : courseUrl,
					"entity.description" : description,
					"entity.titleImg" : titleImg,
					"entity.bannerImg" : bannerImg,
					"entity.hours" : hours,
					"entity.tel" : tel,
					"entity.content" : content,
					"entity.address" : address,
					"entity.longitude" : longitude,
					"entity.latitude" : latitude,
					"entity.priceunit" : "",
					"entity.studyTime" : studyTime,
					"entity.copies" : copies,
					"entity.classType" : isAct,
					"entity.startTimeStr" : startTime,
					"entity.endTimeStr" : endTime,
					"entity.refundType" : refundType,
					"entity.refundRatio" : refundRatio,
					"entity.items" : items,
					"entity.prices" : prices,
					"entity.shopPrices" : shopPrices,
					"entity.refundDays" : refundDays,
					"entity.origi_prices" : origi_prices
				},
				dataType : "json",
				success : function(json) {
					$("#main").unmask();
					layer.msg(json.msg);

		        	$.messager.confirm("提示", "操作成功，是否需要返回到列表？", function(r) {

			            if(json.isSuccess && process == "add") {
			            	if (r) {
			  	            	$("#btn_back").click();
			            	}else{
				            	cleanWin();
			            	}
			            }else{	
			                if (r) {
		  	            	    $("#btn_back").click();
			                }
			            }
		        	  
		        	});
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
}