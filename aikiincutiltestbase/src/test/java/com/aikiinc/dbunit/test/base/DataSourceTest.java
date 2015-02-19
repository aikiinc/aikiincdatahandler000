package com.aikiinc.dbunit.test.base;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.util.jdbc.JDBCCloseUtil;
import com.aikiinc.util.jdbc.JDBCUtil;

/**
 *
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Sep 23, 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-connection.xml" })
public class DataSourceTest extends DbUnitBaseTest {
	private static Logger LOG = Logger.getLogger(DataSourceTest.class);

	@Test
	public void getDataSource() {
		Assert.assertNotNull(dataSource);
	}

	@Test
	public void getConnection() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);
			JDBCCloseUtil.close(con);
		} catch (Exception e) {
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
			// Assert.assertEquals("jdbc:hsqldb:file:target/hsql/db/testdb",
			// info.getURL());
			Assert.assertEquals("sa", info.getUserName().toLowerCase());

			JDBCCloseUtil.close(con);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Test
	public void getDataProperty() {
		Assert.assertNotNull(hibernateProperties);

		getHibernateProperty("hibernate.hbm2ddl.auto");
	}

	@Test
	public void getHibernateProperty() {
		try {
			Assert.assertNull(getHibernateProperty("hibernate.hbm2ddl.bad"));
			Assert.assertNotNull(getHibernateProperty("hibernate.hbm2ddl.auto"));
			// LOG.info("Hibernate property(hibernate.hbm2ddl.auto):  "
			// + getHibernateProperty("hibernate.hbm2ddl.auto"));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
