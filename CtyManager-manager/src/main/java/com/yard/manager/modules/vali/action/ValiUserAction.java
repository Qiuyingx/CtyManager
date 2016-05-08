package com.yard.manager.modules.vali.action;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yard.manager.base.action.BaseAction;
import com.yard.manager.modules.post.entity.PostInviViewEntity;
import com.yard.manager.modules.vali.entity.query.ValiUserQueryEntity;
import com.yard.manager.modules.vali.service.ValiUserService;
import com.yard.manager.platform.shiro.ShiroUtil;

/**
 * 社区验证
 * 
 * @author : xiaym
 * @date : 2015年6月22日 下午3:28:06
 * @version : 1.0
 */
@Results({
	@Result(type = "freemarker", name = "valiuserindex", location="/WEB-INF/content/modules/vali/valiuserindex.html")
})
public class ValiUserAction extends BaseAction {
	private static final long serialVersionUID = 5044411513754625635L;
	private static final String NAMESPACE = "/vali/info";
	private static final ValiUserService vus = ValiUserService.getInstance();
	
	/**
	 * 进入用户订单列表页面
	 */
	@Action(NAMESPACE + "/valiuserindex")
	public String index() {
		return "valiuserindex";
	}
	
	/**
	 * 获取列表数据
	 */
	@Action(NAMESPACE + "/queryvaliuserlist")
	public String queryUserList() {
		try {
			String courtyardIds = ShiroUtil.getUserYards();
			map.put("total", vus.queryCount(query, courtyardIds));
			map.put("rows", vus.queryList(query, page, rows, courtyardIds));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", new ArrayList<PostInviViewEntity>());
		}
		return MAP;
	}
	
	@Action(NAMESPACE + "/changestatus")
	public String changeStatus() {
		vus.valiPass(query, map);
		return MAP;
	}
	
	private ValiUserQueryEntity query;

	public ValiUserQueryEntity getQuery() {
		return query;
	}

	public void setQuery(ValiUserQueryEntity query) {
		this.query = query;
	}
	
}
