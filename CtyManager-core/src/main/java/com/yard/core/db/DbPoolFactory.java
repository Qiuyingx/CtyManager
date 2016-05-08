package com.yard.core.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据连接池工厂
 * 
 * 
 */
public class DbPoolFactory {
	private static DbPoolFactory instance = new DbPoolFactory();
	public static final String DEFAULT = "DEFAULT";
	public static final String MYSQL = "MYSQL";
	// 存储多个连接池(支持多种数据库同时存在)
	private static Map<String, DataSource> poolMap = new HashMap<String, DataSource>();

	private DbPoolFactory() {

	}

	/**
	 * 获取静态工厂类
	 * 
	 * @return 连接池工厂
	 */
	public static DbPoolFactory getInstance() {
		return instance;
	}

	/**
	 * 向工厂类增加连接池配置
	 * 
	 * @param keys
	 * @throws DbException
	 */
	public void add(String... keys) throws Exception {
		try {
			// Class.forName(driver);
			for (String key : keys) {
				ComboPooledDataSource cpds = null;
				if (DEFAULT.equalsIgnoreCase(key)) {
					cpds = new ComboPooledDataSource();
				} else {
					cpds = new ComboPooledDataSource(key);
				}
				if (poolMap.containsKey(key)) {
					poolMap.remove(key);
				}
				poolMap.put(key, cpds);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("增加并初始化连接池[" + keys + "]异常！");
		}
	}

	/**
	 * 是否存在特定连接池
	 * 
	 * @param key
	 * @return 是否存在
	 */
	public boolean containsKey(String key) {
		return poolMap.containsKey(key);
	}

	/**
	 * 获取默认数据源
	 * 
	 * @return 数据源
	 */
	public DataSource getDataSource() {
		return poolMap.get(DEFAULT);
	}

	/**
	 * 获取指定数据源
	 * 
	 * @param key
	 *            数据库配置KEY
	 * @return 数据源
	 */
	public DataSource getDataSource(String key) {
		return poolMap.get(key);
	}

	/**
	 * 获取默认数据源(自动初始化)
	 * 
	 * @return 数据源
	 * @throws DbException
	 */
	public DataSource getDataSourceByInit() throws Exception {
		return getDataSourceByInit(DEFAULT);
	}

	/**
	 * 获取指定数据源(自动初始化)
	 * 
	 * @param key
	 *            数据库配置KEY
	 * @return
	 * @throws DbException
	 */
	public DataSource getDataSourceByInit(String key) throws Exception {
		try {
			if (poolMap.containsKey(key)) {
				return poolMap.get(key);
			} else {
				ComboPooledDataSource cpds = null;
				if (DEFAULT.equalsIgnoreCase(key)) {
					cpds = new ComboPooledDataSource();
				} else {
					cpds = new ComboPooledDataSource(key);
				}
				poolMap.put(key, cpds);
				return cpds;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取并初始化数据源[" + key + "]异常！");
		}
	}
}
