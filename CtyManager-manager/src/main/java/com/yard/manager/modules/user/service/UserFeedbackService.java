package com.yard.manager.modules.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.modules.user.entity.UserFeedbackViewEntity;
import com.yard.manager.modules.user.entity.query.UserFdQueryEntity;

/**
 * 用户反馈
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class UserFeedbackService extends BaseService<UserFeedbackViewEntity>{
	private static final UserFeedbackService us = new UserFeedbackService();
	public UserFeedbackService() {
		
	}
	public static UserFeedbackService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param courtyardIds 管理员所管理的社区
	 */
	public List<UserFeedbackViewEntity> queryUserFdList(UserFdQueryEntity query, long page, long rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.nickname nickName, c.tel, a.user_id userId, ");
		sql.append("a.content_back content, a.user_tel userTel, a.create_time createTime, a.feedBackImages imageNames FROM t_user_feedback a, ");
		sql.append("t_user c WHERE a.user_id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
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
	public long queryUserFdCount(UserFdQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user_feedback a, t_user c WHERE a.user_id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(UserFdQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 昵称
			if(!StringUtils.isBlank(query.getNickName())) {
				sql.append("AND c.nickName = ? ");
				params.add(query.getNickName());
			}
			// 用户电话
			if(!StringUtils.isBlank(query.getUserMobile())) {
				sql.append("AND c.tel = ? ");
				params.add(query.getUserMobile());
			}
			//反馈时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
			}
		}
	}
}
