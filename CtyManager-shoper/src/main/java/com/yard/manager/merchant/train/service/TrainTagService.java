package com.yard.manager.merchant.train.service;

import java.sql.Connection;

import com.yard.core.service.common.BaseService;
import com.yard.manager.merchant.train.entity.TrainTagViewEntity;

/**
 * 培训室标签管理
 * @author : xiaym
 * @date : 2015年9月7日 下午3:44:04
 * @version : 1.0
 */
public class TrainTagService extends BaseService<TrainTagViewEntity> {
	private static final TrainTagService tts = new TrainTagService();
	public static TrainTagService getInstance() {
		return tts;
	}
	
	/**
	 * 删除培训室对应的所有标签
	 * @param trainId 培训室ID
	 */
	public boolean delAllTag(Connection conn, Integer trainId) {
		String sql = "DELETE FROM t_train_tag WHERE trainId = ?";
		try {
			if(conn != null) {
				return update(conn, sql, trainId);
			}else{
				return update(sql, trainId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加培训室对应的所有标签
	 * @param conn
	 * @param trainId 培训室ID
	 * @param tagId 标签ID
	 * @return
	 */
	public Integer addAllTag(Connection conn, Integer trainId, Integer tagId) {
		String sql = "INSERT INTO t_train_tag(trainId, tagId) VALUES(?, ?);";
		try {
			if(conn != null) {
				return add(conn, sql, trainId, tagId);
			}else{
				return add(sql, trainId, tagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
