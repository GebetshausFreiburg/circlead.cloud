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
import java.util.List;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.IValidator;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.ValidationMessage;
import org.rogatio.circlead.control.synchronizer.atlassian.parser.ListParserElement;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.IDataitem;
import org.rogatio.circlead.model.data.RolegroupDataitem;
import org.rogatio.circlead.util.StringUtil;
import org.rogatio.circlead.view.IRenderer;
import org.rogatio.circlead.view.RenderUtil;

public class Activity extends DefaultWorkitem implements IRenderer, IValidator {

	public Activity() {
		this.dataitem = new ActivityDataitem();
	}

	public Activity(IDataitem dataitem) {
		super(dataitem);
	}
	
	public String getRoleIdentifier() {
		return this.getDataitem().getRole();
	}
	
	public void setRoleIdentifier(String roleIdentifier) {
		this.getDataitem().setRole(roleIdentifier);
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
	public Element render() {
		Element element = new Element("p");

		RenderUtil.addRoleItem(element, "Rolle", this.getRoleIdentifier());
		
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

		if (!StringUtil.isNotNullAndNotEmpty(this.getRoleIdentifier())) {
			ValidationMessage m = new ValidationMessage(this);
			m.error("Role missing", "Activity '" + this.getTitle() + "' has no role named");
			messages.add(m);
		}
		
		return messages;
	}

}
