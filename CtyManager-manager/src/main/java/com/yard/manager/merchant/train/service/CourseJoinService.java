package com.yard.manager.merchant.train.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.merchant.train.entity.CourseJoinViewEntity;
import com.yard.manager.merchant.train.entity.query.CourseJoinQueryEntity;

/**
 * 课程报名处理
 * @author : xiaym
 * @date : 2015年9月9日 下午3:12:21
 * @version : 1.0
 */
public class CourseJoinService extends BaseService<CourseJoinViewEntity> {
	private static final CourseJoinService cjs = new CourseJoinService();
	public static CourseJoinService getInstance() {
		return cjs;
	}

	/**
	 * 分页查询开店申请
	 * 
	 * @param page
	 * @param rows
	 * @param trainId 培训室ID
	 * @return
	 */
	public List<CourseJoinViewEntity> queryList(long page, long rows, Integer courseId, CourseJoinQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courseId, a.userId, a.title, a.price, a.hours, a.name, a.tel, a.remark, a.create_time createTime FROM t_course_join a WHERE a.courseId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(courseId);

		queryParams(query, sql, params);
		
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);

		try {
			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页数据统计
	 * 
	 * @return
	 */
	public long queryCount(Integer courseId, CourseJoinQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_course_join a WHERE a.courseId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(courseId);
		
		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(CourseJoinQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 姓名搜索
			if(!StringUtils.isBlank(query.getName())) {
				sql.append("AND a.name like ? ");
				params.add("%" +query.getName()+ "%");
			}
			// 电话搜索
			if(!StringUtils.isBlank(query.getTel())) {
				sql.append("AND a.tel like ? ");
				params.add("%"+query.getTel()+"%");
			}
			// 报名时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 23:59:59"));
			}
		}
	}
}
