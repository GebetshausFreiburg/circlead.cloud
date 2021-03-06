/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.util.PropertyUtil;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ConfluenceClient.
 *
 * @author Matthias Wegner
 * 
 *         CLOUD
 * @see https://developer.atlassian.com/cloud/confluence/rest-api-examples/
 * @see https://developer.atlassian.com/cloud/confluence/rest/#api-content-get
 * 
 *      DEDICATED
 * @see https://docs.atlassian.com/atlassian-confluence/REST/6.6.0/
 */
public class ConfluenceClient extends HttpClient {

	/** The limit. */
	private final int LIMIT = PropertyUtil.getInstance().getIntValue(PropertyUtil.ATLASSIAN_QUERY_LIMIT);

	/** The rest prefix. Is different for cloud to dedicated server */
	private String restPrefix = null;

	/**
	 * Authentification is enabled with basic auth. Check "curl -v
	 * http://url-to-atlassian?os_authType=basic If basic auth enabled, the
	 * "WWW-Authenticate: Basic realm="protected-area" is returned
	 * 
	 * @param baseUrl  Url to the server, could be http or https
	 * @param user     Plain-Text of user to log in
	 * @param password Plain text of password
	 * @param server   true if dedicated server, false if cloud server
	 */
	public ConfluenceClient(String baseUrl, String user, String password, boolean server) {
		this.baseUrl = baseUrl;
		this.user = user;
		this.password = password;

		if (server) {
			// Set rest-prefix if atlassian-dedicated-server
			restPrefix = "rest/api/";
			// LOGGER.info("ConfluenceClient ist set to dedicated server");
		} else {
			// Set rest-prefix if atlassian-cloud-server
			restPrefix = "wiki/rest/api/";
			// LOGGER.info("ConfluenceClient ist set to cloud server");
		}
	}

	/**
	 * Instantiates a new confluence client.
	 */
	public ConfluenceClient() {
		this.baseUrl = PropertyUtil.getInstance().getConfluenceUrl();
		this.user = PropertyUtil.getInstance().getAtlassianUser();
		this.password = PropertyUtil.getInstance().getAtlassianPassword();

		if (PropertyUtil.getInstance().isDedicatedServer()) {
			// Set rest-prefix if atlassian-dedicated-server
			restPrefix = "rest/api/";
			// LOGGER.info("ConfluenceClient ist set to dedicated server");
		} else {
			// Set rest-prefix if atlassian-cloud-server
			restPrefix = "wiki/rest/api/";
			// LOGGER.info("ConfluenceClient ist set to cloud server");
		}
	}

	/**
	 * Delete version.
	 *
	 * @param pageId  the page id
	 * @param version the version
	 * @return the synchronizer result
	 */
	public SynchronizerResult deleteVersion(int pageId, int version) {
		try {
			return this.delete(restPrefix + "content/" + pageId + "/version/" + version);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Load attachment.
	 *
	 * @param pageId     the page id
	 * @param filename   the filename
	 * @param targetPath the target path
	 */
	public void loadAttachment(int pageId, String filename, String targetPath) {
		String mediaType = null;
		String downloadUrl = null;

		try {
			/*
			 * load json-results of attachments. rest-url sets filter to specific attachment
			 * with filename
			 */
			SynchronizerResult res = get("wiki/rest/api/content/" + pageId + "/child/attachment?filename=" + filename);

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			org.rogatio.circlead.control.synchronizer.atlassian.attachment.Results r = mapper.readValue(
					res.getContent(), org.rogatio.circlead.control.synchronizer.atlassian.attachment.Results.class);

			if (r.getResults().size() > 0) {
				mediaType = r.getResults().get(0).getExtensions().getMediaType();
				downloadUrl = r.getResults().get(0).getLinks().getDownload();
			}

			/*
			 * if result of attachment is found, then load
			 */
			if (downloadUrl != null && mediaType != null) {
				URL url = new URL(baseUrl + "wiki" + downloadUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "*/*");
				connection.setRequestProperty("Content-Type", mediaType);
				connection.setRequestProperty("Authorization", "Basic " + this.createCredentials());
				connection.setDoInput(true);
				connection.connect();

				InputStream is = connection.getInputStream();
				File f = new File(targetPath + filename);

				OutputStream os = new FileOutputStream(f);
				byte[] buf = new byte[1024];
				int len;

				while ((len = is.read(buf)) > 0) {
					os.write(buf, 0, len);
				}

				os.close();
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the content versions.
	 *
	 * @param id the id
	 * @return the content versions
	 */
	public SynchronizerResult getContentVersions(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "/version");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the page space.
	 *
	 * @param id the id
	 * @return the page space
	 */
	public SynchronizerResult getPageSpace(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=space");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the page version.
	 *
	 * @param id the id
	 * @return the page version
	 */
	public SynchronizerResult getPageVersion(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=version");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the page history.
	 *
	 * @param id the id
	 * @return the page history
	 */
	public SynchronizerResult getPageHistory(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=history,history.lastUpdated");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the page.
	 *
	 * @param id the id
	 * @return the page
	 */
	public SynchronizerResult getPage(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?"
					+ "expand=body.storage,metadata.labels,history,history.lastUpdated,space,version,ancestors");
		} catch (IOException e) {
			SynchronizerResult res = new SynchronizerResult();
			res.setMessage(e.getMessage());
			if (e.getMessage().contains("Server returned HTTP response code: 403 for URL")) {
				res.setCode(403);
			}
			return res;
		}
	}

	/**
	 * Gets the page content.
	 *
	 * @param id the id
	 * @return the page content
	 */
	public SynchronizerResult getPageContent(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=body.storage");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	public SynchronizerResult getCurrentUser() {
		try {
			return this.get(restPrefix + "user/current");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the systeminfo.
	 *
	 * @return the systeminfo
	 */
	public SynchronizerResult getSysteminfo() {
		try {
			return this.get(restPrefix + "settings/systemInfo");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the metadata.
	 *
	 * @param pageId the page id
	 * @return the metadata
	 */
	public SynchronizerResult getMetadata(int pageId) {
		try {
			return this.get(restPrefix + "content/" + pageId + "/property");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gets the rest prefix.
	 *
	 * @return the rest prefix
	 */
	public String getRestPrefix() {
		return restPrefix;
	}

	/**
	 * Purge page.
	 *
	 * @param pageId the page id
	 * @return the synchronizer result
	 */
	public SynchronizerResult purgePage(int pageId) {
		try {
			return this.delete(restPrefix + "content/" + pageId + "?status=trashed");
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Delete page.
	 *
	 * @param pageId the page id
	 * @return the synchronizer result
	 */
	public SynchronizerResult deletePage(int pageId) {
		try {
			return this.delete(restPrefix + "content/" + pageId);
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Delete metadata.
	 *
	 * @param pageId the page id
	 * @param key    the key
	 * @return the synchronizer result
	 */
	public SynchronizerResult deleteMetadata(int pageId, String key) {
		try {
			return this.delete(restPrefix + "content/" + pageId + "/property/" + key);
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Adds the label.
	 *
	 * @param pageId the page id
	 * @param label  the label
	 * @return the synchronizer result
	 */
	public SynchronizerResult addLabel(String pageId, String label) {
		try {
			int id = Integer.parseInt(pageId);
			return this.addLabel(id, label);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Adds the label.
	 *
	 * @param pageId the page id
	 * @param label  the label
	 * @return the synchronizer result
	 */
	public SynchronizerResult addLabel(int pageId, String label) {

		String data = "{" + "\"name\" : \"" + label + "\"," + "\"prefix\": \"global\"" + "}";

		try {
			SynchronizerResult response = this.post(restPrefix + "content/" + pageId + "/label", data);
			return response;
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Update metadata.
	 *
	 * @param pageId        the page id
	 * @param key           the key
	 * @param value         the value
	 * @param versionNumber the version number
	 * @param minorEdit     the minor edit
	 * @return the synchronizer result
	 */
	public SynchronizerResult updateMetadata(int pageId, String key, String value, int versionNumber,
			boolean minorEdit) {

		if (value.trim().startsWith("{") && value.trim().endsWith("}")) {
			value = value.trim();
		} else {
			value = "\"" + value + "\"";
		}

		String data = "{" + "\"value\" : " + value + "," + "\"version\": {" + "\"minorEdit\": " + minorEdit + ","
				+ "\"number\": " + versionNumber + "" + "}" + "}";

		try {
			return this.put(restPrefix + "content/" + pageId + "/property/" + key, data);
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Adds the metadata.
	 *
	 * @param pageId the page id
	 * @param key    the key
	 * @param value  the value
	 * @return the synchronizer result
	 */
	public SynchronizerResult addMetadata(int pageId, String key, String value) {

		if (value.trim().startsWith("{") && value.trim().endsWith("}")) {
			value = value.trim();
		} else {
			value = "\"" + value + "\"";
		}

		String data = "{ " + "\"key\" : \"" + key + "\", " + "\"value\" : " + value + "}";
		try {
			SynchronizerResult response = this.post(restPrefix + "content/" + pageId + "/property", data);
			return response;
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Search for content in confluence.
	 *
	 * @param cql the cql
	 * @return the synchronizer result
	 */
	public SynchronizerResult search(String cql) {
		try {
			String encoded = URLEncoder.encode(cql, "UTF-8");
			return this.get(restPrefix + "search?limit=" + LIMIT + "&cql=" + encoded);
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Browse content.
	 *
	 * @return the synchronizer result
	 * @see https://developer.atlassian.com/cloud/confluence/rest/#api-content-get
	 */
	public SynchronizerResult browseContent() {
		try {
			return this.get(restPrefix + "content?expand=space&limit=" + LIMIT);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * New page.
	 *
	 * @return the synchronizer result
	 */
	public SynchronizerResult newPage() {
		return newPage("Page " + UUID.randomUUID(), "TEST", "<p>This is a new page</p>");
	}

	/**
	 * New page.
	 *
	 * @param title    the title
	 * @param spaceKey the space key
	 * @param content  the content
	 * @return the synchronizer result
	 */
	public SynchronizerResult newPage(String title, String spaceKey, String content) {
		try {
			String data = "{\"type\":\"page\",\"title\":\"" + title + "\",\"space\":{\"key\":\"" + spaceKey
					+ "\"},\"body\":{\"storage\":{\"value\":\"" + content + "\",\"representation\":\"storage\"}}}";
			return this.post(restPrefix + "content/", data);
		} catch (IOException e) {
			return null;
		}
	}

}
