package com.yard.manager.merchant.train.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.entity.TrainCourseViewEntity;
import com.yard.manager.merchant.train.entity.query.TrainCourseQueryEntity;

/**
 * 培训室课程管理
 * @author : xiaym
 * @date : 2015年9月7日 下午4:54:42
 * @version : 1.0
 */
public class TrainCourseService extends BaseService<TrainCourseViewEntity> {
	private static final TrainCourseService tcs = new TrainCourseService();
	public static TrainCourseService getInstance() {
		return tcs;
	}

	/**
	 * 分页查询开店申请
	 * 
	 * @param page
	 * @param rows
	 * @param trainId 培训室ID
	 * @return
	 */
	public List<TrainCourseViewEntity> queryList(long page, long rows, TrainCourseQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.trainId, a.typeId, a.typeName, a.title, a.title_img titleImg, a.hours, a.tel, a.banner_img bannerImg, d.title trainName, a.description, ");
		sql.append("a.address, a.price, a.topCount, a.views, a.create_time createTime, a.study_time studyTime, a.status, b.id bannerId, c.id listId, ");
		sql.append("a.start_time startTime, a.end_time endTime, a.class_type classType, a.copies, a.refundType, a.refundRatio FROM t_train_info d, t_train_course a LEFT JOIN ");
		sql.append("t_top_banner b ON a.id = b.contentId AND b.dtype = 3 LEFT JOIN  t_top_list c ON a.id = c.contentId AND c.dtype = 3 WHERE a.trainId = d.id ");
		List<Object> params = new ArrayList<Object>();
		
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
	
	public TrainCourseViewEntity findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.trainId, a.typeId, a.typeName, a.title, a.title_img titleImg, a.hours, a.tel, a.longitude, a.latitude, a.cityId, a.banner_img bannerImg, ");
		sql.append("a.address, a.price, a.topCount, a.views, a.create_time createTime, a.study_time studyTime, a.status, a.typeId, a.typeName, ");
		sql.append("a.start_time startTime, a.end_time endTime, a.class_type classType, a.copies, a.content FROM t_train_course a WHERE a.id = ? ");
		
		try {
			return show(sql.toString(), id);
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
	public long queryCount(TrainCourseQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_train_info d, t_train_course a LEFT JOIN ");
		sql.append("t_top_banner b ON a.id = b.contentId AND b.dtype = 3 LEFT JOIN  t_top_list c ON a.id = c.contentId AND c.dtype = 3 WHERE a.trainId = d.id ");
		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 课程状态变更
	 * @param id 课程ID
	 * @param status 状态
	 */
	public boolean changeStatus(Connection conn, Integer id, Integer status) {
		String sql = "UPDATE t_train_course SET status = ? WHERE id = ? ";
		try {
			if(conn != null) {
				return update(conn, sql, status, id);
			}else{
				return update(sql, status, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public boolean changeStatus(Integer id, Integer status) {
		return changeStatus(null, id, status);
	}
	
	public void queryParams(TrainCourseQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 课程名称搜索
			if(!StringUtils.isBlank(query.getTitle())) {
				sql.append("AND a.title like ? ");
				params.add("%" +query.getTitle()+ "%");
			}
			
			// banner推荐列表
			if(!StringUtils.isBlank(query.getIsBannerTop())) {
				sql.append("AND a.id IN (SELECT DISTINCT contentId FROM t_top_banner WHERE dtype = 3) ");
			}
				
			// 列表推荐查询
			if(!StringUtils.isBlank(query.getIsListTop())) {
				sql.append("AND a.id IN (SELECT DISTINCT contentId FROM t_top_list WHERE dtype = 3) ");
			}

			// 课程状态
			if(query.getStatus() != null) {
				sql.append("AND a.status = ? ");
				params.add(query.getStatus());
			}
		}
	}
	
	/**
	 * 根据ID 获取内容详情
	 * @param id
	 * @param map
	 */
	public void getContentInfo(Integer id, Map<String, Object> map) {
		try{
			TrainCourseViewEntity info = findById(id);
			map.put("info", info);
			JsonResult.toJson(map, true, "获取成功！");
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "出现异常，无法内容！");
		return;
	}
	
	/**
	 * 开启/下架课程
	 * @param id 课程ID
	 * @param status 课程状态 
	 */
	public void changeCourse(Integer id, Integer status, Map<String, Object> map) {
		try {
			if(changeStatus(id, status)) {
				JsonResult.toJson(map, true, "操作成功！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "操作失败了！");
		return;
	}
}
