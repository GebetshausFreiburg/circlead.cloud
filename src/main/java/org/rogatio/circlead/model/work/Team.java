/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.model.work;

import static org.rogatio.circlead.model.Parameter.DESCRIPTION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.TeamTableParserElement;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.TeamDataitem;
import org.rogatio.circlead.model.data.TeamEntry;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Class Team.
 */
public class Team extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

	public Team() {
		this.dataitem = new TeamDataitem();
	}

	/**
	 * Instantiates a new team.
	 *
	 * @param dataitem the dataitem
	 */
	public Team(IDataitem dataitem) {
		super(dataitem);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.getDataitem().getDescription();
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.getDataitem().setDescription(description);
	}

	public void setTeamTable(TeamTableParserElement table) {
		List<Map<String, String>> dataList = table.getDataList();
		if (dataList.size() > 0) {
			List<TeamEntry> teams = new ArrayList<TeamEntry>();
			for (Map<String, String> dataMap : dataList) {
				TeamEntry entry = new TeamEntry(dataMap);
				if (StringUtil.isNotNullAndNotEmpty(entry.getRoleIdentifier())) {
					teams.add(entry);
				}
			}
			this.getDataitem().setTeamEntries(teams);
		}

	}

	public List<TeamEntry> getTeamEntries() {
		return this.getDataitem().getTeamEntries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public TeamDataitem getDataitem() {
		return (TeamDataitem) dataitem;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.IRenderer#render()
	 */
	@Override
	public Element render(ISynchronizer synchronizer) {
		ISynchronizerRendererEngine renderer = synchronizer.getRenderer();

		Element element = new Element("p");

		if (StringUtil.isNotNullAndNotEmpty(this.getDescription())) {
			renderer.addH2(element, DESCRIPTION.toString());
			renderer.addItem(element, this.getDescription());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getTeamEntries())) {
			Element table = Parser.createTeamEntryTable(this.getTeamEntries(), synchronizer, true);
			table.appendTo(element);
		}

		return element;
	}

	public Map<String, Integer> getTeamroleMatch() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<TeamEntry> entries = this.getTeamEntries();
		for (TeamEntry teamEntry : entries) {
			if (ObjectUtil.isListNotNullAndEmpty(teamEntry.getPersonIdentifiers())) {
				if (teamEntry.getPersonIdentifiers().size() < teamEntry.getNeeded()) {
					int diff = - teamEntry.getNeeded() + teamEntry.getPersonIdentifiers().size();
					map.put(teamEntry.getRoleIdentifier(), diff);
				}
			} else {
				map.put(teamEntry.getRoleIdentifier(), -teamEntry.getNeeded());
			}
		}
		return map;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		List<TeamEntry> entries = this.getTeamEntries();
		for (TeamEntry teamEntry : entries) {
			if (ObjectUtil.isListNotNullAndEmpty(teamEntry.getPersonIdentifiers())) {
				if (teamEntry.getPersonIdentifiers().size() < teamEntry.getNeeded()) {
					ValidationMessage m = new ValidationMessage(this);
					m.error("Teamrole not completly set", "Teamrole '" + teamEntry.getRoleIdentifier() + "' in Team '"+this.getTitle()+"' needs "+teamEntry.getNeeded() +"persons and has only "+teamEntry.getPersonIdentifiers().size());
					messages.add(m);
				}
			} else {
				ValidationMessage m = new ValidationMessage(this);
				m.error("Teamrole empty", "Teamrole '" + teamEntry.getRoleIdentifier() + "' in Team '"+this.getTitle()+"' needs "+teamEntry.getNeeded() +" persons");
				messages.add(m);
			}
		}

		return messages;
	}

}
