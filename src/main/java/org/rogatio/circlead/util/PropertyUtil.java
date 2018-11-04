package org.rogatio.circlead.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyUtil {

	public static String APPLICATION_VERSION = "application.version";
	public static String APPLICATION_NAME = "application.name";
	public static String DROPBOX_CREDENTIALS_KEY = "dropbox.credentials.key";
	public static String DROPBOX_CREDENTIALS_SECRET = "dropbox.credentials.secret";
	public static String DROPBOX_CREDENTIALS_ACCESSTOKEN = "dropbox.credentials.accesstoken";

	public static String ATLASSIAN_CONFLUENCE_URL = "atlassian.confluence.url";
	public static String ATLASSIAN_JIRA_URL = "atlassian.jira.url";
	public static String ATLASSIAN_LOGIN_USER = "atlassian.login.user";
	public static String ATLASSIAN_LOGIN_PASSWORD = "atlassian.login.password";
	public static String ATLASSIAN_SERVER_DEDICATED = "atlassian.server.dedicated";
	public static String ATLASSIAN_QUERY_LIMIT = "atlassian.query.limit";

	private final static Logger LOGGER = LogManager.getLogger(PropertyUtil.class);

	private static PropertyUtil instance = null;

	private Properties props = new Properties();

	static {
		LOGGER.info("URL of Confluence is set to '"+PropertyUtil.getInstance().getConfluenceUrl()+"'");
		LOGGER.info("USER is set to '"+PropertyUtil.getInstance().getAtlassianUser()+"'");
		
		boolean pswdNotSet = true;
		if (PropertyUtil.getInstance().getAtlassianPassword()!=null) {
			if (!PropertyUtil.getInstance().getAtlassianPassword().equalsIgnoreCase("password")) {
				LOGGER.info("PASSWORD is set");		
				pswdNotSet = false;
			}
		}
		if (pswdNotSet) {
			LOGGER.error("PASSWORD is NOT set");	
		}
		
		LOGGER.info("LIMIT for search results and Index is set to '"+PropertyUtil.getInstance().getAtlassianQueryLimit()+"'");
	}
	
	private PropertyUtil() {
		String currentDir = System.getProperty("user.dir");
		String appConfigPath = currentDir + File.separatorChar + "circlead.properties";
		try {
			props.load(new FileInputStream(appConfigPath));
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}

	}

	public static synchronized PropertyUtil getInstance() {
		if (instance == null)
			instance = new PropertyUtil();
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}

	public Integer getIntValue(String propKey) {
		String val = this.props.getProperty(propKey);
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			LOGGER.error("Error loading property '"+propKey+"'. Value '"+val+"' is invalid.");
			return null;
		}
	}

	public Boolean getBooleanValue(String propKey) {
		String val = this.props.getProperty(propKey);
		try {
			return Boolean.parseBoolean(val);
		} catch (Exception e) {
			LOGGER.error("Error loading property '"+propKey+"'. Value '"+val+"' is invalid.");
			return null;
		}
	}
	
	public Boolean isDedicatedServer() {
		return getBooleanValue(ATLASSIAN_SERVER_DEDICATED);
	}
	
	public String getConfluenceUrl() {
		return getValue(ATLASSIAN_CONFLUENCE_URL);
	}
	
	public String getJiraUrl() {
		return getValue(ATLASSIAN_JIRA_URL);
	}
	
	public Integer getAtlassianQueryLimit() {
		return getIntValue(ATLASSIAN_QUERY_LIMIT);
	}
	
	public String getAtlassianUser() {
		return getValue(ATLASSIAN_LOGIN_USER);
	}
	
	public String getAtlassianPassword() {
		return getValue(ATLASSIAN_LOGIN_PASSWORD);
	}
	
	public String getDropboxAccesstoken() {
		return getValue(DROPBOX_CREDENTIALS_ACCESSTOKEN);
	}
	
}
