package com.yard.manager.modules.activity.service;

import java.sql.Connection;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;

/**
 * 活动-院子映射表
 * @author : xiaym
 * @date : 2015年7月2日 上午11:26:29
 * @version : 1.0
 */
public class ActOfYardServic extends BaseService<Object> {
	private static final ActOfYardServic aoy = new ActOfYardServic();
	public ActOfYardServic(){
		
	}
	public static ActOfYardServic getInstance() {
		return aoy;
	}
	
	/**
	 * 删除活动ID对应的映射数据
	 * @param actId 活动ID
	 * @return
	 */
	public boolean deleteAct(Connection conn, Integer actId) {
		String sql = "DELETE FROM t_couryard_of_activity WHERE activity_id = ? ";
		
		try{
			if(conn != null) {
				return update(conn, sql, actId);
			}else{
				return update(sql, actId);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean deleteAct(Integer actId) {
		return deleteAct(null, actId);
	}
	
	/**
	 * 添加活动-院子映射（单条数据添加）
	 * @param actId
	 * @param courtyardId
	 * @return
	 */
	public boolean add(Integer actId, Integer courtyardId) {
		String sql = "INSERT INTO t_couryard_of_activity(courtyard_id, activity_id) VALUES(?, ?)";
		
		try{
			return update(sql, courtyardId, actId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加面向所有社区关联关系
	 * @param actId 活动ID
	 * @return
	 */
	public boolean addFaceAll(Integer actId) {
		String sql = "INSERT INTO t_couryard_of_activity(courtyard_id, activity_id) VALUES(0, ?)";
		
		try{
			return update(sql, actId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 增加活动-院子 管理数据
	 * @param actId 活动ID
	 * @param courtyardIds 院子IDs 如：1,2
	 * @param isAllYard 是否面向所有社区  1（是）；0（否）
	 */
	public boolean addActOfYard(Integer actId, String courtyardIds, Integer isAllYard) {
		String[] yards = courtyardIds.split(",");
		try{
			if(isAllYard != null && isAllYard == 1) {
				addFaceAll(actId);
			}else{		
				if(StringUtils.isBlank(courtyardIds) || actId == null) {
					return false;
				}
				for(String str : yards) {
					add(actId, Integer.parseInt(str));
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
