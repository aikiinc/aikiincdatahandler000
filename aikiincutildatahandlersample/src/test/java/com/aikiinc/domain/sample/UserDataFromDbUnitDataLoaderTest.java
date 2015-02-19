package com.aikiinc.domain.sample;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.data.handler.DataLoaderDbUnit;
import com.aikiinc.dbunit.test.base.DbUnitBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-connection.xml",
		"classpath:applicationContext-dao.xml" })
public class UserDataFromDbUnitDataLoaderTest extends DbUnitBaseTest {
	private static Logger LOG = Logger
			.getLogger(UserDataFromDbUnitDataLoaderTest.class);

	@Test
	public void getUsers() {
		try {
			setUserData("userdata01.xml");
			// setUserData("userdata02.xml");

			Session session = sessionFactory.getCurrentSession();
			Assert.assertNotNull(session);

			Transaction trans = session.beginTransaction();
			List<User> data = session.createCriteria(User.class).list();
			Assert.assertEquals(4, data.size());
			showDBData(data);

			trans.commit();
			session.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private void setUserData(String datafile) throws Exception, SQLException {
		String datapath = UserDataFromDbUnitDataLoaderTest.class.getClass()
				.getResource("/").getPath()
				+ datafile;
		LOG.info("Reading data from file: " + datapath);
		DataLoaderDbUnit.init(dataSource.getConnection(), datafile).insert();
		// DataLoaderDbUnit.init(dataSource.getConnection(),
		// datafile).cleanInsert();
	}

	private void showDBData(List<User> data) {
		LOG.info(".");
		LOG.info(". data.size(): " + data.size());
		for (User usert : data)
			LOG.info(usert);

		LOG.info(".");
	}

}
