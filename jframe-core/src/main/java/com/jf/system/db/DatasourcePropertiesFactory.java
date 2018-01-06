package com.jf.system.db;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

/**
 * 连接池加密
 * 
 * @author rick
 */
@SuppressWarnings("rawtypes")
public class DatasourcePropertiesFactory implements FactoryBean {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	public Class getObjectType() {
		return Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) throws Exception {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");

		String newUsername = DESUtil.decrypt(originalUsername).trim();
		properties.put("user", newUsername);

		String newPassword = DESUtil.decrypt(originalPassword).trim();
		properties.put("password", newPassword);
	}

}
