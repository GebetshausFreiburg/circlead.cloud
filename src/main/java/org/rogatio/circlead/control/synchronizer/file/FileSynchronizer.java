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
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.PersonDataitem;
import org.rogatio.circlead.model.data.RoleDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.model.work.Team;
import org.rogatio.circlead.util.FileUtil;
import org.rogatio.circlead.view.FileRendererEngine;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.report.IReport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class FileSynchronizer.
 */
public class FileSynchronizer extends DefaultSynchronizer {

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
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getIdPattern()
	 */
	@Override
	public String getIdPattern() {
		return "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	}

	/**
	 * Instantiates a new file synchronizer.
	 *
	 * @param dataDirectory
	 *            the data directory
	 */
	public FileSynchronizer(String dataDirectory) {
		setDataDirectory(dataDirectory);
	}

	/**
	 * Sets the data directory.
	 *
	 * @param dataDirectory
	 *            the new data directory
	 */
	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult update(IWorkitem workitem) {
		SynchronizerFactory.getInstance().setActual(this);
		return add(workitem);
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#update(org.rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult update(IReport report) {
		SynchronizerFactory.getInstance().setActual(this);
		return add(report);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public SynchronizerResult add(IWorkitem workitem) {
		SynchronizerFactory.getInstance().setActual(this);

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
		}  else if (WorkitemType.TEAM.isTypeOf(workitem)) {
			writeWorkitemData(workitem, "teams");
		}

		writeWorkitemRendered(workitem);

		SynchronizerResult res = new SynchronizerResult();
		res.setMessage("Write");
		res.setCode(200);

		return res;
	}
	
	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#add(org.rogatio.circlead.view.IReport)
	 */
	@Override
	public SynchronizerResult add(IReport report) {
		SynchronizerFactory.getInstance().setActual(this);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		writeReportRendered(report);

		SynchronizerResult res = new SynchronizerResult();
		res.setMessage("OK");
		res.setCode(200);

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

	}

	/**
	 * Write workitem data.
	 *
	 * @param workitem
	 *            the workitem
	 * @param folder
	 *            the folder
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
			File f = new File(dir.toString() + File.separatorChar + "" + workitem.getId(this) + "." + workitem.getType().toLowerCase() + ".json");
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
	private void writeReportRendered(IReport report) {
		if (report instanceof IWorkitemRenderer) {
			IWorkitemRenderer renderer = (IWorkitemRenderer) report;
			String filename = report.getName();
			Document doc = new Document("");
			doc.charset(Charset.forName("UTF-8"));
			Element html = doc.appendElement("html");
			Element head = html.appendElement("head");
			head.append("<link rel=\"stylesheet\" href=\"styles.css\">");
			head.append("<meta charset=\"utf-8\">");
			html.appendElement("body");
			Element body = html.appendElement("body");

			body.appendElement("H1").appendText(report.getName());

			renderer.render(this).appendTo(body);

			try {
				String f = "reports/" + filename + ".html";
				
				LOGGER.info("Write/Update file '" + f + "'");
				
				File ff = new File("reports");
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
	 * @param workitem
	 *            the workitem
	 */
	private void writeWorkitemRendered(IWorkitem workitem) {
		if (workitem instanceof IWorkitemRenderer) {
			IWorkitemRenderer renderer = (IWorkitemRenderer) workitem;
			String filename = workitem.getId(this);
			Document doc = new Document("");
			doc.charset(Charset.forName("UTF-8"));
			Element html = doc.appendElement("html");
			Element head = html.appendElement("head");
			head.append("<link rel=\"stylesheet\" href=\"styles.css\">");
			head.append("<meta charset=\"utf-8\">");
			html.appendElement("body");
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
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#loadIndex(org.rogatio.circlead.model.WorkitemType)
	 */
	public List<String> loadIndex(WorkitemType workitemType) {
		SynchronizerFactory.getInstance().setActual(this);

		List<String> fileIndex = new ArrayList<String>();

		if (WorkitemType.ROLE == workitemType) {
			fileIndex = readFolder("roles");
		} else if (WorkitemType.REPORT == workitemType) {
			List<String> files = readFolder("reports");
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
		}

		return fileIndex;
	}

	/**
	 * Read folder.
	 *
	 * @param folder
	 *            the folder
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
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#get(java.lang.String)
	 */
	@Override
	public IWorkitem get(String filename) throws SynchronizerException {
		SynchronizerFactory.getInstance().setActual(this);

		File file = new File(filename);
		Date modified = new Date(file.lastModified());

		if (!file.exists()) {
			throw new SynchronizerException("Item with id '" + filename + "' could not be loaded with File Synchronizer.");
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
				} else if (filename.endsWith(".activity.json")) {
					ActivityDataitem data = mapper.readValue(file, ActivityDataitem.class);
					item = new Activity(data);
				} else if (filename.endsWith(".rolegroup.json")) {
					RolegroupDataitem data = mapper.readValue(file, RolegroupDataitem.class);
					item = new Rolegroup(data);
				} else if (filename.endsWith(".person.json")) {
					PersonDataitem data = mapper.readValue(file, PersonDataitem.class);
					item = new Person(data);
				} else if (filename.endsWith(".team.json")) {
					TeamDataitem data = mapper.readValue(file, TeamDataitem.class);
					item = new Team(data);
				}
			}

			item.setModified(modified);
			setWorkitemId(filename, item);
			return item;

		} catch (JsonParseException e) {
			throw new SynchronizerException("Error (JsonParse) loading role '" + file + "'. " + e.getMessage(), e.getCause());
		} catch (JsonMappingException e) {
			throw new SynchronizerException("Error (JsonMapping) loading role '" + file + "'. " + e.getMessage(), e.getCause());
		} catch (IOException e) {
			throw new SynchronizerException("Error (IOException) loading role '" + file + "'. " + e.getMessage(), e.getCause());
		}

	}

	/**
	 * Sets the workitem id.
	 *
	 * @param filename
	 *            the filename
	 * @param wi
	 *            the wi
	 * @throws SynchronizerException
	 *             the synchronizer exception
	 */
	private void setWorkitemId(String filename, IWorkitem wi) throws SynchronizerException {
		String i[] = filename.split("/");
		for (String ii : i) {
			if (ii.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}." + wi.getType().toLowerCase() + ".json$")) {
				wi.setId(ii.replace("." + wi.getType().toLowerCase() + ".json", ""), this);
			}
		}

		if (!filename.contains(wi.getId(this))) {
			throw new SynchronizerException(
					"Error loading " + wi.getType().toLowerCase() + " '" + filename + "'. Id '" + wi.getId(this) + "' is not similar to filename-uid.");
		}
	}

	/**
	 * Creates the file.
	 *
	 * @param workitem
	 *            the workitem
	 * @param folder
	 *            the folder
	 * @return the file
	 */
	private File createFile(IWorkitem workitem, String folder) {
		File dir = new File(dataDirectory + File.separatorChar + folder);
		File f = new File(dir.toString() + File.separatorChar + "" + workitem.getId(this) + "." + workitem.getType().toLowerCase() + ".json");
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#delete(org.rogatio.circlead.model.work.IWorkitem)
	 */
	@Override
	public String delete(IWorkitem workitem) throws SynchronizerException {
		SynchronizerFactory.getInstance().setActual(this);

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
		}
		if (f != null) {
			if (f.exists()) {
				LOGGER.debug("DELETE " + f);
				f.delete();
				return "OK";
			}
		}
		return "NIO";
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.control.synchronizer.DefaultSynchronizer#getRenderer()
	 */
	@Override
	public ISynchronizerRendererEngine getRenderer() {
		return new FileRendererEngine(this);
	}
}
