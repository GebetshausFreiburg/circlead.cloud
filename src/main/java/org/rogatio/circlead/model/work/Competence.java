/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import static org.rogatio.circlead.model.Parameter.COMPETENCE;
import static org.rogatio.circlead.model.Parameter.DESCRIPTION;
import static org.rogatio.circlead.model.Parameter.ROLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.HeaderTableParserElement;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.Parameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;
import org.rogatio.circlead.view.TreeNode;
import org.rogatio.circlead.view.WorkitemTree;

/**
 * The Class Competence is a special workitem, because Circlead should contain
 * only one (Root)-Competence in {@link org.rogatio.circlead.control.Repository}
 * and all other Competencies should be nested child representations.
 * 
 * @author Matthias Wegner
 */
public class Competence extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

	/**
	 * Instantiates a new competence.
	 */
	public Competence() {
		this.dataitem = new CompetenceDataitem();
	}

	/**
	 * Instantiates a new competence.
	 *
	 * @param dataitem the dataitem of the competence of class
	 *                 {@link org.rogatio.circlead.model.data.CompetenceDataitem}
	 */
	public Competence(IDataitem dataitem) {
		super(dataitem);

		if (!(dataitem instanceof CompetenceDataitem)) {
			throw new IllegalArgumentException("IDataitem must be of type CompetenceDataitem");
		}

		postProcess();
	}

	/**
	 * Gets the competenceIdentifier of the parent.
	 *
	 * @return the parent of the competence. Is null if competence is root.
	 */
	public String getParent() {
		return this.getDataitem().getParent();
	}

	/**
	 * Sets the parentIdentifier of a competence.
	 *
	 * @param parent the competenceIdentifier as parent
	 */
	public void setParent(String parent) {
		this.getDataitem().setParent(parent);
	}

	/**
	 * Gets the description of the competence. Defines the details of the
	 * competence.
	 *
	 * @return the description of the competence
	 */
	public String getDescription() {
		return this.getDataitem().getDescription();
	}

	/**
	 * Sets the description of the competence.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.getDataitem().setDescription(description);
	}

	/**
	 * Sets the competence form parsed html-table.
	 *
	 * @param table the parsed html-table
	 */
	public void setCompetenceTable(HeaderTableParserElement table) {
		List<Map<String, String>> dataList = table.getDataList();
		if (dataList.size() > 0) {
			List<CompetenceDataitem> competencies = new ArrayList<CompetenceDataitem>();
			for (Map<String, String> dataMap : dataList) {
				CompetenceDataitem item = new CompetenceDataitem();
				item.setDescription(dataMap.get(Parameter.DESCRIPTION.toString()));
				item.setParent(dataMap.get(Parameter.PARENT.toString()));
				item.setTitle(dataMap.get(Parameter.COMPETENCE.toString()));
				if (StringUtil.isNotNullAndNotEmpty(item.getTitle())) {
					competencies.add(item);
				}
			}
			setCompetencies(competencies);
			postProcess();
		}

	}

	/**
	 * Check if root competence contains a competence named by identifier in
	 * competence-tree
	 *
	 * @param competence the competenceIdentifier of the searched competence
	 * @return true, if successful found competence in tree
	 */
	public boolean containsCompetence(String competence) {
		for (CompetenceDataitem c : map.keySet()) {
			if (c.getTitle().equals(competence)) {
				return true;
			}
		}
		return false;
	}

	/** Map CompetenceDataitem to correlated Competence-Workitem */
	private Map<CompetenceDataitem, Competence> map = new HashMap<CompetenceDataitem, Competence>();

	/**
	 * Post process to map all CompetenceDataitem's to the correlated Competence-Workitem.
	 */
	private void postProcess() {
		if (map.size() == 0) {
			// Sets mapping-table to have Entities of class-type Competence. Needs to be
			// forced to filled here in
			// CompetenceDataitems are set by Json-ObjectMapper
			for (CompetenceDataitem competenceDataitem : this.getDataitem().getCompetencies()) {
				if (competenceDataitem != null) {
					map.put(competenceDataitem, new Competence(competenceDataitem));
				}
			}
		}

	}

	/**
	 * Gets the competencies
	 *
	 * @return the competencies
	 */
	public List<Competence> getCompetencies() {
		List<Competence> c = new ArrayList<Competence>();
		for (CompetenceDataitem cd : this.getDataitem().getCompetencies()) {
			c.add(map.get(cd));
		}
		return c;
	}

	/**
	 * Adds the competence.
	 *
	 * @param competence the competence
	 */
	public void addCompetence(CompetenceDataitem competence) {
		map.put(competence, new Competence(competence));
		this.getDataitem().getCompetencies().add(competence);
	}

	/**
	 * Sets the competencies.
	 *
	 * @param competencies the new competencies
	 */
	public void setCompetencies(List<CompetenceDataitem> competencies) {
		this.getDataitem().setCompetencies(competencies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public CompetenceDataitem getDataitem() {
		return (CompetenceDataitem) dataitem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#toString()
	 */
	@Override
	public String toString() {
		return this.getDataitem().toString() + ", type=" + getType();
	}

	/**
	 * Gets the root competencies.
	 *
	 * @return the root competencies
	 */
	public List<Competence> getRootCompetencies() {
		List<Competence> roots = new ArrayList<Competence>();
		for (Competence c : this.getCompetencies()) {
			if (c != null) {
				if (!StringUtil.isNotNullAndNotEmpty(c.getParent())) {
					roots.add(c);
				}
			}
		}
		return roots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.validator.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		return messages;
	}

	/**
	 * Gets the child competences
	 *
	 * @param competence the competenceIdentifier
	 * @return the children of the competence with named competenceIdentifier
	 */
	public List<Competence> getChildren(String competence) {
		List<Competence> c = new ArrayList<Competence>();

		if (competence == null) {
			return null;
		}

		for (Competence cd : this.getCompetencies()) {
			if (competence.equalsIgnoreCase(cd.getParent())) {
				c.add(cd);
			}
		}

		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IWorkitemRenderer#render(org.rogatio.circlead.
	 * control.synchronizer.ISynchronizer)
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		Element table = new Element("table");
		table.attr("class", "wrapped");
		Element tbody = table.appendElement("tbody");

		Element tr = tbody.appendElement("tr");
		tr.appendElement("th").attr("colspan", "1").appendText(COMPETENCE.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(ROLES.toString());
		tr.appendElement("th").attr("colspan", "1").appendText(DESCRIPTION.toString());

		WorkitemTree tree = new WorkitemTree(WorkitemType.COMPETENCE);

		showNodes("", tree.getRoot(), tbody, renderer);

		table.appendTo(element);

		return element;
	}

	/**
	 * Show nodes.
	 *
	 * @param spacer   the spacer
	 * @param node     the node
	 * @param tbody    the tbody
	 * @param renderer the renderer
	 */
	private void showNodes(String spacer, TreeNode node, Element tbody, ISynchronizerRendererEngine renderer) {

		if (node.getWorkitem() instanceof Competence) {
			Competence c = (Competence) node.getWorkitem();

			Element tr = tbody.appendElement("tr");
			Element td = tr.appendElement("td").attr("colspan", "1");

			Element t = new Element("table").attr("style", "border:none;");
			Element tre = t.appendElement("tr");
			tre.appendElement("td").attr("style", "white-space:nowrap;").attr("style", "border:none;").append(spacer);

			tre.appendElement("td").attr("style", "border:none;")
					.append("<span style=\"color: rgb(" + node.getColor().getRed() + "," + node.getColor().getGreen()
							+ "," + node.getColor().getBlue() + ");\">" + node.getWorkitem().getTitle() + "</span>");

			t.appendTo(td);

			List<Role> roles = R.findRolesWithCompetence(c.getTitle());
			td = tr.appendElement("td").attr("colspan", "1");
			renderer.addRoleList(td, roles);

			if (node.getChildCount() > 0) {
				for (TreeNode n : node.getChildren()) {
					showNodes(spacer + "&nbsp;&nbsp;", n, tbody, renderer);
				}
			}

			td = tr.appendElement("td").attr("colspan", "1");
			if (c.getDescription() != null) {
				td.appendText(c.getDescription());
			} else {
				td.appendText("-");
			}

		} else {
			if (node.getChildCount() > 0) {
				for (TreeNode n : node.getChildren()) {
					showNodes(spacer + "&nbsp;&nbsp;", n, tbody, renderer);
				}
			}
		}
	}

}
