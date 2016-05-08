package com.yard.manager.merchant.train.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.merchant.train.entity.TrainCourseViewEntity;
import com.yard.manager.merchant.train.entity.query.TrainCourseQueryEntity;
import com.yard.manager.merchant.train.service.TrainCourseService;

/**
 * 培训室课程管理
 * @author : xiaym
 * @date : 2015年8月13日 下午4:53:17
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "traincourseindex", location = "/WEB-INF/content/merchant/train/traincourseindex.html"),
	@Result(type = "freemarker", name = "content_edit", location="/WEB-INF/content/merchant/train/traincourse_edit.html")
})
public class TrainCourseAction extends BaseAction {
	private static final long serialVersionUID = -3719531580009240976L;
	private static final String NAMESPACE = "/train/traincourse";
	private static final TrainCourseService tcs = TrainCourseService.getInstance();
	
	/**
	 * 进入培训室课程列表
	 */
	@Action(NAMESPACE + "/traincourseindex")
	public String trainCourseIndex() {
		return "traincourseindex";
	}
	
	/**
	 * 进入培训室课程编辑页面
	 */
	@Action(NAMESPACE + "/content_edit")
	public String contentEdit() {
		return "content_edit";
	}
	
	/**
	 * 培训室的所有课程信息
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", tcs.queryCount(query));
			map.put("rows", tcs.queryList(page, rows, query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<TrainCourseViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 添加课程
	 * @return
	 */
	@Action(NAMESPACE + "/add")
	public String add() {
		tcs.addCourse(entity, map);
		return MAP;
	}
	
	/**
	 * 修改课程信息 
	 */
	@Action(NAMESPACE + "/update")
	public String update() {
		tcs.updateCourse(entity, map);
		return MAP;
	}
	
	/**
	 * 根据ID获取内容详情
	 * @return
	 */
	@Action(NAMESPACE + "/getcontentinfo")
	public String getContentInfo() {
		tcs.getContentInfo(id, map);
		return MAP;
	}
	
	/**
	 * 状态变更
	 */
	@Action(NAMESPACE + "/changestatus")
	public String changeStatus() {
		tcs.changeCourse(id, status, map);
		return MAP;
	}
	
	private Integer trainId;// 培训室ID
	private String process; // 操作类型  update 修改 add 添加（默认）
	private Integer id; // 课程ID
	private Integer status; // 课程状态
	private TrainCourseViewEntity entity;
	private TrainCourseQueryEntity query;

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrainCourseViewEntity getEntity() {
		return entity;
	}

	public void setEntity(TrainCourseViewEntity entity) {
		this.entity = entity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public TrainCourseQueryEntity getQuery() {
		return query;
	}

	public void setQuery(TrainCourseQueryEntity query) {
		this.query = query;
	}
	
}
