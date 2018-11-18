/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.control.synchronizer.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.DefaultSynchronizer;
import org.rogatio.circlead.control.synchronizer.SynchronizerException;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.control.synchronizer.SynchronizerResult;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Competence;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.PropertyUtil;
import org.rogatio.circlead.view.FileRendererEngine;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.report.IReport;
import org.rogatio.circlead.view.report.IndexCirclead;
import org.rogatio.circlead.view.report.IndexRbs;
import org.rogatio.circlead.view.report.IndexRrgs;
import org.rogatio.circlead.view.report.IndexWorkitems;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class FileSynchronizer.
 * 
 * @author Matthias Wegner
 */
public class FileSynchronizer extends DefaultSynchronizer implements IValidator {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LogManager.getLogger(FileSynchronizer.class);

	/** The data directory. */
	private String dataDirectory;

	/**
	 * Gets the data directory.
	 *
	 * @return the data directory
	 */
	public String getDataDirectory() {
		return dataDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getIdPattern()
	 */
	@Override
	public String getIdPattern() {
		return "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	}

	/**
	 * Instantiates a new file synchronizer.
	 *
	 * @param dataDirectory the data directory
	 */
	public FileSynchronizer(String dataDirectory) {
		setDataDirectory(dataDirectory);
	}

	/**
	 * Sets the data directory.
	 *
	 * @param dataDirectory the new data directory
	 */
	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.
	 * rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult update(IWorkitem workitem) {
		SynchronizerFactory.getInstance().setActual(this);
		return add(workitem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.
	 * rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult update(IReport report) {
		SynchronizerFactory.getInstance().setActual(this);
		return add(report);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio
	 * .circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult add(IWorkitem workitem) {
		SynchronizerFactory.getInstance().setActual(this);
		SynchronizerResult res = new SynchronizerResult();

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()
				&& PropertyUtil.getInstance().isFileSynchronizerWriteMode()) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			if (WorkitemType.ROLE.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "roles");
			} else if (WorkitemType.ACTIVITY.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "activities");
			} else if (WorkitemType.ROLEGROUP.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "rolegroups");
			} else if (WorkitemType.PERSON.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "persons");
			} else if (WorkitemType.TEAM.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "teams");
			} else if (WorkitemType.COMPETENCE.isTypeOf(workitem)) {
				writeWorkitemData(workitem, "competencies");
			}

			writeWorkitemRendered(workitem);

			res.setMessage("Write");
			res.setCode(200);
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio
	 * .circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult add(IReport report) {
		SynchronizerFactory.getInstance().setActual(this);
		SynchronizerResult res = new SynchronizerResult();

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled() 
				&& PropertyUtil.getInstance().isFileSynchronizerWriteMode()) {

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			writeReportRendered(report);

			res.setMessage("OK");
			res.setCode(200);
		}

		return res;
	}

	/**
	 * Delete all.
	 */
	public void deleteAll() {
		// File data = new File(dataDirectory);
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "persons"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "persons" + "' found to delete.");
		}
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "rolegroups"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "rolegroups" + "' found to delete.");
		}
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "activities"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "activities" + "' found to delete.");
		}
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "roles"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "roles" + "' found to delete.");
		}
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "teams"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "teams" + "' found to delete.");
		}
		try {
			FileUtil.deleteRecursive(new File(dataDirectory + File.separatorChar + "competencies"));
		} catch (Exception e) {
			LOGGER.warn("No directory '" + dataDirectory + File.separatorChar + "competencies" + "' found to delete.");
		}

	}

	/**
	 * Write workitem data.
	 *
	 * @param workitem the workitem
	 * @param folder   the folder
	 * @return the string
	 */
	private String writeWorkitemData(IWorkitem workitem, String folder) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		String result = "";

		try {
			File dir = new File(dataDirectory + File.separatorChar + folder);
			dir.mkdirs();
			File f = new File(dir.toString() + File.separatorChar + "" + workitem.getId(this) + "."
					+ workitem.getType().toLowerCase() + ".json");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();

			if (workitem instanceof Role) {
				Role role = (Role) workitem;
				mapper.writeValue(f, role.getDataitem());
			} else if (workitem instanceof Activity) {
				Activity activity = (Activity) workitem;
				mapper.writeValue(f, activity.getDataitem());
			} else if (workitem instanceof Rolegroup) {
				Rolegroup rolegroup = (Rolegroup) workitem;
				mapper.writeValue(f, rolegroup.getDataitem());
			} else if (workitem instanceof Person) {
				Person person = (Person) workitem;
				mapper.writeValue(f, person.getDataitem());
			} else if (workitem instanceof Team) {
				Team team = (Team) workitem;
				mapper.writeValue(f, team.getDataitem());
			} else if (workitem instanceof Competence) {
				Competence competence = (Competence) workitem;
				mapper.writeValue(f, competence.getDataitem());
			}

			result = "" + f + "";

			LOGGER.info("Write/Update file '" + f + "'");

		} catch (IOException e) {
			LOGGER.error(e);
			result = "Error on writing workitem '" + workitem.getId() + "'";
		}

		return result;

	}

	/**
	 * Write report rendered.
	 *
	 * @param report the report
	 */
	public void writeReportRendered(IReport report) {
		if (report instanceof IWorkitemRenderer) {
			
			if (report.getName()==null) {
				LOGGER.warn("Report '"+report.getClass()+"' could not be found.");
				return;
			}
			
			IWorkitemRenderer renderer = (IWorkitemRenderer) report;
			String filename = report.getName();
			Document doc = new Document("");
			doc.charset(Charset.forName("UTF-8"));
			Element html = doc.appendElement("html");
			Element head = html.appendElement("head");
			if (ObjectUtil.isListNotNullAndEmpty(report.getHead())) {
				for (String h : report.getHead()) {
					head.append(h);
				}
			}
			head.append("<link rel=\"apple-touch-icon\" sizes=\"152x152\" href=\"images/apple-touch-icon.png\">");
			head.append("<link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"images/favicon-32x32.png\">");
			head.append("<link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"images/favicon-16x16.png\">");
			head.append("<link rel=\"manifest\" href=\"images/site.webmanifest\">");
			head.append("<link rel=\"mask-icon\" href=\"images/safari-pinned-tab.svg\" color=\"#5bbad5\">");
			head.append("<meta name=\"msapplication-TileColor\" content=\"#da532c\">");
			head.append("<meta name=\"theme-color\" content=\"#ffffff\">");
			head.append("<meta charset=\"utf-8\">");
			Element body = html.appendElement("body");
			
			body.appendElement("H1").appendText(report.getName()); 

			renderer.render(this).appendTo(body);

			try {
				String f = "web/" + filename + ".html";

				LOGGER.info("Write/Update file '" + f + "'");

				File ff = new File("web");
				ff.mkdirs();
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
				try {
					out.write(doc.toString());
				} finally {
					out.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error writing Html-File", e);
			}
		}
	}

	/**
	 * Write workitem rendered.
	 *
	 * @param workitem the workitem
	 */
	private void writeWorkitemRendered(IWorkitem workitem) {
		if (workitem instanceof IWorkitemRenderer) {
			IWorkitemRenderer renderer = (IWorkitemRenderer) workitem;
			String filename = workitem.getId(this);
			Document doc = new Document("");
			doc.charset(Charset.forName("UTF-8"));
			Element html = doc.appendElement("html");
			Element head = html.appendElement("head");
			head.append("<link rel=\"stylesheet\" href=\"styles.css\"/>");
			head.append("<link rel=\"apple-touch-icon\" sizes=\"152x152\" href=\"images/apple-touch-icon.png\">");
			head.append("<link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"images/favicon-32x32.png\">");
			head.append("<link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"images/favicon-16x16.png\">");
			head.append("<link rel=\"manifest\" href=\"images/site.webmanifest\">");
			head.append("<link rel=\"mask-icon\" href=\"images/safari-pinned-tab.svg\" color=\"#5bbad5\">");
			head.append("<meta name=\"msapplication-TileColor\" content=\"#da532c\">");
			head.append("<meta name=\"theme-color\" content=\"#ffffff\">");
			head.append("<meta charset=\"utf-8\"/>");
			Element body = html.appendElement("body");

			body.appendElement("H1").appendText(workitem.getTitle());

			renderer.render(this).appendTo(body);

			try {
				String f = "web/" + filename + ".html";
				File ff = new File("web");
				ff.mkdirs();
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
				try {
					out.write(doc.toString());
				} finally {
					out.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error writing Html-File", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#loadIndex(org.
	 * rogatio.circlead.model.WorkitemType)
	 */
	public List<String> loadIndex(WorkitemType workitemType) {
		SynchronizerFactory.getInstance().setActual(this);

		List<String> fileIndex = new ArrayList<String>();

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()
				&& PropertyUtil.getInstance().isFileSynchronizerReadMode()) {

			if (WorkitemType.ROLE == workitemType) {
				fileIndex = readFolder("roles");
			} else if (WorkitemType.REPORT == workitemType) {
				List<String> files = readFolder("web");
				for (String f : files) {
					File file = new File(f);
					HowTo ht = new HowTo();
					ht.setType("report");
					ht.setSynchronizer(this.toString());
					ht.setId(f);
					ht.setTitle(file.getName());
					try {
						ht.setUrl(file.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						ht.setUrl(f);
					}
					fileIndex.add(ht.toString());
				}
			} else if (WorkitemType.HOWTO == workitemType) {
				List<String> files = readFolder("howtos");
				for (String f : files) {
					File file = new File(f);
					HowTo ht = new HowTo();
					ht.setType("howto");
					ht.setSynchronizer(this.toString());
					ht.setId(f);
					ht.setTitle(file.getName());
					try {
						ht.setUrl(file.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						ht.setUrl(f);
					}
					fileIndex.add(ht.toString());
				}
			} else if (WorkitemType.ACTIVITY == workitemType) {
				fileIndex = readFolder("activities");
			} else if (WorkitemType.ROLEGROUP == workitemType) {
				fileIndex = readFolder("rolegroups");
			} else if (WorkitemType.PERSON == workitemType) {
				fileIndex = readFolder("persons");
			} else if (WorkitemType.TEAM == workitemType) {
				fileIndex = readFolder("teams");
			} else if (WorkitemType.COMPETENCE == workitemType) {
				fileIndex = readFolder("competencies");
			}
		}

		return fileIndex;
	}

	/**
	 * Read folder.
	 *
	 * @param folder the folder
	 * @return the list
	 */
	private List<String> readFolder(String folder) {
		List<String> fileIndex = new ArrayList<String>();
		File directory = new File(dataDirectory + File.separatorChar + folder);
		if (directory != null) {
			if (directory.listFiles() != null) {
				for (File file : directory.listFiles()) {
					if (!file.toString().contains(".schema")) {
						fileIndex.add(file.toString());
					}
				}
			}
		}
		return fileIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#get(java.lang.
	 * String)
	 */
	@Override
	public IWorkitem get(String filename) throws SynchronizerException {
		SynchronizerFactory.getInstance().setActual(this);

		File file = new File(filename);
		Date modified = new Date(file.lastModified());

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()
				&& PropertyUtil.getInstance().isFileSynchronizerReadMode()) {

			if (!file.exists()) {
				throw new SynchronizerException(
						"Item with id '" + filename + "' could not be loaded with File Synchronizer.");
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				LOGGER.info("Read file '" + filename + "'");

				IWorkitem item = null;

				if (!file.toString().contains(".schema")) {
					if (filename.endsWith(".role.json")) {
						RoleDataitem data = mapper.readValue(file, RoleDataitem.class);
						item = new Role(data);
					}
					if (filename.endsWith(".activity.json")) {
						ActivityDataitem data = mapper.readValue(file, ActivityDataitem.class);
						item = new Activity(data);
					}
					if (filename.endsWith(".rolegroup.json")) {
						RolegroupDataitem data = mapper.readValue(file, RolegroupDataitem.class);
						item = new Rolegroup(data);
					}
					if (filename.endsWith(".person.json")) {
						PersonDataitem data = mapper.readValue(file, PersonDataitem.class);
						item = new Person(data);
					}
					if (filename.endsWith(".team.json")) {
						TeamDataitem data = mapper.readValue(file, TeamDataitem.class);
						item = new Team(data);
					}
					if (filename.endsWith(".competence.json")) {
						CompetenceDataitem data = mapper.readValue(file, CompetenceDataitem.class);
						Competence c = new Competence(data);
						item = c;
					}
				}

				item.setModified(modified);
				setWorkitemId(filename, item);

				return item;

			} catch (JsonParseException e) {
				throw new SynchronizerException("Error (JsonParse) loading role '" + file + "'. " + e.getMessage(),
						e.getCause());
			} catch (JsonMappingException e) {
				throw new SynchronizerException("Error (JsonMapping) loading role '" + file + "'. " + e.getMessage(),
						e.getCause());
			} catch (IOException e) {
				throw new SynchronizerException("Error (IOException) loading role '" + file + "'. " + e.getMessage(),
						e.getCause());
			}
		}
		return null;
	}

	/**
	 * Sets the workitem id.
	 *
	 * @param filename the filename
	 * @param wi       the wi
	 * @throws SynchronizerException the synchronizer exception
	 */
	private void setWorkitemId(String filename, IWorkitem wi) throws SynchronizerException {
		String i[] = filename.split("/");
		for (String ii : i) {
			if (ii.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}." + wi.getType().toLowerCase()
					+ ".json$")) {
				wi.setId(ii.replace("." + wi.getType().toLowerCase() + ".json", ""), this);
			}
		}

		if (!filename.contains(wi.getId(this))) {
			throw new SynchronizerException("Error loading " + wi.getType().toLowerCase() + " '" + filename + "'. Id '"
					+ wi.getId(this) + "' is not similar to filename-uid.");
		}
	}

	/**
	 * Creates the file.
	 *
	 * @param workitem the workitem
	 * @param folder   the folder
	 * @return the file
	 */
	private File createFile(IWorkitem workitem, String folder) {
		File dir = new File(dataDirectory + File.separatorChar + folder);
		File f = new File(dir.toString() + File.separatorChar + "" + workitem.getId(this) + "."
				+ workitem.getType().toLowerCase() + ".json");
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#delete(org.
	 * rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public String delete(IWorkitem workitem) throws SynchronizerException {
		SynchronizerFactory.getInstance().setActual(this);

		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()) {

			File f = null;
			if (WorkitemType.ROLE.isTypeOf(workitem)) {
				f = createFile(workitem, "roles");
			} else if (WorkitemType.ACTIVITY.isTypeOf(workitem)) {
				f = createFile(workitem, "activities");
			} else if (WorkitemType.ROLEGROUP.isTypeOf(workitem)) {
				f = createFile(workitem, "rolegroups");
			} else if (WorkitemType.PERSON.isTypeOf(workitem)) {
				f = createFile(workitem, "persons");
			} else if (WorkitemType.TEAM.isTypeOf(workitem)) {
				f = createFile(workitem, "teams");
			} else if (WorkitemType.COMPETENCE.isTypeOf(workitem)) {
				f = createFile(workitem, "competencies");
			}
			if (f != null) {
				if (f.exists()) {
					LOGGER.debug("DELETE " + f);
					f.delete();
					return "OK";
				}
			}
		}
		return "NIO";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getRenderer()
	 */
	@Override
	public ISynchronizerRendererEngine getRenderer() {
		return new FileRendererEngine(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.validator.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		String bpmnFiles[] = { "end-event-escalation.png", "start-event-none.png", "user-task.png",
				"start-event-non-interrupting-multiple.png", "intermediate-event-catch-escalation.png",
				"intermediate-event-throw-escalation.png", "lasso-tool.png", "lane-divide-two.png",
				"conditional-flow.png", "intermediate-event-catch-signal.png", "gateway-none.png",
				"start-event-signal.png", "lane-insert-above.png", "gateway-parallel.png",
				"intermediate-event-catch-link.png", "start-event-parallel-multiple.png", "end-event-signal.png",
				"end-event-compensation.png", "compensation-marker.png", "participant.png", "parallel-mi-marker.png",
				"start-event-non-interrupting-parallel-multiple.png",
				"intermediate-event-catch-non-interrupting-parallel-multiple.png", "lane-insert-below.png",
				"task-none.png", "intermediate-event-catch-multiple.png", "start-event-escalation.png",
				"gateway-or.png", "subprocess-expanded.png", "start-event-compensation.png", "data-store.png",
				"intermediate-event-catch-non-interrupting-message.png", "end-event-error.png",
				"start-event-non-interrupting-message.png", "task.png", "end-event-terminate.png",
				"intermediate-event-catch-cancel.png", "start-event-non-interrupting-condition.png",
				"end-event-message.png", "intermediate-event-catch-error.png", "transaction.png", "ad-hoc-marker.png",
				"gateway-complex.png", "default-flow.png", "start-event-non-interrupting-escalation.png",
				"screw-wrench.png", "intermediate-event-catch-non-interrupting-signal.png", "lane.png",
				"event-subprocess-expanded.png", "end-event-none.png", "subprocess.png", "call-activity.png",
				"intermediate-event-catch-non-interrupting-timer.png", "intermediate-event-throw-signal.png",
				"lane-divide-three.png", "sequential-mi-marker.png",
				"intermediate-event-catch-non-interrupting-multiple.png", "end-event-cancel.png", "receive-task.png",
				"send-task.png", "connection.png", "subprocess-collapsed.png", "start-event-error.png",
				"start-event-multiple.png", "service-task.png", "gateway-eventbased.png",
				"intermediate-event-throw-multiple.png", "trash.png", "intermediate-event-throw-compensation.png",
				"intermediate-event-catch-condition.png", "intermediate-event-catch-message.png", "gateway-xor.png",
				"script-task.png", "start-event-non-interrupting-timer.png",
				"intermediate-event-catch-non-interrupting-condition.png",
				"intermediate-event-catch-parallel-multiple.png", "start-event-condition.png",
				"intermediate-event-catch-non-interrupting-escalation.png", "data-object.png",
				"intermediate-event-catch-compensation.png", "start-event-non-interrupting-signal.png",
				"start-event-message.png", "manual-task.png", "data-input.png", "sub-process-marker.png",
				"connection-multi.png", "intermediate-event-throw-message.png", "hand-tool.png", "text-annotation.png",
				"data-output.png", "loop-marker.png", "start-event-timer.png", "business-rule-task.png",
				"intermediate-event-throw-link.png", "intermediate-event-catch-timer.png",
				"intermediate-event-none.png", "end-event-multiple.png", "space-tool.png", "end-event-link.png" };
		for (String file : bpmnFiles) {
			Path p = Paths.get(
					"web" + File.separatorChar + "images" + File.separatorChar + "bpmn" + File.separatorChar + file);
			if (!Files.exists(p)) {
				ValidationMessage m = new ValidationMessage(this);
				m.error("File missing",
						"File '" + p.toString() + "' is missing for usage in FileSynchronizer (BPMN-Display)");
				messages.add(m);
			}
		}

		String reportImageFiles[] = { "child.png", "group.png", "groupchild.png", "groupparent.png",
				"groupwithrole.png", "parent.png", "role.png" };
		for (String file : reportImageFiles) {
			Path p = Paths.get("web" + File.separatorChar + "images" + File.separatorChar + file);
			if (!Files.exists(p)) {
				ValidationMessage m = new ValidationMessage(this);
				m.error("File missing",
						"File '" + p.toString() + "' is missing for usage in FileSynchronizer (Reports)");
				messages.add(m);
			}
		}

		Path p = Paths.get("web" + File.separatorChar + "stylesCategoryReport.css");
		if (!Files.exists(p)) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("File missing", "File '" + p.toString() + "' is missing for usage in FileSynchronizer (Reports)");
			messages.add(m);
		}

		p = Paths.get("web" + File.separatorChar + "styles.css");
		if (!Files.exists(p)) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("File missing", "File '" + p.toString() + "' is missing for usage in FileSynchronizer (Websites)");
			messages.add(m);
		}

		return messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#writeIndex()
	 */
	@Override
	public void writeIndex() {
		if (PropertyUtil.getInstance().isFileSynchronizerEnabled()
				&& PropertyUtil.getInstance().isFileSynchronizerWriteMode()) {
			LOGGER.info("Write Index");
			writeReportRendered(new IndexCirclead());
			writeReportRendered(new IndexRbs());
			writeReportRendered(new IndexRrgs());
			writeReportRendered(new IndexWorkitems(WorkitemType.ROLE));
			writeReportRendered(new IndexWorkitems(WorkitemType.ROLEGROUP));
			writeReportRendered(new IndexWorkitems(WorkitemType.PERSON));
			writeReportRendered(new IndexWorkitems(WorkitemType.ACTIVITY));
			writeReportRendered(new IndexWorkitems(WorkitemType.TEAM));
			writeReportRendered(new IndexWorkitems(WorkitemType.REPORT));
			writeReportRendered(new IndexWorkitems(WorkitemType.HOWTO));
		}
	}
}
