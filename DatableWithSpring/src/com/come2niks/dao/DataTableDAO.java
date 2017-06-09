package com.come2niks.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dataTableDao")
public class DataTableDAO {

	@Autowired
	DataSource dataSource; /* This is configured in dispatcher-servlet.xml file */

	public Connection getConnection()
	{
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
