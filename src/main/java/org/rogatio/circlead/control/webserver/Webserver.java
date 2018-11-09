/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Dr. Matthias Wegner
 * @version 0.1
 * @since 01.04.2018
 * 
 */
package org.rogatio.circlead.control.webserver;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.resource.Resource;

/**
 * The Class Webserver to run a embedded jetty-webserver.
 *
 * @author Matthias Wegner
 */
public class Webserver extends Server {

	/** The Constant logger. */
	final static Logger LOGGER = LogManager.getLogger(Webserver.class);
	
	/**
	 * Instantiates a new webserver.
	 */
	public Webserver() {
		// Synchronize the Jetty-Webserver-Log to Log4j
		Log.setLog(new Jetty2Log4j2Bridge("Webserver"));
		
		LOGGER.info("Initialize Webserver");

		ServerConnector connector = new ServerConnector(this);
		connector.setPort(8090);
		this.setConnectors(new Connector[] { connector });

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		context.setContextPath("/");
		context.setBaseResource(Resource.newResource(new File("web")));
		context.setWelcomeFiles(new String[] { "Index Circlead.html" });

		ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
		holderPwd.setInitParameter("dirAllowed", "true");
		context.addServlet(holderPwd, "/");
		// context.addServlet(JspServlet.class, "*.jsp");

		this.setHandler(context);
	}
	
	/**
	 * Open in browser.
	 */
	public void openInBrowser() {
		try {
			URI openIt = new URL("http://localhost:8090/").toURI();
			java.awt.Desktop.getDesktop().browse(openIt);
		} catch (MalformedURLException e) {
			LOGGER.error(e);
		} catch (URISyntaxException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	/**
	 * Cancel.
	 */
	public void cancel() {
		LOGGER.info("Stop Webserver");

		try {
			this.stop();
		} catch (Exception e) {
			LOGGER.error("Error thrown on Web-Server", e);
		}
	}

	/**
	 * Run.
	 */
	public void run() {
		LOGGER.info("Start Webserver at http://localhost:8090/");
		try {
			this.start();
			this.join();
		} catch (InterruptedException e) {
			LOGGER.error("Error thrown on Web-Server", e);
		} catch (Exception e) {
			LOGGER.error("Error thrown on Web-Server", e);
		}
	}
}
