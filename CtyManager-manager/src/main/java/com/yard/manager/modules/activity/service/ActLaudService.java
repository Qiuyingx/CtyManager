package com.yard.manager.modules.activity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.activity.entity.ActLaudViewEntity;

/**
 * 活动点赞
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class ActLaudService extends BaseService<ActLaudViewEntity>{
	private static final ActLaudService us = new ActLaudService();
	public ActLaudService() {
		
	}
	public static ActLaudService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param courtyardIds 管理员所管理的社区
	 */
	public List<ActLaudViewEntity> queryList(long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.activity_id actId, a.user_id userId, a.courtyard_id courtyardId, a.create_time createTime, ");
		sql.append("b.courtyard_name courtyardName, c.nickname userName, c.tel userTel, d.activity_title actTitle FROM t_activity_laud a, ");
		sql.append("t_courtyard b, t_user c, t_activity_info d WHERE a.courtyard_id = b.id AND a.user_id = c.id AND a.activity_id = d.id ");
		
		List<Object> params = new ArrayList<Object>();
		
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
	public long queryCount(String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_activity_laud a, t_courtyard b, t_user c, t_activity_info d WHERE a.courtyard_id = b.id AND ");
		sql.append("a.user_id = c.id AND a.activity_id = d.id ");
		
		List<Object> params = new ArrayList<Object>();
		
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
}
