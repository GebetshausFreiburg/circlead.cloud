/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.atlassian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;

/**
 * The Class HttpClient.
 * 
 * @author Matthias Wegner
 */
public class HttpClient {

	/** The Constant logger. */
	final static Logger LOGGER = LogManager.getLogger(HttpClient.class);
	
	/** The base url. */
	protected String baseUrl;
	
	/** The user. */
	protected String user;
	
	/** The password. */
	protected String password;

	/**
	 * Creates the credentials.
	 *
	 * @return the string
	 */
	public String createCredentials() {
		String login = user + ":" + password;
		final byte[] authBytes = login.getBytes(StandardCharsets.UTF_8);
		String encodedCredentials = Base64.getEncoder().withoutPadding().encodeToString(authBytes);
		return encodedCredentials;
	}

	/**
	 * Gets the.
	 *
	 * @param getUrl the get url
	 * @return the synchronizer result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SynchronizerResult get(String getUrl) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + getUrl);
		
		URL url = new URL(baseUrl + getUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		connection.setDoInput(true);
		connection.connect();
		
		result.setCode(connection.getResponseCode());
		result.setMessage(connection.getResponseMessage());
		
		result.setContent(read(connection.getInputStream()));
		return result;
	}

	/**
	 * Delete.
	 *
	 * @param deleteUrl the delete url
	 * @return the synchronizer result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SynchronizerResult delete(String deleteUrl) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + deleteUrl);
		URL url = new URL(baseUrl + deleteUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		connection.setRequestMethod("DELETE");
		result.setMessage(connection.getResponseMessage());
		result.setCode(connection.getResponseCode());
		return result;
	}

	/**
	 * Put.
	 *
	 * @param putUrl the put url
	 * @param data the data
	 * @return the synchronizer result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SynchronizerResult put(String putUrl, String data) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + putUrl);
		URL url = new URL(baseUrl + putUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		con.setDoOutput(true);
		this.sendData(con, data);
		result.setMessage(con.getResponseMessage());
		result.setCode(con.getResponseCode());
		if (con.getResponseCode() == 200) {
			result.setContent(this.read(con.getInputStream()));
			return result;
		} else {
			if (con.getErrorStream() != null) {
				result.setContent(this.read(con.getErrorStream()));
				
				return result;
			} else {
				return null;
			}
		}
	}

	/**
	 * Post.
	 *
	 * @param postUrl the post url
	 * @param data the data
	 * @return the synchronizer result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SynchronizerResult post(String postUrl, String data) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + postUrl);
		URL url = new URL(baseUrl + postUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		con.setDoOutput(true);
		this.sendData(con, data);
		result.setCode(con.getResponseCode());
		result.setMessage(con.getResponseMessage());
		if (con.getResponseCode() == 200) {
			result.setContent(this.read(con.getInputStream()));
			return result;
		} else {
			result.setContent(this.read(con.getErrorStream()));
			return result;
		}
	}

	/**
	 * Send data.
	 *
	 * @param con the con
	 * @param data the data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void sendData(HttpURLConnection con, String data) throws IOException {
		BufferedWriter bw = null;
		try {
		bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
		bw.write(data);
		bw.flush();
		bw.close();
		} catch (IOException exception) {
			
		} finally {
			this.closeQuietly(bw);
		}
		
		// This Code in not practicable for UTF-8. Throws error on sending json. Found working solution at https://stackoverflow.com/questions/24060787/sending-utf-8-string-using-httpurlconnection
	/*	DataOutputStream wr = null;
		try {
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
		} catch (IOException exception) {
			throw exception;
		} finally {
			this.closeQuietly(wr);
		}*/
	}

	/**
	 * Read.
	 *
	 * @param is the is
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String read(InputStream is) throws IOException {
		BufferedReader in = null;
		String inputLine;
		StringBuilder body;
		try {
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			body = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				body.append(inputLine);
			}
			in.close();

			return body.toString();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			this.closeQuietly(in);
		}
	}

	/**
	 * Close quietly.
	 *
	 * @param closeable the closeable
	 */
	private void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ex) {

		}
	}
}
