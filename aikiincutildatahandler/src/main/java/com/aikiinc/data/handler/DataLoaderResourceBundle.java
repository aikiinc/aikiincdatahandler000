package com.aikiinc.data.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Get data for an entity, pojo, by loading the data from a property file. The
 * java.util.ResourceBundle and the Apache BeanUtils does the magic. Easy
 * approach to loading data into a pojo. Chaining technique is used to access
 * action while populating data:
 * <p>
 * 
 * We use the code as such:<br>
 * &nbsp;&nbsp;&nbsp;User user = (User) DataLoaderResourceBundle.init(new
 * User()).showData().execute();<br>
 * &nbsp;&nbsp;&nbsp;Notice that the showData() is chained before the execute()
 *
 * @Copyright (C) Aiki Innovations Inc 2013-2015 - http://www.aikiinc.com
 * @Author Philip Jahmani Chauvet.
 * @Dated Oct 02, 2013 - Oct 05, 2013
 */
public class DataLoaderResourceBundle {
	private static final Logger LOG = Logger
			.getLogger(DataLoaderResourceBundle.class);

	public DataLoaderResourceBundle() {
	}

	/**
	 * Initialize the chain of command with an entity object
	 * 
	 * @param object
	 * @return
	 */
	public static ResourceBundlerCommand init(Object object) {
		return new ResourceBundlerCommand(object);
	}

	/**
	 * We are designing by interface
	 * 
	 * @author Philip Jahmani Chauvet
	 */
	public interface ResourceBundleChain extends Chain {
		/**
		 * public ResourceBundle getResourceBundle() - Get access to a pojo's
		 * resource bundle. The resources bundle is a properties file placed in
		 * the same package directory as the pojo. I usually create a package in
		 * the resource/ directory and the place the property file there.
		 * 
		 * @return
		 */
		ResourceBundle getResourceBundle();
	}

	public static class ResourceBundlerCommand implements ResourceBundleChain {
		private ResourceBundle rb;
		private Map<String, String> props = new HashMap<String, String>();
		private Object entity;

		public ResourceBundlerCommand(Object obj) {
			this.entity = obj;
			setResourceBundle();
		}

		/**
		 * Set the resource bundle for this class.
		 */
		private void setResourceBundle() {
			String className = entity.getClass().getName();
			try {
				rb = ResourceBundle.getBundle(className);
				setProperties();
			} catch (MissingResourceException mre) {
				LOG.warn("Resource bundle was not found: " + className);
			}
		}

		private void setProperties() {
			try {
				Enumeration<String> keys = rb.getKeys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					props.put(key, rb.getString(key));
				}

				BeanUtils.copyProperties(entity, props);
			} catch (Exception e) {
				LOG.warn("Properties were not loaded for: "
						+ entity.getClass().getSimpleName());
			}
		}

		/**
		 * Show the properties loaded for the entity, if properies exist.
		 * 
		 * @return Chain command
		 */
		public ResourceBundleChain showData() {
			Enumeration<String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				LOG.info(key + "=" + rb.getString(key));
			}

			return this;
		}

		/**
		 * Finally get the entity with the loaded properties, if any.<br>
		 * 
		 * @return
		 */
		public Object execute() {
			return entity;
		}

		/**
		 * Get the resource bundle to access the properties in the bundle<br>
		 * 
		 * @return
		 */
		public ResourceBundle getResourceBundle() {
			return rb;
		}

	}

}
