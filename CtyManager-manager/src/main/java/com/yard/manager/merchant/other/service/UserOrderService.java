package com.yard.manager.merchant.other.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.other.entity.CashSettleRecordViewEntity;
import com.yard.manager.merchant.other.entity.TotalFeeViewEntity;
import com.yard.manager.merchant.other.entity.TrainCutOffViewEntity;
import com.yard.manager.merchant.other.entity.TrainInfoEntity;
import com.yard.manager.merchant.other.entity.UserOrderViewEntity;
import com.yard.manager.merchant.other.entity.query.UserOrderQueryEntity;
import com.yard.manager.modules.util.YardUtils;
import com.yard.manager.platform.shiro.ShiroUtil;
import com.yard.manager.platform.sms.SmsProcess;

/**
 * 
 * @author : leihc
 * @date : 2015年10月16日
 * @version : 1.0
 */
public class UserOrderService extends BaseService<UserOrderViewEntity> {

	private static final UserOrderService instance = new UserOrderService();
	
	public static UserOrderService getInstance(){
		return instance;
	}
	/**
	 * 查询订单列表，不传学堂ID时表示查询所有订单
	 * @param page
	 * @param rows
	 * @param trainId
	 * @param query
	 * @return
	 */
	public List<UserOrderViewEntity> findList(long page, long rows, Integer trainId,UserOrderQueryEntity query){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT o.orderNo,o.orderStatus,o.payType,u.nickname,o.buyername,o.tel,o.remark,");
		sql.append("t.title AS trainName,o.goodsName,o.address,o.itemName,o.totalFeeBefore,o.shopTotalFee,");
		sql.append("o.goodsAmount,o.payTradeNo,o.createTime,o.payTime,o.refundFee,o.refundReason,o.validateCode ");
		List<Object> params = new ArrayList<Object>();
		if(trainId!=null){
			sql.append("FROM t_user_order AS o LEFT JOIN t_user AS u ON o.userId=u.id LEFT JOIN t_train_info AS t ON o.trainId=t.id WHERE o.trainId=? ");
			params.add(trainId);
		}else{
			sql.append("FROM t_user_order AS o LEFT JOIN t_user AS u ON o.userId=u.id LEFT JOIN t_train_info AS t ON o.trainId=t.id where 1=1 ");
		}
		
		queryParams(query, sql, params);

		sql.append(" ORDER BY o.createTime DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);

		try {
			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void queryParams(UserOrderQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			if(query.getTrainId()!=null){
				sql.append(" AND trainId=? ");
				params.add(query.getTrainId());
			}
			// 课程名称搜索,10表示所有状态
			if(query.getOrderStatus()!=null&&query.getOrderStatus()!=10) {
				sql.append("AND o.orderStatus = ? ");
				params.add(query.getOrderStatus());
			}
			try{
				if(StringUtils.isNotBlank(query.getStartTime())){
					params.add(DateUtil.parseDateTime(query.getStartTime()+" 00:00:00").getTime());
					sql.append("AND o.createTime>? ");
				}
				// 课程状态
				if(StringUtils.isNotBlank(query.getEndTime())) {
					params.add(DateUtil.parseDateTime(query.getEndTime()+" 23:59:59").getTime());
					sql.append("AND o.createTime<? ");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			if(StringUtils.isNotBlank(query.getOrderNo())){
				params.add("%"+query.getOrderNo()+"%");
				sql.append("AND o.orderNo like ? ");
			}
			if(StringUtils.isNotBlank(query.getTel())){
				params.add("%"+query.getTel()+"%");
				sql.append("AND o.tel like ? ");
			}
			if(StringUtils.isNotBlank(query.getUserName())){
				params.add("%"+query.getUserName()+"%");
				sql.append("AND o.buyername like ? ");
			}
		}
	}
	/**
	 * 查询订单数，不传学堂ID时查询所有订单数
	 * @param query
	 * @param trainId
	 * @return
	 */
	public long queryCount(UserOrderQueryEntity query, Integer trainId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if(trainId!=null){
			sql.append("SELECT COUNT(*) FROM t_user_order as o WHERE trainId=? ");
			params.add(trainId);
		}else{
			sql.append("SELECT COUNT(*) FROM t_user_order as o WHERE 1=1 ");
		}

		queryParams(query, sql, params);
		
		try {
			if(!params.isEmpty()){
				return (Long) certain(sql.toString(), params.toArray());
			}else{
				return (Long) certain(sql.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 统计指定条件下的总金额
	 * @param query
	 * @return
	 */
	public TotalFeeViewEntity sumTotalFee(UserOrderQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(totalFeeBefore) AS totalFee,SUM(shopTotalFee) AS shopTotalFee FROM t_user_order as o WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);
		try {
			TotalFeeViewEntity tve = show(TotalFeeViewEntity.class, sql.toString(), params.toArray());
			return tve;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 改变订单状态
	 * @param orderNo 订单ID
	 * @param status 订单状态 
	 */
	public void changeStatus(String orderNo, Integer status, Map<String, Object> map) {
		try {
			String sql = "UPDATE t_user_order SET orderStatus = ? WHERE orderNo = ? ";
			boolean success = update(sql, status, orderNo);
			if(success){
				JsonResult.toJson(map, true, "操作成功！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "操作失败了！");
		return;
	}
	
	/**
	 * 按指定结算周期查询每个商家的结算金额
	 * @param page
	 * @param rows
	 * @param period
	 * @return
	 */
	public List<TrainCutOffViewEntity> findCutoffCashList(long page, long rows,Integer period){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id AS trainId,t.title AS trainName,tel,t.period,");
		sql.append("COALESCE((SELECT SUM(totalFeeBefore) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3) AND o.createTime<=?),0) AS totalSales, ");
		sql.append("COALESCE((SELECT SUM(totalFeeBefore) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3)),0) AS currentTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3) AND o.createTime<=?),0) AS shopTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3)),0) AS currentShopTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=2 OR orderStatus=6) AND o.createTime<=?),0) AS shopTotalIncome,");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=2 OR orderStatus=6)),0) AS currentShopTotalIncome,");
		sql.append("COALESCE((SELECT SUM(withdrawcash) FROM t_cashsettle_record AS r WHERE r.trainId=t.id),0) AS shopCutOffSales");
//		sql.append("COALESCE((SELECT SUM(withdrawcash) FROM t_cashsettle_record AS r WHERE r.trainId=t.id AND r.createTime>?),0) AS currentCuttoff");
		sql.append(" FROM t_train_info AS t WHERE t.period=? LIMIT ?, ?;");
		
		long currentCutoffTime = YardUtils.getCutoffPeriodEndTime(period);
		List<Object> params = new ArrayList<Object>();
		params.add(currentCutoffTime);
		params.add(currentCutoffTime);
		params.add(currentCutoffTime);
		params.add(period);
		params.add((page - 1) * rows);
		params.add(rows);
		try{
			List<TrainCutOffViewEntity> results = this.query(TrainCutOffViewEntity.class, sql.toString(), params.toArray());
			if(results!=null&&!results.isEmpty()){
				for(TrainCutOffViewEntity entity:results){
					entity.setRemain(entity.getShopTotalIncome()-entity.getShopCutOffSales());
				}
			}
			return results;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询同一结算周期的商家个数
	 * @param period
	 * @return
	 */
	public long queryCount(Integer period) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_train_info as t WHERE t.period=? ");
		List<Object> params = new ArrayList<Object>();
		params.add(period);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void cutofftrain(int trainId,double cutoffamount, Map<String, Object> map){
		if(cutoffamount<=0){
			JsonResult.toJson(map, false, "可结算金额必须大于0！");
			return;
		}
		TrainInfoService tis = TrainInfoService.getInstance();
		TrainInfoEntity trainInfo = tis.findTrainInfo(trainId);
		int period = trainInfo.getPeriod();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id AS trainId,t.title AS trainName,tel,t.period,");
		sql.append("COALESCE((SELECT SUM(totalFeeBefore) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3) AND o.createTime<=?),0) AS totalSales, ");
		sql.append("COALESCE((SELECT SUM(totalFeeBefore) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3)),0) AS currentTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3) AND o.createTime<=?),0) AS shopTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=1 OR orderStatus=2 OR orderStatus=3)),0) AS currentShopTotalSales, ");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=2 OR orderStatus=6) AND o.createTime<=?),0) AS shopTotalIncome,");
		sql.append("COALESCE((SELECT SUM(shopTotalFee) FROM t_user_order AS o WHERE o.trainId=t.id AND (orderStatus=2 OR orderStatus=6)),0) AS currentShopTotalIncome,");
		sql.append("COALESCE((SELECT SUM(withdrawcash) FROM t_cashsettle_record AS r WHERE r.trainId=t.id),0) AS shopCutOffSales");
//		sql.append("COALESCE((SELECT SUM(withdrawcash) FROM t_cashsettle_record AS r WHERE r.trainId=t.id AND r.createTime>?),0) AS currentCuttoff");
		sql.append(" FROM t_train_info AS t WHERE t.id=?;");
		long currentCutoffTime = YardUtils.getCutoffPeriodEndTime(period);
		List<Object> params = new ArrayList<Object>();
		params.add(currentCutoffTime);
		params.add(currentCutoffTime);
		params.add(currentCutoffTime);
		params.add(trainId);
		try {
			TrainCutOffViewEntity tcv = show(TrainCutOffViewEntity.class,sql.toString(), params.toArray());
			tcv.setRemain(tcv.getShopTotalIncome()-tcv.getShopCutOffSales());
			if(tcv.getRemain()<=0){
				JsonResult.toJson(map, false, "没有可结算余额！");
				return;
			}
			if(cutoffamount>tcv.getRemain()){
				JsonResult.toJson(map, false, "结算金额不能大于可结算金额！");
				return;
			}
			sql = new StringBuffer();
			sql.append("INSERT INTO t_cashsettle_record(trainId,withdrawcash,managerId,createTime) VALUES(?,?,?,?);");
			params = new ArrayList<Object>();
			params.add(trainId);
			params.add(cutoffamount);
			params.add(ShiroUtil.getUserId());
			params.add(System.currentTimeMillis());
			update(sql.toString(), params.toArray());
			tcv.setRemain(tcv.getRemain()-cutoffamount);
			tcv.setShopCutOffSales(tcv.getShopCutOffSales()+cutoffamount);
			if(StringUtils.isNotBlank(trainInfo.getTel())){
				SmsProcess.getInstance().sendMsg(trainInfo.getTel(), "结算金额："+cutoffamount);
			}
			map.put("info", tcv);
			JsonResult.toJson(map, true, "结算成功！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询结算记录
	 * @param trainId
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<CashSettleRecordViewEntity> findCutoffRecord(Integer trainId,long page, long rows){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.title AS trainName,r.withdrawcash,u.createusername AS managerName,r.createTime FROM t_cashsettle_record AS r ");
		sql.append("LEFT JOIN t_train_info AS i ON r.trainId=i.id LEFT JOIN sys_user AS u ON r.managerId=u.sysuserid WHERE r.trainId=? ");
		sql.append(" ORDER BY r.createTime DESC LIMIT ?, ?");
		List<Object> params = new ArrayList<Object>();
		params.add(trainId);
		params.add((page - 1) * rows);
		params.add(rows);
		try {
			return query(CashSettleRecordViewEntity.class,sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long queryRecordCount(Integer trainId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_cashsettle_record WHERE trainId=? ");
		List<Object> params = new ArrayList<Object>();
		params.add(trainId);
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
