/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

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
import org.rogatio.circlead.model.TreeNode;
import org.rogatio.circlead.model.WorkitemTree;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.CompetenceDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

public class Competence extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

	public Competence() {
		this.dataitem = new CompetenceDataitem();
	}

	public Competence(IDataitem dataitem) {
		super(dataitem);
	}

	public String getParent() {
		return this.getDataitem().getParent();
	}

	public void setParent(String parent) {
		this.getDataitem().setParent(parent);
	}

	public String getDescription() {
		return this.getDataitem().getDescription();
	}

	public void setDescription(String description) {
		this.getDataitem().setDescription(description);
	}

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
		}

	}

	private Map<CompetenceDataitem, Competence> map = new HashMap<CompetenceDataitem, Competence>();

	public List<Competence> getCompetencies() {
		List<Competence> c = new ArrayList<Competence>();
		for (CompetenceDataitem cd : this.getDataitem().getCompetencies()) {
			c.add(map.get(cd));
		}
		return c;
	}

	public void setCompetencies(List<CompetenceDataitem> competencies) {

		for (CompetenceDataitem competenceDataitem : competencies) {
			map.put(competenceDataitem, new Competence(competenceDataitem));
		}

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

	public List<Competence> getRootCompetencies() {
		List<Competence> roots = new ArrayList<Competence>();
		for (Competence c : this.getCompetencies()) {
			if (!StringUtil.isNotNullAndNotEmpty(c.getParent())) {
				roots.add(c);
			}
		}
		return roots;
	}

	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		return messages;
	}

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

	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();
		Element element = new Element("p");

		WorkitemTree tree = new WorkitemTree(WorkitemType.COMPETENCE);

		Element ul = element.appendElement("ul");

		showNodes("", tree.getRoot(), ul);

//		for (CompetenceDataitem c : this.getCompetencies()) {
//			ul.appendElement("li");
//		}
//		<span style="color: rgb(0,255,0);">sdsdsds</span>

		return element;
	}

	private void showNodes(String spacer, TreeNode node, Element e) {
//		System.out.println(node);
		e.appendElement("li")
				.append(spacer + "<span style=\"color: rgb(" + node.getColor().getRed() + ","
						+ node.getColor().getGreen() + "," + node.getColor().getBlue() + ");\">"
						+ node.getWorkitem().getTitle() + "</span>");
		if (node.getChildCount() > 0) {
			for (TreeNode n : node.getChildren()) {
				showNodes(spacer + "&nbsp;&nbsp;", n, e);
			}
		}
	}

}
