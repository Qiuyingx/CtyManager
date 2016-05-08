package com.yard.manager.modules.content.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.content.entity.TopBannerViewEntity;
import com.yard.manager.modules.content.entity.query.ContentQueryEntity;

/**
 * 推荐Banner管理（专题，资讯，课程推荐）
 * @author : xiaym
 * @date : 2015年9月10日 下午2:42:32
 * @version : 1.0
 */
public class TopBannerService extends BaseService<TopBannerViewEntity> {
	private static final TopBannerService tbs = new TopBannerService();
	public static TopBannerService getInstance() {
		return tbs;
	}
	
	/**
	 * 判断是否已经推荐
	 * @param contentId 文章ID
	 * @param dtype 文章类型
	 * @return
	 */
	public boolean isExits(Integer contentId, Integer dtype) {
		String sql = "SELECT * FROM t_top_banner WHERE contentId = ? AND dtype = ? ";
		try {
			List<TopBannerViewEntity> list = query(sql, contentId, dtype);
			return (list != null && list.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否已经超出数量限制（最多6个）
	 * @return
	 */
	public long isLimit() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM t_top_banner ");

		try {
			return (Long) certain(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 7;
	}
	
	/**
	 * 添加推荐记录
	 * @param contentId 文章ID
	 * @param dtype 文章类型
	 * @return
	 */
	public boolean add(Integer contentId, Integer dtype) {
		String sql = "INSERT INTO t_top_banner(contentId, dtype, create_time) VALUES(?, ?, ?);";
		try {
			return update(sql, contentId, dtype, System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 取消推荐，删除推荐记录
	 * @param contentId 文章ID
	 * @param dtype 文章类型
	 * @return
	 */
	public boolean del(Integer contentId, Integer dtype) {
		String sql = "DELETE FROM t_top_banner WHERE contentId = ? AND dtype = ?;";
		try {
			return update(sql, contentId, dtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 新增Banner推荐
	 * @param contentId 文章ID
	 * @param dtype 文章类型
	 * @param map
	 */
	public void addTop(Integer contentId, Integer dtype, Map<String, Object> map) {
		try {
			// 判断是否已经推荐
			if(isExits(contentId, dtype)) {
				JsonResult.toJson(map, false, "已被推荐，不能重复操作！");
				return;
			}
			// 判断是否已经超出数量限制
			if(isLimit() >= 6) {
				JsonResult.toJson(map, false, "首页banner推荐最多允许6个，请下架其它推荐之后再做操作！");
				return;
			}
			// 添加推荐记录
			if(add(contentId, dtype)) {
				JsonResult.toJson(map, true, "推荐成功！");
			}else{
				JsonResult.toJson(map, false, "推荐失败了！");
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
		}
	}
	
	/**
	 * 取消Banner推荐
	 * @param contntId 文章ID
	 * @param dtype 文章类型
	 * @param map
	 */
	public void delTop(Integer contentId, Integer dtype, Map<String, Object> map) {
		try {
			// 判断是否已经推荐
			if(!isExits(contentId, dtype)) {
				JsonResult.toJson(map, false, "还未被推荐，不能执行操作！");
				return;
			}
			// 删除推荐记录
			if(del(contentId, dtype)){
				JsonResult.toJson(map, true, "操作成功！");
			}else{
				JsonResult.toJson(map, false, "操作失败了！");
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.toJson(map, false, "出现异常，操作失败！");
		}
	}
}
