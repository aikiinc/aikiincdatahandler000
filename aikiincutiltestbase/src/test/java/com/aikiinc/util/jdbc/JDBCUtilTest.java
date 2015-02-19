package com.aikiinc.util.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.dbunit.test.base.DbUnitBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-connection.xml" })
public class JDBCUtilTest extends DbUnitBaseTest {
	private static Logger LOG = Logger.getLogger(JDBCUtilTest.class);

	@Test
	public void getConnection() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);
			JDBCCloseUtil.close(con);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Test
	public void getConnectionInfo() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);

			JDBCUtil.ConnectionInfo info = JDBCUtil.getConnectionInfo(con);
			Assert.assertNotNull(info);
			Assert.assertEquals("HSQL Database Engine Driver",
					info.getDriverName());
			Assert.assertEquals("jdbc:hsqldb:file:target/hsql/db/testdb",
					info.getURL());
			Assert.assertEquals("sa", info.getUserName().toLowerCase());

			JDBCCloseUtil.close(con);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Test
	public void showMetaData() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);

			JDBCUtil.showMetaData(con);
			JDBCCloseUtil.close(con);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Test
	public void getConnectionMetaData() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);

			DatabaseMetaData dmd = JDBCUtil.getConnectionMetaData(con);
			Assert.assertNotNull(dmd);

			JDBCCloseUtil.close(con);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

}
