package com.yard.manager.modules.activity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.activity.entity.ActSignupViewEntity;

/**
 * 活动报名
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class ActSignupService extends BaseService<ActSignupViewEntity>{
	private static final ActSignupService us = new ActSignupService();
	public ActSignupService() {
		
	}
	public static ActSignupService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param actId 活动ID
	 */
	public List<ActSignupViewEntity> queryList(long page, long rows, Integer actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.user_id userId, a.activity_id actId, a.create_time createTime, a.name, a.tel, a.content, ");
		sql.append("b.courtyard_name courtyardName, c.nickname nickName, d.activity_title actTitle FROM t_activity_signup a, ");
		sql.append("t_courtyard b, t_user c, t_activity_info d WHERE a.user_id = c.id AND c.courtyard_id = b.id AND a.activity_id = d.id AND a.activity_id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(actId);
		
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
	 * @param actId 活动ID
	 * @return
	 */
	public long queryCount(Integer actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_activity_signup a, t_courtyard b, t_user c, t_activity_info d WHERE a.user_id = c.id ");
		sql.append("AND c.courtyard_id = b.id AND a.activity_id = d.id AND a.activity_id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(actId);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
