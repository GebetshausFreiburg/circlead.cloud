package org.rogatio.circlead.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class PropertyUtil.
 * 
 * @author Matthias Wegner
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

	/** The application default role. */
	public static String APPLICATION_DEFAULT_ROLE_REPORT = "application.default.role.report";

	/** The runtime lastmodified date. */
	public static String RUNTIME_LASTMODIFIED_DATE = "application.lastmodified";

	/** The mail host. */
	public static String MAIL_HOST = "mail.host";

	/** The mail port. */
	public static String MAIL_PORT = "mail.port";

	/** The mail sender. */
	public static String MAIL_SENDER = "mail.sender";

	/** The application update mode. */
	public static String APPLICATION_UPDATE_MODE = "application.mode.update";

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

	/** The webserver port. */
	public static String WEBSERVER_PORT = "webserver.port";

	/** The webserver url. */
	public static String WEBSERVER_URL = "webserver.url";

	/** The webserver landingpage. */
	public static String WEBSERVER_LANDINGPAGE = "webserver.landingpage";

	/** The webserver directory. */
	public static String WEBSERVER_DIRECTORY = "webserver.directory";

	/** The application default role corememberorganisation. */
	public static String APPLICATION_DEFAULT_ROLE_COREMEMBERORGANISATION = "application.default.role.core";

	/** The application specialized char. */
	public static String APPLICATION_SPECIALIZED_CHAR = "application.specialized.char";

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(PropertyUtil.class);

	/** The Constant APPLICATION_DISPLAY_TEAM_LEVEL. */
	private static final String APPLICATION_DISPLAY_TEAM_LEVEL = "application.display.team.level";

	/** The instance. */
	private static PropertyUtil instance = null;

	/** The ApplicationProperties. */
	private Properties applicationProperties = new Properties();

	/** The runtime properties. */
	private Properties runtimeProperties = new Properties();

	static {
		LOGGER.info("" + PropertyUtil.getInstance().getApplicationValue(APPLICATION_NAME) + " (v"
				+ PropertyUtil.getInstance().getApplicationValue(APPLICATION_VERSION) + ")");
		LOGGER.info("Default Rolegroup: " + PropertyUtil.getInstance().getApplicationDefaultRolegroup());
		LOGGER.info("Default Teamcategory: " + PropertyUtil.getInstance().getApplicationDefaultTeamcategory());
		LOGGER.info("Application-Update-Mode is '" + PropertyUtil.getInstance().getApplicationUpdateMode() + "'");

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
		String runtimeConfigPath = currentDir + File.separatorChar + "runtime.properties";
		try {
			applicationProperties.load(new FileInputStream(appConfigPath));
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		try {
			runtimeProperties.load(new FileInputStream(runtimeConfigPath));
		} catch (FileNotFoundException e) {
			LOGGER.info("Runtime-Properties not set yet.");
		} catch (IOException e) {
			LOGGER.info("Runtime-Properties not set yet.");
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
	 * Gets the runtime modified date.
	 *
	 * @return the runtime modified date
	 */
	public Date getRuntimeModifiedDate() {
		String sDate = getRuntimeValue(RUNTIME_LASTMODIFIED_DATE);

		if (sDate == null) {
			return null;
		}

		if (sDate != null) {
			sDate = sDate.trim();
		}

		try {
			if (Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", sDate)) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
				return date;
			}
			if (Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}", sDate)) {
				Date date = new SimpleDateFormat("yyyy-MM-dd kk:mm").parse(sDate);
				return date;
			}
			if (Pattern.matches("[0-9]{4}.[0-9]{2}.[0-9]{2}", sDate)) {
				Date date = new SimpleDateFormat("yyyy.MM.dd").parse(sDate);
				return date;
			}
			if (Pattern.matches("[0-9]{4}.[0-9]{2}.[0-9]{2} [0-9]{2}:[0-9]{2}", sDate)) {
				Date date = new SimpleDateFormat("yyyy.MM.dd kk:mm").parse(sDate);
				return date;
			}
		} catch (ParseException e) {
			LOGGER.error("Last Modified Runtime Date '" + sDate + "' could not be parsed.");
		}
		return null;
	}

	/**
	 * Gets the runtime value.
	 *
	 * @param propKey the prop key
	 * @return the runtime value
	 */
	public String getRuntimeValue(String propKey) {
		return this.runtimeProperties.getProperty(propKey);
	}

	/**
	 * Sets the runtime modified date to actual date (+1 minute).
	 */
	public void setRuntimeModifiedDateToActual() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 1);
		setRuntimeModifiedDate(c.getTime());
	}

	/**
	 * Sets the runtime modified date.
	 *
	 * @param date the new runtime modified date
	 */
	public void setRuntimeModifiedDate(Date date) {
		DateFormat form = new SimpleDateFormat("yyyy.MM.dd kk':'mm");
		String s = form.format(date);
//		System.out.println(s);
		try {
			Properties props = new Properties();
			props.setProperty(RUNTIME_LASTMODIFIED_DATE, s);
			File f = new File("runtime.properties");
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Application Runtime Properties - Generated from Circlead");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the value.
	 *
	 * @param propKey the prop key
	 * @return the value
	 */
	public String getApplicationValue(String propKey) {
		return this.applicationProperties.getProperty(propKey);
	}

	/**
	 * Gets the int value.
	 *
	 * @param propKey the prop key
	 * @return the int value
	 */
	public Integer getIntValue(String propKey) {
		String val = this.applicationProperties.getProperty(propKey);
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
		String val = this.applicationProperties.getProperty(propKey);
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
	 * Checks if is application display team level.
	 *
	 * @return the boolean
	 */
	public Boolean isApplicationDisplayTeamLevel() {
		return getBooleanValue(APPLICATION_DISPLAY_TEAM_LEVEL);
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
		return getApplicationValue(ATLASSIAN_CONFLUENCE_URL);
	}

	/**
	 * Gets the jira url.
	 *
	 * @return the jira url
	 */
	public String getJiraUrl() {
		return getApplicationValue(ATLASSIAN_JIRA_URL);
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
	 * Checks if is application update mode full.
	 *
	 * @return true, if is application update mode full
	 */
	public boolean isApplicationUpdateModeFull() {
		String s = getApplicationUpdateMode();
		if (s != null) {
			if (s.equalsIgnoreCase("FULL")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is application update mode O incremental.
	 *
	 * @return true, if is application update mode O incremental
	 */
	public boolean isApplicationUpdateModeIncremental() {
		String s = getApplicationUpdateMode();
		if (s != null) {
			if (s.equalsIgnoreCase("INCREMENTAL")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the application update mode.
	 *
	 * @return the application update mode
	 */
	public String getApplicationUpdateMode() {
		return getApplicationValue(APPLICATION_UPDATE_MODE);
	}

	/**
	 * Gets the application default teamcategory.
	 *
	 * @return the application default teamcategory
	 */
	public String getApplicationDefaultTeamcategory() {
		return getApplicationValue(APPLICATION_DEFAULT_TEAMCATEGORY);
	}

	/**
	 * Gets the application default Role.
	 *
	 * @return the application default Role
	 */
	public String getApplicationDefaultRoleReport() {
		return getApplicationValue(APPLICATION_DEFAULT_ROLE_REPORT);
	}

	/**
	 * Gets the application default role core member.
	 *
	 * @return the application default role core member
	 */
	public String getApplicationDefaultRoleCoreMember() {
		return getApplicationValue(APPLICATION_DEFAULT_ROLE_COREMEMBERORGANISATION);
	}

	/**
	 * Gets the application specialized char.
	 *
	 * @return the application specialized char
	 */
	public String getApplicationSpecializedChar() {
		String c = getApplicationValue(APPLICATION_SPECIALIZED_CHAR);
		if (c == null) {
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
		return getApplicationValue(APPLICATION_DEFAULT_ROLEGROUP);
	}

	/**
	 * Gets the dropbox team username.
	 *
	 * @return the dropbox team username
	 */
	public String getDropboxTeamUsername() {
		return getApplicationValue(DROPBOX_TEAM_DISPLAYUSERNAME);
	}

	/**
	 * Gets the atlassian user.
	 *
	 * @return the atlassian user
	 */
	public String getAtlassianUser() {
		return getApplicationValue(ATLASSIAN_LOGIN_USER);
	}

	/**
	 * Gets the atlassian password.
	 *
	 * @return the atlassian password
	 */
	public String getAtlassianPassword() {
		return getApplicationValue(ATLASSIAN_LOGIN_PASSWORD);
	}

	/**
	 * Gets the dropbox accesstoken.
	 *
	 * @return the dropbox accesstoken
	 */
	public String getDropboxAccesstoken() {
		return getApplicationValue(DROPBOX_CREDENTIALS_ACCESSTOKEN);
	}

	/**
	 * Gets the webserver directory.
	 *
	 * @return the webserver directory
	 */
	public String getWebserverDirectory() {
		return getApplicationValue(WEBSERVER_DIRECTORY);
	}

	/**
	 * Gets the webserver landingpage.
	 *
	 * @return the webserver landingpage
	 */
	public String getWebserverLandingpage() {
		return getApplicationValue(WEBSERVER_LANDINGPAGE);
	}

	/**
	 * Gets the webserver url.
	 *
	 * @return the webserver url
	 */
	public String getWebserverUrl() {
		return getApplicationValue(WEBSERVER_URL);
	}

	/**
	 * Gets the webserver port.
	 *
	 * @return the webserver port
	 */
	public int getWebserverPort() {
		return getIntValue(WEBSERVER_PORT);
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
		return getApplicationValue(MAIL_PASSWORD);
	}

	/**
	 * Gets the mail user.
	 *
	 * @return the mail user
	 */
	public String getMailUser() {
		return getApplicationValue(MAIL_USERNAME);
	}

	/**
	 * Gets the mail host.
	 *
	 * @return the mail host
	 */
	public String getMailHost() {
		return getApplicationValue(MAIL_HOST);
	}

	/**
	 * Gets the mail sender.
	 *
	 * @return the mail sender
	 */
	public String getMailSender() {
		return getApplicationValue(MAIL_SENDER);
	}

}
