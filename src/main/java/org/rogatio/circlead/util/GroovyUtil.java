package org.rogatio.circlead.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * The Class GroovyUtil allows scripting in application. Needs modules (>Java v1.9)
 * 
 * --add-opens=java.base/java.io=ALL-UNNAMED
 * --add-opens=java.base/java.lang=ALL-UNNAMED
 * --add-opens=java.base/java.lang.annotation=ALL-UNNAMED
 * --add-opens=java.base/java.lang.invoke=ALL-UNNAMED
 * --add-opens=java.base/java.lang.module=ALL-UNNAMED
 * --add-opens=java.base/java.lang.ref=ALL-UNNAMED
 * --add-opens=java.base/java.lang.reflect=ALL-UNNAMED
 * --add-opens=java.base/java.math=ALL-UNNAMED
 * --add-opens=java.base/java.net=ALL-UNNAMED
 * --add-opens=java.base/java.net.spi=ALL-UNNAMED
 * --add-opens=java.base/java.nio=ALL-UNNAMED
 * --add-opens=java.base/java.nio.channels=ALL-UNNAMED
 * --add-opens=java.base/java.nio.channels.spi=ALL-UNNAMED
 * --add-opens=java.base/java.nio.charset=ALL-UNNAMED
 * --add-opens=java.base/java.nio.charset.spi=ALL-UNNAMED
 * --add-opens=java.base/java.nio.file=ALL-UNNAMED
 * --add-opens=java.base/java.nio.file.attribute=ALL-UNNAMED
 * --add-opens=java.base/java.nio.file.spi=ALL-UNNAMED
 * --add-opens=java.base/java.security=ALL-UNNAMED
 * --add-opens=java.base/java.security.acl=ALL-UNNAMED
 * --add-opens=java.base/java.security.cert=ALL-UNNAMED
 * --add-opens=java.base/java.security.interfaces=ALL-UNNAMED
 * --add-opens=java.base/java.security.spec=ALL-UNNAMED
 * --add-opens=java.base/java.text=ALL-UNNAMED
 * --add-opens=java.base/java.text.spi=ALL-UNNAMED
 * --add-opens=java.base/java.time=ALL-UNNAMED
 * --add-opens=java.base/java.time.chrono=ALL-UNNAMED
 * --add-opens=java.base/java.time.format=ALL-UNNAMED
 * --add-opens=java.base/java.time.temporal=ALL-UNNAMED
 * --add-opens=java.base/java.time.zone=ALL-UNNAMED
 * --add-opens=java.base/java.util=ALL-UNNAMED
 * --add-opens=java.base/java.util.concurrent=ALL-UNNAMED
 * --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
 * --add-opens=java.base/java.util.concurrent.locks=ALL-UNNAMED
 * --add-opens=java.base/java.util.function=ALL-UNNAMED
 * --add-opens=java.base/java.util.jar=ALL-UNNAMED
 * --add-opens=java.base/java.util.regex=ALL-UNNAMED
 * --add-opens=java.base/java.util.spi=ALL-UNNAMED
 * --add-opens=java.base/java.util.stream=ALL-UNNAMED
 * --add-opens=java.base/java.util.zip=ALL-UNNAMED
 * --add-opens=java.datatransfer/java.awt.datatransfer=ALL-UNNAMED
 * --add-opens=java.instrument/java.lang.instrument=ALL-UNNAMED
 * --add-opens=java.logging/java.util.logging=ALL-UNNAMED
 * --add-opens=java.management/java.lang.management=ALL-UNNAMED
 * --add-opens=java.prefs/java.util.prefs=ALL-UNNAMED
 * --add-opens=java.rmi/java.rmi=ALL-UNNAMED
 * --add-opens=java.rmi/java.rmi.activation=ALL-UNNAMED
 * --add-opens=java.rmi/java.rmi.dgc=ALL-UNNAMED
 * --add-opens=java.rmi/java.rmi.registry=ALL-UNNAMED
 * --add-opens=java.rmi/java.rmi.server=ALL-UNNAMED
 * --add-opens=java.sql/java.sql=ALL-UNNAMED
 * 
 * as VM arguments to suppress reflection-warnings of Java 9.x or 10.x
 * 
 * @author Matthias Wegner
 * @see https://issues.apache.org/jira/browse/GROOVY-8339
 */
public class GroovyUtil {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(GroovyUtil.class);

	/**
	 * Load and run scripts.
	 *
	 * @return the map
	 */
	public static Map<Object, String> loadAndRunScripts() {
		Map<Object, String> map = new TreeMap<Object, String>();
		try {
			Files.walk(Paths.get("scripts"))
					.filter(p -> !p.toString().endsWith(".report.groovy") && p.toString().endsWith(".groovy"))
					.filter(Files::isRegularFile).forEach(file -> {
						Object o = loadAndRunScript(file.toFile().getAbsolutePath());
						if (o != null) {
							map.put(o, file.toFile().getName());
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Load and run groovy-script.
	 *
	 * @param fileName the file name
	 * @return the object
	 */
	public static Object loadAndRunScript(String fileName) {
		try {
			LOGGER.info("Running script '" + fileName + "'");
			String script = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
			return runScript(script, fileName);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return null;
	}

	/**
	 * Run script.
	 *
	 * @param scriptText the script text
	 * @return the object
	 */
	public static Object runScript(String scriptText) {
		return runScript(scriptText, null);
	}

	/**
	 * Run script.
	 *
	 * @param scriptText the script text
	 * @param loggerName the logger name
	 * @return the object
	 */
	public static Object runScript(String scriptText, String loggerName) {
		Binding binding = new Binding();
		binding.setVariable("R", Repository.getInstance());
		binding.setVariable("ObjectUtil", new ObjectUtil());
		binding.setVariable("MailUtil", new MailUtil());
		binding.setVariable("StringUtil", new StringUtil());
		binding.setVariable("FileUtil", new FileUtil());
		if (loggerName != null) {
			binding.setVariable("LOG", LogManager.getLogger(loggerName.replace(".", "_")));
		} else {
			binding.setVariable("LOG", LogManager.getLogger("script"));		
		}
		binding.setVariable("DropboxUtil", new DropboxUtil());
		binding.setVariable("PropertyUtil", PropertyUtil.getInstance());
		binding.setVariable("GanttUtil", new GanttUtil());
		GroovyShell shell = new GroovyShell(binding);
		return shell.evaluate(scriptText);
	}

}
