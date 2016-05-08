package com.yard.manager.charts.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.charts.post.service.PostService;
import com.yard.manager.charts.user.entity.BaseCount;
import com.yard.manager.charts.user.entity.UserDataCount;

/**
 * 用户表统计
 * @author : xiaym
 * @date : 2015年8月12日 上午11:19:22
 * @version : 1.0
 */
public class UserService extends BaseService<UserDataCount>{
	private static final UserService us = new UserService();
	private static final PostService ps = PostService.getInstance();
	public UserService() {
		
	}
	public static UserService getInstance() {
		return us;
	}
	
	/**
	 * 获取每个社区的注册量
	 * @return
	 */
	public List<UserDataCount> countUserArea() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IFNULL(b.courtyard_name,\"未选择社区\") name, COUNT(*) value FROM t_user a LEFT JOIN  ");
		sql.append("t_courtyard b ON a.courtyard_id = b.id GROUP BY a.courtyard_id");
		
		try{
			return query(sql.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 统计当天注册总数
	 * @return
	 */
	public long countCurrTotalUser() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user a WHERE a.register_time >= ?  and a.register_time <= ? ");
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
	 * 统计当天登录用户数量
	 * @return
	 */
	public long countCurrLoginUser() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_user a WHERE a.last_login_time >= ?  and a.last_login_time <= ? ");
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
	 * 展示每个社区的注册总量
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void showCountUserArea(Map<String, Object> maps) {
		List<UserDataCount> listCount = countUserArea();
		
		List menuItem = new ArrayList();
		List dataItem = new ArrayList();
		
		if(listCount != null) {
			for(UserDataCount data : listCount) {

				menuItem.add(data.getName());
				dataItem.add(data);
			}
		}
		
		maps.put("menuItem", menuItem);
		maps.put("dataItem", dataItem);
	}
	
	/**
	 * 展示当天的关键指标
	 */
	public void showCountCurrNumber(Map<String, Object> map) {
		BaseCount count = new BaseCount();
		count.setCurrPostCount(ps.countCurrPost());
		count.setCurrPostLaudCount(ps.countCurrPostLaud());
		count.setCurrPostReplyCount(ps.countCurrPostReply());
		count.setCurrPostUserCount(ps.countCurrPostUser());
		count.setCurrUserCount(us.countCurrTotalUser());
		count.setCurrLoginUserCount(us.countCurrLoginUser());
		
		map.put("counts", count);
	}
}
