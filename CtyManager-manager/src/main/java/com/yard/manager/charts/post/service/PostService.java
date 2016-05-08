package com.yard.manager.charts.post.service;

import java.util.Date;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.charts.pojo.BaseEntity;

/**
 * 和帖子相关的统计
 * @author : xiaym
 * @date : 2015年8月12日 上午11:19:22
 * @version : 1.0
 */
public class PostService extends BaseService<BaseEntity>{
	private static final PostService us = new PostService();
	public PostService() {
		
	}
	public static PostService getInstance() {
		return us;
	}
	
	/**
	 * 统计当天参与发帖人数
	 * @return
	 */
	public long countCurrPostUser() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(DISTINCT user_id) FROM t_post a WHERE a.create_time >= ?  and a.create_time <= ? ");
		try{
			long start = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 00:00:00");
			long end = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 23:59:59");
			return (Long)certain(sql.toString(), start, end);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 统计当天的发帖数量
	 * @return
	 */
	public long countCurrPost() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_post a WHERE a.create_time >= ?  and a.create_time <= ? ");
		try{
			long start = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 00:00:00");
			long end = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 23:59:59");
			return (Long)certain(sql.toString(), start, end);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 统计当天的感谢总数
	 * @return
	 */
	public long countCurrPostLaud() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_post_laud a WHERE a.create_time >= ?  and a.create_time <= ? ");
		try{
			long start = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 00:00:00");
			long end = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 23:59:59");
			return (Long)certain(sql.toString(), start, end);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 统计当天的评论总数
	 * @return
	 */
	public long countCurrPostReply() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_post_reply a WHERE a.create_time >= ?  and a.create_time <= ? ");
		try{
			long start = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 00:00:00");
			long end = DateUtil.getMillisTime(DateUtil.formatDate(new Date())+ " 23:59:59");
			return (Long)certain(sql.toString(), start, end);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
