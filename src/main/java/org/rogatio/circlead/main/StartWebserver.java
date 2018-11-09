package org.rogatio.circlead.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.webserver.Webserver;

/**
 * The Class StartWebserver.
 */
public class StartWebserver {
	
	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(StartWebserver.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// Create Webserver
		Webserver server = new Webserver();
		// Open Localhost in Browser
		server.openInBrowser();
		// Start Webserver
		server.run();
	}

}
