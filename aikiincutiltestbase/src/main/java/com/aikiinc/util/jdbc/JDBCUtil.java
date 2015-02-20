package com.aikiinc.util.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Base JDBC utility for handling Hibernate Connection and information
 * 
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author: Author: Philip Jahmani Chauvet.
 * @Dated: Feb 03, 2013 - Feb 19, 2015
 */
public class JDBCUtil extends JdbcDaoSupport {
	private static final Logger LOG = Logger.getLogger(JDBCUtil.class);
	private static JDBCUtil jdbcUtil;

	static {
		jdbcUtil = new JDBCUtil();
	}

	private JDBCUtil() {
		super();
	}

	public static Connection getJDBCConnection() {
		Connection conn = null;
		try {
			conn = jdbcUtil.getConnection();
			LOG.info("conn: " + conn);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Can not get JDBC connection " + e.getMessage());
		}

		return conn;
	}

	public static void showMetaData(Connection conn) {
		try {
			DatabaseMetaData dmd = conn.getMetaData();

			LOG.info("----------------");
			LOG.info("conn URL: " + dmd.getURL());
			LOG.info("conn UserName: " + dmd.getUserName());
		} catch (SQLException e) {
			LOG.error("----------------");
			LOG.error(getEntireExceptionMessage(e));
		}
	}

	public static DatabaseMetaData getConnectionMetaData(Connection conn) {
		DatabaseMetaData dmd = null;
		try {
			dmd = conn.getMetaData();

			LOG.info("----------------");
			LOG.info("conn URL: " + dmd.getURL());
			LOG.info("conn UserName: " + dmd.getUserName());
		} catch (SQLException e) {
			LOG.error("----------------");
			LOG.error(JDBCUtil.getEntireExceptionMessage(e));
		}

		return dmd;
	}

	public static ConnectionInfo getConnectionInfo(Connection con)
			throws Exception {
		return jdbcUtil.new ConnectionInfo(con);
	}

	public class ConnectionInfo {
		private String driverName;
		private String databaseProductName;
		private String driverVersion;
		private String url;
		private String userName;
		private Connection con;
		private DatabaseMetaData dm;

		public ConnectionInfo(Connection con) throws JDBCUtilException {
			this.con = con;
			try {
				dm = con.getMetaData();

				url = dm.getURL();
				try {
					driverName = dm.getDriverName();
				} catch (Exception e) {
				}
				try {
					databaseProductName = dm.getDatabaseProductName();
				} catch (Exception e) {
				}
				try {
					driverVersion = dm.getDriverVersion();
				} catch (Exception e) {
				}
				try {
					userName = dm.getUserName();
				} catch (Exception e) {
				}
			} catch (Throwable e) {
				throw new JDBCUtilException(
						JDBCUtil.getEntireExceptionMessage(e));
			}
		}

		public DatabaseMetaData getDatabaseMetaData() {
			return dm;
		}

		public String getURL() {
			return url;
		}

		public String getDriverName() {
			return driverName;
		}

		public String getDriverVersion() {
			return driverVersion;
		}

		public String getUserName() {
			return userName;
		}

		public Connection getConnection() {
			return this.con;
		}

		public String getDatabaseProductName() {
			return databaseProductName;
		}

		@Override
		public String toString() {
			return "ConnectionInfo [driverName=" + driverName
					+ ", databaseProductName=" + databaseProductName
					+ ", driverVersion=" + driverVersion + ", url=" + url
					+ ", userName=" + userName + ", con=" + con + ", dm=" + dm
					+ "]";
		}

	}

	/**
	 * Some exceptions, don't show the real cause of the error. This method
	 * provides the combines the Throwable.getMessage() with
	 * Throwable.getCause(). Specially handy when drilling down into a database
	 * exception, getMessage() does no always provide the full details -
	 * getCause() sometimes does.
	 * 
	 * @param e
	 * @return
	 */
	public static String getEntireExceptionMessage(Throwable e) {
		return "getMessage(): " + e.getMessage() + ", getCause(): "
				+ e.getCause();
	}
}
