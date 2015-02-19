package com.aikiinc.data.handler;

import java.util.List;

import org.apache.log4j.Logger;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aikiinc.data.handler.DataLoaderDbUnit;
import com.aikiinc.data.handler.DataLoaderDbUnit.DbUnitChain;
import com.aikiinc.data.handler.DataLoaderDbUnit.DbUnitCommand;
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
public class DataLoaderDbUnitTest extends DbUnitBaseTest {
	Logger LOG = Logger.getLogger(DataLoaderDbUnitTest.class);

	@Test
	public void showData() {
		try {
			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata01.xml")
					.showData();
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void showData02() {
		try {
			DbUnitChain dchain = DataLoaderDbUnit.init(
					dataSource.getConnection(), "userdata02.xml");
			dchain.showData();
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void getIDataSet() {
		try {
			IDataSet dataSet = (IDataSet) DataLoaderDbUnit
					.init(dataSource.getConnection(), "userdata01.xml")
					.cleanInsert().execute();

			// Get table IDataSet
			ITable[] tables = dataSet.getTables();
			for (ITable table : tables) {
				LOG.info("Table Rows: " + table.getRowCount());
				LOG.info(table.toString());
				LOG.info(table.getTableMetaData());
				// Get first row table value
				LOG.info(table.getValue(0, "username"));
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void insertDataAndShowDBData() {
		try {
			Session session = sessionFactory.getCurrentSession();
			Assert.assertNotNull(session);

			Transaction trans = session.beginTransaction();

			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata02.xml")
					.insert();
			session.flush();

			List<User> data = session.createCriteria(User.class).list();
			Assert.assertEquals(4, data.size());
			showDBData(data);

			trans.commit();
			session.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void cleanInsertDataAndShowDBData() {
		try {
			Session session = sessionFactory.getCurrentSession();
			Assert.assertNotNull(session);

			Transaction trans = session.beginTransaction();

			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata02.xml")
					.cleanInsert();
			session.flush();

			List<User> data = session.createCriteria(User.class).list();
			Assert.assertEquals(4, data.size());
			showDBData(data);

			trans.commit();
			session.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void insertAndDeleteAndShowDBData() {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();

			// Clean table and insert 4 rows
			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata01.xml")
					.cleanInsert().execute();
			session.flush();
			List<User> data = session.createCriteria(User.class).list();
			Assert.assertEquals(4, data.size());
			showDBData(data);

			// Insert 4 more rows
			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata02.xml")
					.insert().execute();
			session.flush();
			data = session.createCriteria(User.class).list();
			Assert.assertEquals(8, data.size());
			showDBData(data);

			// Delete 1st 4 data rows
			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata01.xml")
					.delete();
			session.flush();
			data = session.createCriteria(User.class).list();
			Assert.assertEquals(4, data.size());

			// Delete remaining 4 data rows
			DataLoaderDbUnit.init(dataSource.getConnection(), "userdata02.xml")
					.delete();
			session.flush();
			data = session.createCriteria(User.class).list();
			Assert.assertEquals(0, data.size());

			// At this point the table is empty

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
