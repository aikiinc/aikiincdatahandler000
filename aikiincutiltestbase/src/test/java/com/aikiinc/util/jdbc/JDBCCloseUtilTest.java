package com.aikiinc.util.jdbc;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.dbunit.test.base.DbUnitBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-connection.xml" })
public class JDBCCloseUtilTest extends DbUnitBaseTest {
	private static Logger LOG = Logger.getLogger(JDBCCloseUtilTest.class);

	@Test
	public void close() {
		try {
			Connection con = dataSource.getConnection();
			Assert.assertNotNull(con);
			JDBCCloseUtil.close(con);
			Assert.assertTrue(con.isClosed());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
