package com.yard.manager.modules.notice.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.modules.notice.entity.NoticeViewEntity;
import com.yard.manager.modules.notice.entity.query.NoticeQueryEntity;
import com.yard.manager.modules.notice.service.NoticeService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 消息群发
 * @author : xiaym
 * @date : 2015年7月8日 下午3:27:39
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "pushindex", location="/WEB-INF/content/modules/notice/pushindex.html"),
	@Result(type = "freemarker", name = "radioindex", location="/WEB-INF/content/modules/notice/radioindex.html"),
	@Result(type = "freemarker", name = "noticeindex", location="/WEB-INF/content/modules/notice/noticeindex.html")
})
public class PushAction extends BaseAction {
	private static final long serialVersionUID = -2170789104855964439L;
	private static final String NAMESPACE = "/notice/push";
	private static final NoticeService ns = NoticeService.getInstance();
	
	/**
	 * 进入群发页面
	 * @return
	 */
	@Action(NAMESPACE + "/index")
	public String index() {
		return "pushindex";
	}
	
	/**
	 * 进入社区公告
	 */
	@Action(NAMESPACE + "/radioindex")
	public String radioIndex() {
		return "radioindex";
	}
	
	/**
	 * 进入通知列表
	 */
	@Action(NAMESPACE + "/noticeindex")
	public String noticeIndex() {
		return "noticeindex";
	}

	/**
	 * 群发通知
	 * @return
	 */
	@Action(NAMESPACE + "/send")
	public String send() {
		ns.pushMass(entity);
		JsonResult.toJson(map, true, "推送成功！");
		return MAP;
	}
	
	/**
	 * 发布社区公告
	 */
	@Action(NAMESPACE + "/radiosend")
	public String radioSend() {
		ns.pushAddRadio(entity.getContent(), ShiroUtil.getUserYards());
		JsonResult.toJson(map, true, "发布成功！");
		return MAP;
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", ns.queryCount(query));
			map.put("rows", ns.queryList(query, page, rows));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<NoticeViewEntity>());
		}
		return MAP;
	}
	
	private NoticeViewEntity entity;
	private NoticeQueryEntity query;
	private String forwardPath;

	public NoticeViewEntity getEntity() {
		return entity;
	}

	public void setEntity(NoticeViewEntity entity) {
		this.entity = entity;
	}

	public NoticeQueryEntity getQuery() {
		return query;
	}

	public void setQuery(NoticeQueryEntity query) {
		this.query = query;
	}

	public String getForwardPath() {
		return forwardPath;
	}

	public void setForwardPath(String forwardPath) {
		this.forwardPath = forwardPath;
	}
	
}
