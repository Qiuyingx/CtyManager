package com.yard.manager.modules.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.modules.user.entity.UserOrderViewEntity;
import com.yard.manager.modules.user.entity.query.UserOrderQueryEntity;

/**
 * 用户订单
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class UserOrderService extends BaseService<UserOrderViewEntity>{
	private static final UserOrderService us = new UserOrderService();
	public UserOrderService() {
		
	}
	public static UserOrderService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param courtyardIds 管理员所管理的社区
	 */
	public List<UserOrderViewEntity> queryUserOrderList(UserOrderQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.user_id userId, a.userName, a.tel userMobile, a.address, b.courtyard_name courtyardName, d.nickname nickName, ");
		sql.append("a.courtyard_id courtyardId, a.order_id orderId, a.goods_id goodsId, c.goodsName, a.amount COUNT, a.create_time createTime ");
		sql.append("FROM t_user_order a, t_courtyard b, t_shop c, t_user d WHERE a.courtyard_id = b.id AND a.goods_id = c.id AND a.user_id = d.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}		
		
		// 分页排序
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		
		try {
			return query(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 数据统计
	 * @param courtyardIds 管理员所管理的院子IDs
	 * @param courtyardIds
	 * @return
	 */
	public long queryUserOrderCount(UserOrderQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user_order a, t_courtyard b, t_shop c, t_user d  WHERE a.courtyard_id = b.id AND a.goods_id = c.id AND a.user_id = d.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}	
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(UserOrderQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 订单ID
			if(!StringUtils.isBlank(query.getOrderId())) {
				sql.append("AND a.order_id = ? ");
				params.add(query.getOrderId());
			}
			// 姓名
			if(!StringUtils.isBlank(query.getUserName())) {
				sql.append("AND a.userName = ? ");
				params.add(query.getUserName());
			}
			// 电话
			if(!StringUtils.isBlank(query.getUserMobile())) {
				sql.append("AND a.tel = ? ");
				params.add(query.getUserMobile());
			}
			// 用户昵称
			if(!StringUtils.isBlank(query.getNickName())) {
				sql.append("AND d.nickname = ? ");
				params.add(query.getNickName());
			}
			// 商品名称
			if(!StringUtils.isBlank(query.getGoodsName())) {
				sql.append("AND c.goodsName like ? ");
				params.add("%" + query.getGoodsName() + "%");
			}
			// 订单时间
			if (!StringUtils.isBlank(query.getBuyStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getBuyStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getBuyEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getBuyEndTime()+ " 23:59:59"));
			}
			// 所属院子
			if(!StringUtils.isBlank(query.getCourtyardIds())) {
				sql.append("AND a.courtyard_id IN (" + query.getCourtyardIds() +") ");
			}
		}
	}
}
