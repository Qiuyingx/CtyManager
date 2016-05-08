package com.yard.manager.modules.activity.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.activity.entity.ActInfoViewEntity;
import com.yard.manager.modules.activity.entity.query.ActQueryEntity;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.modules.relation.entity.AtRelationsViewEntity;
import com.yard.manager.modules.relation.service.AtRelationsService;
import com.yard.manager.modules.util.JdbcUtils;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 活动基础信息
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class ActInfoService extends BaseService<ActInfoViewEntity>{
	private static final ActInfoService us = new ActInfoService();
	private static final ActOfYardServic aoy = ActOfYardServic.getInstance();
	private static final NoticeService ns = NoticeService.getInstance();
	private static final AtRelationsService ars = AtRelationsService.getInstance();
	
	public ActInfoService() {
		
	}
	public static ActInfoService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param courtyardIds 管理员所管理的社区
	 */
	public List<ActInfoViewEntity> queryList(ActQueryEntity query, long page, long rows, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.activity_title actTitle, a.startTime, a.endTime, a.views, a.description, ");
		sql.append("a.createTime, a.managerId, a.image, a.peoplesLimit countLimit, a.signDisable isDisable, ");
		sql.append("a.isAllYards, a.courtyardNames, a.cityId, a.courtyardIds, c.sysusername mNickName, c.sysuserno mUserName FROM t_activity_info a, ");
		sql.append("sys_user c WHERE a.managerId = c.sysuserid ");
		
		List<Object> params = new ArrayList<Object>();

		queryParams(query, sql, params);
		
		// 分页排序
		sql.append(" ORDER BY a.createTime DESC LIMIT ?, ?");
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
	public long queryCount(ActQueryEntity query, String courtyardIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_activity_info a, sys_user c WHERE a.managerId = c.sysuserid ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParams(query, sql, params);

		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(ActQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			//活动标题模糊筛选
			if(!StringUtils.isBlank(query.getActTitle())) {
				sql.append("AND a.activity_title like ? ");
				params.add("%" + query.getActTitle() + "%");
			}
			//所属院子筛选
			if(!StringUtils.isBlank(query.getCourtyardId())) {
				sql.append("AND a.courtyard_id IN ("+query.getCourtyardId()+") ");
			}
			// 发帖时间筛选
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.createTime >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.createTime <= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 23:59:59"));
			}
			
			//发布者昵称筛选
			if(!StringUtils.isBlank(query.getNickName())) {
				sql.append("AND c.nickname like ? ");
				params.add("%" + query.getNickName() + "%");
			}
			
			//是否可报名筛选
			if(query.getIsDisable() != null) {
				sql.append("AND a.signDisable = ? ");
				params.add(query.getIsDisable());
			}
		}
	}
	
	public void isPushAll(ActInfoViewEntity entity, Integer contentId) {
		if(entity != null && entity.getIsPushAll() != null 
				&& entity.getIsPushAll() == 1 && entity.getIsDisable() != null) {
			Integer isAllYards = entity.getIsAllYards();
			
			if(entity.getIsDisable() == 0) {
				// 0可报名==>>活动
				ns.pushAddAct(isAllYards == 1 ? 1 : 2, isAllYards == 1 ? "" : entity.getCityId().toString(), 
						isAllYards == 1 ? 1 : 2, isAllYards == 1 ? "" : entity.getCourtyardIds(), entity.getActTitle(), contentId);
			}else{
				// 1 不可报名==>活动新闻
				ns.pushAddNews(isAllYards == 1 ? 1 : 2, isAllYards == 1 ? "" : entity.getCityId().toString(), 
						isAllYards == 1 ? 1 : 2, isAllYards == 1 ? "" : entity.getCourtyardIds(), entity.getActTitle(), contentId);
			}
		}
	}
	
	/**
	 * 添加活动
	 * @param entity 活动信息
	 * @param map
	 */
	public void add(ActInfoViewEntity entity, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_activity_info(courtyard_id, activity_title, startTime, endTime, content, createTime, description, ");
		sql.append("managerId, image, peoplesLimit, signDisable, version, isAllYards, courtyardIds, cityId, courtyardNames) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?, ?);");
		try{
			List<Object> params = new ArrayList<Object>();
			params.add(entity.getCourtyardId());
			params.add(entity.getActTitle());
			params.add(entity.getStartTime());
			params.add(entity.getEndTime());
			params.add(entity.getContent());
			params.add(new Date().getTime());
			params.add(entity.getDescription());
			params.add(ShiroUtil.getUserId());
			params.add(entity.getImage());
			params.add(entity.getCountLimit()==null?0:entity.getCountLimit());
			params.add(entity.getIsDisable()!=null?(entity.getIsDisable()==0?false:true):true);
			params.add(entity.getIsAllYards());
			params.add(entity.getCourtyardIds());
			params.add(entity.getCityId());
			params.add(entity.getCourtyardNames());

			int id = add(sql.toString(), params.toArray());

			// 添加活动关联关系
			aoy.addActOfYard(id, entity.getCourtyardIds(), entity.getIsAllYards());
			
			// 是否群发推送
			isPushAll(entity, id);
			
			// 添加@关联关系
			addRelation(entity, id);

			JsonResult.toJson(map, true, "活动添加成功！");
		}catch(Exception e){
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作出现异常，添加失败！");
			return;
		}
		return;
	}
	
	/**
	 * 通过ID查询活动信息
	 * @param id 活动ID
	 * @return
	 */
	public ActInfoViewEntity findById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.courtyard_id courtyardId, a.activity_title actTitle, a.startTime, a.endTime, a.views, a.description, ");
		sql.append("a.content, a.createTime, a.managerId, a.image, a.peoplesLimit countLimit, a.signDisable isDisable, ");
		sql.append("a.isAllYards, a.courtyardNames, a.cityId, a.courtyardIds FROM t_activity_info a WHERE a.id = ? ");
		
		try{
			return show(sql.toString(), id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改活动
	 * @param entity 活动信息
	 * @param map
	 */
	public void update(ActInfoViewEntity entity, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_activity_info SET courtyard_id = ?, activity_title = ?, startTime = ?, endTime = ?, signDisable = ?, description = ?, ");
		sql.append("content = ?, image = ?, peoplesLimit = ?, isAllYards = ?, courtyardIds = ?, cityId = ?, courtyardNames = ? WHERE id = ? ");
		
		try{
			//删除活动关联关系
			aoy.deleteAct(entity.getId());
			//添加活动关联关系
			aoy.addActOfYard(entity.getId(), entity.getCourtyardIds(), entity.getIsAllYards());

			List<Object> params = new ArrayList<Object>();
			params.add(entity.getCourtyardId());
			params.add(entity.getActTitle());
			params.add(entity.getStartTime());
			params.add(entity.getEndTime());
			params.add(entity.getIsDisable()!=null?(entity.getIsDisable()==0?false:true):true);
			params.add(entity.getDescription());
			params.add(entity.getContent());
			params.add(entity.getImage());
			params.add(entity.getCountLimit()==null?0:entity.getCountLimit());
			params.add(entity.getIsAllYards());
			params.add(entity.getCourtyardIds());
			params.add(entity.getCityId());
			params.add(entity.getCourtyardNames());
			params.add(entity.getId());

			// 是否群发推送
			//isPushAll(entity, entity.getId());
			
			if(updates(sql.toString(), params.toArray()) != 1) {
				JsonResult.toJson(map, false, "活动编辑失败！");
			}else{
				JsonResult.toJson(map, true, "活动编辑成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作出现异常，添加失败！");
			return;
		}
		return;
	}
	
	/**
	 * 根据活动ID删除活动信息
	 * @param actId
	 * @return
	 */
	public boolean delete(Connection conn, Integer actId) {
		String sql = "DELETE FROM t_activity_info WHERE id = ? ";
		try{
			if(conn != null) {
				return update(conn, sql, actId);
			}else{
				return update(sql, actId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除活动入口
	 * @param entity
	 * @param map
	 */
	public void deleteAct(ActInfoViewEntity entity, Map<String, Object> map) {
		try{
			JdbcUtils.start();
			Connection conn = JdbcUtils.getConnection();
			
			// 删除活动-院子关联关系
			aoy.deleteAct(conn, entity.getId());
			
			// 删除活动信息
			us.delete(conn, entity.getId());
			
			JdbcUtils.commit();
			JsonResult.toJson(map, true, "操作成功！");
		}catch(Exception e) {
			JdbcUtils.roleback();
			JsonResult.toJson(map, false, "操作失败！");
			e.printStackTrace();
		} finally {
			JdbcUtils.close();
		}
	}

	/**
	 * 通过活动ID获取活动内容并返回
	 * @param id
	 * @param map
	 */
	public void getActContent(Integer id, Map<String, Object> map) {
		try{
			String content = findById(id).getContent();
			map.put("content", content);
			JsonResult.toJson(map, true, "获取成功！");
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "无法获取活动信息！");
		return;
	}
	
	/**
	 * 根据ID 获取内容详情
	 * @param id
	 * @param map
	 */
	public void getContentInfo(Integer id, Map<String, Object> map) {
		try{
			ActInfoViewEntity info = findById(id);
			map.put("info", info);
			JsonResult.toJson(map, true, "获取成功！");
			return;
		}catch(Exception e) {
			e.printStackTrace();
		}
		JsonResult.toJson(map, false, "出现异常，无法内容！");
		return;
	}
	
	/**
	 * 添加@对象关联关系
	 * @param entity 
	 * @param actId 活动Id
	 */
	public void addRelation(ActInfoViewEntity actInfo, Integer actId) {
		String atTarget = actInfo.getAtTarget();
		if(StringUtils.isBlank(atTarget)) {
			return;
		}
		String[] atList = atTarget.split(";");
		for(String atItem : atList) {
			String[] item = atItem.split("\\|");
			if(item != null && item.length>0) {
				AtRelationsViewEntity entity = new AtRelationsViewEntity(Integer.parseInt(item[0]), item[1], ManagerConstant.CONTENT_TYPE_4, actId);
				ars.addRelations(entity);
			}
		}
	}
}
