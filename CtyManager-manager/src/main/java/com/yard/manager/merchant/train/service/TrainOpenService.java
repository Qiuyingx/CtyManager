package com.yard.manager.merchant.train.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.entity.TrainInfoViewEntity;
import com.yard.manager.merchant.train.entity.TrainOpenViewEntity;
import com.yard.manager.merchant.train.service.thread.TrainOpenFailedThread;
import com.yard.manager.merchant.train.service.thread.TrainOpenSuccessThread;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.user.entity.UserViewEntity;
import com.yard.manager.modules.user.service.UserService;
import com.yard.manager.modules.util.JdbcUtils;
import com.yard.manager.system.service.SysUserService;

/**
 * 
 * @author : xiaym
 * @date : 2015年8月13日 下午4:10:54
 * @version : 1.0
 */
public class TrainOpenService extends BaseService<TrainOpenViewEntity> {
	private static final TrainOpenService tos = new TrainOpenService();
	private NoticeService ns = NoticeService.getInstance();
	private SysUserService sus = SysUserService.getInstance();
	private UserService us = UserService.getInstance();
	private TrainInfoService tis = TrainInfoService.getInstance();
	
	public static TrainOpenService getInstance() {
		return tos;
	}

	/**
	 * 分页查询开店申请
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<TrainOpenViewEntity> queryList(long page, long rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.userId, a.category, a.introduction, a.tel, a.email, a.passed, a.image_names imageNames, a.name, ");
		sql.append("a.create_time createTime, a.vali_time valiTime, a.remark, b.tel userName, a.source, ");
		sql.append("b.nickname nickName FROM t_train_open a LEFT JOIN t_user b ");
		sql.append("ON a.userId = b.id ");
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
	
	public List<TrainOpenViewEntity> findByUserId(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.userId, a.category, a.introduction, a.tel, a.email, a.passed, a.name, ");
		sql.append("a.create_time createTime, a.vali_time valiTime, a.remark, b.tel userName, a.source, ");
		sql.append("b.nickname nickName FROM t_train_open a LEFT JOIN t_user b ");
		sql.append("ON a.userId = b.id WHERE a.userId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		try {
			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分页数据统计
	 * @return
	 */
	public long queryCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_train_open a, t_user b ");
		sql.append("WHERE a.userId = b.id");
		
		try {
			return (Long)certain(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**创建申请记录*/
	public boolean createRecord(Connection conn, Integer userId, String tel, String email) {
		String sql = "INSERT INTO t_train_open(userId, tel, email, passed, source, create_time, vali_time) VALUES(?, ?, ?, ?, ?, ?, ?);";
		
		try {
			return update(conn, sql, userId, tel, email, 2, 3, System.currentTimeMillis(), System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加或修改审核备注
	 */
	public void addOrUpdateRemark(TrainOpenViewEntity entity, Map<String, Object> map) {
		String sql = "UPDATE t_train_open SET remark = ? WHERE id = ?";
		try {
			if(update(sql, entity.getRemark(), entity.getId())) {
				JsonResult.toJson(map, true, "操作成功！");
			}else{
				JsonResult.toJson(map, false, "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
		}
	}
	
	/**
	 * 变更状态（审核通过，审核不通过）
	 * @param conn
	 * @param entity
	 * @return
	 */
	public boolean update(Connection conn, TrainOpenViewEntity entity) {
		String sql = "UPDATE t_train_open SET passed = ?, vali_time = ? WHERE id = ?";
		try {
			if(conn != null) {
				return update(conn, sql, entity.getPassed(), System.currentTimeMillis(), entity.getId());
			}else{
				return update(sql, entity.getPassed(), System.currentTimeMillis(), entity.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean update(TrainOpenViewEntity entity){
		return update(null, entity);
	}
	
	public boolean bindUserId(Integer openId, Integer userId) {
		String sql = "UPDATE t_train_open SET userId = ? WHERE id = ?";
		try {
			return update(sql, userId, openId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/***
	 * 绑定商户账号
	 * @param openId 申请ID
	 * @param tel 商户账号
	 */
	public boolean bindName(Integer openId, String tel) {
		UserViewEntity user = us.findByTel(tel);
		if(user == null) {
			return false;
		}
		
		List<TrainOpenViewEntity> listopen = findByUserId(user.getId());
		if(listopen != null && listopen.size() > 0) {
			return false;
		}
		
		return bindUserId(openId, user.getId());
	}
	
	public Integer bindNameForId(Integer openId, String tel) {
		UserViewEntity user = us.findByTel(tel);
		if(user == null) {
			return -1;
		}
		
		List<TrainOpenViewEntity> listopen = findByUserId(user.getId());
		if(listopen != null && listopen.size() > 0) {
			return -1;
		}
		
		if(bindUserId(openId, user.getId())){
			return user.getId();
		}
		return -1;
	}
	
	/**
	 * 修改审核状态
	 * 所需参数：passed, id, userId, email, tel
	 */
	public void updateStatus(TrainOpenViewEntity entity, Map<String, Object> map) {
		boolean isSuccessNotice = false;
		boolean isFailedNotice = false;
		try {
			if(entity.getPassed() == 2) {
				JdbcUtils.start();
				Connection conn = JdbcUtils.getConnection();
				// 获取用户信息
				UserViewEntity user = us.findById(entity.getUserId());
				if(user == null) {
					JsonResult.toJson(map, false, "无法获取用户信息！");
					throw new RuntimeException();
				}
				// 修改状态
				update(conn, entity);
				// 创建后台账号
				Integer managerId = sus.createTrainAdmin(conn, entity.getEmail(), map);
				// 预留店铺位置
				TrainInfoViewEntity info = new TrainInfoViewEntity(entity.getUserId(), managerId);
				boolean oks = tis.add(conn, info);
				if(oks) {
					JdbcUtils.commit();
					// 发送通知 
					JsonResult.toJson(map, true, ""+managerId);
					isSuccessNotice = true;
				}else{
					JsonResult.toJson(map, true, "操作失败了！");
				}
			}else{
				isFailedNotice = update(entity);
				JsonResult.toJson(map, true, "操作成功！");
			}
		} catch (Exception e) {
			JdbcUtils.roleback();
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
		} finally {
			JdbcUtils.close();
		}

		/**
		 * 前期取消推送和短信邮件通知====
		 */
		/*if(isSuccessNotice) {
			successNotice(entity);
		}
		
		if(isFailedNotice) {
			failedNotice(entity);
		}*/
	}
	
	/**
	 * 审核通过通知消息
	 */
	private void successNotice(TrainOpenViewEntity entity) {
		new Thread(new TrainOpenSuccessThread(ns, entity)).run();
	}
	
	/**
	 * 审核不通过通知消息
	 */
	private void failedNotice(TrainOpenViewEntity entity) {
		new Thread(new TrainOpenFailedThread(ns, entity)).run();
	}
	
	/**
	 * 商户账号绑定入口
	 * @param openId
	 * @param tel
	 * @param map
	 */
	public void bindNameMap(Integer openId, String  tel, Map<String, Object> map) {
		if(openId == null || StringUtils.isBlank(tel)) {
			JsonResult.toJson(map, false, "参数错误，绑定失败！");
			return;
		}
		
		Integer userId = bindNameForId(openId, tel);
		if(userId != null && userId > 0) {
			JsonResult.toJson(map, true, userId+"");
		}else{
			JsonResult.toJson(map, false, "绑定失败了, 手机号无效或者手机号已被绑定！");
		}

		return;
	}
	
	/**后台商户入驻*/
	public void trainSubmit(TrainOpenViewEntity entity, Map<String, Object> map) {
		try {
			JdbcUtils.start();
			Connection conn = JdbcUtils.getConnection();
			Integer userId = null;

			// 判断是否需要自动创建商家账号    1：需要
			if(!StringUtils.isBlank(entity.getIsCreated()) && entity.getIsCreated().equals("1")) {
				// 判断是否电话是否被注册
				UserViewEntity uservali = us.findByTel(entity.getTel());
				if(uservali != null) {
					userId = uservali.getId();
				}else{
					UserViewEntity userMess = new UserViewEntity();
					userMess.setTel(entity.getTel());
					Integer userIdNew = us.createUser(conn, userMess);
					if(userIdNew == null && userIdNew <= 0) {
						JsonResult.toJson(map, false, "商户账号创建失败！");
						return;
					}
					userId = userIdNew;
				}
			}else{
				// 验证商家账号是否合法
				UserViewEntity user = us.findByTel(entity.getUserName());
				if(user == null) {
					JsonResult.toJson(map, false, "商户账号无效，操作失败！");
					return;
				}
				userId = user.getId();
			}

			
			List<TrainOpenViewEntity> listopen = findByUserId(userId);
			if(listopen != null && listopen.size() > 0) {
				JsonResult.toJson(map, false, "商户账号已被绑定，操作失败！");
				return;
			}
			
			// 创建入驻记录
			createRecord(conn, userId, entity.getTel(), entity.getEmail());
			// 创建商家账号
			Integer managerId = sus.createTrainAdmin(conn, entity.getEmail(), map);
			
			// 预留商家空间	
			TrainInfoViewEntity info = new TrainInfoViewEntity(userId, managerId);
			boolean oks = tis.add(conn, info);
					
			if(oks) {
				JdbcUtils.commit();
				JsonResult.toJson(map, true, ""+managerId);
			}else{
				JsonResult.toJson(map, false, "操作失败了！");
			}
		} catch (Exception e) {
			JdbcUtils.roleback();
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
		} finally {
			JdbcUtils.close();
		}
	}
}
