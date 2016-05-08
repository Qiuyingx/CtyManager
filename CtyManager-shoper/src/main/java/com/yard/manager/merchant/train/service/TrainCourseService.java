package com.yard.manager.merchant.train.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.entity.TrainCourseViewEntity;
import com.yard.manager.merchant.train.entity.query.TrainCourseQueryEntity;
import com.yard.manager.platform.shiro.ShiroUtil;

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
		sql.append("SELECT a.id, a.trainId, a.typeId, a.typeName, a.title, a.title_img titleImg, a.hours, a.tel, a.description, ");
		sql.append("a.address, a.price, a.priceunit, a.topCount, a.views, a.create_time createTime, a.study_time studyTime, a.status, ");
		sql.append("a.start_time startTime, a.end_time endTime, a.class_type classType, a.copies FROM t_train_course a WHERE a.trainId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(ShiroUtil.getTrainId());
		
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
		sql.append("SELECT a.id, a.trainId, a.typeId, a.typeName, a.title, a.title_img titleImg, a.hours, a.tel, a.longitude, a.latitude, a.cityId, a.description, ");
		sql.append("a.address, a.price, a.priceunit, a.topCount, a.views, a.create_time createTime, a.study_time studyTime, a.status, a.typeId, a.typeName, ");
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
		sql.append("SELECT COUNT(*) FROM t_train_course a WHERE a.trainId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(ShiroUtil.getTrainId());

		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 添加课程
	 */
	public boolean add(Connection conn, TrainCourseViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_train_course(trainId, typeId, typeName, cityId, title, description, title_img, hours, tel, ");
		sql.append("content, address, longitude, latitude, price, priceunit, create_time, study_time, status, start_time, end_time, class_type, ");
		sql.append("copies) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		
		List<Object> params = new ArrayList<Object>();
		try {
			params.add(ShiroUtil.getTrainId());
			params.add(entity.getTypeId());
			params.add(entity.getTypeName());
			params.add(entity.getCityId());
			params.add(entity.getTitle());
			params.add(entity.getDescription());
			params.add(entity.getTitleImg());
			params.add(entity.getHours());
			params.add(entity.getTel());
			params.add(entity.getContent());
			params.add(entity.getAddress());
			params.add(entity.getLongitude());
			params.add(entity.getLatitude());
			params.add(entity.getPrice());
			params.add(entity.getPriceunit());
			params.add(System.currentTimeMillis());
			params.add(entity.getStudyTime());
			params.add(0);
			if(!StringUtils.isBlank(entity.getStartTimeStr())) {
				params.add(DateUtil.getMillisTime(entity.getStartTimeStr()));
			}else{
				params.add(0);
			}
			if(!StringUtils.isBlank(entity.getEndTimeStr())){
				params.add(DateUtil.getMillisTime(entity.getEndTimeStr()));
			}else{
				params.add(0);
			}
			params.add(entity.getClassType());
			params.add(entity.getCopies());
			
			if(conn != null) {
				return update(conn, sql.toString(), params.toArray());
			}else {
				return update(sql.toString(), params.toArray());
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean add(TrainCourseViewEntity entity) {
		return add(null, entity);
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
	
	/**
	 * 修改课程
	 */
	public boolean update(Connection conn, TrainCourseViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_train_course SET  typeId = ?, typeName = ?, cityId = ?, title = ?, description = ?, ");
		sql.append("title_img = ?, hours = ?, tel = ?, content = ?, address = ?, longitude = ?, latitude = ?, price = ?, ");
		sql.append("priceunit = ?, study_time = ?, start_time = ?, end_time = ?, copies = ?, class_type = ? WHERE id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		try {
			params.add(entity.getTypeId());
			params.add(entity.getTypeName());
			params.add(entity.getCityId());
			params.add(entity.getTitle());
			params.add(entity.getDescription());
			params.add(entity.getTitleImg());
			params.add(entity.getHours());
			params.add(entity.getTel());
			params.add(entity.getContent());
			params.add(entity.getAddress());
			params.add(entity.getLongitude());
			params.add(entity.getLatitude());
			params.add(entity.getPrice());
			params.add(entity.getPriceunit());
			params.add(entity.getStudyTime());
			if(!StringUtils.isBlank(entity.getStartTimeStr())) {
				params.add(DateUtil.getMillisTime(entity.getStartTimeStr()));
			}else{
				params.add(0);
			}
			if(!StringUtils.isBlank(entity.getEndTimeStr())){
				params.add(DateUtil.getMillisTime(entity.getEndTimeStr()));
			}else{
				params.add(0);
			}
			params.add(entity.getCopies());
			params.add(entity.getClassType());
			params.add(entity.getId());
			
			if(conn != null) {
				return update(conn, sql.toString(), params.toArray());
			}else{
				return update(sql.toString(), params.toArray());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean update(TrainCourseViewEntity entity) {
		return update(null, entity);
	}
	

	public void queryParams(TrainCourseQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 课程名称搜索
			if(!StringUtils.isBlank(query.getTitle())) {
				sql.append("AND a.title like ? ");
				params.add("%" +query.getTitle()+ "%");
			}
			// 课程状态
			if(query.getStatus() != null) {
				sql.append("AND a.status = ? ");
				params.add(query.getStatus());
			}
		}
	}
	
	/**
	 * 添加课程入口
	 */
	public void addCourse(TrainCourseViewEntity entity, Map<String, Object> map) {
		try {
			if(add(entity)) {
				JsonResult.toJson(map, true, "操作成功！");
			}else{
				JsonResult.toJson(map, false, "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作失败了！");
		}
	}
	
	/**
	 * 修改课程入口
	 */
	public void updateCourse(TrainCourseViewEntity entity, Map<String, Object> map) {
		try {
			if(update(entity)) {
				JsonResult.toJson(map, true, "操作成功！");
			}else{
				JsonResult.toJson(map, false, "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作失败了！");
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
