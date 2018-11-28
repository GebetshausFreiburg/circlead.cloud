/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.util.MailUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.util.StringUtil;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * The Class DefaultReport.
 * 
 * @author Matthias Wegner
 */
public class DefaultReport implements IReport {

	private final static Logger LOGGER = LogManager.getLogger(DefaultReport.class);

	/** The r. */
	protected final Repository R = Repository.getInstance();

	/**
	 * Instantiates a new default report.
	 */
	public DefaultReport() {

	}

	/**
	 * Instantiates a new default report.
	 *
	 * @param scriptFile the script file
	 */
	public DefaultReport(String scriptFile) {
		setScriptFile(scriptFile);
	}

	/** The name. */
	private String name = null;

	/** The description. */
	private String description = null;

	/** The script file. */
	private String scriptFile = null;

	/**
	 * Gets the script file.
	 *
	 * @return the script file
	 */
	public String getScriptFile() {
		return scriptFile;
	}

	/**
	 * Sets the script file.
	 *
	 * @param scriptFile the new script file
	 */
	public void setScriptFile(String scriptFile) {
		try {
			this.scriptFile = scriptFile;
			Binding binding = new Binding();

			binding.setVariable("name", new String());
			binding.setVariable("description", new String());
			binding.setVariable("description", new String());
			binding.setVariable("synchronizer", SynchronizerFactory.getInstance().getActual());
			binding.setVariable("R", Repository.getInstance());
			binding.setVariable("ObjectUtil", new ObjectUtil());
			binding.setVariable("MailUtil", new MailUtil());
			binding.setVariable("StringUtil", new StringUtil());
			binding.setVariable("PropertyUtil", PropertyUtil.getInstance());
			GroovyShell shell = new GroovyShell(binding);

			String script = new String(Files.readAllBytes(Paths.get(scriptFile)));
		    shell.evaluate(script);
			this.name = (String) binding.getProperty("name");
			this.description = (String) binding.getProperty("description");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IReport#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IWorkitemRenderer#render(org.rogatio.circlead.
	 * control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {

		if (scriptFile != null) {
			try {
				String script = new String(Files.readAllBytes(Paths.get(scriptFile)));

				Binding binding = new Binding();

				binding.setVariable("name", new String());
				binding.setVariable("description", new String());
				binding.setVariable("synchronizer", synchronizer);
				binding.setVariable("R", Repository.getInstance());
				binding.setVariable("ObjectUtil", new ObjectUtil());
				binding.setVariable("MailUtil", new MailUtil());
				binding.setVariable("StringUtil", new StringUtil());
				binding.setVariable("PropertyUtil", PropertyUtil.getInstance());
				GroovyShell shell = new GroovyShell(binding);

				Object o = shell.evaluate(script);

				String name = (String) binding.getProperty("name");
				String description = (String) binding.getProperty("description");
				Element element = (Element) o;

				this.name = name;
				this.description = description;

				return element;

			} catch (IOException e) {
				LOGGER.error("Error on loading script '" + scriptFile + "'", e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.report.IReport#getHead()
	 */
	@Override
	public List<String> getHead() {
		List<String> head = new ArrayList<String>();
		head.add("<link rel=\"stylesheet\" href=\"styles.css\">");
		return head;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.report.IReport#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

}
