package com.aikiinc.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * General close JDBC connection objects:the Connection, ResutSet, Statements
 * 
 * @Copyright (C) Aiki Innovations Inc 2015-2015 - http://www.aikiinc.com
 * @Author: Author: Philip Jahmani Chauvet. 
 * @Dated: Feb 13, 2015
 */
public class JDBCCloseUtil {
	private static final Logger LOG = Logger.getLogger(JDBCCloseUtil.class);

	/**
	 * Close JCBC Connection
	 * 
	 * @param con
	 * @throws JDBCUtilException
	 */
	public static void close(Connection con) throws JDBCUtilException {
		try {
			if (con != null)
				con.close();
		} catch (Throwable e) {
			LOG.error("Error closing connection: " + e.getMessage());
			throw new JDBCUtilException(e);
		}

	}

	/**
	 * Close JCBC ResutSet
	 * 
	 * @param rs
	 * @throws JDBCUtilException
	 */
	public static void close(ResultSet rs) throws JDBCUtilException {
		try {
			if (rs != null)
				rs.close();
		} catch (Throwable e) {
			LOG.error("Error closing resuetSet: " + e.getMessage());
			throw new JDBCUtilException(e);
		}

	}

	/**
	 * Close JCBC Statement
	 * 
	 * @param st
	 * @throws JDBCUtilException
	 */
	public static void close(Statement st) throws JDBCUtilException {
		try {
			if (st != null)
				st.close();
		} catch (Throwable e) {
			LOG.error("Error closing Statement: " + e.getMessage());
			throw new JDBCUtilException(e);
		}

	}

	/**
	 * General close open JDBC connection objects: Connection, ResutSet,
	 * PreparedStatement
	 * 
	 * @param con
	 * @param rs
	 * @param ps
	 * @throws JDBCUtilException
	 */
	public static void close(Connection con, ResultSet rs, PreparedStatement ps)
			throws JDBCUtilException {
		try {
			close(con);
			close(rs);
			close(ps);
		} catch (Throwable e) {
			LOG.error("Error closing connection objects: " + e.getMessage());
			throw new JDBCUtilException(e);
		}

	}

	/**
	 * General close open JDBC connection objects: Connection, ResutSet,
	 * Statement
	 * 
	 * @param con
	 * @param rs
	 * @param stmt
	 * @throws JDBCUtilException
	 */
	public static void close(Connection con, ResultSet rs, Statement stmt)
			throws JDBCUtilException {
		try {
			close(con);
			close(rs);
			close(stmt);
		} catch (JDBCUtilException e) {
			LOG.error("Error closing connection objects: " + e.getMessage());
			throw e;
		}
	}

}
