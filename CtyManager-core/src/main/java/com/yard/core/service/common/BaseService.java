package com.yard.core.service.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yard.core.util.ClassUtil;

/**
 * @Title: Commmethod.java
 * @Description:公用服务类
 * @version V1.0
 */
@SuppressWarnings({ "hiding", "unchecked" })
public class BaseService<T> {
	/**
	 * 查询方法
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 返回集合对象T
	 * @throws SQLException
	 */
	public List<T> query(String sql, Object... obj) throws SQLException {
		DataSource dataSource = DBService.getDataSource();
		ResultSetHandler<List<T>> h = new BeanListHandler<T>(entityClass);
		QueryRunner run = new QueryRunner(dataSource);
		return run.query(sql, h, obj);
	}

	/**
	 * 
	 * @param t
	 *            需要返回的对象(用户自行传入的)
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 返回集合对象T
	 * @throws SQLException
	 */
	public <T> List<T> query(Class<T> t, String sql, Object... obj) throws SQLException {
		ResultSetHandler<List<T>> h = new BeanListHandler<T>(t);
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.query(sql, h, obj);
	}

	/**
	 * 查询谋一个值
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 返回参数
	 * @throws SQLException
	 */
	public Object certain(String sql, Object... obj) throws SQLException {
		ResultSetHandler<Object> h = new ScalarHandler<Object>();
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.query(sql, h, obj);
	}

	/**
	 * 非查询方法(同时修改多条数据)
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 返回受影响的行数
	 * @throws SQLException
	 */
	public int updates(String sql, Object... obj) throws SQLException {
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.update(sql, obj);
	}
	
	/**
	 * 添加(不带事务)
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return ID
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public int add(String sql, Object... obj) throws SQLException {
		DataSource dataSource = DBService.getDataSource();
		Connection con = dataSource.getConnection();
		QueryRunner run = new QueryRunner();
		run.update(con, sql, obj);
		ResultSetHandler h = new KeyedHandler("id");
		Map s =  run.query(con, "select last_insert_id() id ", h); 
		return  Integer.parseInt(""+s.keySet().toArray()[0]);
	}
	
	/**
	 * 添加(带事务)
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return ID
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public int add(Connection con, String sql, Object... obj) throws SQLException {
		try{
			QueryRunner run = new QueryRunner();
			run.update(con, sql, obj);
			ResultSetHandler h = new KeyedHandler("id");
			Map s =  run.query(con, "select last_insert_id() id ", h); 
			return  Integer.parseInt(""+s.keySet().toArray()[0]);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 非查询方法(同时修改多条数据) 带事务
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 返回受影响的行数
	 * @throws SQLException
	 */
	public int updates(Connection conn, String sql, Object... obj) throws SQLException {
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.update(conn, sql, obj);
	}

	/**
	 * 非查询方法
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 是否执行成功（return 成功 false 失败）
	 * @throws SQLException
	 */
	public boolean update(String sql, Object... obj) throws SQLException {
		return this.updates(sql, obj) == 1 ? true : false;
	}

	/**
	 * 加入了事务
	 * 
	 * @param conn
	 *            连接对象
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 是否执行成功（return 成功 false 失败）
	 * @throws SQLException
	 */
	public boolean update(Connection conn, String sql, Object... obj) throws SQLException {
		return this.updates(conn, sql, obj) == 1 ? true : false;
	}

	/**
	 * 查询单条数据
	 * 
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 查询出的对象
	 * @throws SQLException
	 */
	public T show(String sql, Object... obj) throws SQLException {
		ResultSetHandler<T> h = new BeanHandler<T>(entityClass);
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.query(sql, h, obj);
	}

	/**
	 * 
	 * @param t
	 *            返回的实体类对象（传入的是什么实体类对象，就返回与传入的实体类相同的对象，传入USER 返回USER）
	 * @param sql
	 *            组装好的sql语句
	 * @param obj
	 *            参数
	 * @return 查询出的对象
	 * @throws SQLException
	 */
	public <T> T show(Class<T> t, String sql, Object... obj) throws SQLException {
		ResultSetHandler<T> h = new BeanHandler<T>(t);
		DataSource dataSource = DBService.getDataSource();
		QueryRunner run = new QueryRunner(dataSource);
		return run.query(sql, h, obj);
	}

	// 程序运行时，当前类将作为基类被继承，通过反射工具类getGenericClassTpye，得到此时泛型 所指定的实际型参数类型
	private Class<T> entityClass = ClassUtil.getGenericClassTpye(this.getClass());

}
