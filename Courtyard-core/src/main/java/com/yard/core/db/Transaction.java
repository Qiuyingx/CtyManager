package com.yard.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * 数据库事务处理
 * 
 * @author 王维
 * 
 */
public class Transaction {
	/**
	 * 数据库连接。同一个线程使用同一个连接。
	 */
	protected static ThreadLocal<Connection> connectionLocal = new ThreadLocal<Connection>();
	// protected static ThreadLocal<DataSource> dataSourceLocal = new
	// ThreadLocal<DataSource>();
	protected static boolean isInUsed = false;

	/**
	 * 开始一个事务 如果当前线程的数据库连接不存在，则会新获得一个数据库连接
	 * 
	 * @param key
	 * @throws Exception
	 */
	public static void beginTransaction(String key) throws Exception {
		Connection conn = (Connection) connectionLocal.get();
		if (conn == null) {
			conn = createConnection(key);
			setConnection(conn);
		}
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		isInUsed = true;
	}

	public static void beginTransaction() throws Exception {
		beginTransaction(DbPoolFactory.DEFAULT);
	}

	/**
	 * 提交一个事务
	 * 
	 * @throws SQLException
	 */
	public static void commitTransaction() throws SQLException {
		Connection conn = (Connection) connectionLocal.get();
		conn.commit();
		closeConnection();
	}

	/**
	 * 回滚一个事务
	 * 
	 * @throws SQLException
	 */
	public static void rollbackTransaction() throws SQLException {
		Connection conn = (Connection) connectionLocal.get();
		conn.rollback();
		closeConnection();
	}

	public static boolean getIsInUsed() {
		return isInUsed;
	}

	/**
	 * 关闭当前链接
	 */
	private static void closeConnection() {
		try {
			Connection conn = (Connection) connectionLocal.get();
			if (conn != null) {
				connectionLocal.set(null);
				conn.close();
			}
		} catch (SQLException sqlexception) {

		}
	}

	@SuppressWarnings("unused")
	private static void close(ResultSet rs) {
		try {
			Statement st = rs.getStatement();
			Connection conn = st.getConnection();
			rs.close();
			st.close();
			conn.close();
		} catch (Exception exception) {
		}
	}

	/**
	 * 获得数据库连接 同一个线程只返回相同的一个连接。
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = (Connection) connectionLocal.get();
		if (conn == null)
			throw new NullPointerException();
		else
			return conn;
	}

	private static void setConnection(Connection conn) {
		connectionLocal.set(conn);
	}

	/**
	 * 获得一个新的数据库连接
	 * 
	 * @param key
	 * @return Connection
	 * @throws Exception
	 */

	private static Connection createConnection(String key) throws Exception {
		DataSource ds_pooled = DbPoolFactory.getInstance().getDataSource(key);
		Connection conn = ds_pooled.getConnection();
		return conn;
	}
}
