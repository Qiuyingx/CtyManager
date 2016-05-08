package com.yard.manager.modules.meta.service;

import java.util.List;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.meta.entity.CourtyardViewEntity;

/**
 * 院子管理
 * @author : xiaym
 * @date : 2015年7月9日 上午11:45:58
 * @version : 1.0
 */
public class CourtyardService extends BaseService<CourtyardViewEntity> {
	private static final CourtyardService cts = new CourtyardService();
	public CourtyardService() {
		
	}
	public static CourtyardService getInstance(){
		return cts;
	}
	
	/**
	 * 查询单个城市下所有院子
	 * 
	 * @param cityId
	 * @return
	 */
	public List<CourtyardViewEntity> findByCityId(Integer cityId) {
		String sql = "SELECT id, city_id cityId, courtyard_name courtyardName FROM t_courtyard WHERE city_id = ? ";
		try{
			return query(sql, cityId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询单个城市下所有院子
	 * 
	 * @param cityId
	 * @return
	 */
	public List<CourtyardViewEntity> findByCityIds(String cityIds) {
		String sql = "SELECT id, city_id cityId, courtyard_name courtyardName FROM t_courtyard WHERE city_id IN (?) ";
		try{
			return query(sql, cityIds);
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
