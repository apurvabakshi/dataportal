package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnection
{
	Connection con=null;
	/**
	 * This method is for creating connection with cassandra database
	 * @return connection
	 */
	public Connection connectCassandra()
	{
		try
		{
			Class.forName(Constants.CASSANDRA_DRIVER);
			Connection   con = DriverManager.getConnection(Constants.CASSANDRA_LOCATION,Constants.CASSANDRA_USERNAME,Constants.CASSANDRA_PASSWORD);
			return con;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	public Connection getConnection() throws Exception
	{
		if (con != null && con.isValid(100))
		{
			return con;
		}
		else
		{
			con =connectCassandra();
			return con;
		}
	}


}