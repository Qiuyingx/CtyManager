package com.yard.manager.merchant.other.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.other.entity.TrainInfoEntity;
import com.yard.manager.merchant.other.entity.TrainInfoViewEntity;
import com.yard.manager.modules.util.JdbcUtils;

/**
 * 用户店铺信息完善
 * 
 * @author : xiaym
 * @date : 2015年8月13日 下午4:10:54
 * @version : 1.0
 */
public class TrainInfoService extends BaseService<TrainInfoViewEntity> {
	private static final TrainInfoService tis = new TrainInfoService();
	private static final TrainTagService tts = TrainTagService.getInstance();

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
	public List<TrainInfoViewEntity> queryList(long page, long rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.courtyardId, a.title, a.logo, a.description, a.banner_img bannerImg, a.views, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.category , a.categoryName, a.create_time createTime, ");
		sql.append("a.status, b.nickname nickName FROM t_train_info a, t_user b ");
		sql.append("WHERE a.userId = b.id AND a.status != 0 ");
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
		sql.append("SELECT count(*) FROM t_train_info a, t_user b WHERE a.userId = b.id AND a.status != 0 ");

		try {
			return (Long) certain(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 第一次登陆后台完善资料 （判断标准：createTime为空）
	 */
	public boolean complete(Connection conn, TrainInfoViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_train_info a SET a.cityId = ?, a.title = ?, a.logo = ?, a.description = ?, a.content = ?, a.banner_img = ?, a.businessHours = ?, ");
		sql.append("a.tel = ?, a.address = ?, a.latitude = ?, a.longitude = ?, a.create_time = ?, a.status = ?, a.category = ?, a.categoryName = ? WHERE a.managerId = ? ");
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getCityId());
			params.add(entity.getTitle());
			params.add(entity.getLogo());
			params.add(entity.getDescription());
			params.add(entity.getContent());
			params.add(entity.getBannerImg());
			params.add(entity.getBusinessHours());
			params.add(entity.getTel());
			params.add(entity.getAddress());
			params.add(entity.getLatitude());
			params.add(entity.getLongitude());
			params.add(System.currentTimeMillis());
			params.add(ManagerConstant.STATUS_TRAIN_INFO_UP);
			params.add(entity.getCategory());
			params.add(entity.getCategoryName());
			params.add(entity.getManagerId());

			if (conn != null) {
				return update(conn, sql.toString(), params.toArray());
			} else {
				return update(sql.toString(), params.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean complete(TrainInfoViewEntity entity) {
		return complete(null, entity);
	}

	/**
	 * 后期修改
	 * 
	 * @param conn
	 * @param entity
	 * @return
	 */
	public boolean update(Connection conn, TrainInfoViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_train_info a SET a.cityId = ?, a.title = ?, a.logo = ?, a.description = ?, a.content = ?, a.banner_img = ?, a.businessHours = ?, ");
		sql.append("a.tel = ?, a.address = ?, a.latitude = ?, a.longitude = ?, a.category = ?, a.categoryName = ?,a.bankname=?,a.bankcardNo=?,a.accountType=?,a.bankusername=?,a.openbankname=?,a.period=?  WHERE a.id = ? ");
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getCityId());
			params.add(entity.getTitle());
			params.add(entity.getLogo());
			params.add(entity.getDescription());
			params.add(entity.getContent());
			params.add(entity.getBannerImg());
			params.add(entity.getBusinessHours());
			params.add(entity.getTel());
			params.add(entity.getAddress());
			params.add(entity.getLatitude());
			params.add(entity.getLongitude());
			params.add(entity.getCategory());
			params.add(entity.getCategoryName());
			params.add(entity.getBankname());
			params.add(entity.getBankcardNo());
			params.add(entity.getAccountType());
			params.add(entity.getBankusername());
			params.add(entity.getOpenbankname());
			params.add(entity.getPeriod());
			params.add(entity.getId());

			if (conn != null) {
				return update(conn, sql.toString(), params.toArray());
			} else {
				return update(sql.toString(), params.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean update(TrainInfoViewEntity entity) {
		return update(null, entity);
	}

	/**
	 * 根据ID查找用户店铺信息
	 * 
	 * @param managerId
	 *            用户后台账号ID
	 * @return
	 */
	public TrainInfoViewEntity findById(Integer managerId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.cityId, a.userId, a.managerId, a.cityId, a.title, a.logo, a.description, a.content, a.banner_img bannerImg, a.views, a.status, ");
		sql.append("a.businessHours, a.tel, a.address, a.latitude, a.longitude, a.category, a.categoryName, a.create_time createTime FROM t_train_info a WHERE a.managerId = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(managerId);

			return show(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据学堂ID查询学堂信息
	 * @param trainId
	 * @return
	 */
	public TrainInfoEntity findTrainInfo(int trainId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM t_train_info a WHERE a.id = ? ");

		try {
			List<Object> params = new ArrayList<Object>();
			params.add(trainId);

			return show(TrainInfoEntity.class, sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 是否开通培训室
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
	
	/**
	 * 添加培训室标签处理
	 * @param trainId 培训室ID
	 * @param tagStr 标签字符串形式
	 */
	public void addTags(Connection conn, Integer trainId, String tagStr) {
		try {
			if(!StringUtils.isBlank(tagStr)) {
				String[] tags = tagStr.split(",");
				for(String tag : tags) {
					tts.addAllTag(conn, trainId, Integer.parseInt(tag));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 修改培训室信息
	 */
	public void updateHouseMessage(TrainInfoViewEntity entity, Map<String, Object> map) {
		try {
			JdbcUtils.start();
			Connection conn = JdbcUtils.getConnection();
			
			// 修改培训室资料
			update(conn, entity);
			
			// 删除培训室所有标签
			tts.delAllTag(conn, entity.getId());
			
			// 添加培训室标签
			addTags(conn, entity.getId(), entity.getCategory());
			
			JsonResult.toJson(map, true, "培训室资料修改成功！");
			JdbcUtils.commit();
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作失败了！");
			JdbcUtils.roleback();
		} finally{
			JdbcUtils.close();
		}
	}

	/**
	 * 第一次完善培训室信息
	 */
	public void completeHouseMessage(TrainInfoViewEntity entity, Map<String, Object> map) {
		try {
			JdbcUtils.start();
			Connection conn = JdbcUtils.getConnection();
			
			// 完善培训室资料
			complete(conn, entity);
			
			// 删除培训室所有标签
			tts.delAllTag(conn, entity.getId());
			
			// 添加培训室标签
			addTags(conn, entity.getId(), entity.getCategory());
			
			JdbcUtils.commit();

			TrainInfoViewEntity entitys = findById(entity.getManagerId());
			if(entitys != null && entitys.getId() != null) {
				JsonResult.toJson(map, true, entitys.getId()+"");
			}else{
				JsonResult.toJson(map, true, "培训室资料修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作失败了！");
			JdbcUtils.roleback();
		} finally{
			JdbcUtils.close();
		}
	}
	
}
