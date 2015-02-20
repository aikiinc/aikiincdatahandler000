package com.aikiinc.data.handler;

import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

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
import com.aikiinc.test.domain.User;

/**
 *
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author: Philip Jahmani Chauvet.
 * @Dated Oct 02, 2013 - Oct 05, 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-connection.xml" })
public class DataLoaderResourceBundleTest extends DbUnitBaseTest {
    Logger LOG = Logger.getLogger(DataLoaderResourceBundleTest.class);

    @Test
    public void getUser() {
        User user = (User) DataLoaderResourceBundle.init(new User()).execute();
        LOG.info("->: " + user);
    }

    @Test
    public void showDataAndGetPojo() {
        User user = (User) DataLoaderResourceBundle.init(new User()).showData()
                .execute();
        LOG.info("->: " + user);
    }

    @Test
    public void showResourceBundle() {
        ResourceBundle rb = DataLoaderResourceBundle.init(new User())
                .showData().getResourceBundle();

        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            LOG.info(key + "=" + rb.getString(key));
        }
    }

    @Test
    public void insertDataFromProperty() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Assert.assertNotNull(session);

            Transaction trans = session.beginTransaction();

            User user = (User) DataLoaderResourceBundle.init(new User())
                    .execute();
            LOG.info("->: " + user);

            session.save(user);
            session.flush();

            List<User> data = session.createCriteria(User.class).list();
            Assert.assertEquals(1, data.size());
            showDBData(data);

            trans.commit();
            session.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private void showDBData(List<User> data) {
        LOG.info(".");
        LOG.info(". data.size(): " + data.size());
        for (User usert : data)
            LOG.info(usert);

        LOG.info(".");
    }

}
