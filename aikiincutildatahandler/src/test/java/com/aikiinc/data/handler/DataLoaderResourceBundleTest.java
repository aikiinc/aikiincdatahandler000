package com.aikiinc.data.handler;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.aikiinc.data.handler.DataLoaderResourceBundle;
import com.aikiinc.test.domain.User;

/**
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Oct 02, 2013 - Oct 05, 2013
 */
public class DataLoaderResourceBundleTest {
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

}
