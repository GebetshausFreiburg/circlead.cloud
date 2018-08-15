package org.rogatio.circlead.control.synchronizer.atlassian;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.rogatio.circlead.control.synchronizer.SynchronizerResult;

/**
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

	private String restPrefix = null;

	public ConfluenceClient(String baseUrl, String user, String password, boolean server) {
		this.baseUrl = baseUrl;
		this.user = user;
		this.password = password;

		if (server) {
			// Set rest-prefix if atlassian-dedicated-server
			restPrefix = "rest/api/";
			logger.info("ConfluenceSynchronizer ist set to dedicated server");
		} else {
			// Set rest-prefix if atlassian-cloud-server
			restPrefix = "wiki/rest/api/";
			logger.info("ConfluenceSynchronizer ist set to cloud server");
		}
	}

	public SynchronizerResult getPageSpace(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=space");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getPageVersion(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=version");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getPageHistory(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=history,history.lastUpdated");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getPage(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=body.storage,metadata.labels,history,history.lastUpdated,space,version,ancestors");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getPageContent(int id) {
		try {
			return this.get(restPrefix + "content/" + id + "?" + "expand=body.storage");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getCurrentUser() {
		try {
			return this.get(restPrefix + "user/current");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getSysteminfo() {
		try {
			return this.get(restPrefix + "settings/systemInfo");
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult getMetadata(int pageId) {
		try {
			return this.get(restPrefix + "content/" + pageId + "/property");
		} catch (IOException e) {
			return null;
		}
	}

	public String getRestPrefix() {
		return restPrefix;
	}

	public SynchronizerResult purgePage(int pageId) {
		try {
			return this.delete(restPrefix + "content/" + pageId + "?status=trashed");
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	public SynchronizerResult deletePage(int pageId) {
		try {
			return this.delete(restPrefix + "content/" + pageId);
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	public SynchronizerResult deleteMetadata(int pageId, String key) {
		try {
			return this.delete(restPrefix + "content/" + pageId + "/property/" + key);
		} catch (UnsupportedEncodingException e1) {
		} catch (IOException e) {
		}
		return null;
	}

	public SynchronizerResult addLabel(String pageId, String label) {
		try {
			int id = Integer.parseInt(pageId);
			return this.addLabel(id, label);
		} catch (Exception e) {

		}
		return null;
	}

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

	public SynchronizerResult updateMetadata(int pageId, String key, String value, int versionNumber, boolean minorEdit) {

		if (value.trim().startsWith("{") && value.trim().endsWith("}")) {
			value = value.trim();
		} else {
			value = "\"" + value + "\"";
		}

		String data = "{" + "\"value\" : " + value + "," + "\"version\": {" + "\"minorEdit\": " + minorEdit + "," + "\"number\": " + versionNumber + "" + "}"
				+ "}";

		try {
			return this.put(restPrefix + "content/" + pageId + "/property/" + key, data);
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

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

	public SynchronizerResult search(String cql) {
		try {
			String encoded = URLEncoder.encode(cql, "UTF-8");
			return this.get(restPrefix + "search?limit=" + Constant.LIMIT + "&cql=" + encoded);
		} catch (UnsupportedEncodingException e1) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @see https://developer.atlassian.com/cloud/confluence/rest/#api-content-get
	 */
	public SynchronizerResult browseContent() {
		try {
			return this.get(restPrefix + "content?expand=space&limit=" + Constant.LIMIT);
		} catch (IOException e) {
			return null;
		}
	}

	public SynchronizerResult newPage() {
		return newPage("Page " + UUID.randomUUID(), "TEST", "<p>This is a new page</p>");
	}

	public SynchronizerResult newPage(String title, String spaceKey, String content) {
		try {
			String data = "{\"type\":\"page\",\"title\":\"" + title + "\",\"space\":{\"key\":\"" + spaceKey + "\"},\"body\":{\"storage\":{\"value\":\""
					+ content + "\",\"representation\":\"storage\"}}}";
			return this.post(restPrefix + "content/", data);
		} catch (IOException e) {
			return null;
		}
	}

}
