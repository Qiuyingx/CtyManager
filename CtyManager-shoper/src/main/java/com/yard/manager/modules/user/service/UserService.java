package com.yard.manager.modules.user.service;

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
import com.yard.manager.modules.detail.service.LindouDetailService;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.user.entity.UserPush;
import com.yard.manager.modules.user.entity.UserSetting;
import com.yard.manager.modules.user.entity.UserViewEntity;
import com.yard.manager.modules.user.entity.query.UserQueryEntity;
import com.yard.manager.modules.util.JdbcUtils;
import com.yard.manager.platform.config.LevelUpConfig;

/**
 * 用户基础信息
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class UserService extends BaseService<UserViewEntity>{
	private static final UserService us = new UserService();
	private static final LindouDetailService lindouDetail = LindouDetailService.getInstance();
	private static final NoticeService ns = NoticeService.getInstance();
	public UserService() {
		
	}
	public static UserService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @params page 页号
	 * @params rows 页数
	 * @params courtyardIds 管理员所管理的社区
	 */
	public List<UserViewEntity> queryUserList(UserQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.tel, a.nickname nickName, a.birthday, a.careerId, a.courtyard_id courtyardId, b.courtyard_name courtyardName, IFNULL(e.validate_status, 4) yardsValiStatus, ");
		sql.append("e.validate_type validateType, e.append, e.create_time valiTime, f.courtyard_name valiYards, e.remark valiRemark, e.id valiId, ");
		sql.append("a.register_time registerTime, a.gender, a.head_icon headIcon, a.inviteCode, a.last_login_time lastLoginTime, a.platform, a.banningTime, a.exp, a.level, c.amount ");
		sql.append("FROM t_user a LEFT JOIN t_courtyard b ON a.courtyard_id = b.id LEFT JOIN t_validate_user e ON a.id = e.user_id LEFT JOIN t_courtyard f ON f.id = e.courtyard_id , t_user_lindou c WHERE  a.id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}		

		queryParams(query, sql, params);
		
		// 分页排序
		sql.append(" ORDER BY a.register_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		
		try {
			return query(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserViewEntity> queryUserList(UserQueryEntity query) {
		return queryUserList(query, 1, 100000, null);
	}
	
	/**
	 * 数据统计
	 * @param courtyardIds 管理员所管理的院子IDs
	 * @param courtyardIds
	 * @return
	 */
	public long queryUserCount(UserQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user a LEFT JOIN t_courtyard b ON a.courtyard_id = b.id , t_user_lindou c WHERE  a.id = c.id ");
		
		List<Object> params = new ArrayList<Object>();
		
		// 所管理的社区
		if(!StringUtils.isEmpty(courtyardIds) && !courtyardIds.equals("0")) {
			sql.append("AND b.id IN ("+courtyardIds+") ");
		}	
		
		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void queryParams(UserQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query == null) {
			return;
		}
		// 昵称
		if(!StringUtils.isBlank(query.getNickName())) {
			sql.append("AND a.nickname like ? ");
			params.add("%"+query.getNickName()+"%");
		}
		// 电话
		if(!StringUtils.isBlank(query.getTel())) {
			sql.append("AND a.tel = ? ");
			params.add(query.getTel());
		}
		// 职业
		if(query.getCareerId() != null) {
			sql.append("AND a.careerId = ? ");
			params.add(query.getCareerId());
		}
		// 所属院子
		if(!StringUtils.isBlank(query.getCourtyardId())) {
			sql.append("AND a.courtyard_id IN ("+query.getCourtyardId()+") ");
		}
		// 性别
		if(query.getGender() != null) {
			sql.append("AND a.gender = ? ");
			params.add(query.getGender());
		}
		// 注册平台
		if(query.getPlatform() != null) {
			sql.append("AND a.platform = ? ");
			params.add(query.getPlatform());
		}
		
		// 发帖时间筛选
		if (!StringUtils.isBlank(query.getStartTime())) {
			sql.append("AND a.register_time >= ? ");
			params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
		}

		if (!StringUtils.isBlank(query.getEndTime())) {
			sql.append("AND a.register_time <= ? ");
			params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
		}
		
		// 邀请码
		if(!StringUtils.isBlank(query.getInviteCode())) {
			sql.append("AND a.inviteCode = ? ");
			params.add(query.getInviteCode());
		}
		
		// 最后登录时间
		if (!StringUtils.isBlank(query.getLastLoginStartTime())) {
			sql.append("AND a.last_login_time >= ? ");
			params.add(DateUtil.getMillisTime(query.getLastLoginStartTime()+ " 00:00:00"));
		}

		if (!StringUtils.isBlank(query.getLastLoginEndTime())) {
			sql.append("AND a.last_login_time <= ? ");
			params.add(DateUtil.getMillisTime(query.getLastLoginEndTime()+ " 23:59:59"));
		}
		
		// 用户ID（邀请人验证码）
		if(query.getUserId() != null) {
			sql.append("AND a.id = ? ");
			params.add(query.getUserId());
		}
	}
	
	/**
	 * 根据所属院子IDs获取ios用户
	 * @param courtyardIds 院子IDs 0 表示推送所有IOS用户
	 */
	public List<UserPush> getIosPushUser(String courtyardIds) {
		if(!StringUtils.isBlank(courtyardIds)) {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id, last_login_token lastToken, last_login_platform lastPlatform FROM t_user WHERE last_login_token is not null AND last_login_token != '' AND last_login_platform = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(ManagerConstant.DEVICE_IOS);
			
			if(!courtyardIds.equals("0")) {
				sql.append("AND courtyard_id IN ("+ courtyardIds +")");
			}
			
			try{
				return query(UserPush.class, sql.toString(), params.toArray());
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	/**
	 * 根据用户ID 获取用户UserPush
	 */
	public UserPush getPushUserById(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, last_login_token lastToken, last_login_platform lastPlatform, courtyard_id courtyardId FROM t_user WHERE id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		try{
			return show(UserPush.class, sql.toString(), params.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据用户ID，获取用户信息
	 * @param userId
	 * @return
	 */
	public UserViewEntity findById(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, last_login_token lastToken, last_login_platform lastPlatform, courtyard_id courtyardId, exp, level FROM t_user WHERE id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		try{
			return show(sql.toString(), params.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 增加经验
	 * @param userId 用户ID
	 * @param exp 变更经验 
	 * @param conn 数据库连接，非空支持事务
	 */
	public boolean upExp(Connection conn, Integer userId, Integer exp, boolean isUpLevel) {
		if(userId == null || exp == null || exp == 0) {
			return false;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_user SET exp = (exp + ?) ");
		if(isUpLevel) {
			sql.append(", level = (level + 1) ");
		}
		sql.append("WHERE id = ? ");
		
		try{
			if(conn != null) {
				return update(conn, sql.toString(), exp, userId);
			}else{
				return update(sql.toString(), exp, userId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean upExp(Integer userId, Integer exp) {
		return upExp(null, userId, exp, false);
		
		
	}
	
	/**
	 * 增加邻豆
	 * @param userId 用户ID
	 * @param lindou 变更邻豆
	 * @param conn 数据库连接，非空支持事务
	 */
	public boolean upLindou(Connection conn, Integer userId, Integer lindou) {
		if(userId == null  || lindou == null || lindou == 0) {
			return false;
		}		
		String sql = "UPDATE t_user_lindou SET amount = (amount + ?) WHERE id = ?";
		
		try{
			if(conn != null) {
				return update(conn, sql, lindou, userId);
			}else{
				return update(sql, lindou, userId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean upLindou(Integer userId, Integer lindou) {
		return upLindou(null, userId, lindou);
	}
	
	/**
	 * 验证是否增加等级
	 * @param exp 用户当前经验
	 * @param addExp 增加经验
	 * @param level 用户当前等级
	 */
	public boolean valiAddLevel(Integer exp, Integer addExp, Integer level) {
		try{
			// 到达下一级所需要经验
			int nextExp = LevelUpConfig.getDatas().get(level+1).getExp();
			if((exp+addExp) >= nextExp) {
				return true;
			}
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 *  获取用户是否接受推送消息配置 
	 *  
	 * @param userId
	 * @return
	 */
	public UserSetting getUserSetting(Integer userId) {
		String sql = "SELECT * FROM t_user_setting WHERE id = ? ";
		
		try{
			return show(UserSetting.class, sql, userId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过用户昵称模糊查询用户信息
	 * @param nickName 用户昵称
	 * @return
	 */
	public List<UserViewEntity> getUserByNickName(String nickName) {
		String sql = "SELECT id, nickname nickName, head_icon headIcon FROM t_user WHERE nickname LIKE ? ";
		try {
			return query(sql, "%"+nickName+"%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置禁用时间（禁用账号）
	 * 
	 * @param userId 用户ID
	 * @param times 禁用截止时间
	 * @param map
	 */
	public void toDisabled(Integer userId, String times, Map<String, Object> map) {
		String sql = "UPDATE t_user SET banningTime = ? WHERE id = ? ";
		
		try{
			 if(update(sql, DateUtil.getMillisTime(times), userId)) {
				 JsonResult.toJson(map, true, "禁用成功！");
			 }else{
				 JsonResult.toJson(map, false, "禁用失败！");
			 }
			 return;
		}catch(Exception e) {
			 JsonResult.toJson(map, false, "出现异常，操作失败！");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 解除禁用 
	 * @param userId
	 * @param map
	 */
	public void cancelLimitTime(Integer userId, Map<String, Object> map) {
		String sql = "UPDATE t_user SET banningTime = ? WHERE id = ? ";
		
		try{
			 if(update(sql, 0, userId)) {
				 JsonResult.toJson(map, true, "禁用解除成功！");
			 }else{
				 JsonResult.toJson(map, false, "禁用解除失败！");
			 }
			 return;
		}catch(Exception e) {
			 JsonResult.toJson(map, false, "出现异常，操作失败！");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 后台赠送邻豆
	 * @param userId 用户ID
	 * @param lindou 赠送数量
	 * @param remark 备注
	 */
	public void adminToLindou(Integer userId, Integer lindou, String remark, Map<String, Object> map) {
		if(lindou == null || lindou.equals(0)) {
			JsonResult.toJson(map, false, "操作失败，请告诉我需要给用户赠送多少邻豆呢?");
			return;
		}
		JdbcUtils.start();
		Connection conn =  null;
		try{
			conn = JdbcUtils.getConnection();
			// 增加邻豆
			us.upLindou(conn, userId, lindou);
			
			// 记录邻豆赠送记录
			lindouDetail.add(conn, userId, lindou, "后台发放");
			
			// 新增消息通知
			ns.pushUserGetLindou(userId, remark);
			JsonResult.toJson(map, true, "邻豆赠送成功！");
			JdbcUtils.commit();
			
		}catch(Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "操作失败！");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
	}
}
