package com.yard.manager.modules.post.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.entity.UserReportViewEntity;
import com.yard.manager.modules.post.entity.query.PostQueryEntity;

/**
 * 帖子举报删除
 * @author : xiaym
 * @date : 2015年6月19日 下午4:31:05
 * @version : 1.0
 */
public class UserReportService extends BaseService<UserReportViewEntity> {
	private final static UserReportService urs = new UserReportService();
	private static PostService postService = PostService.getInstance();
	private static PostRemovedService postRemovedService = PostRemovedService.getInstance();
	
	public UserReportService() {
		
	}
	public static UserReportService getInstance() {
		return urs;
	}
	
	/**
	 * 分页查询 （帮帮和话题）
	 * 
	 * @param page 页号
	 * @param rows 页数
	 * @param contentType 内容类型
	 * @param courtyardIds 所管理的社区数组， 1,2,3
	 * @return
	 */
	public List<UserReportViewEntity> queryUserReportList(PostQueryEntity query, long page, long rows, Integer contentType,  String courtyardIds){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.user_id userId, a.content_type contentType, a.target_id targetId, a.courtyard_id courtyardId, ");
		sql.append("a.create_time createTime, a.status, a.content, a.report_type reportType, b.nickname oNickName, b.tel oTel, ");
		sql.append("c.courtyard_name courtyardName FROM t_user_report a, t_user b,  t_courtyard c WHERE a.user_id = b.id  AND a.courtyard_id = c.id ");
		sql.append("AND a.status = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(ManagerConstant.REPORT_STATUS_0);
		
		queryParams(query, sql, params);
		
		if(query != null && query.getContentType() != null  && query.getContentType() == 2) {
			sql.append("AND a.content_type = ? ");
			params.add(ManagerConstant.CONTENT_TYPE_1);
		}else{
			sql.append("AND (a.content_type = ? OR a.content_type = ? )");
			params.add(ManagerConstant.CONTENT_TYPE_2);
			params.add(ManagerConstant.CONTENT_TYPE_3);
		}
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND a.id IN ("+courtyardIds+") ");
		}
		
		// 分页排序
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		try {
			return query(UserReportViewEntity.class, sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 统计数量（帮帮和话题）
	 * 
	 * @param query
	 * @param contentType
	 * @param courtyardIds
	 * @return
	 */
	public long queryUserReportCount(PostQueryEntity query, Integer contentType, String courtyardIds){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_user_report a, t_user b,  t_courtyard c WHERE a.user_id = b.id  AND a.courtyard_id = c.id ");
		sql.append("AND a.status = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(ManagerConstant.REPORT_STATUS_0);
		
		queryParams(query, sql, params);
		
		if(query != null && query.getContentType() != null  && query.getContentType() == 2) {
			sql.append("AND a.content_type = ? ");
			params.add(ManagerConstant.CONTENT_TYPE_1);
		}else{
			sql.append("AND (a.content_type = ? OR a.content_type = ? )");
			params.add(ManagerConstant.CONTENT_TYPE_2);
			params.add(ManagerConstant.CONTENT_TYPE_3);
		}
		
		try {
			return (Long)certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查看帖子详情
	 * 
	 * @param postId 帖子ID
	 * @return
	 */
	public PostViewEntity getInfoById(Integer postId) {
		//查询帖子库
		PostViewEntity post = postService.findById(postId);
		if(post == null) {
			//查询已被移除的帖子库
			post = postRemovedService.findByPostId(postId);
		}
		if(post == null) {
			post = new PostViewEntity();
		}
		return post;
	}
	
	public void queryParams(PostQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null){
			// 帖子类型
			if(query.getContentType() != null) {
				sql.append("AND a.content_type = ? ");
				params.add(query.getContentType());
			}
			// 所属院子
			if(!StringUtils.isBlank(query.getYardids())) {
				sql.append("AND a.courtyard_id IN (" + query.getYardids() +") ");
			}
			// 举报者昵称
			if(!StringUtils.isBlank(query.getNickname())) {
				sql.append("AND b.nickname = ? ");
				params.add(query.getNickname());
			}
			// 举报者电话
			if(!StringUtils.isBlank(query.getTel())) {
				sql.append("AND b.tel = ? ");
				params.add(query.getTel());
			}
			// 时间筛选
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
			}
			//  举报类型
			if(!StringUtils.isBlank(query.getReportType())) {
				sql.append("AND a.report_type IN (" +query.getReportType() +") ");
			}
		}
	}
	
}
