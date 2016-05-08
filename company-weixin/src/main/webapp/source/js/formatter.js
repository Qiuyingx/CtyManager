// 订单状态  系统取消订单(-2),用户取消订单(-1),新订单(0),已支付(1),已消费(2),申请退款(3),退款中(4),已退款(5);
function orderStatusFormatter(status) {
	switch (status) {
	case -2:
		return "系统取消订单";
		break;
	case -1:
		return "已取消";
		break;
	case 0:
		return "新订单";
		break;
	case 1:
		return "已支付";
		break;
	case 2:
		return "已消费";
		break;
	case 3:
		return "已申请退款";
		break;
	case 4:
		return "退款中";
		break;
	case 5:
		return "已退款";
		break;
	case 6:
		return "已过期";
		break;

	default:
		return "无效订单";
		break;
	}
}

/**性别*/
function sexFormatter(value){
	value = parseInt(value);
	switch (value) {
	case 1:
		 return "男";
		 break;
	case 2:
		 return "女";
		 break;
	}
}