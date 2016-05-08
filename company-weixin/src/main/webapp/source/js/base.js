
function formate(d){
		return d>9?d:'0'+d;
	}
function isNull(value) {
	return typeof(value) == "undefined" || value == "" || value == null || value == "undefined";
}

function subStr(value, length) {
	if(!isNull(value)) {
		if(parseInt(length) > 0 && parseInt(length) <= value.length) {
			return value.substr(0, length)+"...";
		}else{
			return value;
		}
	}
	return "";
}

// 判断是否为中文
function isChinese(value) {
	if(/.*[\u4e00-\u9fa5]+.*$/.test(value)) { 
		return false; 
	} 
	return true; 
}

function mills2DateTime(value) {
	value = parseInt(value);
	if(value == 0) {
		return "";
	}
	var d = new Date(value);
	
	var year=d.getFullYear();
	var day=d.getDate();
	var month=+d.getMonth()+1;
	var hour=d.getHours();
	var minute=d.getMinutes();
	var second=d.getSeconds();
	
	var f=year+"-"+formate(month)+"-"+formate(day);
	return f;
}

function getAge(brith){
	var age;
	var d=new Date();
	var thisYear=d.getFullYear();
	var thisMonth=(d.getMonth()+1);
	var thisDay=d.getDate();
	brithy=brith.substr(0,4);
	brithm=brith.substr(4,2);
	brithd=brith.substr(6,2);
	if(thisYear-brithy<0)
		{
		       alert("输入错误!");
		       age="";
		}
		else
		{
		       if(thisMonth-brithm<0)
		       {
		              age = thisYear-brithy-1;
		       }
		       else
		       {
		              if(thisDay-brithd>=0)
		              	
		              {
		                     age = thisYear-brithy;
		              }
		              else
		              {
		                     age = thisYear-brithy-1;
		              }
		       }
		}
       return age;
}

// 获取已经选中的兴趣爱好名称
function getUserInterestName(){
		var checkedDiv = $("div[class='btn_checkbox_normal insert_choose_div_selected']");
		var tagName = "";
		for(var i=0; i<checkedDiv.length; i++) {
			if(!isNull(checkedDiv[i].title)) {
				tagName += " "+checkedDiv[i].title;
			}
		}
		return tagName;
}

function getUserInterestNameFormatter(){
	var checkedDiv = $("div[class='btn_checkbox_normal insert_choose_div_selected']");
	var tagName = "";
	for(var i=0; i<checkedDiv.length; i++) {
		if(!isNull(checkedDiv[i].title)) {
			tagName += (checkedDiv[i].id).replace("interest_", "")+"|";
		}
	}
	return tagName;
}

// 获取已经选中的兴趣爱好ID
function getUserInterestId(){

}

// 
function photoCaption(){
	var photos = $(".text_content img");
	var imgArrays = new Array();
	$(".text_content img").each(function(index){
		var imgObj = new Object(); 
		imgObj.url = $(this).prop("src");
		imgArrays.push(imgObj);
	});
	// 图片浏览器
	  var myPhotoBrowserCaptions = $.photoBrowser({
	      photos : imgArrays,
	      theme: 'dark',
	      type: 'popup'
	  });
	  $(document).on('click','img',function () {
	    myPhotoBrowserCaptions.open();
	  });
}

// 微信支付
function onBridgeReady(orderNo, appId, timeStamp, nonceStr, packageStr, signType, paySign) {
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId" : appId,     //公众号名称，由商户传入     
	           "timeStamp" : timeStamp,         //时间戳，自1970年以来的秒数     
	           "nonceStr" : nonceStr, //随机串     
	           "package" : packageStr,     
	           "signType" : signType,         //微信签名方式：     
	           "paySign" : paySign //微信签名 
	       },
	       function(res){
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	        	   click_order_down(orderNo);
	           }else if(res.err_msg == "get_brand_wcpay_request:cancel") {
	        	   click_order_owner();
	           }else if(res.err_msg == "get_brand_wcpay_request:fail"){
	        	   $.toast("支付失败！");
	           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	       }
	   ); 
}

function start_pay_wx(orderNo, appId, timeStamp, nonceStr, packageStr, signType, paySign) {
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
		onBridgeReady(orderNo, appId, timeStamp, nonceStr, packageStr, signType, paySign);
	}
}

// 判断是否为微信浏览器
var is_weixin = (function() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
})();

$(function(){
	//getUserInterestName();
	// 兴趣选择
	$(".interest_choose_div table tr td").click(function(){
		// 判断是否超过6个
		var checkedCouont = $("div[class='btn_checkbox_normal insert_choose_div_selected']").length;
	

		// 勾选操作
		var isChecked = $(this).children(".insert_choose_ok").css("display");
		if(isChecked == "block") {
			$(this).children(".btn_checkbox_normal").removeClass("insert_choose_div_selected");
			$(this).children(".insert_choose_ok").hide();
		}else{		
			if(checkedCouont >= 6) {
				$.toast("最多可选择6个兴趣爱好");
				return;
			}
			$(this).children(".btn_checkbox_normal").removeClass("btn_checkbox_normal")
					.addClass("btn_checkbox_normal insert_choose_div_selected");
			$(this).children(".insert_choose_ok").show();
		}
	});
});
