package com.yard.manager.modules.detail.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.modules.detail.entity.LindouViewDetail;
import com.yard.manager.modules.detail.entity.query.LindouQueryDetail;

/**
 * 用户邻豆
 * @author : xiaym
 * @date : 2015年6月29日 下午3:47:20
 * @version : 1.0
 */
public class LindouDetailService extends BaseService<LindouViewDetail> {
	private final static LindouDetailService vus = new LindouDetailService();
	public LindouDetailService() {
		
	}
	public static LindouDetailService getInstance() {
		return vus;
	}
	
	/**
	 * 获取分页数据
	 * @param query 查询对象
	 * @param page 页号
	 * @param rows 页数
	 * @param userId 用户ID
	 * @return
	 */
	public List<LindouViewDetail> queryList(LindouQueryDetail query, long page, long rows, Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.user_id userId, a.changeAmount, a.remark, a.create_time createTime FROM t_lindou_detail a ");
		sql.append("WHERE a.user_id = ? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		queryParams(query, sql, params);
		
		// 分页排序
		sql.append(" ORDER BY a.create_time DESC LIMIT ?, ?");
		params.add((page - 1) * rows);
		params.add(rows);
		
		try{
			return query(sql.toString(), params.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 数据统计
	 * 
	 * @param query
	 * @param userId 用户ID
	 * @return
	 */
	public long queryCount(LindouQueryDetail query, Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_lindou_detail a WHERE a.user_id = ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		queryParams(query, sql, params);
		
		try {
			return (Long)certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParams(LindouQueryDetail query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			//备注
			if(!StringUtils.isBlank(query.getRemark())) {
				String queryStr = "";
				String[] rm = query.getRemark().split(",");
				for(String str : rm){
					queryStr += " a.remark = '"+ str + "' OR";
				}
				sql.append("AND ( "+queryStr.substring(0, queryStr.lastIndexOf("OR"))+" )");
			}
			//提交时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
			}
		}
	}
	
	/**
	 * 增加邻豆日志 (支持事务)
	 * @param userId 用户ID
	 * @param changeAmount 改变邻豆数量
	 * @param remark 标记
	 */
	public boolean add(Connection conn, Integer userId, Integer changeAmount, String remark) {
		String sql = "INSERT INTO t_lindou_detail(user_id, changeAmount, remark, create_time) VALUES(?, ?, ?, ?);";
		try{
			List<Object> params = new ArrayList<Object>();
			params.add(userId);
			params.add(changeAmount);
			params.add(remark);
			params.add(new Date().getTime());
			
			if(conn != null) {
				return update(conn, sql, params.toArray());
			}else{
				return update(sql, params.toArray());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 增加邻豆日志 （不支持事务）
	 * @param userId 用户ID
	 * @param changeAmount 改变邻豆数量
	 * @param remark 标记
	 * @return
	 */
	public boolean add(Integer userId, Integer changeAmount, String remark) {
		return add(null, userId, changeAmount, remark);
	}
}
