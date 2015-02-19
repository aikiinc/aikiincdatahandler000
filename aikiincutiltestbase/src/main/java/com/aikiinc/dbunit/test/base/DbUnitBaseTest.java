package com.aikiinc.dbunit.test.base;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.orm.hibernate4.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Base DBUnit test configuration from a spring context file.<br>
 * Spring context file has these basic dependency injection configuration:<br>
 * &nbsp;&nbsp;&nbsp;PropertyPlaceholderConfigurer propertyConfigurer<br>
 * &nbsp;&nbsp;&nbsp;DataSource dataSource<br>
 * &nbsp;&nbsp;&nbsp;PropertiesFactoryBean hibernateProperties<br>
 * 
 * @See the sample test/resources/applicationContext-connection.xml
 *
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Author: Philip Jahmani Chauvet.
 * @Dated Sep 23, 2013
 */

public class DbUnitBaseTest {
    private static Logger LOG = Logger.getLogger(DbUnitBaseTest.class);
    private static Properties properties;
    @Autowired
    protected PropertyPlaceholderConfigurer propertyConfigurer;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    protected PropertiesFactoryBean hibernateProperties;

    @Before
    public void setUp() throws DbUnitBaseTestException {
	try {
	    TransactionSynchronizationManager.bindResource(sessionFactory,
		    new SessionHolder(sessionFactory.openSession()));

	    LOG.info("Hibernate property(hibernate.hbm2ddl.auto) setting:  "
		    + getHibernateProperty("hibernate.hbm2ddl.auto"));
	} catch (Exception e) {
	    throw new DbUnitBaseTestException(e.getMessage());
	}
    }

    @After
    public void tearDown() throws DbUnitBaseTestException {
	try {
	    SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
		    .unbindResource(sessionFactory);
	    SessionFactoryUtils.closeSession(sessionHolder.getSession());
	} catch (Exception e) {
	    throw new DbUnitBaseTestException(e.getMessage());
	}
    }

    /**
     * Read a hibernate property from the defined spring context
     * hibernateProperties configuration
     * 
     * @param prop
     * @return
     */
    public String getHibernateProperty(String prop) {
	String propval = null;
	try {
	    if (properties == null)
		properties = hibernateProperties.getObject();
	    propval = properties.getProperty(prop);
	} catch (Exception e) {
	}

	return propval;
    }

}
