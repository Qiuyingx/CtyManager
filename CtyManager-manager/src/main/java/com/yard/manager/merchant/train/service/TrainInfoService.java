package com.yard.manager.merchant.train.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.entity.TrainInfoViewEntity;
import com.yard.manager.merchant.train.entity.query.TrainInfoQueryEntity;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 用户店铺信息完善
 * 
 * @author : xiaym
 * @date : 2015年8月13日 下午4:10:54
 * @version : 1.0
 */
public class TrainInfoService extends BaseService<TrainInfoViewEntity> {
	private static final TrainInfoService tis = new TrainInfoService();

	public static TrainInfoService getInstance() {
		return tis;
	}

	/**
	 * 分页查询开店申请
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<TrainInfoViewEntity> queryList(long page, long rows, TrainInfoQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.title, a.logo, a.description, a.banner_img bannerImg, a.views, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.category , a.categoryName, a.create_time createTime, ");
		sql.append("a.status, b.nickname nickName,a.bankname,a.bankcardNo,a.accountType,a.bankusername,a.openbankname,a.period FROM t_train_info a, t_user b ");
		sql.append("WHERE a.userId = b.id ");
		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);
		
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
	 * 专题相关学堂推荐列表
	 * @param page
	 * @param rows
	 * @param contentId 专题ID
	 * @return
	 */
	public List<TrainInfoViewEntity> queryLinkList(long page, long rows, Integer contentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.title, a.logo, a.description, a.banner_img bannerImg, a.views, c.create_time getTime, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.category , a.categoryName, a.create_time createTime, ");
		sql.append("a.status, b.nickname nickName FROM t_train_info a, t_user b, t_top_content c ");
		sql.append("WHERE a.userId = b.id AND a.status != 0 AND a.id = c.trainId AND c.contentId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(contentId);

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
	public long queryCount(TrainInfoQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_train_info a, t_user b WHERE a.userId = b.id ");

		List<Object> params = new ArrayList<Object>();
		
		try {
			queryParams(query, sql, params);
			
			return (Long) certain(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 专题相关学堂推荐
	 * @param contentId 专题ID
	 * @return
	 */
	public long queryLinkCount(Integer contentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_train_info a, t_user b, t_top_content c WHERE a.userId = b.id AND a.status != 0 AND a.id = c.trainId AND c.contentId = ? ");

		try {
			return (Long) certain(sql.toString(), contentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 开店申请通过，创建店铺 (此时资料未完善)
	 */
	public boolean add(Connection conn, TrainInfoViewEntity entity) {
		String sql = "INSERT INTO t_train_info(userId, managerId, status) VALUES(?, ?, ?);";
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getUserId());
			params.add(entity.getManagerId());
			params.add(0);

			if (conn != null) {
				return update(conn, sql, params.toArray());
			} else {
				return update(sql, params.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据ID查找用户店铺信息
	 * 
	 * @param managerId
	 *            用户后台账号ID
	 * @return
	 */
	public TrainInfoViewEntity findById() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.title, a.logo, a.description, a.content, a.banner_img bannerImg, a.views, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.classType, a.category, a.categoryName, a.create_time createTime FROM t_train_info a WHERE a.managerId = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(ShiroUtil.getUserId());

			return show(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TrainInfoViewEntity getById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.title, a.logo, a.description, a.content, a.banner_img bannerImg, a.views, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.category, a.categoryName, a.create_time createTime FROM t_train_info a WHERE a.id = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(id);

			return show(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据ID查找用户店铺信息
	 * 
	 * @param userId 商户账号ID
	 * @return
	 */
	public List<TrainInfoViewEntity> findByUserId(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.title, a.logo, a.description, a.content, a.banner_img bannerImg, a.views, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.classType, a.category, a.categoryName, a.create_time createTime FROM t_train_info a WHERE a.userId = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(userId);

			return query(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 启用、禁用店铺
	 * @param status 店铺状态
	 * @param id 店铺ID
	 */
	public boolean changeStatus(Connection conn, Integer status, Integer id) {
		String sql = "UPDATE t_train_info SET status = ? WHERE id = ?";
		try {
			if(conn != null) {
				return update(conn, sql, status, id);
			}else{
				return update(sql, status, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean changeStatus(Integer status, Integer id) {
		return changeStatus(null,status, id);
	}
	
	public void queryParams(TrainInfoQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 学堂标题筛选
			if(!StringUtils.isBlank(query.getTitle())) {
				sql.append("AND a.title like ? ");
				params.add("%"+query.getTitle()+"%");
			}
			// 状态
			if(query.getStatus() != null) {
				sql.append("AND a.status = ? ");
				params.add(query.getStatus());
			}
		}
	}
	
	/**
	 * 店铺管理 启用、禁用
	 * @param entity
	 * @param map
	 */
	public void updateStatus(TrainInfoViewEntity entity, Map<String, Object> map) {
		try {
			if (changeStatus(entity.getStatus(), entity.getId())) {
				if(entity.getStatus() == ManagerConstant.STATUS_TRAIN_INFO_UP) {
					// 开通
					//ns.pushTrainOpen(entity.getUserId());
				} else if(entity.getStatus() == ManagerConstant.STATUS_TRAIN_INFO_DOWN) {
					// 禁用
					//ns.pushTrainDown(entity.getUserId());
				}
				JsonResult.toJson(map, true, "操作成功！");
			} else {
				JsonResult.toJson(map, false, "操作失败了！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败了！");
		}
	}
}
