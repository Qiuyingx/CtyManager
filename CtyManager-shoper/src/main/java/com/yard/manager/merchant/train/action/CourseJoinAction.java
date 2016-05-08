package com.yard.manager.merchant.train.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.base.util.JsonResult;
import com.yard.manager.merchant.train.entity.CourseJoinViewEntity;
import com.yard.manager.merchant.train.entity.query.CourseJoinQueryEntity;
import com.yard.manager.merchant.train.service.CourseJoinService;
import com.yard.manager.platform.excel.ExcelProcess;

/**
 * 课程报名处理
 * @author : xiaym
 * @date : 2015年9月9日 下午3:26:11
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "coursejoinindex", location = "/WEB-INF/content/merchant/train/coursejoinindex.html")
})
public class CourseJoinAction extends BaseAction {
	private static final long serialVersionUID = -3719531580009240976L;
	private static final String NAMESPACE = "/course/coursejoin";
	private static final CourseJoinService cjs = CourseJoinService.getInstance();
	
	/**
	 * 进入报名列表
	 */
	@Action(NAMESPACE + "/coursejoinindex")
	public String courseJoinIndex() {
		return "coursejoinindex";
	}
	
	/**
	 * 获取报名信息
	 * @return
	 */
	@Action(NAMESPACE + "/querylist")
	public String queryList() {
		try {
			map.put("total", cjs.queryCount(courseId, query));
			map.put("rows", cjs.queryList(page, rows, courseId, query));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<CourseJoinViewEntity>());
		}
		return MAP;
	}
	
	/**
	 * 导出报名数据
	 * @return
	 */
	@Action(NAMESPACE + "/downdata")
	public String downData() {
		String sheetTitle = "课程报名数据统计";
		String[] titleName = {"姓名", "电话", "备注", "课程名称", "价格", "课时", "报名时间"};
		String files = "/excel/shoper/downsignup";
		
		try{
			List<CourseJoinViewEntity> list = cjs.queryList(1, 1000, courseId, query);
			if(list != null && !list.isEmpty()) {
				
				String[][] bodyValue = new String[list.size()][7];
				for(int i=0; i<list.size(); i++) {
					CourseJoinViewEntity or = list.get(i);
					bodyValue[i][0] = or.getName();
					bodyValue[i][1] = or.getTel();
					bodyValue[i][2] = or.getRemark();
					bodyValue[i][3] = or.getTitle();
					bodyValue[i][4] = or.getPrice()+"";
					bodyValue[i][5] = or.getHours();
					bodyValue[i][6] = or.getCreateTime()+"";
				}
				
				String fileName = "downSignUp-"+new Date().getTime();
				ExcelProcess.start(sheetTitle, titleName, bodyValue, request, files, fileName);
				JsonResult.toJson(map, true, files+"/"+fileName+".xls");
			}else{
				JsonResult.toJson(map, false, "暂时无数据！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return MAP;
	}
	
	private Integer courseId; // 课程ID
	private CourseJoinQueryEntity query;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public CourseJoinQueryEntity getQuery() {
		return query;
	}

	public void setQuery(CourseJoinQueryEntity query) {
		this.query = query;
	}
	
}
