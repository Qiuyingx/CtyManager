package com.yard.manager.modules.post.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.post.entity.PostRemovedViewEntity;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.util.StringUtil;

/**
 * 帖子删除
 * @author : xiaym
 * @date : 2015年6月19日 下午5:04:39
 * @version : 1.0
 */
public class PostRemovedService extends BaseService<PostRemovedViewEntity> {
	private final static PostRemovedService prs = new PostRemovedService();
	public PostRemovedService() {
		
	}
	public static PostRemovedService getInstance() {
		return prs;
	}
	
	/**
	 * 通过帖子ID查询移除的帖子信息
	 * @param postId
	 * @return
	 */
	public PostRemovedViewEntity findById(Integer postId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.user_id userId, a.content_type contentType, a.image_names imageNames, ");
		sql.append("a.priority, a.title, a.content, a.create_time createTime, a.tag FROM t_post_removed a WHERE a.id = ?");
		
		try {
			return show(sql.toString(), postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过帖子ID查询帖子信息
	 * 
	 * @param postId 帖子ID
	 * @return
	 */
	public PostViewEntity findByPostId(Integer postId) {
		//"a.acceptId, a.reward, a.myself,  无字段
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.user_id userId, a.content_type contentType, ");
		sql.append("a.image_names imageNames, a.priority, a.title, a.content, a.create_time createTime, a.tag, ");
		sql.append("b.courtyard_name courtyardName, c.nickname nickName, ");
		sql.append("(SELECT COUNT(*) FROM t_post_laud d WHERE d.post_id = a.id ) laudCount, ");
		sql.append("(SELECT COUNT(*) FROM t_post_reply e WHERE e.post_id = a.id ) replyCount ");
		sql.append("FROM t_post_removed a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id  AND a.user_id = c.id ");
		sql.append("AND a.id = ? ");
		
		try {
			return show(PostViewEntity.class, sql.toString(), postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 记录被删除的帖子信息
	 */
	public boolean add(Connection conn, PostViewEntity post) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_post_removed(id, courtyard_id, user_id, content_type, image_names, ");
		sql.append("priority, title, content, create_time, tag, acceptId, reward, myself, vali_status, show_around, topicTag ) ");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		
		List<Object> params = new ArrayList<Object>();
		
		try{
			params.add(post.getId());
			params.add(post.getCourtyardId());
			params.add(post.getUserId());
			params.add(post.getContentType());
			params.add(StringUtil.str(post.getImageNames()));
			params.add(post.getPriority());
			params.add(StringUtil.str(post.getTitle()));
			params.add(StringUtil.str(post.getContent()));
			params.add(post.getCreateTime());
			params.add(post.getTag()==null?0:post.getTag());
			params.add(post.getAcceptId()==null?0:post.getAcceptId());
			params.add(post.getReward()==null?0:post.getAcceptId());
			params.add(post.getMyself());
			params.add(post.getValiStatus());
			params.add(post.getShowAround());
			params.add(post.getTopicTag());
			
			if(conn == null) {
				return update(conn, sql.toString(), params.toArray());
			}else{
				return update(sql.toString(), params.toArray());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean add(PostViewEntity post) {
		return add(null, post);
	}
}
