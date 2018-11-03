package org.rogatio.circlead.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.webserver.Webserver;

public class StartWebserver {
	final static Logger LOGGER = LogManager.getLogger(StartWebserver.class);

	public static void main(String[] args) {
		// Create Webserver
		Webserver server = new Webserver();
		// Open Localhost in Browser
		server.openInBrowser();
		// Start Webserver
		server.run();
	}

}
