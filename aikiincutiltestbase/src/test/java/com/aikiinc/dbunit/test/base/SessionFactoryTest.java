package com.aikiinc.dbunit.test.base;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Sep 23, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-connection.xml" })
public class SessionFactoryTest extends DbUnitBaseTest {
	private static Logger LOG = Logger.getLogger(SessionFactoryTest.class);

	@Test
	public void getSessionFactory() {
		Assert.assertNotNull(sessionFactory);
	}

	@Test
	public void getSession() {
		Session session = sessionFactory.getCurrentSession();
		Assert.assertNotNull(session);

		LOG.info(".");
		LOG.info("session: " + session);
		LOG.info(".");
	}

}
