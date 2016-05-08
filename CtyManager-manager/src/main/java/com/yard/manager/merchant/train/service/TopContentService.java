package com.yard.manager.merchant.train.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.merchant.train.entity.TopContentViewEntity;
import com.yard.manager.merchant.train.entity.TrainCourseViewEntity;
import com.yard.manager.merchant.train.entity.TrainInfoViewEntity;

/**
 * 专题相关学堂推荐
 * @author : xiaym
 * @date : 2015年9月10日 下午2:42:32
 * @version : 1.0
 */
public class TopContentService extends BaseService<TopContentViewEntity> {
	private static final TopContentService tbs = new TopContentService();
	private TrainInfoService tis = TrainInfoService.getInstance();
	private TrainCourseService tcs = TrainCourseService.getInstance();
	public static TopContentService getInstance() {
		return tbs;
	}
	
	/**
	 * 判断是否已经推荐
	 * @param contentId 专题ID
	 * @param trainId 学堂ID
	 * @return
	 */
	public boolean isExits(Integer contentId, Integer trainId) {
		String sql = "SELECT * FROM t_top_content WHERE contentId = ? AND trainId = ? ";
		try {
			List<TopContentViewEntity> list = query(sql, contentId, trainId);
			return (list != null && list.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加推荐记录
	 * @param contentId 专题ID
	 * @param trainId 学堂ID
	 * @return
	 */
	public boolean add(Integer contentId, Integer trainId, String bannerImg, Integer dtype) {
		String sql = "INSERT INTO t_top_content(contentId, trainId, create_time, dtype) VALUES(?, ?, ?, ?);";
		try {
			return update(sql, contentId, trainId, System.currentTimeMillis(), dtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 取消推荐，删除推荐记录
	 * @param contentId 专题ID
	 * @param trainId 学堂ID
	 * @param dtype 推荐分类（3课程  4学堂）
	 * @return
	 */
	public boolean del(Integer contentId, Integer dtype) {
		String sql = "DELETE FROM t_top_content WHERE contentId = ? AND dtype = ? ";
		try {
			return update(sql, contentId, dtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 推荐学堂处理(3课程推荐  4学堂推荐)
	 * @param contentId 专题ID
	 * @param trainIds 学堂IDs
	 */
	public void processTrain(Integer contentId, String trainIds) {
		if(contentId != null && !StringUtils.isBlank(trainIds)) {
			// 删除原来推荐
			try {
				del(contentId, 4);
				
				 // 增加新推荐
				String[] trainIdStrs = trainIds.split(",");
				if(trainIdStrs != null) {
					for(String trains : trainIdStrs) {
						Integer trainId = Integer.parseInt(trains);
						TrainInfoViewEntity trainInfo = tis.getById(trainId);
						if(trainInfo != null) {
							add(contentId, trainId, trainInfo.getBannerImg(), 4);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 推荐学堂处理(3课程推荐  4学堂推荐)
	 * @param contentId 专题ID
	 * @param courseIds 课程IDs
	 */
	public void processCourse(Integer contentId, String courseIds) {
		if(contentId != null && !StringUtils.isBlank(courseIds)) {
			// 删除原来推荐
			try {
				del(contentId, 3);
				
				 // 增加新推荐
				String[] courseIdStrs = courseIds.split(",");
				if(courseIdStrs != null) {
					for(String courses : courseIdStrs) {
						Integer courseId = Integer.parseInt(courses);
						TrainCourseViewEntity courseInfo = tcs.findById(courseId);
						if(courseInfo != null) {
							add(contentId, courseId, courseInfo.getTitleImg(), 3);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
