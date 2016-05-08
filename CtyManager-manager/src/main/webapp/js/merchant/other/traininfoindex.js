$(function() {
	/**
	 * 点击上传封面图
	 */
	$("#chooseImgBtn, #imgShowDiv").click(function() {
		$("#viewId").click();
	});
	
	/**
	 * 上传LOGO
	 */
	$("#chooseImgBtnLogo, #imgShowLogoDiv").click(function() {
		$("#fileNameLogo").click();
	});
	
});

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
	}
}

function complete(uri) {
	$.messager.confirm("提示", "请你发布前确认你填写的店名、营业时间、地址等信息是否正确？", function(r) {
		if(r) {
			var id = $("#id").val();
			
			if(Common.isNull(managerId)) {
				layer.msg("无法获取商家信息！");
				return false;
			}
		
			var title = $("#title").textbox("getValue");
			if (Common.isNull(title)) {
				layer.msg("请输入培训室的名称！");
				return false;
			}
			if(title.length < 3) {
				layer.msg("您输入的名称长度太短了，3~32个字符！");
				return false;
			}
			var description = $("#description").textbox("getValue")+"";
			if (Common.isNull(description)) {
				layer.msg("请对培训室做简要介绍！");
				return false;
			}
			var businessHours = $("#businessHours").textbox("getValue")+"";
			if (Common.isNull(businessHours)) {
				layer.msg("请输入培训室的培训时间！");
				return false;
			}
			var bankname = $("#bankname").combobox("getValue");
			if(Common.isNull(bankname)) {
				layer.msg("请选择银行！");
				return false;
			}
			var bankcardNo = $("#bankcardNo").textbox("getValue");
			if (Common.isNull(bankcardNo)) {
				layer.msg("请输入商家的银行卡号！");
				return false;
			}
			var accountType = $("#accountType").combobox("getValue");
			if(Common.isNull(accountType)) {
				layer.msg("请选择账号类型！");
				return false;
			}
			var bankusername = $("#bankusername").textbox("getValue");
			if (Common.isNull(bankusername)) {
				layer.msg("请输入开户人姓名！");
				return false;
			}
			var openbankname = $("#openbankname").textbox("getValue");
			if (Common.isNull(openbankname)) {
				layer.msg("请输入开户支行名称！");
				return false;
			}
			var period = $("#period").combobox("getValue");
			if(Common.isNull(period)) {
				layer.msg("请选择结算周期！");
				return false;
			}
			var tel = $("#tel").textbox("getValue");
			if (Common.isNull(tel)) {
				layer.msg("请输入培训室的联系方式！");
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
			var cityId = $("#cityId").combobox("getValue");
			if(Common.isNull(cityId)) {
				layer.msg("请选择所在城市！");
				return false;
			}
			var address = $("#address").textbox("getValue");
			if (Common.isNull(address)) {
				layer.msg("请输入培训室的详细地址！");
				return false;
			}
		/*	var classType = $("#classType").combobox("getValue");
			if(Common.isNull(classType)) {
				layer.msg("请选择一级类别！");
				return false;
			}*/
			var classTypeChoose = $("#classTypeChoose").combotree("getValues")+"";
			if(Common.isNull(classTypeChoose)) {
				layer.msg("请选择培训室的类型！");
				return false;
			}
			var classTypeChooseText = $("#classTypeChoose").combotree("getText")+"";
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
			var imageLogo = $("#imageLogo").val();
			if(Common.isNull(imageLogo)) {
				layer.msg("请上传培训室的Logo图！");
				return false;
			}
			var bannerImg = $("#image").val();
			if (Common.isNull(bannerImg)) {
				layer.msg("请上传培训室的封面图！");
				return false;
			}
			var content = ue.getContent();
			if (Common.isNull(content)) {
				layer.msg("请编辑培训室的详细介绍！");
				return false;
			}
		
			$("#main").mask("正在处理中，请稍候......");
			$.ajax({
				url : uri,
				type : "post",
				data : {
					"entity.id" : id,
					"entity.managerId" : managerId,
					"entity.cityId" : cityId,
					"entity.title" : title,
					"entity.logo" : imageLogo,
					"entity.description" : description,
					"entity.businessHours" : businessHours,
					"entity.tel" : tel,
					"entity.address" : address,
					"entity.longitude" : longitude,
					"entity.latitude" : latitude,
					"entity.bannerImg" : bannerImg,
					"entity.content" : content,
					"entity.classType" : 1,
					"entity.category" : classTypeChoose,
					"entity.bankname" : bankname,
					"entity.bankcardNo" : bankcardNo,
					"entity.accountType" : accountType,
					"entity.bankusername" : bankusername,
					"entity.openbankname" : openbankname,
					"entity.period" : period
				},
				dataType : "json",
				success : function(json) {
					$("#main").unmask();
					if (json.isSuccess) {
						if(uri == "complete") {
	 						$("#submit_open").text("确认修改");
							$("#submit_open").attr("onclick",
									"javascript:complete('update')");

							var trainIds = json.msg;
							if(!Common.isNull(trainIds)) {
								$.messager.confirm("提示", "恭喜您，学堂资料已经完善，您是否需要发布课程信息？", function(r) {
									if(r) {
										window.location.href="../../train/traincourse/content_edit?process=add&trainId="+trainIds;
									}else{
										$("#btn_back").click();
									}
								});
							}
						}else{
							layer.msg(json.msg);
							
					      	$.messager.confirm("提示", "操作成功，是否需要返回到列表？", function(r) {
				                if (r) {
			  	            	    $("#btn_back").click();
				                }
				        	});
						}
					}else{
						layer.msg(json.msg);
					}
				},
				error : function(json) {
					$("#main").unmask();
					layer.msg("服务器繁忙，请稍候再试！！");
				}
			});
		}
	});
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