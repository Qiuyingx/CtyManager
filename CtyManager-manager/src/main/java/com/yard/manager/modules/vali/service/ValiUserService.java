package com.yard.manager.modules.vali.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.detail.service.ExpDetailService;
import com.yard.manager.modules.detail.service.LindouDetailService;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.post.entity.PostViewEntity;
import com.yard.manager.modules.post.service.PostService;
import com.yard.manager.modules.user.entity.UserViewEntity;
import com.yard.manager.modules.user.entity.query.UserQueryEntity;
import com.yard.manager.modules.user.service.UserService;
import com.yard.manager.modules.util.JdbcUtils;
import com.yard.manager.modules.util.ThingsAdder;
import com.yard.manager.modules.vali.entity.ValiUserViewEntity;
import com.yard.manager.modules.vali.entity.query.ValiUserQueryEntity;
import com.yard.manager.platform.config.ExpInfo;
import com.yard.manager.platform.config.LindouInfo;

/**
 * 用户社区验证
 * 
 * @author : xiaym
 * @date : 2015年6月29日 下午3:47:20
 * @version : 1.0
 */
public class ValiUserService extends BaseService<ValiUserViewEntity> {
	private final static ValiUserService vus = new ValiUserService();
	private final static ExpDetailService es = ExpDetailService.getInstance();
	private final static LindouDetailService ls = LindouDetailService.getInstance();
	private static final NoticeService ns = NoticeService.getInstance();
	private static final UserService us = UserService.getInstance();
	private static final PostService ps = PostService.getInstance();

	public ValiUserService() {

	}

	public static ValiUserService getInstance() {
		return vus;
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
	 * @param courtyardIds
	 *            登录用户所管理的社区IDs
	 * @return
	 */
	public List<ValiUserViewEntity> queryList(ValiUserQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.user_id userId, a.courtyard_id courtyardId, a.user_id userId, a.create_time createTime, a.validate_type valiType, ");
		sql.append("a.append, a.validate_status valiStatus, b.courtyard_name courtyardName, c.nickname nickName, c.inviteCode inviteCode, ");
		sql.append("c.tel userMobile FROM t_validate_user a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id AND a.user_id = c.id ");

		List<Object> params = new ArrayList<Object>();

		// 所管理的社区
		if (!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN (" + courtyardIds + ") ");
		}

		queryParams(query, sql, params);

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
	 * @param courtyardIds
	 * @return
	 */
	public long queryCount(ValiUserQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_validate_user a, t_courtyard b, t_user c WHERE a.courtyard_id = b.id AND a.user_id = c.id ");

		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);

		// 所管理的社区
		if (!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN (" + courtyardIds + ") ");
		}

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void queryParams(ValiUserQueryEntity query, StringBuffer sql, List<Object> params) {
		if (query != null) {
			// 昵称
			if (!StringUtils.isBlank(query.getNickName())) {
				sql.append("AND c.nickname like ? ");
				params.add("%"+query.getNickName()+"%");
			}
			// 电话
			if (!StringUtils.isBlank(query.getUserMobile())) {
				sql.append("AND c.tel = ? ");
				params.add(query.getUserMobile());
			}
			// 所属社区
			if (!StringUtils.isBlank(query.getCourtyardIds())) {
				sql.append("AND a.courtyard_id IN (" + query.getCourtyardIds() + ") ");
			}
			// 验证方式
			if (!StringUtils.isBlank(query.getValiType())) {
				sql.append("AND a.validate_type IN (" + query.getValiType() + ") ");
			}
			// 验证状态
			if (!StringUtils.isBlank(query.getValiStatus())) {
				sql.append("AND a.validate_status IN (" + query.getValiStatus() + ") ");
			}
			// 提交时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime() + " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime() + " 23:59:59"));
			}
		}
	}

	/**
	 * 审核用户社区请求 （支持事务）
	 * 
	 * @param conn
	 * @param entity
	 *            审核信息
	 * @return
	 */
	public boolean changeStatus(Connection conn, ValiUserQueryEntity entity) {
		String sql = "UPDATE t_validate_user SET validate_status = ?, remark = ? WHERE id = ? ";

		List<Object> params = new ArrayList<Object>();
		params.add(entity.getValiStatus());
		params.add(entity.getRemark() == null ? "" : entity.getRemark());
		params.add(entity.getId());

		try {
			if (conn == null) {
				return update(sql, params.toArray());
			} else {
				return update(conn, sql, params.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean changeStatus(ValiUserQueryEntity entity) {
		return changeStatus(null, entity);
	}

	/**
	 * 审核入口
	 */
	public void valiPass(ValiUserQueryEntity entity, Map<String, Object> map) {
	/*	// 开启事务
		JdbcUtils.start();
		// 获取链接
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			// 审核通过
			if (entity.getValiStatus().equals(ManagerConstant.VALI_YARDS_USER_PASS)) {
				// 增加邻豆
				LindouInfo info = ThingsAdder.社区认证.getLindouInfo();
				us.upLindou(conn, entity.getUserId(), info.getLindou());

				ls.add(conn, entity.getUserId(), info.getLindou(), info.getRemark());

				// 增加经验
				ExpInfo expInfo = ThingsAdder.社区认证.getExpInfo();
				
				// 获取用户ID
				UserViewEntity userView = us.findById(entity.getUserId());
				boolean userIsUp = false;
				if(userView != null) {
					// 判断是否需要升级
					userIsUp = us.valiAddLevel(userView.getExp(), expInfo.getExp(), userView.getLevel());
				}
				
				us.upExp(conn, entity.getUserId(), expInfo.getExp(), userIsUp);
				
				es.add(conn, entity.getUserId(), expInfo.getExp(), expInfo.getRemark());

				if (entity.getInviteCode() != null && entity.getInviteCode() > 0) {
					List<UserViewEntity> inviterList = us.queryUserList(new UserQueryEntity(entity.getInviteCode()));
					if (inviterList != null && inviterList.size() > 0) {
						UserViewEntity inviter = inviterList.get(0);
						boolean isNeighbor = inviter.getCourtyardId() == entity.getCourtyardId();
						LindouInfo inviInfo = isNeighbor ? ThingsAdder.邀请邻居.getLindouInfo() : ThingsAdder.邀请非邻居
								.getLindouInfo();
						us.upLindou(conn, inviter.getId(), inviInfo.getLindou());
						ls.add(conn, inviter.getId(), inviInfo.getLindou(), inviInfo.getRemark());

						ExpInfo inviExpInfo = isNeighbor ? ThingsAdder.邀请邻居.getExpInfo() : ThingsAdder.邀请非邻居
								.getExpInfo();
						
						// 获取用户ID
						boolean inviterIsUp = false;
						if(inviter != null) {
							// 判断是否需要升级
							inviterIsUp = us.valiAddLevel(inviter.getExp(), inviExpInfo.getExp(), inviter.getLevel());
						}
						
						us.upExp(conn, inviter.getId(), inviExpInfo.getExp(), inviterIsUp);
						es.add(conn, inviter.getId(), inviExpInfo.getExp(), inviExpInfo.getRemark());
					}
				}
				ns.pushUserValiYardsSuccess(entity.getUserId(), expInfo.getExp(), info.getLindou());
				PostViewEntity postView = new PostViewEntity(entity.getCourtyardId(), entity.getUserId(),
						ManagerConstant.SHOW_POST_HELLO_TITLE, ManagerConstant.SHOW_POST_HELLO_CONTENT.replace(
								"${courtyardName}", entity.getCourtyardName()).replace("${nickName}",
								entity.getNickName()));
				ps.add(postView);
			} else {
				// 审核不通过
				ns.pushUserValiYardsFailed(entity.getUserId());
			}
			changeStatus(conn, entity);
			JdbcUtils.commit();
			JsonResult.toJson(map, true, "审核成功！");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作失败！");
			JdbcUtils.roleback();
		} finally {
			JdbcUtils.close();
		}*/
	}
}
