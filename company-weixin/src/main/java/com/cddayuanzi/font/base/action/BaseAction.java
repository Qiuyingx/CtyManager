package com.cddayuanzi.font.base.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cddayuanzi.font.base.entity.ConditionEntity;
import com.cddayuanzi.font.util.HttpUtils;
import com.cddayuanzi.wxsdk.accesstoken.AccessTokenManager;
import com.cddayuanzi.wxsdk.result.AccessTokenJsonRespEntity;
import com.cddayuanzi.wxsdk.result.JsApiTicketJsonRespEntity;
import com.yard.core.struts.action.JsonServletAction;

/**
 * 
 * @author xiayuanming
 *
 */
@Results({ @Result(type = "json", name = "TREENODE", params = { "root", "treeNode" }) })
public abstract class BaseAction extends JsonServletAction {
	private static final long serialVersionUID = -842489292342484165L;

	protected static final String INDEX = "index";
	protected static final String EDIT = "edit";
	protected static final String JASPERREPORT = "jasperreport";
	protected static final String TREENODE = "TREENODE";
	protected static final Map staticMap = new HashMap();

	protected long page; // 页码
	protected long rows; // 单页条数
	protected String order; // 排序方法
	protected String sort; // 排序字段
	protected ConditionEntity condition; // 查询条件
	
	private String accessToken;
	protected String jsTicket;
	
	private String appId = "wx87b4438a2a8abff3";
	private String appSecret = "f50ac37191b828a71ee20721411611ce";
	private static final long TOKEN_EXPIRESTIME_OFFSET = 200000; // 毫秒，微信官方过期时间为7200秒，这里为保险用7000秒，做200秒的偏移量

	private String httpUrl = HttpUtils.ServerUrl;

	public void refreshAccessToken() throws Exception {
		// 重新获取accesstoken
		AccessTokenJsonRespEntity accessTokenEntity = AccessTokenManager.getAccessToken(appId, appSecret);
		staticMap.put("accessToken", accessTokenEntity.getAccess_token());
		staticMap.put("accessTokenTime", (System.currentTimeMillis() + (accessTokenEntity.getExpires_in() * 1000) - TOKEN_EXPIRESTIME_OFFSET));
	}
	
	public void refreshJsTicket() throws Exception {
		// 重新获取jsTicket
		JsApiTicketJsonRespEntity jsTicketEntity = AccessTokenManager.getJsApiTicket(accessToken);
		staticMap.put("jsTicket", jsTicketEntity.getTicket());
		staticMap.put("jsTicketTime", (System.currentTimeMillis() + (jsTicketEntity.getExpires_in() * 1000) - TOKEN_EXPIRESTIME_OFFSET));
	}
	
	public void init() {
		try {
			String accessTokenMap = (String)staticMap.get("accessToken");
			long accessTokenTime = 0;
			try {
				accessTokenTime = (Long) staticMap.get("accessTokenTime");
			} catch (Exception e) {
				refreshAccessToken();
				accessTokenTime = (Long) staticMap.get("accessTokenTime");
			}
			if(accessTokenMap == null || System.currentTimeMillis() >= accessTokenTime || accessTokenTime <= 0) {
				refreshAccessToken();
			}
			accessToken = (String)staticMap.get("accessToken");
			String jsTicketMap = (String)staticMap.get("jsTicket");
			long jsTicketTime = 0;
			try {
				jsTicketTime = (Long)staticMap.get("jsTicketTime");
			} catch (Exception e) {
				refreshJsTicket();
				jsTicketTime = (Long)staticMap.get("jsTicketTime");
			}
			if(jsTicketMap == null || System.currentTimeMillis() >= jsTicketTime || jsTicketTime <= 0) {
				refreshJsTicket();
			}
			jsTicket = (String)staticMap.get("jsTicket");
			System.out.println("--->>"+jsTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}

	public ConditionEntity getCondition() {
		return condition;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public static Map getStaticmap() {
		return staticMap;
	}

	public String getJsTicket() {
		init();
		return jsTicket;
	}

	public void setJsTicket(String jsTicket) {
		this.jsTicket = jsTicket;
	}

}
