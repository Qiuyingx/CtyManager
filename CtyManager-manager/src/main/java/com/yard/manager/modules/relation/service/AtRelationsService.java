package com.yard.manager.modules.relation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.relation.entity.AtRelationsViewEntity;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 专题，活动资讯@关联
 * @author : xiaym
 * @date : 2015年8月9日 上午11:11:17
 * @version : 1.0
 */
public class AtRelationsService extends BaseService<AtRelationsViewEntity> {
	public static final AtRelationsService ars = new AtRelationsService();
	public static AtRelationsService getInstance() {
		return ars;
	}
	public AtRelationsService(){
		
	}
	
	/**
	 * 添加关联关系
	 * @param entity
	 * @return
	 */
	public boolean add(AtRelationsViewEntity entity) {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO t_at_relations(user_id, at_target_id, at_nickname, scene, append, create_time) VALUES(?, ?, ?, ?, ?, ?);");
			
			List<Object> params = new ArrayList<Object>();
			params.add(ShiroUtil.getUserId());
			params.add(entity.getAtTargetId());
			params.add(entity.getAtNickName());
			params.add(entity.getScene());
			params.add(entity.getAppend());
			params.add(new Date().getTime());

			return update(sql.toString(), params.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 判断关联关系是否存在
	 * @param entity
	 * @return false 不存在 true 存在
	 */
	@SuppressWarnings("rawtypes")
	public boolean isExist(AtRelationsViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM t_at_relations WHERE at_target_id = ? AND at_nickname = ? AND scene = ? AND append = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getAtTargetId());
		params.add(entity.getAtNickName());
		params.add(entity.getScene());
		params.add(entity.getAppend());
		
		try{
			List list = query(sql.toString(), params.toArray());
			if(list != null && list.size() > 0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取已经存在的关联关系
	 * @param entity
	 * @return
	 */
	public List<AtRelationsViewEntity> query(AtRelationsViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, user_id userId, at_target_id atTargetId, at_nickname atNickName, scene, append FROM t_at_relations WHERE scene = ? AND append = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getScene());
		params.add(entity.getAppend());
		
		try{
			return query(sql.toString(), params.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除关联关系
	 * @param entity
	 * @return
	 */
	public boolean del(AtRelationsViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM t_at_relations WHERE at_target_id = ? AND at_nickname = ? AND scene = ? AND append = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getAtTargetId());
		params.add(entity.getAtNickName());
		params.add(entity.getScene());
		params.add(entity.getAppend());
		
		try {
			return update(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 添加@关联关系
	 */
	public boolean addRelations(AtRelationsViewEntity entity) {
		// 判断是否已经添加过
		if(!isExist(entity)) {
			return add(entity);
		}
		return false;
	}
}
