package com.suresh.projects.movieworld;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RdsMySqlDbConnection {

	@Value("${rds.base.url}")
	private String rdsBaseUrl;
	@Value("${rds.user.name}")
	private String rdsUserName;
	@Value("${rds.password}")
	private String rdsPassword;
	@Value("${rds.db.name}")
	private String rdsDbName;
	
	private Connection connection;

	public Connection getConnection() throws Exception {
		if (null != connection) {
			return connection;
		} else {
			String url = "jdbc:mysql://" + rdsBaseUrl;
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			connection = DriverManager.getConnection(url + rdsDbName, rdsUserName, rdsPassword);	
		}
		return connection;
	}
}
