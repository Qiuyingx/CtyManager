package com.yard.manager.platform.action;

public interface RestAction {
	/**
	 * 列表页
	 * 
	 * @return
	 */
	public String index() throws Exception;

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String query();

	/**
	 * 查看单条记录
	 * 
	 * @return
	 */
	public String show();

	/**
	 * 创建记录
	 * 
	 * @return
	 */
	public String create();

	/**
	 * 更新记录
	 * 
	 * @return
	 */
	public String update();

	/**
	 * 删除记录
	 * 
	 * @return
	 */
	public String delete();

	/**
	 * 销毁记录
	 * 
	 * @return
	 */
	public String destroy();

	/**
	 * 编辑记录
	 * 
	 * @return
	 */
	public String edit() throws Exception;

	/**
	 * 新增记录
	 * 
	 * @return
	 */
	public String editNew() throws Exception;
}
