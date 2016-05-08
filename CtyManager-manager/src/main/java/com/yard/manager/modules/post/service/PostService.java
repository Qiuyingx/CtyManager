package com.yard.manager.modules.post.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.post.entity.PostOrderEntity;
import com.yard.manager.modules.post.entity.PostTopEntity;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.entity.query.PostQueryEntity;
import com.yard.manager.modules.util.JdbcUtils;

/**
 * 话题帮帮
 * 
 * @author : xiaym
 * @date : 2015年6月18日 上午1:22:33
 * @version : 1.0
 */
public class PostService extends BaseService<PostViewEntity> {
	private final static PostService ps = new PostService();
	private static final NoticeService ns = NoticeService.getInstance();
	private static final PostRemovedService rs = PostRemovedService.getInstance();

	private PostService() {

	}

	public static PostService getInstance() {
		return ps;
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            页号
	 * @param rows
	 *            页数
	 * @param contentType
	 *            内容类型
	 * @param courtyardIds
	 *            所管理的社区数组， 1,2,3
	 * @return
	 */
	public List<PostViewEntity> queryPostList(PostQueryEntity query, long page, long rows, Integer contentType,
			String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.user_id userId, a.content_type contentType, ");
		sql.append("a.image_names imageNames, a.priority, a.title, a.content, a.create_time createTime, a.tag, ");
		sql.append("a.acceptId, a.reward, a.myself, b.courtyard_name courtyardName, c.nickname nickName, a.vali_status valiStatus, ");
		sql.append("(SELECT COUNT(*) FROM t_post_laud d WHERE d.post_id = a.id ) laudCount, ");
		sql.append("(SELECT COUNT(*) FROM t_post_reply e WHERE e.post_id = a.id ) replyCount ");
		sql.append("FROM t_post a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id  AND a.user_id = c.id ");

		List<Object> params = new ArrayList<Object>();
		// 内容类型分类
		if (contentType != null) {
			sql.append("AND a.content_type = ? ");
			params.add(contentType);
		}

		// 所管理的社区
		if (!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN (" + courtyardIds + ") ");
		}

		// 条件筛选查询
		queryParmas(query, sql, params);

		// 分页排序
		sql.append("ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);

		try {
			return query(PostViewEntity.class, sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询数据统计
	 * 
	 * @param contentType
	 *            内容类型
	 * @param courtyardIds
	 *            所管理的社区数组， 1,2,3
	 * @return
	 */
	public long queryPostListCount(PostQueryEntity query, Integer contentType, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_post a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id AND a.user_id = c.id ");
		List<Object> params = new ArrayList<Object>();

		// 内容类型分类
		if (contentType != null) {
			sql.append("AND a.content_type = ? ");
			params.add(contentType);
		}

		// 所管理的社区
		if (!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN (" + courtyardIds + ") ");
		}

		// 条件筛选查询
		queryParmas(query, sql, params);

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void queryParmas(PostQueryEntity query, StringBuffer sql, List<Object> params) {

		if (query == null) {
			return;
		}

		// 帖子标题筛选
		if (!StringUtils.isBlank(query.getTitle())) {
			sql.append("AND a.title like ? ");
			params.add("%" + query.getTitle() + "%");
		}

		// 发帖人昵称筛选
		if (!StringUtils.isBlank(query.getNickname())) {
			sql.append("AND c.nickname like ? ");
			params.add("%" + query.getNickname() + "%");
		}

		// 帖子所属小区筛选
		if (!StringUtils.isBlank(query.getYardids())) {
			sql.append("AND b.id IN (" + query.getYardids() + ") ");
		}

		// 发帖时间筛选
		if (!StringUtils.isBlank(query.getStartDate())) {
			sql.append("AND a.create_time >= ? ");
			params.add(DateUtil.getMillisTime(query.getStartDate() + " 00:00:00"));
		}

		if (!StringUtils.isBlank(query.getEndDate())) {
			sql.append("AND a.create_time <= ? ");
			params.add(DateUtil.getMillisTime(query.getEndDate() + " 23:59:59"));
		}

		// 发帖人电话
		if (!StringUtils.isBlank(query.getTel())) {
			sql.append("AND c.tel = ? ");
			params.add(query.getTel());
		}

		// 是否紧急
		if (query.getIsHarry() != null && query.getIsHarry().equals("true")) {
			sql.append("AND a.priority = 1 ");
		}

		// 是否已采纳评论
		if (query.getIsAccept() != null && query.getIsAccept().equals("true")) {
			sql.append("AND a.acceptId > 0 ");
		}

		// 专题活动标题
		if (!StringUtils.isBlank(query.getSpecialTitle())) {
			sql.append("AND (a.title like ? OR a.content like ? )");
			params.add("%" + query.getSpecialTitle() + "%");
			params.add("%" + query.getSpecialTitle() + "%");
		}
		
		// 是否查询置顶帖子（true查询置顶帖）
		if (!StringUtils.isBlank(query.getIsTop()) && query.getIsTop().equals("true")) {
			sql.append("AND a.id IN (SELECT DISTINCT post_id FROM t_top_post) ");
		}
	}

	/**
	 * 发布帖子
	 * 
	 * @param entity
	 * @return
	 */
	public boolean add(PostViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_post(version, courtyard_id, user_id, content_type, image_names, ");
		sql.append("priority, title, content, create_time, tag, acceptId, reward, myself, vali_status, show_around) ");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

		List<Object> params = new ArrayList<Object>();

		try {
			params.add(0);
			params.add(entity.getCourtyardId());
			params.add(entity.getUserId());
			params.add(entity.getContentType());
			params.add(entity.getImageNames());
			params.add(entity.getPriority());
			params.add(entity.getTitle());
			params.add(entity.getContent());
			params.add(new Date().getTime());
			params.add(entity.getTag() == null ? 0 : entity.getTag());
			params.add(entity.getAcceptId() == null ? 0 : entity.getAcceptId());
			params.add(entity.getReward() == null ? 0 : entity.getAcceptId());
			params.add(entity.getMyself());
			params.add(0);
			params.add(entity.getShowAround() == null ? 0 : entity.getShowAround());

			return update(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 通过帖子ID查询帖子信息
	 * 
	 * @param postId
	 *            帖子ID
	 * @return
	 */
	public PostViewEntity findById(Integer postId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.user_id userId, a.content_type contentType, ");
		sql.append("a.image_names imageNames, a.priority, a.title, a.content, a.create_time createTime, a.tag, ");
		sql.append("a.acceptId, a.reward, a.myself, b.courtyard_name courtyardName, c.nickname nickName, ");
		sql.append("(SELECT COUNT(*) FROM t_post_laud d WHERE d.post_id = a.id ) laudCount, ");
		sql.append("(SELECT COUNT(*) FROM t_post_reply e WHERE e.post_id = a.id ) replyCount ");
		sql.append("FROM t_post a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id  AND a.user_id = c.id ");
		sql.append("AND a.id = ? ");

		try {
			return show(sql.toString(), postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取原帖信息
	 */
	public PostViewEntity getPostById(Integer postId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.user_id userId, a.content_type contentType, ");
		sql.append("a.image_names imageNames, a.priority, a.title, a.content, a.create_time createTime, a.tag, ");
		sql.append("a.acceptId, a.reward, a.myself, a.vali_status valiStatus, a.show_around showAround, a.topicTag FROM t_post a WHERE a.id = ? ");

		try {
			return show(sql.toString(), postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发帖活跃量统计
	 */
	public List<PostOrderEntity> postOrder(String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.user_id userId,COUNT(*) counts, b.nickname nickName, b.tel, c.courtyard_name courtyardName ");
		sql.append("FROM t_post a, t_user b, t_courtyard c WHERE a.user_id = b.id AND a.courtyard_id = c.id AND b.tel IS NOT NULL ");
		sql.append("AND b.tel != '' AND tel NOT LIKE '9999%' AND tel NOT IN('17608003414','13983823863','13509481513','13509481512','13983823865') ");
		if (!StringUtils.isBlank(courtyardIds)) {
			sql.append("AND a.courtyard_id IN(" + courtyardIds + ") ");
		}
		sql.append("GROUP BY a.user_id  ORDER BY counts DESC  LIMIT 0, 30 ");

		try {
			return query(PostOrderEntity.class, sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加置顶帖子
	 * 
	 * @param topEntity
	 * @return
	 */
	public boolean addTopPost(PostTopEntity topEntity) {
		String sql = "INSERT INTO t_top_post(post_id, courtyard_id, topType, priority) VALUES(?, ?, ?, ?);";
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(topEntity.getPostId());
			params.add(topEntity.getCourtyardId());
			params.add(topEntity.getTopType());
			params.add(topEntity.getPriority());

			return update(sql, params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 通过帖子ID查询置顶信息
	 * @param postId 帖子ID
	 * @return
	 */
	public PostTopEntity getTopPost(Integer postId) {
		String sql = "SELECT id, post_id postId, courtyard_id courtyardId, topType, priority FROM t_top_post WHERE post_id = ? ";
		try {
			return show(PostTopEntity.class, sql, postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取消置顶帖子
	 * 
	 * @param postId
	 *            帖子Id
	 */
	public boolean delTopPost(Integer postId) {
		String sql = "DELETE FROM t_top_post WHERE post_id = ?";
		try {
			return update(sql, postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void pushAll(PostViewEntity post) {
		if (post != null && post.getValiStatus() == 1 && post.getId() != null) {
			ns.pushHelp(post.getIsAllCity(), post.getCityIds(), post.getIsAllYards(), post.getYardIds(), post.getId(),
					post.getNickName(), post.getContent(), post.getCourtyardName());
		}
	}

	/**
	 * 审核状态变更
	 */
	public void changeValiStatus(PostViewEntity post, Map<String, Object> map) {
		String sql = "UPDATE t_post SET vali_status = ? WHERE id = ? ";

		try {
			if (update(sql, post.getValiStatus(), post.getId())) {
				// 审核通过 推送消息
				pushAll(post);

				JsonResult.toJson(map, true, "操作成功！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
			return;
		}
		JsonResult.toJson(map, false, "操作失败！");
		return;

	}

	/**
	 * 删除原帖(带事务处理)
	 * 
	 * @param postId
	 *            帖子ID
	 */
	public boolean delete(Connection conn, Integer postId) {
		String sql = "DELETE FROM t_post WHERE id = ? ";

		try {
			if (conn != null) {
				return update(conn, sql, postId);
			} else {
				return update(sql, postId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean delete(Integer postId) {
		return delete(null, postId);
	}

	/**
	 * 移除选中帖子（删除t_post表数据，备份至t_post_removed）
	 * 
	 * @param postId
	 *            帖子ID（话题或帮帮）
	 */
	public void deletePost(Integer postId, Map<String, Object> map) {
		try {
			JdbcUtils.start();
			Connection conn = JdbcUtils.getConnection();
			// 获取原帖信息
			PostViewEntity post = getPostById(postId);
			if (post == null) {
				JsonResult.toJson(map, false, "无法获取帖子信息!");
				return;
			}
			// 删除原帖
			delete(conn, postId);

			// 移至remove表
			rs.add(conn, post);

			JdbcUtils.commit();
			JsonResult.toJson(map, true, "操作成功，已将帖子移除！");
			return;

		} catch (Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "操作失败！");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
	}

	/**
	 * 获取发帖人-评论者 采纳贴数据
	 * 
	 * @param sUserId
	 *            发帖人ID
	 * @param cUserId
	 *            评论者ID
	 */
	public List<PostViewEntity> getAcceptedPost(Integer sUserId, Integer cUserId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.create_time createTime, b.nickname nickName, a.content, a.image_names imageNames, ");
		sql.append("c.nickname cNickName, c.head_icon cFaceImage, d.create_time cTime, d.content cContent FROM t_post a, ");
		sql.append("t_user b, t_user c, t_post_reply d WHERE a.user_id = b.id AND a.id = d.post_id AND d.accepted = 1 AND ");
		sql.append("a.content_type = ? AND d.replyer_id = c.id AND c.id = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(ManagerConstant.CONTENT_TYPE_2);
		params.add(cUserId);
		if (sUserId != null && sUserId != 0) {
			sql.append("AND a.user_id = ? ");
			params.add(sUserId);
		}
		try {
			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取发帖人--评论者 采纳次数
	 * 
	 * @param cUserId
	 *            评论者ID
	 */
	public List<PostViewEntity> getAcceptedCount(Integer cUserId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.user_id userId, b.nickname nickName, COUNT(*) count FROM t_post a, t_user b, t_user c, t_post_reply d WHERE a.user_id = b.id AND a.id = d.post_id AND ");
		sql.append("d.accepted = 1 AND a.content_type = ? AND d.replyer_id = c.id AND c.id = ? GROUP BY a.user_id");

		List<Object> params = new ArrayList<Object>();
		params.add(ManagerConstant.CONTENT_TYPE_2);
		params.add(cUserId);
		try {
			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
