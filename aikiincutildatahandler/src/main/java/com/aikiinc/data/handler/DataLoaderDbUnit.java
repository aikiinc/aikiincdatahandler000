package com.aikiinc.data.handler;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * Populate database tables using DbUnit data files. Chaining technique is used
 * to access action while populating data:<br>
 * 
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Oct 02, 2013 - Oct 05, 2013
 */
public class DataLoaderDbUnit extends DataLoaderResourceBundle {
	private static final Logger LOG = Logger.getLogger(DataLoaderDbUnit.class);
	private static IDataSet dataSet;
	private static IDatabaseConnection con;

	/**
	 * Inititialize the chain of command with an entity object
	 * 
	 * @param jdbccon
	 *            The JDBC connection
	 * @param file
	 *            The DbUnit data file to load, best to place file in /resources
	 *            directory, or same directory as this class file.
	 * @return The DBUnitChain of commands
	 */
	public static DbUnitChain init(java.sql.Connection jdbccon, String file) {
		String filepath = DataLoaderResourceBundle.class.getClass()
				.getResource("/").getPath()
				+ file;

		return new DbUnitCommand(jdbccon, filepath);
	}

	/**
	 * We are designing by interface
	 * 
	 * @author Philip Jahmani Chauvet
	 */
	public interface DbUnitChain extends Chain {
		/**
		 * Insert data into the table using property file
		 * 
		 * @return The chain command
		 */
		public DbUnitChain insert();

		/**
		 * Clean and insert data into the table using property file
		 * 
		 * @return The chain command
		 */
		public DbUnitChain cleanInsert();

		/**
		 * Delete table data using data in the read property file
		 * 
		 * @return The chain command
		 */
		public DbUnitChain delete();
	}

	public static class DbUnitCommand implements DbUnitChain {

		public DbUnitCommand(java.sql.Connection jdbccon, String path) {
			try {
				con = new DatabaseConnection(jdbccon);
				dataSet = new FlatXmlDataSet(new FileInputStream(path));
			} catch (Exception e) {
				LOG.warn("Database connection was not made, or file was not loaded: "
						+ e.getMessage());
			}
		}

		/**
		 * DbUnit implementation shows the table names
		 * 
		 * @see com.aikiinc.data.handler.DataLoaderResourceBundle.ResourceBundleChain#showData()
		 */
		@Override
		public DbUnitChain showData() {
			try {
				String[] tables = dataSet.getTableNames();
				int j = 1;
				for (String table : tables)
					LOG.info("Table(" + (j++) + "): " + table);
			} catch (DataSetException e) {
				LOG.warn(e.getMessage());
			}

			return this;
		}

		/**
		 * Use DbUnit insert(), cleaninsert(), and other methods to manipulate
		 * the data.
		 * 
		 * This excecute() returns no pojo, but the DbUnit IDataSet to allow
		 * manipulation and investigation.
		 * 
		 * @see com.aikiinc.data.handler.DataLoaderResourceBundle.ResourceBundleChain#execute()
		 */
		@Override
		public IDataSet execute() {
			return dataSet;
		}

		/**
		 * User DatabaseOperation.INSERT to insert more data
		 */
		public DbUnitChain insert() {
			try {
				DatabaseOperation.INSERT.execute(con, dataSet);
			} catch (Throwable e) {
				LOG.error(e.getMessage());
			}

			return this;
		}

		/**
		 * User DatabaseOperation.CLEAN_INSERT to clean and insert new data
		 */
		public DbUnitChain cleanInsert() {
			try {
				DatabaseOperation.CLEAN_INSERT.execute(con, dataSet);
			} catch (Throwable e) {
				LOG.error(e.getMessage());
			}

			return this;
		}

		/**
		 * User DatabaseOperation.DELETE to delete data
		 */
		public DbUnitChain delete() {
			try {
				DatabaseOperation.DELETE.execute(con, dataSet);
			} catch (Throwable e) {
				LOG.error(e.getMessage());
			}

			return this;
		}

	}

}
