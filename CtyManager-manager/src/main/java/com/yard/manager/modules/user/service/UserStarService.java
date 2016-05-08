package com.yard.manager.modules.user.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.detail.service.ExpDetailService;
import com.yard.manager.modules.detail.service.LindouDetailService;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.user.entity.UserStarViewEntity;
import com.yard.manager.modules.user.entity.UserViewEntity;
import com.yard.manager.modules.util.JdbcUtils;
import com.yard.manager.modules.util.ThingsAdder;
import com.yard.manager.platform.config.ExpInfo;
import com.yard.manager.platform.config.LindouInfo;

/**
 * 用户技能认证
 * @author : xiaym
 * @date : 2015年8月17日 下午7:14:28
 * @version : 1.0
 */
public class UserStarService extends BaseService<UserStarViewEntity> {
	private static final UserStarService uss = new UserStarService();
	private NoticeService ns = NoticeService.getInstance();
	private ExpDetailService es = ExpDetailService.getInstance();
	private LindouDetailService ls = LindouDetailService.getInstance();
	private UserService us = UserService.getInstance();

	public static UserStarService getInstance() {
		return uss;
	}

	/**
	 * 分页查询技能认证列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<UserStarViewEntity> queryList(long page, long rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.user_id userId, a.tel, a.content, a.image_names imageNames, a.skill tagName, a.status, a.remark, ");
		sql.append("a.create_time createTime, b.nickname nickName, c.courtyard_name courtyardName FROM t_user_star a, ");
		sql.append("t_user b, t_courtyard c WHERE a.user_id = b.id AND b.courtyard_id = c.id ");
		List<Object> params = new ArrayList<Object>();

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
	public long queryCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user_star a, t_user b, t_courtyard c WHERE a.user_id = b.id AND b.courtyard_id = c.id ");

		try {
			return (Long) certain(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 审核处理技能认证请求
	 */
	public boolean changeStatus(Connection conn, Integer status, Integer id, String remark) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_user_star SET status = ? ");
		List<Object> params = new ArrayList();
		params.add(status);
		try {
			if(!StringUtils.isBlank(remark)) {
				sql.append(", remark = ? ");
				params.add(remark);
			}
			sql.append("WHERE id = ? ");
			params.add(id);
			
			if(conn != null) {
				return update(conn, sql.toString(), params.toArray());
			}else{
				return update(sql.toString(), params.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean changeStatus(Integer status, Integer id, String remark) {
		return changeStatus(null,status, id, remark);
	}
	
	/**
	 * 审核认证技能
	 * @param entity
	 * @param map
	 */
	public void updateStatus(UserStarViewEntity entity, Map<String, Object> map) {
		try {
			if (changeStatus(entity.getStatus(), entity.getId(), entity.getRemark())) {
				if(ManagerConstant.USER_STAR_STATUS_1 == entity.getStatus()) {
					// 审核通过
					JdbcUtils.start();
					Connection conn = JdbcUtils.getConnection();
					
					try {
						// 增加经验
						ExpInfo expInfo = ThingsAdder.技能认证.getExpInfo();
						UserViewEntity userView = us.findById(entity.getUserId());
						boolean userIsUp = false;
						if(userView != null) {
							// 判断是否需要升级
							//userIsUp = us.valiAddLevel(userView.getExp(), expInfo.getExp(), userView.getLevel());
						}
						
						us.upExp(conn, entity.getUserId(), expInfo.getExp(), userIsUp);
						es.add(conn, entity.getUserId(), expInfo.getExp(), expInfo.getRemark());
						
						// 增加邻豆
						LindouInfo lindouInfo = ThingsAdder.技能认证.getLindouInfo();
						
						us.upLindou(conn, entity.getUserId(), lindouInfo.getLindou());
						ls.add(conn, entity.getUserId(), lindouInfo.getLindou(), lindouInfo.getRemark());
						
						// 推送消息
						ns.pushUserValiStarSuccess(entity.getUserId(), expInfo.getExp(), lindouInfo.getLindou());
						
						JdbcUtils.commit();

						JsonResult.toJson(map, true, "操作成功！");
						return;
					} catch (Exception e) {
						e.printStackTrace();
						JsonResult.toJson(map, false, "操作失败！");
		            	JdbcUtils.roleback();
		            } finally {
		             	JdbcUtils.close();
	              	}

					JsonResult.toJson(map, true, "操作失败了！");
				}else if(ManagerConstant.USER_STAR_STATUS_2 == entity.getStatus()) {
					// 审核不通过
					ns.pushUserValiStarFailed(entity.getUserId(), entity.getRemark());
					JsonResult.toJson(map, true, "操作成功！");
				}
			} else {
				JsonResult.toJson(map, false, "操作失败了！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败了！");
		}
	}
}
