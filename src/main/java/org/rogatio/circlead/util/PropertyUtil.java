package org.rogatio.circlead.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class PropertyUtil.
 */
public class PropertyUtil {

	/** The application version. */
	public static String APPLICATION_VERSION = "application.version";
	
	/** The application name. */
	public static String APPLICATION_NAME = "application.name";
	
	/** The dropbox credentials key. */
	public static String DROPBOX_CREDENTIALS_KEY = "dropbox.credentials.key";
	
	/** The dropbox credentials secret. */
	public static String DROPBOX_CREDENTIALS_SECRET = "dropbox.credentials.secret";
	
	/** The dropbox credentials accesstoken. */
	public static String DROPBOX_CREDENTIALS_ACCESSTOKEN = "dropbox.credentials.accesstoken";
	
	/** The dropbox team displayusername. */
	public static String DROPBOX_TEAM_DISPLAYUSERNAME = "dropbox.credentials.team.username";
	
	/** The application default rolegroup. */
	public static String APPLICATION_DEFAULT_ROLEGROUP = "application.default.rolegroup";
	
	/** The application default teamcategory. */
	public static String APPLICATION_DEFAULT_TEAMCATEGORY = "application.default.team.category";

	/** The mail host. */
	public static String MAIL_HOST = "mail.host";
	
	/** The mail port. */
	public static String MAIL_PORT = "mail.port";
	
	/** The mail sender. */
	public static String MAIL_SENDER = "mail.sender";
	
	/** The mail username. */
	public static String MAIL_USERNAME = "mail.username";
	
	/** The mail password. */
	public static String MAIL_PASSWORD = "mail.password";

	/** The file synchronizer enabled. */
	public static String FILE_SYNCHRONIZER_ENABLED = "file.synchronizer.enabled";
	
	/** The file synchronizer read. */
	public static String FILE_SYNCHRONIZER_READ = "file.synchronizer.read";
	
	/** The file synchronizer write. */
	public static String FILE_SYNCHRONIZER_WRITE = "file.synchronizer.write";

	/** The atlassian synchronizer enabled. */
	public static String ATLASSIAN_SYNCHRONIZER_ENABLED = "atlassian.synchronizer.enabled";
	
	/** The atlassian synchronizer read. */
	public static String ATLASSIAN_SYNCHRONIZER_READ = "atlassian.synchronizer.read";
	
	/** The atlassian synchronizer write. */
	public static String ATLASSIAN_SYNCHRONIZER_WRITE = "atlassian.synchronizer.write";

	/** The atlassian confluence url. */
	public static String ATLASSIAN_CONFLUENCE_URL = "atlassian.confluence.url";
	
	/** The atlassian jira url. */
	public static String ATLASSIAN_JIRA_URL = "atlassian.jira.url";
	
	/** The atlassian login user. */
	public static String ATLASSIAN_LOGIN_USER = "atlassian.login.user";
	
	/** The atlassian login password. */
	public static String ATLASSIAN_LOGIN_PASSWORD = "atlassian.login.password";
	
	/** The atlassian server dedicated. */
	public static String ATLASSIAN_SERVER_DEDICATED = "atlassian.server.dedicated";
	
	/** The atlassian query limit. */
	public static String ATLASSIAN_QUERY_LIMIT = "atlassian.query.limit";

	public static String APPLICATION_SPECIALIZED_CHAR = "application.specialized.char";
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(PropertyUtil.class);

	/** The instance. */
	private static PropertyUtil instance = null;

	/** The props. */
	private Properties props = new Properties();

	static {
		LOGGER.info("" + PropertyUtil.getInstance().getValue(APPLICATION_NAME) + " (v"
				+ PropertyUtil.getInstance().getValue(APPLICATION_VERSION) + ")");
		LOGGER.info("Default Rolegroup: " + PropertyUtil.getInstance().getApplicationDefaultRolegroup());
		LOGGER.info("Default Teamcategory: " + PropertyUtil.getInstance().getApplicationDefaultTeamcategory());

		if (PropertyUtil.getInstance().isAtlassianSynchronizerEnabled()) {
			LOGGER.info("URL of Jira is set to '" + PropertyUtil.getInstance().getJiraUrl() + "'");
			LOGGER.info("URL of Confluence is set to '" + PropertyUtil.getInstance().getConfluenceUrl() + "'");
			LOGGER.info("USER is set to '" + PropertyUtil.getInstance().getAtlassianUser() + "'");

			boolean pswdNotSet = true;
			if (PropertyUtil.getInstance().getAtlassianPassword() != null) {
				if (!PropertyUtil.getInstance().getAtlassianPassword().equalsIgnoreCase("password")) {
					LOGGER.info("PASSWORD is set");
					pswdNotSet = false;
				}
			}
			if (pswdNotSet) {
				LOGGER.error("PASSWORD is NOT set");
			}
		}

		LOGGER.info("LIMIT for search results and Index is set to '"
				+ PropertyUtil.getInstance().getAtlassianQueryLimit() + "'");
	}

	/**
	 * Instantiates a new property util.
	 */
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

	/**
	 * Gets the single instance of PropertyUtil.
	 *
	 * @return single instance of PropertyUtil
	 */
	public static synchronized PropertyUtil getInstance() {
		if (instance == null)
			instance = new PropertyUtil();
		return instance;
	}

	/**
	 * Gets the value.
	 *
	 * @param propKey the prop key
	 * @return the value
	 */
	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}

	/**
	 * Gets the int value.
	 *
	 * @param propKey the prop key
	 * @return the int value
	 */
	public Integer getIntValue(String propKey) {
		String val = this.props.getProperty(propKey);
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			LOGGER.error("Error loading property '" + propKey + "'. Value '" + val + "' is invalid.");
			return null;
		}
	}

	/**
	 * Gets the boolean value.
	 *
	 * @param propKey the prop key
	 * @return the boolean value
	 */
	public Boolean getBooleanValue(String propKey) {
		String val = this.props.getProperty(propKey);
		try {
			return Boolean.parseBoolean(val);
		} catch (Exception e) {
			LOGGER.error("Error loading property '" + propKey + "'. Value '" + val + "' is invalid.");
			return null;
		}
	}

	/**
	 * Checks if is file synchronizer enabled.
	 *
	 * @return the boolean
	 */
	public Boolean isFileSynchronizerEnabled() {
		return getBooleanValue(FILE_SYNCHRONIZER_ENABLED);
	}

	/**
	 * Checks if is file synchronizer read mode.
	 *
	 * @return the boolean
	 */
	public Boolean isFileSynchronizerReadMode() {
		return getBooleanValue(FILE_SYNCHRONIZER_READ);
	}

	/**
	 * Checks if is file synchronizer write mode.
	 *
	 * @return the boolean
	 */
	public Boolean isFileSynchronizerWriteMode() {
		return getBooleanValue(FILE_SYNCHRONIZER_WRITE);
	}

	/**
	 * Checks if is atlassian synchronizer enabled.
	 *
	 * @return the boolean
	 */
	public Boolean isAtlassianSynchronizerEnabled() {
		return getBooleanValue(ATLASSIAN_SYNCHRONIZER_ENABLED);
	}

	/**
	 * Checks if is atlassian synchronizer read mode.
	 *
	 * @return the boolean
	 */
	public Boolean isAtlassianSynchronizerReadMode() {
		return getBooleanValue(ATLASSIAN_SYNCHRONIZER_READ);
	}

	/**
	 * Checks if is atlassian synchronizer write mode.
	 *
	 * @return the boolean
	 */
	public Boolean isAtlassianSynchronizerWriteMode() {
		return getBooleanValue(ATLASSIAN_SYNCHRONIZER_WRITE);
	}

	/**
	 * Checks if is dedicated server.
	 *
	 * @return the boolean
	 */
	public Boolean isDedicatedServer() {
		return getBooleanValue(ATLASSIAN_SERVER_DEDICATED);
	}

	/**
	 * Gets the confluence url.
	 *
	 * @return the confluence url
	 */
	public String getConfluenceUrl() {
		return getValue(ATLASSIAN_CONFLUENCE_URL);
	}

	/**
	 * Gets the jira url.
	 *
	 * @return the jira url
	 */
	public String getJiraUrl() {
		return getValue(ATLASSIAN_JIRA_URL);
	}

	/**
	 * Gets the atlassian query limit.
	 *
	 * @return the atlassian query limit
	 */
	public Integer getAtlassianQueryLimit() {
		return getIntValue(ATLASSIAN_QUERY_LIMIT);
	}

	/**
	 * Gets the application default teamcategory.
	 *
	 * @return the application default teamcategory
	 */
	public String getApplicationDefaultTeamcategory() {
		return getValue(APPLICATION_DEFAULT_TEAMCATEGORY);
	}
	
	public String getApplicationSpecializedChar() {
		String c = getValue(APPLICATION_SPECIALIZED_CHAR);
		if (c==null) {
			return "UNKNOWN_SPECIALIZED_CHAR";
		}
		return c.trim();
	}

	/**
	 * Gets the application default rolegroup.
	 *
	 * @return the application default rolegroup
	 */
	public String getApplicationDefaultRolegroup() {
		return getValue(APPLICATION_DEFAULT_ROLEGROUP);
	}

	/**
	 * Gets the dropbox team username.
	 *
	 * @return the dropbox team username
	 */
	public String getDropboxTeamUsername() {
		return getValue(DROPBOX_TEAM_DISPLAYUSERNAME);
	}

	/**
	 * Gets the atlassian user.
	 *
	 * @return the atlassian user
	 */
	public String getAtlassianUser() {
		return getValue(ATLASSIAN_LOGIN_USER);
	}

	/**
	 * Gets the atlassian password.
	 *
	 * @return the atlassian password
	 */
	public String getAtlassianPassword() {
		return getValue(ATLASSIAN_LOGIN_PASSWORD);
	}

	/**
	 * Gets the dropbox accesstoken.
	 *
	 * @return the dropbox accesstoken
	 */
	public String getDropboxAccesstoken() {
		return getValue(DROPBOX_CREDENTIALS_ACCESSTOKEN);
	}

	/**
	 * Gets the mail port.
	 *
	 * @return the mail port
	 */
	public Integer getMailPort() {
		return getIntValue(MAIL_PORT);
	}

	/**
	 * Gets the mail password.
	 *
	 * @return the mail password
	 */
	public String getMailPassword() { 
		return getValue(MAIL_PASSWORD); 
	}

	/**
	 * Gets the mail user.
	 *
	 * @return the mail user
	 */
	public String getMailUser() {
		return getValue(MAIL_USERNAME);
	}

	/**
	 * Gets the mail host.
	 *
	 * @return the mail host
	 */
	public String getMailHost() {
		return getValue(MAIL_HOST);
	}

	/**
	 * Gets the mail sender.
	 *
	 * @return the mail sender
	 */
	public String getMailSender() {
		return getValue(MAIL_SENDER);
	}

}
