package com.yard.core.service.common;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.yard.core.db.DbPoolFactory;

public class DBService {
	static {
		try {
			DbPoolFactory.getInstance().add(DbPoolFactory.MYSQL);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接池初始化失败");
		}
	}

	public static DataSource getDataSource() {
		return DbPoolFactory.getInstance().getDataSource(DbPoolFactory.MYSQL);
	}

	public static QueryRunner getRunner() {
		return new QueryRunner(getDataSource());
	}
}
