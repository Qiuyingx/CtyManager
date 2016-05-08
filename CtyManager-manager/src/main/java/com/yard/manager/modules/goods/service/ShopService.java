package com.yard.manager.modules.goods.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.goods.entity.ShopViewEntity;
import com.yard.manager.modules.goods.entity.query.ShopQueryEntity;
import com.yard.manager.modules.util.JdbcUtils;

/**
 * 邻豆商城
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class ShopService extends BaseService<ShopViewEntity>{
	private static final ShopService us = new ShopService();
	public ShopService() {
		
	}
	public static ShopService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param courtyardIds 管理员所管理的社区
	 */
	public List<ShopViewEntity> queryList(ShopQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.goodsName, a.price, a.stockLimit, a.image, a.remark, a.status, a.listImage FROM t_shop a where 1=1 ");
		
		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);
		
		// 分页排序
		sql.append("ORDER BY a.id DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		
		try {
			return query(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 数据统计
	 * @param courtyardIds 管理员所管理的院子IDs
	 * @param courtyardIds
	 * @return
	 */
	public long queryCount(ShopQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_shop a where 1=1 ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(ShopQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			//商品名称搜索
			if(!StringUtils.isBlank(query.getGoodsName())) {
				sql.append("AND a.goodsName like ? ");
				params.add("%" + query.getGoodsName() + "%");
			}
			//商品状态搜索
			if(!StringUtils.isBlank(query.getStatus())) {
				sql.append("AND a.status = ? ");
				params.add(query.getStatus());
			}
		}
	}
	
	/**
	 * 添加商品
	 * @param entity 活动信息
	 * @param map
	 * @return  返回添加的商品ID
	 */
	public int add(Connection conn, ShopViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_shop(goodsName, price, stockLimit, listImage, image, remark, status, version) VALUES(?, ?, ?, ?, ?, ?, ?, 0);");
		try{
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getGoodsName());
			params.add(entity.getPrice()<0?0:entity.getPrice());
			params.add(entity.getStockLimit()<0?0:entity.getStockLimit());
			params.add(entity.getListImage());
			params.add(entity.getImage());
			params.add(entity.getRemark());
			params.add(entity.getStatus());
			if(conn != null) {
				return add(conn, sql.toString(), params.toArray());
			}else{
				return add(sql.toString(), params.toArray());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改商品
	 * @param entity 活动信息
	 * @param map
	 */
	public boolean update(Connection conn, ShopViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_shop SET goodsName = ?, price = ?, stockLimit = ?, listImage = ?, image = ?, remark = ?, status = ? WHERE id = ? ");
		
		try{
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getGoodsName());
			params.add(entity.getPrice()<0?0:entity.getPrice());
			params.add(entity.getStockLimit()<0?0:entity.getStockLimit());
			params.add(entity.getListImage());
			params.add(entity.getImage());
			params.add(entity.getRemark());
			params.add(entity.getStatus());
			params.add(entity.getId());

			if(conn != null) {
				return update(conn, sql.toString(), params.toArray());
			}else{
				return update(sql.toString(), params.toArray());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 在售/下架商品
	 */
	public void changeStatus(ShopQueryEntity goods, Map<String, Object> map) {
		String sql = "UPDATE t_shop SET status = abs(status - 1) WHERE id = ? ";

		try {
			if (updates(sql, goods.getId()) == 1) {
				JsonResult.toJson(map, true, "在售/下架商品成功！");
			} else {
				JsonResult.toJson(map, false, "在售/下架商品失败，数据库操作失败！");
			}
		} catch (Exception e) {
			JsonResult.toJson(map, false, "在售/下架商品失败，服务器繁忙请稍候再试！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加商品-院子对应关系
	 * @param courtyardId 0表示所有社区  社区ID
	 * @param goodsId 商品ID
	 * @return
	 */
	public boolean addViewCourt(Connection conn, Integer courtyardId, Integer goodsId) {
		try{
			String sql = "INSERT INTO t_shop_courtyard(courtyard_id, goods_id) VALUES(?, ?);";
			if(conn != null) {
				return update(conn, sql, courtyardId, goodsId);
			}else{
				return update(sql, courtyardId, goodsId);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean addViewCourt(Integer courtyardId, Integer goodsId) {
		return addViewCourt(null, courtyardId, goodsId);
	}
	
	/**
	 * 删除指定商品对应的社区关系
	 * @param goodsId 商品ID
	 * @return
	 */
	public boolean deleteViewCourt(Connection conn, Integer goodsId) {
		String sql = "DELETE FROM t_shop_courtyard WHERE goods_id = ? ";
		try{
			if(conn != null) {
				return update(conn, sql, goodsId);
			}else{
				return update(sql, goodsId);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean deleteViewCourt(Integer goodsId) {
		return deleteViewCourt(null, goodsId);
	}
	
	/**
	 * 删除商品信息
	 * @param conn
	 * @param goodsId 商品ID
	 * @return
	 */
	public boolean delete(Connection conn, Integer goodsId) {
		String sql = "DELETE FROM t_shop WHERE id = ? ";
		try{
			if(conn != null) {
				return update(conn, sql, goodsId);
			}else{
				return update(sql, goodsId);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean delete(Integer goodsId) {
		return delete(null, goodsId);
	}
	
	/**
	 * 添加商品（以及与院子对应关系）
	 */
	public void addGoods(ShopViewEntity entity, Map<String, Object> map) {
		JdbcUtils.start();
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			// 添加商品信息
			int goodsId = add(conn, entity);
			
			// 添加商品-院子对应关系
			addViewCourt(conn, 0, goodsId);
			
			JdbcUtils.commit();
			JsonResult.toJson(map, true, "商品添加成功!");
		} catch (Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "操作失败!");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
		return;
	}
	
	/**
	 * 修改商品（以及与原子对应关系）
	 */
	public void updateGoods(ShopViewEntity entity, Map<String, Object> map) {
		JdbcUtils.start();
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			// 删除与院子关联关系
			deleteViewCourt(entity.getId());
			
			// 添加与院子关联关系
			addViewCourt(conn, 0, entity.getId());
			
			// 修改商品信息
			update(conn, entity);
			
			JdbcUtils.commit();
			JsonResult.toJson(map, true, "商品修改成功!");
		} catch (Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "操作失败!");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
	}
	
	/**
	 * 删除商品
	 * @param entity
	 * @param map
	 */
	public void deleteGoods(ShopViewEntity entity, Map<String, Object> map) {
		JdbcUtils.start();
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			// 删除与院子关联关系
			deleteViewCourt(entity.getId());
			
			// 删除商品信息
			delete(conn, entity.getId());
			
			JdbcUtils.commit();
			JsonResult.toJson(map, true, "商品删除成功!");
			return;
		} catch(Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "出现异常，操作失败!");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
	}
	
	/**
	 * 通过ID查询商品信息
	 * @param id 专题ID
	 * @return
	 */
	public ShopViewEntity findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.goodsName, a.price, a.stockLimit, a.image, a.remark, a.status, a.listImage FROM t_shop a WHERE a.id = ? ");
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
			ShopViewEntity info = findById(id);
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
