package com.yard.manager.modules.notice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yard.core.service.common.BaseService;
import com.yard.core.util.DateUtil;
import com.yard.manager.base.constant.ManagerConstant;
import com.yard.manager.modules.meta.entity.CourtyardViewEntity;
import com.yard.manager.modules.meta.service.CourtyardService;
import com.yard.manager.modules.notice.entity.NoticeViewEntity;
import com.yard.manager.modules.notice.entity.PushType;
import com.yard.manager.modules.notice.entity.query.NoticeQueryEntity;
import com.yard.manager.modules.user.entity.UserPush;
import com.yard.manager.modules.user.entity.UserSetting;
import com.yard.manager.modules.user.service.UserService;
import com.yard.manager.modules.util.ApnsUtil;
import com.yard.manager.modules.util.PropertiesFactory;
import com.yard.manager.modules.util.StringUtil;
import com.yard.manager.platform.redis.MyJedisPool;

public class NoticeService extends BaseService<NoticeViewEntity> {
	private static final NoticeService ns = new NoticeService();
	private static CourtyardService cs = CourtyardService.getInstance();
	private static UserService us = UserService.getInstance();

	public NoticeService() {
	}

	public static final NoticeService getInstance() {
		return ns;
	}

	/**
	 * 添加通知
	 * 
	 * @param entity
	 * @return
	 */
	public boolean add(NoticeViewEntity entity) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_notice(version, content, courtyard_id, user_id, create_time, notice_type, append, title, push_type) VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?);");

		List<Object> params = new ArrayList<Object>();
		try {
			params.add(entity.getContent());
			params.add(entity.getCourtyardId());
			params.add(entity.getUserId() == null ? 0 : entity.getUserId());
			params.add(new Date().getTime());
			params.add(entity.getNoticeType());
			params.add(entity.getContentId() == null ? 0 : entity
					.getContentId());
			params.add(entity.getTitle() == null ? "" : entity.getTitle());
			params.add(entity.getPushType());

			return update(sql.toString(), params.toArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addEntity(NoticeViewEntity entity, Integer noticeType,
			Integer courtyardId) {
		// 所有用户推送
		entity.setCourtyardId(courtyardId);
		// 添加通知记录
		entity.setNoticeType(noticeType);
		add(entity);
	}

	/**
	 * 通知未读数 缓存
	 * 
	 * @param user
	 */
	public long noticeNumber(UserPush user) {
		return MyJedisPool.noticeUnread(user.getId());
	}

	/**
	 * 所选城市下 所有院子推送
	 * 
	 * @param entity
	 * @param noticeType
	 */
	private String chooseCityAllYards(NoticeViewEntity entity,
			Integer noticeType) {
		String courtyardIds = "";
		if (!StringUtils.isBlank(entity.getCityIds())) {
			List<CourtyardViewEntity> list = cs.findByCityIds(entity
					.getCityIds());
			if (list != null && list.size() > 0) {
				for (CourtyardViewEntity court : list) {
					addEntity(entity, noticeType, court.getId());
					courtyardIds += court.getId() + ",";
				}
			}
		}
		if (courtyardIds.endsWith(",")) {
			courtyardIds = courtyardIds.substring(0, courtyardIds.length() - 1);
		}

		return courtyardIds;
	}

	private String onlyChooseCityAllYards(NoticeViewEntity entity,
			Integer noticeType) {
		String courtyardIds = "";
		if (!StringUtils.isBlank(entity.getCityIds())) {
			List<CourtyardViewEntity> list = cs.findByCityIds(entity
					.getCityIds());
			if (list != null && list.size() > 0) {
				for (CourtyardViewEntity court : list) {
					courtyardIds += court.getId() + ",";
				}
			}
		}
		if (courtyardIds.endsWith(",")) {
			courtyardIds = courtyardIds.substring(0, courtyardIds.length() - 1);
		}

		return courtyardIds;
	}
	
	/**
	 * 自选院子推送
	 * 
	 * @param entity
	 * @param noticeType
	 */
	private void chooseYards(NoticeViewEntity entity, Integer noticeType) {
		String[] yards = entity.getYardIds().split(",");
		if (yards != null && yards.length >= 1) {
			for (String str : yards) {
				addEntity(entity, noticeType, Integer.parseInt(str));
			}
		}
	}

	/**
	 * 推送群体确认
	 * 
	 * @param entity
	 * @param type
	 *            推送类型
	 * @param contentId
	 *            文章ID 可为空前端不做跳转
	 * @return
	 */
	public void pushObject(NoticeViewEntity entity, Integer noticeType,
			PushType type, Integer contentId) {
		entity.setContentId(contentId == null ? 0 : contentId);
		if (entity.getIsAllCity() != null && entity.getIsAllCity() == 1) {
			// 推送至所有院子
			addEntity(entity, noticeType, 0);
			iosPush("0", entity.getContent(), type, contentId);
		} else {
			if (entity.getIsAllYards() != null && entity.getIsAllYards() == 1) {
				// 所选城市下 所有院子推送
				String allYards = chooseCityAllYards(entity, noticeType);
				iosPush(allYards, entity.getContent(), type, contentId);
			} else {
				// 自选院子推送
				chooseYards(entity, noticeType);
				iosPush(entity.getYardIds(), entity.getContent(), type,
						contentId);
			}
		}
	}
	public void onlyPushObject(NoticeViewEntity entity, Integer noticeType,
			PushType type, Integer contentId) {
		entity.setContentId(contentId == null ? 0 : contentId);
		if (entity.getIsAllCity() != null && entity.getIsAllCity() == 1) {
			// 推送至所有院子
			iosPush("0", entity.getContent(), type, contentId);
		} else {
			if (entity.getIsAllYards() != null && entity.getIsAllYards() == 1) {
				// 所选城市下 所有院子推送
				String allYards = onlyChooseCityAllYards(entity, noticeType);
				iosPush(allYards, entity.getContent(), type, contentId);
			} else {
				// 自选院子推送
				iosPush(entity.getYardIds(), entity.getContent(), type,
						contentId);
			}
		}
	}

	private void pushObject(NoticeViewEntity entity, Integer noticeType,
			PushType type) {
		pushObject(entity, noticeType, type, null);
	}
	
	private void onlyPushObject(NoticeViewEntity entity, Integer noticeType,
			PushType type) {
		onlyPushObject(entity, noticeType, type, null);
	}

	/**
	 * IOS推送
	 */
	private void iosPush(String courtyardIds, String content, PushType type,
			Integer contentId) {
		List<UserPush> list = us.getIosPushUser(courtyardIds);
		if (list == null || list.size() <= 0) {
			return;
		}
		for (UserPush user : list) {
			if (!StringUtils.isBlank(user.getLastToken())
					&& user.getLastPlatform() == (ManagerConstant.DEVICE_IOS)) {
				UserSetting setting = us.getUserSetting(user.getId());
				if (setting != null && setting.getSystem() != null
						&& setting.getSystem() == 1) {
					long num = 1;
					/*
					 * try{ num = noticeNumber(user); }catch(Exception e){
					 * e.printStackTrace(); }
					 */
					if (contentId != null) {
						ApnsUtil.getInstance().send(user, type, content,
								contentId, (int) num);
					} else {

						ApnsUtil.getInstance().send(user, type, content,
								(int) num);
					}
				}
			}
		}
	}

	/**
	 * 活动发布通知
	 * 
	 * @param isAllYards
	 *            是推送至所有城市
	 * @param cityIds
	 *            城市IDs
	 * @param isAllYards
	 *            是推送至所有院子
	 * @param yardIds
	 *            院子IDs
	 * @param actTitle
	 *            活动标题
	 * @param contentId
	 *            活动ID
	 */
	public void pushAddAct(Integer isAllCity, String cityIds,
			Integer isAllYards, String yardIds, String actTitle,
			Integer contentId) {
		NoticeViewEntity entity = new NoticeViewEntity(
				ManagerConstant.SHOW_PUSH_TEXT_ACT.replace("${actTitle}",
						actTitle), ManagerConstant.SHOW_RECORD_TEXT_ACT.replace("${actTitle}",
								actTitle), isAllCity, cityIds, isAllYards,
				yardIds);
		entity.setTitle("活动通知");
		entity.setPushType(PushType.活动.ordinal());
		onlyPushObject(entity, ManagerConstant.NOTICE_NORMAL, PushType.活动,
				contentId);
	}

	/**
	 * 活动资讯通知
	 * 
	 * @param isAllYards
	 *            是推送至所有城市
	 * @param cityIds
	 *            城市IDs
	 * @param isAllYards
	 *            是推送至所有院子
	 * @param yardIds
	 *            院子IDs
	 * @param newsTitle
	 *            新闻标题
	 * @param contentId
	 *            新闻ID
	 */
	public void pushAddNews(Integer isAllCity, String cityIds,
			Integer isAllYards, String yardIds, String newsTitle,
			Integer contentId) {
		NoticeViewEntity entity = new NoticeViewEntity(
				ManagerConstant.SHOW_PUSH_TEXT_ACT_INFO.replace(
						"${actInfoTitle}", newsTitle), ManagerConstant.SHOW_RECORD_TEXT_ACT_INFO.replace(
								"${actInfoTitle}", newsTitle), isAllCity,
				cityIds, isAllYards, yardIds);
		entity.setTitle("新闻资讯");
		entity.setPushType(PushType.新闻.ordinal());
		onlyPushObject(entity, ManagerConstant.NOTICE_NORMAL, PushType.新闻,
				contentId);
	}

	/**
	 * 社区公告通知
	 * 
	 * @param content
	 *            公告内容
	 * @param yardIds
	 *            院子ID
	 */
	public void pushAddRadio(String content, String yardIds) {
		NoticeViewEntity entity = new NoticeViewEntity(
				ManagerConstant.SHOW_PUSH_TEXT_RADIO.replace("${radioContent}",
						StringUtil.subStr20(content)), ManagerConstant.SHOW_RECORD_TEXT_RADIO.replace("${radioContent}",
								content), null, yardIds);
		if (yardIds.equals("0")) {
			entity.setIsAllYards(1);
		}
		entity.setTitle("社区公告");
		entity.setPushType(PushType.通知.ordinal());
		pushObject(entity, ManagerConstant.NOTICE_RADIO, PushType.通知);
	}

	/**
	 * 群发推送通知
	 * 
	 */
	public void pushMass(NoticeViewEntity entity) {
		entity.setPushContent(ManagerConstant.SHOW_PUSH_TEXT_MASS.replace("${massContent}",
				StringUtil.subStr20(entity.getContent())));
		entity.setContent(ManagerConstant.SHOW_RECORD_TEXT_MASS.replace("${massContent}",
				entity.getContent()));
		entity.setTitle("系统通知");
		entity.setPushType(PushType.群发.ordinal());
		pushObject(entity, ManagerConstant.NOTICE_NORMAL, PushType.通知);
	}

	/**
	 * 专题发布通知
	 * 
	 * @param subjectTitle
	 *            专题标题
	 * @param cityIds
	 *            城市ID
	 */
	public void pushSubject(String subjectTitle, String cityIds) {
		NoticeViewEntity entity = new NoticeViewEntity(
				ManagerConstant.SHOW_PUSH_TEXT_SUBJECT.replace(
						"${subjectTitle}", subjectTitle), ManagerConstant.SHOW_RECORD_TEXT_SUBJECT.replace(
								"${subjectTitle}", subjectTitle), cityIds);
		if (cityIds.equals("0")) {
			entity.setIsAllCity(1);
		}
		entity.setTitle("邻聚专题");
		entity.setPushType(PushType.专题.ordinal());
		onlyPushObject(entity, ManagerConstant.NOTICE_NORMAL, PushType.专题);
	}

	/**
	 * 后台审核紧急求助
	 * 
	 * @param isAllYards
	 *            是推送至所有城市
	 * @param cityIds
	 *            城市IDs
	 * @param isAllYards
	 *            是推送至所有院子
	 * @param yardIds
	 *            院子IDs
	 * @param helpId
	 *            帮帮ID
	 * @param nickName 求助人昵称
	 * @param helpContent 求助内容            
	 * @param courtyardName 社区名称
	 */
	public void pushHelp(Integer isAllCity, String cityIds, Integer isAllYards,
			String yardIds, Integer helpId, String nickName, String helpContent, String courtyardName) {
		NoticeViewEntity entity = new NoticeViewEntity(
				ManagerConstant.SHOW_PUSH_TEXT_HELP,
				ManagerConstant.SHOW_RECORD_TEXT_HELP.replace("${nickName}", nickName).replace("${helpContent}", helpContent)
				.replace("${courtyardName}", courtyardName)
				    , isAllCity, cityIds,
				isAllYards, yardIds);
		entity.setTitle("求助通知");
		entity.setPushType(PushType.通知.ordinal());
		pushObject(entity, ManagerConstant.NOTICE_HELP, PushType.通知, helpId);
	}

	/**
	 * 社区审核验证通过
	 * 
	 * @param userId
	 *            用户ID
	 * @param expCount 赠送经验数量
	 * @param lindouCount 赠送邻豆数量          
	 */
	public void pushUserValiYardsSuccess(Integer userId, Integer expCount, Integer lindouCount) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				ManagerConstant.SHOW_PUSH_TEXT_YARDS_SUCCESS,
				ManagerConstant.SHOW_RECORD_TEXT_YARDS_SUCCESS.replace("${expCount}", expCount+"").replace("${lindouCount}", lindouCount+""));
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}

	/**
	 * 社区审核验证不通过
	 * 
	 * @param userId
	 *            用户ID
	 */
	public void pushUserValiYardsFailed(Integer userId) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				ManagerConstant.SHOW_PUSH_TEXT_YARDS_FAILED,
				ManagerConstant.SHOW_RECORD_TEXT_YARDS_FAILED);
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}
	
	/**
	 * 社区审核验证不通过
	 * 
	 * @param userId
	 *            用户ID
	 */
	public void pushUserGetLindou(Integer userId, String content) {
		NoticeViewEntity entity = new NoticeViewEntity(userId, content, content);
		entity.setNoticeType(ManagerConstant.NOTICE_NORMAL);
		entity.setTitle("后台送豆通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_NORMAL, PushType.通知);
	}

	/**
	 *  申请开店审核通过 
	 */
	public void pushUserValiTrainSuccess(Integer userId) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				ManagerConstant.SHOW_PUSH_TEXT_VALI_TRAIN_SUCCESS,
				ManagerConstant.SHOW_RECORD_TEXT_VALI_TRAIN_SUCCESS);
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}
	
	/**
	 * 申请开店审核不通过
	 * @param detailReasons 认证失败的理由，后台填写
	 */
	public void pushUserValiTrainFailed(Integer userId, String detailReasons) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				ManagerConstant.SHOW_PUSH_TEXT_VALI_TRAIN_FAILED,
				ManagerConstant.SHOW_RECORD_TEXT_VALI_TRAIN_FAILED.replace("${detailReasons}", detailReasons));
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}
	
	/**
	 *  商家店铺启用 
	 */
	public void pushTrainOpen(Integer userId) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				PropertiesFactory.getTEXT_PUSH_TRAIN_MANAGER_OPEN(),
				PropertiesFactory.getTEXT_TRAIN_MANAGER_OPEN());
		entity.setNoticeType(ManagerConstant.NOTICE_NORMAL);
		entity.setTitle("系统通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_NORMAL, PushType.通知);
	}
	
	/**
	 * 商家店铺禁用
	 */
	public void pushTrainDown(Integer userId) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				PropertiesFactory.getTEXT_PUSH_TRAIN_MANAGER_DOWN(),
				PropertiesFactory.getTEXT_TRAIN_MANAGER_DOWN());
		entity.setNoticeType(ManagerConstant.NOTICE_NORMAL);
		entity.setTitle("系统通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_NORMAL, PushType.通知);
	}

	/**
	 *  技能认证成功
	 *  @param expCount 赠送经验数量
	 *  @param lindouCount 赠送邻豆数量
	 */
	public void pushUserValiStarSuccess(Integer userId, Integer expCount, Integer lindouCount) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				PropertiesFactory.getTEXT_PUSH_USER_STAR_MANAGER_SUCCESS(),
				PropertiesFactory.getTEXT_USER_STAR_MANAGER_SUCCESS().replace("${expCount}", expCount+"").replace("${lindouCount}", lindouCount+""));
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}
	
	/**
	 * 技能认证失败
	 * @param detailReasons 认证失败的理由，后台填写
	 */
	public void pushUserValiStarFailed(Integer userId, String detailReasons) {
		NoticeViewEntity entity = new NoticeViewEntity(userId,
				PropertiesFactory.getTEXT_PUSH_USER_STAR_MANAGER_FAILED(),
				PropertiesFactory.getTEXT_USER_STAR_MANAGER_FAILED().replace("${detailReasons}", detailReasons+""));
		entity.setNoticeType(ManagerConstant.NOTICE_VALIDATE);
		entity.setTitle("验证通知");
		entity.setPushType(PushType.通知.ordinal());
		pushUser2Notice(entity, ManagerConstant.NOTICE_VALIDATE, PushType.通知);
	}
	
	/**
	 * 通知推送推送单个用户
	 * 
	 * @param userId
	 *            用户ID
	 * @param content
	 *            推送内容
	 */
	public void pushUser2Notice(NoticeViewEntity entity, Integer noticeType,
			PushType pushType) {
		UserPush user = us.getPushUserById(entity.getUserId());
		if (user != null) {
			UserSetting setting = us.getUserSetting(user.getId());
			// 产生通知记录
			addEntity(entity,
					noticeType == null ? ManagerConstant.NOTICE_NORMAL
							: noticeType, user.getCourtyardId());
			if (setting != null && setting.getSystem() != null
					&& setting.getSystem() == 1) {
				long num = 1;
				/*try {
					// 增加未读数缓存
					num = noticeNumber(user);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				// IOS推送
				try{
					ApnsUtil.getInstance().send(user, pushType,
							entity.getPushContent(), (int) num);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询分页数据
	 * @param page 页号
	 * @param rows 页数
	 */
	public List<NoticeViewEntity> queryList(NoticeQueryEntity query, long page, long rows) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.content, a.courtyard_id courtyardId, a.user_id userId, a.create_time createTime, a.notice_type noticeType, ");
		sql.append("a.append, a.title, b.courtyard_name courtyardName, c.nickname nickName  FROM t_notice a LEFT JOIN t_courtyard b ON a.courtyard_id = b.id ");
		sql.append("LEFT JOIN t_user c ON a.user_id = c.id WHERE 1= 1 ");
		
		List<Object> params = new ArrayList<Object>();

		queryParam(query, sql, params);
		
		// 分页排序
		sql.append("ORDER BY a.id DESC LIMIT ?, ?");
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
	 * @return
	 */
	public long queryCount(NoticeQueryEntity query) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_notice a LEFT JOIN t_courtyard b ON a.courtyard_id = b.id ");
		sql.append("LEFT JOIN t_user c ON a.user_id = c.id WHERE 1= 1 ");
		
		List<Object> params = new ArrayList<Object>();
		
		queryParam(query, sql, params);
		
		try {
			return (Long) certain(sql.toString(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void queryParam(NoticeQueryEntity query, StringBuffer sql, List<Object> params) {
		if(query != null) {
			// 创建时间
			if (!StringUtils.isBlank(query.getStartTime())) {
				sql.append("AND a.create_time >= ? ");
				params.add(DateUtil.getMillisTime(query.getStartTime()+ " 00:00:00"));
			}

			if (!StringUtils.isBlank(query.getEndTime())) {
				sql.append("AND a.create_time <= ? ");
				params.add(DateUtil.getMillisTime(query.getEndTime()+ " 23:59:59"));
			}
			
			// 通知类型
			if(query.getNoticeType() != null) {
				sql.append("AND a.notice_type = ? ");
				params.add(query.getNoticeType());
			}
			
			// 推送类型
			if(query.getPushType() != null) {
				sql.append("AND a.push_type = ? ");
				params.add(query.getPushType());
			}
		}
	}
	
	public static void main(String[] args) {
		UserPush user = new UserPush();
		user.setId(1);
		user.setCourtyardId(1);
		user.setLastPlatform(2);
		user.setLastToken("34d5359b9c585eb1dbfcca2d45f295a83647eb452b1e01e29c0d5c6327f8be27");
		ApnsUtil.getInstance().send(user, PushType.通知, "2323", (int) 1);
	}
}
