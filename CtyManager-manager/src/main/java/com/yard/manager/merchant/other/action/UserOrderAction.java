package com.yard.manager.merchant.other.action;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.core.util.DateUtil;
import com.yard.manager.base.action.BaseAction;
import com.yard.manager.merchant.other.entity.CashSettleRecordViewEntity;
import com.yard.manager.merchant.other.entity.TotalFeeViewEntity;
import com.yard.manager.merchant.other.entity.TrainCutOffViewEntity;
import com.yard.manager.merchant.other.entity.UserOrderViewEntity;
import com.yard.manager.merchant.other.entity.query.UserOrderQueryEntity;
import com.yard.manager.merchant.other.service.UserOrderService;
import com.yard.manager.modules.util.YardUtils;

/**
 * 
 * @author : leihc
 * @date : 2015年10月16日
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "orderlist", location = "/WEB-INF/content/merchant/other/ordersoftrain.html"),
	@Result(type = "freemarker", name = "allorder", location = "/WEB-INF/content/merchant/other/allorders.html"),
	@Result(type = "freemarker", name = "cutofflist", location = "/WEB-INF/content/merchant/other/traincutoffindex.html")
})
public class UserOrderAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String NAMESPACE = "/other/train/userorder";
	private static final UserOrderService uos = UserOrderService.getInstance();
	
	private Integer trainId;// 培训室ID
	// 订单查询条件
	private UserOrderQueryEntity query;
	// 改变订单状态的操作，-2 官方或者系统取消 4 置为退款中 5 置为已退款，其他值无效
	private Integer status;
	// 订单号
	private String orderNo;
	// 结算周期 1 按周 2 按月
	private Integer period;
	private Integer temp;
	// 结算金额
	private Double cutoffamount;
	
	public Double getCutoffamount() {
		return cutoffamount;
	}

	public void setCutoffamount(Double cutoffamount) {
		this.cutoffamount = cutoffamount;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public UserOrderQueryEntity getQuery() {
		return query;
	}

	public void setQuery(UserOrderQueryEntity query) {
		this.query = query;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Action(NAMESPACE + "/orderlist")
	public String trainCourseIndex() {
		return "orderlist";
	}
	
	@Action(NAMESPACE + "/allorder")
	public String toallorder() {
		return "allorder";
	}
	
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", uos.queryCount(query, trainId));
			map.put("rows", uos.findList(page, rows, trainId, query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<UserOrderViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 查询所有订单
	 * @return
	 */
	@Action(NAMESPACE + "/getallorder")
	public String getallorder() {
		try {
			map.put("total", uos.queryCount(query, null));
			map.put("rows", uos.findList(page, rows, null, query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<UserOrderViewEntity>());
		}
		return MAP;
	}

	/**
	 * 查询指定条件的订单总额
	 * @return
	 */
	@Action(NAMESPACE + "/queryTotalFee")
	public String queryTotalFee(){
		try {
			map.put("totalFees", uos.sumTotalFee(query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("totalFees", new TotalFeeViewEntity());
		}
		return MAP;
	}
	
	/**
	 * 订单状态变更
	 */
	@Action(NAMESPACE + "/changestatus")
	public String changeStatus() {
		uos.changeStatus(orderNo, status, map);
		return MAP;
	}
	
	/**
	 * 到结算列表页面
	 * @return
	 */
	@Action(NAMESPACE + "/cutofflist")
	public String cutoffIndex() {
		return "cutofflist";
	}
	
	/**
	 * 获取商家结算金额
	 * @return
	 */
	@Action(NAMESPACE + "/querycutofflist")
	public String querycutoffList() {
		try {
			if(temp!=null){
				period = temp;
			}
			map.put("total", uos.queryCount(period));
			map.put("rows", uos.findCutoffCashList(page, rows, period));
			map.put("endTime", DateUtil.formatDateTime(new Date(YardUtils.getCutoffPeriodEndTime(period))));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<TrainCutOffViewEntity>());
		}
		return MAP;
	}
	/**
	 * 结算
	 */
	@Action(NAMESPACE + "/cutofftrain")
	public String cutofftrain(){
		uos.cutofftrain(trainId, cutoffamount, map);
		return MAP;
	}
	/**
	 * 查询学堂的结算记录
	 * @return
	 */
	@Action(NAMESPACE + "/cutoffrecord")
	public String cutoffrecord(){
		try {
			map.put("total", uos.queryRecordCount(trainId));
			map.put("rows", uos.findCutoffRecord(trainId, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<CashSettleRecordViewEntity>());
		}
		return MAP;
	}
}
