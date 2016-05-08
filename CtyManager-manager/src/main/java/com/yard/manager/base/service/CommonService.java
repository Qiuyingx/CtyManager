package com.yard.manager.base.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.base.entity.ComboEntity;
import com.yard.manager.platform.entity.User;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 公共请求操作类
 * 
 * @author jiangbo
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonService extends BaseService {

	/**
	 * 用户可操作的栏目
	 * 
	 * @param wxConfigId
	 * @return
	 */
	public List<ComboEntity> getChannelCombo(String wxConfigId) {
		try {
			String sql = "SELECT CHANNELID ID, CHANNELNAME TEXT FROM WX_CHANNEL WHERE WXCONFIGID = ? AND STATUS = ? ";

			List<Object> params = new ArrayList<Object>();
			params.add(wxConfigId);
			params.add(ManagerConstant.ENABLED);

			return query(ComboEntity.class, sql, params.toArray());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 用户组下拉列表
	 * @param wxConfigId
	 * @return
	 */
	public List<ComboEntity> getSysGroupCombo(User user) {
		try {
			List<Object> params = new ArrayList<Object>();
			
			String sql = "SELECT SYSGROUPID ID, SYSGROUPNAME TEXT FROM SYS_GROUP WHERE STATUS = ? ";
			params.add(ManagerConstant.ENABLED);
			
			if (!user.getSysUserNo().equals(ManagerConstant.SYSTEM_USER) && !user.getSysUserNo().equals(ManagerConstant.SYSTEM_SUPPER)) {
				// 非内置账户选择自己公众号的
				sql += "AND sysgroupid != ? AND sysgroupid != ? AND sysgroupid != ? AND sysgroupid != ?";
				params.add(ManagerConstant.GROUP_AREA);
				params.add(ManagerConstant.GROUP_SUPPER);
				params.add(ManagerConstant.GROUP_TEST);
				params.add(ManagerConstant.GROUP_INNER);
			}
			
			return query(ComboEntity.class, sql, params.toArray());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过channelId获取所有文章列表
	 * @param channelId
	 * @return
	 */
	public List<ComboEntity> getContentCombo(String channelId) {
		try {
			List<Object> params = new ArrayList<Object>();
			
			String sql = "SELECT CONTENTID ID, TITLE TEXT FROM WX_CONTENT WHERE STATUS = ? AND WXCONFIGID = ? ";
			
			params.add(ManagerConstant.ENABLED);
			params.add(ShiroUtil.getWxConfigId());
			
			if (null != channelId) {
				sql += "AND CHANNELID = ? ";
				params.add(channelId);
			}
			sql += "ORDER BY CREATETIME DESC";
			
			return query(ComboEntity.class, sql, params.toArray());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 查询所有的院子列表
	 * @param cityId 城市ID
	 * @return
	 */
	public List<ComboEntity> getYardCombo(Integer cityId) {
		String courtyardIds = ShiroUtil.getUserYards();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.id, a.courtyard_name TEXT FROM t_courtyard a WHERE 1= 1 ");
			List<Object> params = new ArrayList<Object>();
			
			if(cityId != null) {
				sql.append("AND a.city_id = ? ");
				params.add(cityId);
			}	
			User user = ShiroUtil.getUser();
			String userNo = user.getSysUserNo();
			// 所管理的社区
			if(!userNo.equals(ManagerConstant.SYSTEM_USER) && !userNo.equals(ManagerConstant.SYSTEM_SUPPER) 
					&& user.getYardids() != null && !user.getYardids().equals("0")) {
				sql.append("AND a.id IN ("+courtyardIds+") ");
			}		
			
			sql.append("ORDER BY id ASC");
			return query(ComboEntity.class, sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ComboEntity> getYards() {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.id, a.courtyard_name TEXT FROM t_courtyard a ");
			
			sql.append("ORDER BY id ASC");
			System.out.println("==============??"+query(ComboEntity.class, sql.toString()).size());
			return query(ComboEntity.class, sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询所有的院子列表
	 * @param cityId 城市ID
	 * @return
	 */
	public List<ComboEntity> getYardComboByCityIds(String cityIds) {
		String courtyardIds = ShiroUtil.getUserYards();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.id, a.courtyard_name TEXT FROM t_courtyard a WHERE 1= 1 ");
			List<Object> params = new ArrayList<Object>();
			
			if(!StringUtils.isBlank(cityIds)) {
				sql.append("AND a.city_id IN ("+cityIds+") ");
			}	
			User user = ShiroUtil.getUser();
			String userNo = user.getSysUserNo();
			// 所管理的社区
			if(!userNo.equals(ManagerConstant.SYSTEM_USER) && !userNo.equals(ManagerConstant.SYSTEM_SUPPER)) {
				sql.append("AND a.id IN ("+courtyardIds+") ");
			}		
			
			sql.append("ORDER BY id ASC");
			return query(ComboEntity.class, sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
