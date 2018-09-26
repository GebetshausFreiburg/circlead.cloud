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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.HeadTableParserElement;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.control.validator.IValidator;
import org.rogatio.circlead.control.validator.ValidationMessage;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.util.ObjectUtil;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.ISynchronizerRendererEngine;
import org.rogatio.circlead.view.IWorkitemRenderer;

/**
 * The Class Activity.
 */
public class Activity extends DefaultWorkitem implements IWorkitemRenderer, IValidator {

	/**
	 * Instantiates a new activity.
	 */
	public Activity() {
		this.dataitem = new ActivityDataitem();
	}

	/**
	 * Instantiates a new activity.
	 *
	 * @param dataitem the dataitem
	 */
	public Activity(IDataitem dataitem) {
		super(dataitem);
	}

	/**
	 * Instantiates a new activity.
	 *
	 * @param data the data
	 */
	public Activity(Map<String, String> data) {
		this.dataitem = new ActivityDataitem();
		this.setAid(data.get("Aid"));
		this.setBPMN(data.get("BPMN"));
		this.setChild(data.get("Nachfolger"));
		this.setDescription(data.get("Beschreibung"));
		this.setTitle(data.get("Aktivität"));
		this.setResults(data.get("Erwartetes Ergebnis"));
		this.setResponsibleIdentifier(data.get("Durchführender"));
		this.setSupplierIdentifier(data.get("Unterstützer"));
		this.setConsultantIdentifier(data.get("Berater"));
		this.setInformedIdentifier(data.get("Informierter"));
	}

	/**
	 * Gets the role identifier.
	 *
	 * @return the responsible role identifier
	 */
	public String getResponsibleIdentifier() {
		return this.getDataitem().getResponsible();
	}

	/**
	 * Gets the accountable identifier.
	 *
	 * @return the accountable identifier
	 */
	public String getAccountableIdentifier() {
		return this.getDataitem().getAccountable();
	}

	/**
	 * Gets the informed identifiers.
	 *
	 * @return the informed identifiers
	 */
	public List<String> getInformedIdentifiers() {
		return this.getDataitem().getInformed();
	}

	/**
	 * Gets the supplier identifiers.
	 *
	 * @return the supplier identifiers
	 */
	public List<String> getSupplierIdentifiers() {
		return this.getDataitem().getSupplier();
	}

	/**
	 * Gets the consultant identifiers.
	 *
	 * @return the consultant identifiers
	 */
	public List<String> getConsultantIdentifiers() {
		return this.getDataitem().getConsultant();
	}

	/**
	 * Sets the role identifier.
	 *
	 * @param roleIdentifier the new responsible role identifier
	 */
	public void setResponsibleIdentifier(String roleIdentifier) {
		this.getDataitem().setResponsible(roleIdentifier);
	}

	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public String getResults() {
		return this.getDataitem().getResults();
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.getDataitem().getDescription();
	}

	public String getChild() {
		return this.getDataitem().getChild();
	}
	
	public String getBpmn() {
		return this.getDataitem().getBpmn();
	}
	
	/**
	 * Gets the aid.
	 *
	 * @return the aid
	 */
	public String getAid() {
		return this.getDataitem().getAid();
	}

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public void setResults(String results) {
		this.getDataitem().setResults(results);
	}

	public void setBPMN(String bpmn) {
		this.getDataitem().setBpmn(bpmn);
	}
	
	public void setChild(String child) {
		this.getDataitem().setChild(child);
	}
	
	/**
	 * Sets the aid.
	 *
	 * @param aid the new aid
	 */
	public void setAid(String aid) {
		this.getDataitem().setAid(aid);
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.getDataitem().setDescription(description);
	}

	/**
	 * Sets the consultant identifier.
	 *
	 * @param roleIdentifiers the new consultant identifier
	 */
	public void setConsultantIdentifier(String roleIdentifiers) {
		this.setConsultantIdentifier(StringUtil.toList(roleIdentifiers));
	}

	/**
	 * Sets the informed identifier.
	 *
	 * @param roleIdentifiers the new informed identifier
	 */
	public void setInformedIdentifier(String roleIdentifiers) {
		this.setInformedIdentifier(StringUtil.toList(roleIdentifiers));
	}

	/**
	 * Sets the supplier identifier.
	 *
	 * @param roleIdentifiers the new supplier identifier
	 */
	public void setSupplierIdentifier(String roleIdentifiers) {
		this.setSupplierIdentifier(StringUtil.toList(roleIdentifiers));
	}

	/**
	 * Sets the supplier identifier.
	 *
	 * @param roleIdentifiers the new supplier identifier
	 */
	public void setSupplierIdentifier(List<String> roleIdentifiers) {
		this.getDataitem().setSupplier(roleIdentifiers);
	}

	/**
	 * Sets the consultant identifier.
	 *
	 * @param roleIdentifiers the new consultant identifier
	 */
	public void setConsultantIdentifier(List<String> roleIdentifiers) {
		this.getDataitem().setConsultant(roleIdentifiers);
	}

	/**
	 * Sets the informed identifier.
	 *
	 * @param roleIdentifiers the new informed identifier
	 */
	public void setInformedIdentifier(List<String> roleIdentifiers) {
		this.getDataitem().setInformed(roleIdentifiers);
	}

	/**
	 * Sets the accountable identifier.
	 *
	 * @param roleIdentifier the new accountable identifier
	 */
	public void setAccountableIdentifier(String roleIdentifier) {
		this.getDataitem().setAccountable(roleIdentifier);
	}

	/**
	 * Sets the subactivities.
	 *
	 * @param table the new subactivities
	 */
	public void setSubactivities(HeadTableParserElement table) {
		this.setSubactivities(table.getActivities());
	}

	/**
	 * Sets the subactivities.
	 *
	 * @param subactivities the new subactivities
	 */
	public void setSubactivities(List<ActivityDataitem> subactivities) {
		this.getDataitem().setSubactivities(subactivities);
	}

	public List<ActivityDataitem> getSubactivitiesWithResponsible(String responsible) {
		List<ActivityDataitem> list = new ArrayList<ActivityDataitem>();
		if (ObjectUtil.isListNotNullAndEmpty(this.getSubactivities())) {
			for (ActivityDataitem sub : this.getSubactivities()) {
				if (StringUtil.isNotNullAndNotEmpty(sub.getResponsible())) {
					if (sub.getResponsible().equals(responsible)) {
						list.add(sub);
					}
				}
			}
		}
		return list;
	}

	public List<ActivityDataitem> getSubactivitiesWithResponsible() {
		List<ActivityDataitem> list = new ArrayList<ActivityDataitem>();
		if (ObjectUtil.isListNotNullAndEmpty(this.getSubactivities())) {
			for (ActivityDataitem sub : this.getSubactivities()) {
				if (StringUtil.isNotNullAndNotEmpty(sub.getResponsible())) {
					list.add(sub);
				}
			}
		}
		return list;
	}

	/**
	 * Gets the subactivities.
	 *
	 * @return the subactivities
	 */
	public List<ActivityDataitem> getSubactivities() {
		return this.getDataitem().getSubactivities();
	}

	/**
	 * Sets the how tos.
	 *
	 * @param howtos the new how tos
	 */
	public void setHowTos(List<String> howtos) {
		this.getDataitem().setHowtos(howtos);
	}

	/**
	 * Gets the how tos.
	 *
	 * @return the how tos
	 */
	public List<String> getHowTos() {
		return this.getDataitem().getHowtos();
	}

	/**
	 * Sets the how tos.
	 *
	 * @param howtos the new how tos
	 */
	public void setHowTos(String howtos) {
		List<String> list = Arrays.asList(howtos.split(","));
		this.setHowTos(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.model.work.DefaultWorkitem#getDataitem()
	 */
	@Override
	public ActivityDataitem getDataitem() {
		return (ActivityDataitem) dataitem;
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
		if (StringUtil.isNotNullAndNotEmpty(getAid())) {
			renderer.addItem(element, "AID", this.getAid());
		}

		Activity a = Repository.getInstance().getActivityWithSubactivity(this.getTitle());
		if (a != null) {
			renderer.addActivityItem(element, "Übergeordnete Aktivität", a.getTitle());
		}

		renderer.addH2(element, "Beteiligte Rollen");
		if (StringUtil.isNotNullAndNotEmpty(getResponsibleIdentifier())) {
			renderer.addRoleItem(element, "Durchführender", this.getResponsibleIdentifier());
		} else {
			renderer.addRoleItem(element, "Durchführender", "-");
		}

		if (StringUtil.isNotNullAndNotEmpty(getAccountableIdentifier())) {
			renderer.addRoleItem(element, "Rechenschaftsgebender", this.getAccountableIdentifier());
		} else {
			renderer.addRoleItem(element, "Rechenschaftsgebender", "-");
		}

		List<Role> roles = Repository.getInstance().getRoles(this.getSupplierIdentifiers());
		if (ObjectUtil.isListNotNullAndEmpty(roles)) {
			renderer.addItem(element, "Unterstützer:", "");
			renderer.addRoleList(element, roles);
		}

		roles = Repository.getInstance().getRoles(this.getConsultantIdentifiers());
		if (ObjectUtil.isListNotNullAndEmpty(roles)) {
			renderer.addItem(element, "Berater:", "");
			renderer.addRoleList(element, roles);
		}

		roles = Repository.getInstance().getRoles(this.getInformedIdentifiers());
		if (ObjectUtil.isListNotNullAndEmpty(roles)) {
			renderer.addItem(element, "Informierte:", "");
			renderer.addRoleList(element, roles);
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getDescription())) {
			renderer.addH2(element, "Beschreibung");
			renderer.addItem(element, this.getDescription());
		}

		if (StringUtil.isNotNullAndNotEmpty(this.getResults())) {
			renderer.addH2(element, "Erwartetes Ergebnis");
			renderer.addItem(element, this.getResults());
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getSubactivities())) {
			Element table = Parser.createHeaderTable(this.getSubactivities(), synchronizer, true);

			/*
			 * for (ActivityDataitem subactivity : getSubactivities()) { Activity a =
			 * Repository.getInstance().getActivity(subactivity.getTitle()); if (a != null)
			 * { Elements es = table.getElementsContainingText(subactivity.getTitle());
			 * System.out.println(es); Element e = es.get(0); e.text("");
			 * renderer.addActivityItem(e, null, subactivity.getTitle()); } }
			 */

			table.appendTo(element);
		}

		List<HowTo> howtos = Repository.getInstance().getIndexHowTos();
		if (this.getHowTos() != null) {
			for (String ht : this.getHowTos()) {
				for (HowTo howTo : howtos) {
					if (ht.equalsIgnoreCase(howTo.getTitle())) {
						if (howTo.getSynchronizer().equals(synchronizer.toString())) {
							renderer.addHowToItem(element, "HowTo", howTo.getTitle());
						}
					}
				}
			}
		}

		return element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.control.IValidator#validate()
	 */
	@Override
	public List<ValidationMessage> validate() {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

		if (!StringUtil.isNotNullAndNotEmpty(this.getResponsibleIdentifier())) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Role Responsible missing", "Activity '" + this.getTitle() + "' has no responsible role named");
			messages.add(m);
		}

		if (ObjectUtil.isListNotNullAndEmpty(this.getSubactivities())) {
			for (ActivityDataitem activity : this.getSubactivities()) {
				Activity a = Repository.getInstance().getActivity(activity.getTitle());
				if (a != null) {
					String actR = a.getResponsibleIdentifier();
					String subR = activity.getResponsible();
					if (StringUtil.isNotNullAndNotEmpty(actR)) {
						if (!StringUtil.isNotNullAndNotEmpty(subR)) {
							ValidationMessage m = new ValidationMessage(this);
							m.error("Responsible missing", "Subactivity '" + activity.getTitle()
									+ "' has not named responsible '" + actR + "'");
							messages.add(m);
						}
					}
					if (StringUtil.isNotNullAndNotEmpty(subR)) {
						if (!StringUtil.isNotNullAndNotEmpty(actR)) {
							ValidationMessage m = new ValidationMessage(this);
							m.error("Responsible missing",
									"Activity '" + a.getTitle() + "' has not named responsible '" + subR + "'");
							messages.add(m);
						}
					}
					
					if (StringUtil.isNotNullAndNotEmpty(subR)) {
						if (StringUtil.isNotNullAndNotEmpty(actR)) {
							if (subR.equals(actR)) {
								ValidationMessage m = new ValidationMessage(this);
								m.error("Responsible unequal", "Activity '" + a.getTitle()
										+ "' has unequal responsible '" + subR + "'!='" + actR + "'");
								messages.add(m);
							}
						}
					}
				}
			}
		}

//		Activity parentActivity = Repository.getInstance().getActivityWithSubactivity(this.getTitle());
//		if (parentActivity != null) {
//			x
//		}

		return messages;
	}

}
