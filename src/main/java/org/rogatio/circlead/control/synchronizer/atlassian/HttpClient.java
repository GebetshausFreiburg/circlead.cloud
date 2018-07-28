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

public class HttpClient {

	final static Logger logger = LogManager.getLogger(HttpClient.class);
	
	protected String baseUrl;
	protected String user;
	protected String password;

	private String createCredentials() {
		String login = user + ":" + password;
		final byte[] authBytes = login.getBytes(StandardCharsets.UTF_8);
		String encodedCredentials = Base64.getEncoder().withoutPadding().encodeToString(authBytes);
		return encodedCredentials;
	}

	public SynchronizerResult get(String getUrl) throws IOException {
//		logger.debug("GET (Start) "+(baseUrl + getUrl));
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
		
//		logger.debug("GET (End) Connection-Response: code="+connection.getResponseCode()+", message="+connection.getResponseMessage());
		result.setCode(connection.getResponseCode());
		result.setMessage(connection.getResponseMessage());
		
		result.setContent(read(connection.getInputStream()));
		return result;
	}

	public SynchronizerResult delete(String deleteUrl) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + deleteUrl);
//		logger.debug("DELETE (Start) "+(baseUrl + deleteUrl));
		URL url = new URL(baseUrl + deleteUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		connection.setRequestMethod("DELETE");
//		logger.debug("DELETE (End) Connection-Response: code="+connection.getResponseCode()+", message="+connection.getResponseMessage());
		result.setMessage(connection.getResponseMessage());
		result.setCode(connection.getResponseCode());
		return result;
	}

	public SynchronizerResult put(String putUrl, String data) throws IOException {
//		logger.debug("PUT (Start) "+(baseUrl + putUrl));
		SynchronizerResult result = new SynchronizerResult();
		result.setSource(baseUrl + putUrl);
		URL url = new URL(baseUrl + putUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Basic " + this.createCredentials());
		con.setDoOutput(true);
		this.sendData(con, data);
		//System.out.println(con.getResponseCode()+": "+con.getResponseMessage());
//		logger.debug("PUT (End) Connection-Response: code="+con.getResponseCode()+", message="+con.getResponseMessage());
		result.setMessage(con.getResponseMessage());
		result.setCode(con.getResponseCode());
		if (con.getResponseCode() == 200) {
			result.setContent(this.read(con.getInputStream()));
			return result;
		} else {
			if (con.getErrorStream() != null) {
				
//				result.setMessage(con.getErrorStream());
//				result.setCode(con.getResponseCode());
				result.setContent(this.read(con.getErrorStream()));
				
				return result;
			} else {
				return null;
			}
		}
	}

	public SynchronizerResult post(String postUrl, String data) throws IOException {
		SynchronizerResult result = new SynchronizerResult();
//		logger.debug("POST (Start) "+(baseUrl + postUrl));
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
//		logger.debug("POST (End) Connection-Response: code="+con.getResponseCode()+", message="+con.getResponseMessage());
		if (con.getResponseCode() == 200) {
			result.setContent(this.read(con.getInputStream()));
			return result;
		} else {
			result.setContent(this.read(con.getErrorStream()));
			return result;
		}
	}

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

	private String read(InputStream is) throws IOException {
		BufferedReader in = null;
		String inputLine;
		StringBuilder body;
		try {
			in = new BufferedReader(new InputStreamReader(is));

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

	private void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ex) {

		}
	}
}
