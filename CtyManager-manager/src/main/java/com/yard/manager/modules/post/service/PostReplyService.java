package com.yard.manager.modules.post.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.post.entity.PostReplyViewEntity;
import com.yard.manager.modules.post.entity.query.PostReplyQueryEntity;

/**
 * 帖子评论
 * @author : xiaym
 * @date : 2015年6月18日 上午11:24:43
 * @version : 1.0
 */
public class PostReplyService extends BaseService<PostReplyViewEntity>{
	private final static PostReplyService prs = new PostReplyService();
	
	public PostReplyService(){
		
	}
	
	public static PostReplyService getInstance(){
		return prs;
	}
	
	/**
	 * 根据帖子ID 统计 评论次数
	 * @param postId
	 * @return
	 */
	public long getCountByPostId(Integer postId) {
		String sql = "SELECT COUNT(*) FROM t_post_reply WHERE post_id = ?";
		
		try {
			return (Long)certain(sql, postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取分页数据
	 * @param query 查询对象
	 * @param page 页号
	 * @param rows 页数
	 * @param postId 帖子ID
	 * @param contentType 帖子类型  1邀约 2帮帮 3话题
	 * @return
	 */
	public List<PostReplyViewEntity> queryList(PostReplyQueryEntity query, long page, long rows, Integer postId, Integer contentType) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT a.id, a.post_id postId, a.content_type contentType, a.replyer_id replyerId, a.content, a.create_time createTime, ");
		sql.append("a.at_targetId atTargetId, a.post_sender_id postSenderId, a.courtyard_id courtyardId, a.reply_id replyId, a.accepted, ");
		sql.append("b.nickname replyerName, c.nickname replyName FROM t_post_reply a LEFT JOIN t_user b ON a.replyer_id = b.id LEFT JOIN t_user c ");
		sql.append("ON a.at_targetId = c.id WHERE a.post_id = ? AND a.content_type = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(postId);
		params.add(contentType);
		
		queryParam(query, sql, params);
		
		// 分页排序
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		try{
			return query(sql.toString(), params.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 数据统计
	 * 
	 * @param query
	 * @param postId 帖子ID
	 * @param contentType 帖子类型  1邀约 2帮帮 3话题
	 * @return
	 */
	public long queryCount(PostReplyQueryEntity query, Integer postId, Integer contentType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_post_reply a LEFT JOIN t_user b ON a.replyer_id = b.id LEFT JOIN t_user c ");
		sql.append("ON a.at_targetId = c.id WHERE a.post_id = ? AND a.content_type = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(postId);
		params.add(contentType);
		
		queryParam(query, sql, params);
		
		try {
			return (Long)certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 评论数统计
	 * 
	 * @return
	 */
	public long queryPostReplyCount(String tel, String time) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_post_reply a LEFT JOIN t_user b ON a.replyer_id = b.id");
		sql.append(" WHERE b.tel = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(tel);
		
		// 评论时间筛选
		if (!StringUtils.isBlank(time)) {
			sql.append("AND a.create_time >= ? ");
			params.add(DateUtil.getMillisTime(time + " 00:00:00"));
		}

		if (!StringUtils.isBlank(time)) {
			sql.append("AND a.create_time <= ? ");
			params.add(DateUtil.getMillisTime(time + " 23:59:59"));
		}
		
		try {
			return (Long)certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParam(PostReplyQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query == null) {
			return;
		}
		// 评论内容模糊查询
		if(!StringUtils.isBlank(query.getContent())) {
			sql.append("AND a.content like ? ");
			params.add("%"+query.getContent()+"%");
		}
	}
	
	/**
	 * 删除选中评论
	 * @param id 评论ID
	 */
	public void del(Integer id, Map<String, Object> map) {
		String sql = "DELETE FROM t_post_reply WHERE id = ? ";
		
		try{
			if(update(sql, id)) {
				JsonResult.toJson(map, true, "评论删除成功！");
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作异常，评论删除失败！");
			return;
		}
		JsonResult.toJson(map, false, "评论删除失败！");
		return;
	}
}
