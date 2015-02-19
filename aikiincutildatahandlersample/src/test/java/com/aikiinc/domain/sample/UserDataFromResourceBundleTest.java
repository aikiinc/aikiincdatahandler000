package com.aikiinc.domain.sample;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.data.handler.DataLoaderResourceBundle;
import com.aikiinc.dbunit.test.base.DbUnitBaseTest;
import com.aikiinc.domain.sample.User;

/**
 * Add a test user data from a property file entry: User.properties. The file is
 * in com.aikiinc.domain.sample in the resource/ directory.
 * 
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author: Philip Jahmani Chauvet.
 * @Dated: Nov 05, 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-connection.xml",
		"classpath:applicationContext-dao.xml" })
public class UserDataFromResourceBundleTest extends DbUnitBaseTest {
	private static Logger LOG = Logger
			.getLogger(UserDataFromResourceBundleTest.class);

	@Test
	public void showUserFromProperty() {
		User user = getTestUserFromProperty();

		LOG.info("->: " + user);
	}

	@Test
	public void insertAndShowDBData() {
		try {
			Session session = sessionFactory.getCurrentSession();
			Assert.assertNotNull(session);

			// Save user read from property to DB
			User user = getTestUserFromProperty();
			session.save(user);
			session.flush();

			// Let's see it from the DB
			LOG.info(".");
			Transaction trans = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<User> data = session.createCriteria(User.class).list();
			showDBData(data);

			trans.commit();
			session.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private User getTestUserFromProperty() {
		User user = (User) DataLoaderResourceBundle.init(new User()).showData()
				.execute();

		return user;
	}

	private void showDBData(List<User> data) {
		LOG.info(".");
		LOG.info(". data.size(): " + data.size());
		for (User usert : data)
			LOG.info(usert);

		LOG.info(".");
	}

}
