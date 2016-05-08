package com.cddayuanzi.font.modules.normal.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.font.base.action.BaseAction;

/**
 * 网站入口基础处理
 * 
 * @author xiayuanming
 * 
 */
@Results({ 
	@Result(type = "freemarker", name = "index", location = "/WEB-INF/page/index.html"),
	@Result(type = "freemarker", name = "around_index", location = "/WEB-INF/page/content/course_around_list.html"),
	@Result(type = "freemarker", name = "content_view", location = "/WEB-INF/page/content/content_view.html"),
	@Result(type = "freemarker", name = "news_view", location = "/WEB-INF/page/content/news_view.html"),
	@Result(type = "freemarker", name = "course_view", location = "/WEB-INF/page/content/course_view.html"),
	@Result(type = "freemarker", name = "train_home", location = "/WEB-INF/page/content/train_home.html"),
	@Result(type = "freemarker", name = "history_content", location = "/WEB-INF/page/content/content_before_list.html"),
	@Result(type = "freemarker", name = "train_type", location = "/WEB-INF/page/content/traintype.html"),
	@Result(type = "freemarker", name = "course_search_list", location = "/WEB-INF/page/content/course_search_list.html"),
	@Result(type = "freemarker", name = "order_owner", location = "/WEB-INF/page/order/order_owner.html"),
	@Result(type = "freemarker", name = "shop_index", location = "/WEB-INF/page/shop/shop_index.html"),
	@Result(type = "freemarker", name = "shop_order_list", location = "/WEB-INF/page/shop/shop_order_list.html"),
	@Result(type = "freemarker", name = "shop_home", location = "/WEB-INF/page/shop/shop_home.html"),
	@Result(type = "freemarker", name = "enter_login", location = "/WEB-INF/page/enter/login.html"),
	@Result(type = "freemarker", name = "enter_register", location = "/WEB-INF/page/enter/register.html"),
	@Result(type = "freemarker", name = "enter_userhome", location = "/WEB-INF/page/center/userhome.html"),
	@Result(type = "freemarker", name = "order_pay_status", location = "/WEB-INF/page/order/order_pay_status.html"),
	@Result(type = "freemarker", name = "order_refund", location = "/WEB-INF/page/order/order_refund.html"),
	@Result(type = "freemarker", name = "order_write", location = "/WEB-INF/page/order/order_write.html"),
	@Result(type = "freemarker", name = "order_down", location = "/WEB-INF/page/order/order_down.html")
})
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -7535629533795069592L;
	private static final String NAMESPACE = "/normal";

	/**
	 * 进入首页
	 */
	@Action(NAMESPACE + "/index")
	public String contentIndex() {
		return "index";
	}
	
	/**
	 * 进入首页附近
	 */
	@Action(NAMESPACE + "/around_index")
	public String aroundIndex() {
		return "around_index";
	}	
	
	/**
	 * 进入专题资讯详情
	 */
	@Action(NAMESPACE + "/content_view")
	public String contentView() {
		if(contentType == 2) {
			return "news_view";
		}
		return "content_view";
	}
	
	/**
	 * 进入课程详情
	 */
	@Action(NAMESPACE + "/course_view")
	public String courseView() {
		return "course_view";
	}	
	
	/**
	 * 进入学堂主页
	 */
	@Action(NAMESPACE + "/train_view")
	public String trainHome() {
		return "train_home";
	}
	
	/**
	 * 进入往期专题列表
	 */
	@Action(NAMESPACE + "/history_content")
	public String historyContent() {
		return "history_content";
	}
	
	/**
	 * 进入课程分类页面
	 */
	@Action(NAMESPACE + "/train_type")
	public String trainType() {
		return "train_type";
	}	
	
	/**
	 * 进入课程大分类
	 */
	@Action(NAMESPACE + "/course_search_list")
	public String courseSearchList() {
		if(trainTypeId != null) {
			switch (trainTypeId) {
			case 1:
				this.setTrainTypeName("早教");
				break;
			case 2:
				this.setTrainTypeName("音乐");
				break;
			case 3:
				this.setTrainTypeName("舞蹈");
				break;
			case 4:
				this.setTrainTypeName("游泳");
				break;
			case 5:
				this.setTrainTypeName("画画");
				break;
			case 6:
				this.setTrainTypeName("钢琴");
				break;
			case 7:
				this.setTrainTypeName("瑜伽");
				break;
			case 8:
				this.setTrainTypeName("烘焙");
				break;
			case 9:
				this.setTrainTypeName("手工DIY");
				break;
			case 10:
				this.setTrainTypeName("花艺");
				break;
			case 11:
				this.setTrainTypeName("摄影");
				break;
			case 12:
				this.setTrainTypeName("多肉植物");
				break;

			default:
				this.setTrainTypeName("");
				break;
			}
		}
		return "course_search_list";
	}
	
	/**
	 * 进入我的订单
	 */
	@Action(NAMESPACE + "/order_owner")
	public String orderOwner() {
		return "order_owner";
	}	
	
	/**
	 * 进入我的店铺
	 */
	@Action(NAMESPACE + "/shop_index")
	public String shopIndex() {
		return "shop_index";
	}
	
	/**
	 * 进入商户订单管理
	 */
	@Action(NAMESPACE + "/shop_order_list")
	public String shopOrderList() {
		return "shop_order_list";
	}
	
	/**
	 * 进入商户店铺管理
	 */
	@Action(NAMESPACE + "/shop_home")
	public String shopHome() {
		return "shop_home";
	}
	
	/**
	 * 进入登录
	 */
	@Action(NAMESPACE + "/enter_login")
	public String enterLogin() {
		return "enter_login";
	}
	
	/**
	 * 进入注册
	 */
	@Action(NAMESPACE + "/enter_register")
	public String enterRegister() {
		return "enter_register";
	}
	
	/**
	 * 注册完善资料
	 */
	@Action(NAMESPACE + "/enter_userhome")
	public String enterUserHome() {
		return "enter_userhome";
	}
	
	/**
	 * 进入订单详情
	 */
	@Action(NAMESPACE + "/order_pay_status")
	public String orderPayStatus() {
		return "order_pay_status";
	}
	
	/**
	 * 进入申请退款
	 */
	@Action(NAMESPACE + "/order_refund")
	public String orderRefund() {
		return "order_refund";
	}
	
	/**
	 * 进入填单填写
	 */
	@Action(NAMESPACE + "/order_write")
	public String orderWrite() {
		return "order_write";
	}	
	
	/**
	 * 订单支付成功
	 */
	@Action(NAMESPACE + "/order_down")
	public String orderDown() {
		return "order_down";
	}
		
	
	private Integer contentId; // 资讯专题ID
	private Integer contentType; // 文章类型 1专题 2资讯
	private Integer courseId; // 课程ID
	private Integer classType; // 课程类型 1普通 2活动
	private Integer trainId; // 学堂ID
	private Integer trainTypeId; // 课程分类ID
	private String trainTypeName; // 课程分类名称
	private Integer userId; // 用户ID
	private String orderId; // 订单ID
	private String orderNo; // 订单编号
	private String price; // 退款金额
	private String forwardFunction; // 跳转函数
	private String remark; //备注
	

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getClassType() {
		return classType;
	}

	public void setClassType(Integer classType) {
		this.classType = classType;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public Integer getTrainTypeId() {
		return trainTypeId;
	}

	public void setTrainTypeId(Integer trainTypeId) {
		this.trainTypeId = trainTypeId;
	}

	public String getTrainTypeName() {
		return trainTypeName;
	}

	public void setTrainTypeName(String trainTypeName) {
		this.trainTypeName = trainTypeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getForwardFunction() {
		return forwardFunction;
	}

	public void setForwardFunction(String forwardFunction) {
		this.forwardFunction = forwardFunction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
