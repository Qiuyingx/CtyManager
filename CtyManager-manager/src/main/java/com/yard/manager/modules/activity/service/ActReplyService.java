package com.yard.manager.modules.activity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.activity.entity.ActReplyViewEntity;

/**
 * 活动评论
 * @author : xiaym
 * @date : 2015年6月22日 下午3:01:09
 * @version : 1.0
 */
public class ActReplyService extends BaseService<ActReplyViewEntity>{
	private static final ActReplyService us = new ActReplyService();
	public ActReplyService() {
		
	}
	public static ActReplyService getInstance() {
		return us;
	}
	
	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 * @param actId 活动Id
	 */
	public List<ActReplyViewEntity> queryList(long page, long rows, Integer actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.activity_id actId, a.courtyard_id courtyardId, a.replyer_id replyerId, ");
		sql.append("a.content, a.at_targetId targetId, a.reply_id replyId, a.create_time createTime, b.nickname rNickName, ");
		sql.append("c.nickname tNickName FROM  t_activity_reply a LEFT JOIN t_user b ON a.replyer_id = b.id LEFT JOIN t_user c ");
		sql.append("ON a.at_targetId = c.id WHERE a.activity_id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(actId);

		// 分页排序
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
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
	 * @param actId 活动Id
	 * @return
	 */
	public long queryCount(Integer actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_activity_reply a LEFT JOIN t_user b ON a.replyer_id = b.id LEFT JOIN t_user c ");
		sql.append("ON a.at_targetId = c.id WHERE a.activity_id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(actId);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除选中评论
	 * @param id 评论ID
	 */
	public void del(Integer id, Map<String, Object> map) {
		String sql = "DELETE FROM t_activity_reply WHERE id = ? ";
		
		try{
			if(update(sql, id)) {
				JsonResult.toJson(map, true, "评论删除成功！");
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "操作异常，评论删除失败！");
			return;
		}
		JsonResult.toJson(map, false, "评论删除失败！");
		return;
	}
}
