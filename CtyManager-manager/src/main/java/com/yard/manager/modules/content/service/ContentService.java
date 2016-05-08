package com.yard.manager.modules.content.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.service.TopContentService;
import com.yard.manager.modules.content.entity.ContentEntity;
import com.yard.manager.modules.content.entity.ContentViewEntity;
import com.yard.manager.modules.content.entity.query.ContentQueryEntity;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 文章管理
 * 
 * @author : xiaym
 * @date : 2015年7月3日 下午4:47:03
 * @version : 1.0
 */
public class ContentService extends BaseService<ContentViewEntity> {
	private static final ContentService cs = new ContentService();
	private TopContentService tcs = TopContentService.getInstance();

	public ContentService() {

	}

	public static ContentService getInstance() {
		return cs;
	}

	/**
	 * 获取分页数据
	 * 
	 * @param query
	 *            查询对象
	 * @param page
	 *            页号
	 * @param rows
	 *            页数
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<ContentViewEntity> queryList(ContentQueryEntity query, long page, long rows, Integer channelId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.managerId, a.title, a.description, a.title_img titleImg, a.create_time createTime, a.views, a.banner_img bannerImg, ");
		sql.append("a.trainIds, a.channel_id channelId, a.status, b.id bannerId, c.id listId FROM t_content a  LEFT JOIN ");
		sql.append("t_top_banner b ON a.id = b.contentId AND b.dtype = ? LEFT JOIN  t_top_list c ON a.id = c.contentId AND c.dtype = ? WHERE a.channel_id = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(channelId);
		params.add(channelId);
		params.add(channelId);
		
		queryParam(query, sql, params, channelId);

		// 分页排序
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
	 * 数据统计
	 * 
	 * @param query
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public long queryCount(ContentQueryEntity query, Integer channelId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_content a WHERE a.channel_id = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(channelId);

		queryParam(query, sql, params, channelId);

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void queryParam(ContentQueryEntity query, StringBuffer sql, List<Object> params, Integer channelId) {
		if (query != null) {
			// 文章标题
			if (!StringUtils.isBlank(query.getTitle())) {
				sql.append("AND a.title like ? ");
				params.add("%" + query.getTitle() + "%");
			}

			// 所属栏目
			if (!StringUtils.isBlank(query.getChannelIds())) {
				sql.append("AND a.channel_id IN(" + query.getChannelIds() + ") ");
			}

			// 发布时间(起)
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime() + " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime() + " 23:59:59"));
			}

			// 文章状态
			if (query.getStatus() != null) {
				sql.append("AND a.status = ? ");
				params.add(query.getStatus());
			}
			
			// banner推荐列表
			if(!StringUtils.isBlank(query.getIsBannerTop())) {
				sql.append("AND a.id IN (SELECT DISTINCT contentId FROM t_top_banner WHERE dtype = ?) ");
				params.add(channelId);
			}
			
			// 列表推荐查询
			if(!StringUtils.isBlank(query.getIsListTop())) {
				sql.append("AND a.id IN (SELECT DISTINCT contentId FROM t_top_list WHERE dtype = ?) ");
				params.add(channelId);
			}
		}
	}

	/**
	 * 添加商品
	 * 
	 * @param entity
	 *            活动信息
	 * @param map
	 */
	public void add(ContentEntity entity, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_content(managerId, title, description, title_img, create_time, content, ");
		sql.append("channel_id, status, banner_img, trainIds, courseIds) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(ShiroUtil.getUserId());
			params.add(entity.getTitle());
			params.add(entity.getDescription());
			params.add(entity.getTitleImg());
			params.add(new Date().getTime());
			params.add(entity.getContent());
			params.add(entity.getChannelId());
			params.add(entity.getStatus());
			params.add(entity.getBannerImg());
			params.add(entity.getTrainIds());
			params.add(entity.getCourseIds());

			Integer id = add(sql.toString(), params.toArray());
			if (id <= 0) {
				JsonResult.toJson(map, false, "操作失败了！");
			} else {
				JsonResult.toJson(map, true, "操作成功！");
				if(entity.getChannelId() != null && entity.getChannelId() == 1) {
					tcs.processTrain(id, entity.getTrainIds());
					tcs.processCourse(id, entity.getCourseIds());
				}
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作出现异常，操作失败！");
			return;
		}
	}

	/**
	 * 修改商品
	 * 
	 * @param entity
	 *            活动信息
	 * @param map
	 */
	public void update(ContentEntity entity, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_content SET title = ?, description = ?, title_img = ?, content = ?, status = ?, banner_img = ?, trainIds = ?, courseIds = ? WHERE id = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getTitle());
			params.add(entity.getDescription());
			params.add(entity.getTitleImg());
			params.add(entity.getContent());
			params.add(entity.getStatus());
			params.add(entity.getBannerImg());
			params.add(entity.getTrainIds());
			params.add(entity.getCourseIds());
			params.add(entity.getId());

			if (updates(sql.toString(), params.toArray()) != 1) {
				JsonResult.toJson(map, false, "操作失败！");
			} else {
				JsonResult.toJson(map, true, "操作成功！");
				if(entity.getChannelId() != null && entity.getChannelId() == 1) {
					tcs.processTrain(entity.getId(), entity.getTrainIds());
					tcs.processCourse(entity.getId(), entity.getCourseIds());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作出现异常，操作失败！");
			return;
		}
		return;
	}

	/**
	 * 发布/草稿 文章
	 */
	public void changeStatus(ContentEntity entity, Map<String, Object> map) {
		String sql = "UPDATE t_content SET status = abs(status - 1) WHERE id = ? ";

		try {
			if (updates(sql, entity.getId()) == 1) {
				JsonResult.toJson(map, true, "操作成功！");
			} else {
				JsonResult.toJson(map, false, "操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "操作失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/***
	 * 删除文章
	 */
	public void del(ContentEntity entity, Map<String, Object> map) {
		try {
			String sql = "DELETE FROM t_content WHERE id = ? ";
			if (update(sql, entity.getId())) {
				JsonResult.toJson(map, true, "删除成功！");
			} else {
				JsonResult.toJson(map, false, "删除失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "操作失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}

	/**
	 * 通过ID查询专题信息
	 * @param id 专题ID
	 * @return
	 */
	public ContentViewEntity findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.managerId, a.title, a.description, a.title_img titleImg, a.create_time createTime, a.views, a.banner_img bannerImg, ");
		sql.append("a.trainIds, a.courseIds, a.content, a.channel_id channelId, a.status FROM t_content a WHERE a.id = ? ");
		
		try{
			return show(sql.toString(), id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据ID 获取内容详情
	 * @param id
	 * @param map
	 */
	public void getContentInfo(Integer id, Map<String, Object> map) {
		try{
			ContentViewEntity info = findById(id);
			map.put("info", info);
			JsonResult.toJson(map, true, "获取成功！");
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "出现异常，无法内容！");
		return;
	}
}
