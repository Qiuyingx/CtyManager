package com.yard.manager.modules.post.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.post.entity.query.PostInviQueryEntity;

/**
 * 邀约贴
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午1:06:30
 * @version : 1.0
 */
public class PostInviService extends BaseService<PostInviViewEntity>{
	private static final PostInviService pis = new PostInviService();
	public PostInviService(){
		
	}
	public static PostInviService getInstance(){
		return pis;
	}
	
	/**
	 * 分页查询数据
	 * 
	 * @param page
	 * @param rows
	 * @param courtyardIds 所管理的院子IDs
	 * @return
	 */
	public List<PostInviViewEntity> queryPostInviList(PostInviQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, b.courtyard_name courtyardName, a.user_id userId, ");
		sql.append("c.nickname nickName, c.tel, a.invitation_type inviType, a.activity_place place, a.activity_time time, ");
		sql.append("a.content, a.create_time createTime, a.image_names imageNames FROM t_invitation a, t_courtyard b, t_user c ");
		sql.append("WHERE a.courtyard_id = b.id AND a.user_id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);

		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}
		
		// 分页排序
		sql.append("ORDER BY a.create_time DESC LIMIT ?, ?");
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
	 * @param courtyardIds 所管理的院子IDs
	 */
	public long queryPostInviCount(PostInviQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_invitation a, t_courtyard b, t_user c ");
		sql.append("WHERE a.courtyard_id = b.id AND a.user_id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}
		
		try {
			return (Long)certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(PostInviQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 所属院子
			if(!StringUtils.isBlank(query.getCourtyardIds())) {
				sql.append("AND a.courtyard_id IN (" + query.getCourtyardIds() + ") ");
			}
			// 邀约类型ID
			if(!StringUtils.isBlank(query.getInviTypeIds())) {
				sql.append("AND a.invitation_type IN (" + query.getInviTypeIds() + ") ");
			}
			// 昵称
			if(!StringUtils.isBlank(query.getNickName())) {
				sql.append("AND c.nickname = ? ");
				params.add(query.getNickName());
			}
			// 用户电话
			if(!StringUtils.isBlank(query.getUserMobile())) {
				sql.append("AND c.tel = ? ");
				params.add(query.getUserMobile());
			}
			// 邀约发起时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
			}
			// 活动时间
			if (!StringUtils.isBlank(query.getToStartTime())) {
				sql.append("AND a.activity_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getToStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getToEndTime())) {
				sql.append("AND a.activity_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getToEndTime()+ " 23:59:59"));
			}
		}
		
	}
}
