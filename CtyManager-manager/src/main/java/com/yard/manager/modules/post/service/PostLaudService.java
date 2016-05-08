package com.yard.manager.modules.post.service;

import java.sql.SQLException;

import com.yard.core.service.common.BaseService;
import com.yard.manager.modules.post.entity.PostLaudViewEntity;

/**
 * 帖子感谢
 * @author : xiaym
 * @date : 2015年6月18日 上午10:58:25
 * @version : 1.0
 */
public class PostLaudService extends BaseService<PostLaudViewEntity>{
	private final static PostLaudService pls = new PostLaudService();
	
	public PostLaudService() {
		
	}
	
	public static PostLaudService getInstance() {
		return pls;
	}
	
	/**
	 * 根据帖子ID 统计 感谢次数
	 * @param postId 帖子ID
	 */
	public long queryCountByPostId(Integer postId) {
		String sql = "SELECT COUNT(*) FROM t_post_laud WHERE post_id = ?";

		try {
			return (Long)certain(sql, postId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
