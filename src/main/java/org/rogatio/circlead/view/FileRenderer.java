/*
 * Circlead - Develop and structure evolutionary Organisations
 * 
 * @author Matthias Wegner
 * @version 0.1
 * @since 01.07.2018
 * 
 */
package org.rogatio.circlead.view;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
//import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.StatusParameter;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;

/**
 * The Class RenderUtil.
 */
public class FileRenderer implements ISynchronizerRenderer {

	private ISynchronizer synchronizer;

	public FileRenderer(ISynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	public ISynchronizer getSynchronizer() {
		return synchronizer;
	}

	public void addActivityList(Element element, List<Activity> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Activity activity : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", activity.getId(synchronizer) + ".html").appendText(activity.getTitle());
				}
			}
		}
	}

	public void addRolegroupList(Element element, List<Rolegroup> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Rolegroup rolegroup : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", rolegroup.getId(synchronizer) + ".html").appendText(rolegroup.getTitle());
				}
			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 */
	public void addRoleList(Element element, List<Role> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					Role r = Repository.getInstance().getRole(role.getTitle());
					li.appendElement("a").attr("href", role.getId(synchronizer) + ".html").appendText(role.getTitle());
				}
			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 * @param person
	 *            the person
	 */
	public void addRoleList(Element element, List<Role> list, Person person) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					Role r = Repository.getInstance().getRole(role.getTitle());
					if (r != null) {
						li.appendElement("a").attr("href", role.getId(synchronizer) + ".html").appendText(role.getTitle());
					} else {
						li.appendText(role.getTitle());
					}

					if (role.getDataitem().hasRepresentation(person.getFullname())) {
						String representation = role.getDataitem().getRepresentation(person.getFullname());
						StatusParameter status = StatusParameter.get(representation);
						if (status != null) {
							li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor()).appendText(status.getName());
						}
					}
					if (role.getDataitem().hasSkill(person.getFullname())) {
						String skill = role.getDataitem().getSkill(person.getFullname());
						li.append("&nbsp;");
						li.appendText("" + skill + "%");
					}
				}
			}
		}
	}

	/**
	 * Adds the data pair.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param table
	 *            the table
	 */
	private void addDataPair(String key, String value, Element table) {
		if (value != null) {
			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText(key.trim());
			tr.appendElement("td").appendText(value.trim());
		}
	}

	/**
	 * Adds the table.
	 *
	 * @param element
	 *            the element
	 * @param map
	 *            the map
	 */
	public void addTable(Element element, Map<String, String> map) {
		if (map != null) {
			if (map.size() > 0) {
				Element table = element.appendElement("div").appendElement("table");
				for (String key : map.keySet()) {
					String value = map.get(key);
					addDataPair(key, value, table);
				}
			}
		}
	}

	/**
	 * Adds the person list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 * @param role
	 *            the role
	 */
	public void addPersonList(Element element, List<String> list, Role role) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");
			if (person != null) {
				li.appendElement("a").attr("href", person.getId(synchronizer) + ".html").appendText(person.getTitle());
			} else {
				li.appendText(identifier);
			}
			if (role.getDataitem().hasRepresentation(identifier)) {
				String representation = role.getDataitem().getRepresentation(identifier);
				StatusParameter status = StatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor()).appendText(status.getName());
				}
			}
			if (role.getDataitem().hasSkill(identifier)) {
				String skill = role.getDataitem().getSkill(identifier);
				li.append("&nbsp;");
				li.appendText("" + skill + "%");
			}
		}

	}

	/**
	 * Adds the person list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 * @param role
	 *            the role
	 * @param leadPerson
	 *            the lead person
	 */
	public void addPersonList(Element element, List<String> list, Role role, String leadPerson) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");

			if (person != null) {
				if (identifier.equals(leadPerson)) {
					li.appendElement("u").appendElement("a").attr("href", person.getId(synchronizer) + ".html").appendText(person.getTitle());
				} else {
					li.appendElement("a").attr("href", person.getId(synchronizer) + ".html").appendText(person.getTitle());
				}
			} else {
				if (identifier.equals(leadPerson)) {
					li.appendElement("u").appendText(identifier);
				} else {
					li.appendText(identifier);
				}
			}
			if (role.getDataitem().hasRepresentation(identifier)) {
				String representation = role.getDataitem().getRepresentation(identifier);
				StatusParameter status = StatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor()).appendText(status.getName());
				}
			}
			if (role.getDataitem().hasSkill(identifier)) {
				String skill = role.getDataitem().getSkill(identifier);
				li.append("&nbsp;");
				li.appendText("" + skill + "%");
			}
		}

	}

	/**
	 * Adds the list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 * @param underlinedElement
	 *            the underlined element
	 */
	public void addList(Element element, List<String> list, String underlinedElement) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (String item : list) {

					if (item.equals(underlinedElement)) {
						ul.appendElement("li").appendElement("u").appendText(item);
					} else {
						ul.appendElement("li").appendText(item);
					}
				}
			}
		}
	}

	/**
	 * Adds the list.
	 *
	 * @param element
	 *            the element
	 * @param list
	 *            the list
	 */
	public void addList(Element element, List<String> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (String item : list) {
					ul.appendElement("li").appendText(item);
				}
			}
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element
	 *            the element
	 * @param description
	 *            the description
	 */
	public void addItem(Element element, String description) {
		if (description != null) {
			Element div = element.appendElement("div");
			div.appendText(description);
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element
	 *            the element
	 * @param description
	 *            the description
	 * @param list
	 *            the list
	 */
	public void addItem(Element element, String description, List<String> list) {
		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (list != null) {
			div.appendText(String.join(", ", list));
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the role item.
	 *
	 * @param element
	 *            the element
	 * @param description
	 *            the description
	 * @param content
	 *            the content
	 */
	public void addRoleItem(Element element, String description, String content) {
		Role r = Repository.getInstance().getRole(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(":").append("&nbsp;");
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", r.getId(synchronizer) + ".html").appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the rolegroup item.
	 *
	 * @param element
	 *            the element
	 * @param description
	 *            the description
	 * @param content
	 *            the content
	 */
	public void addRolegroupItem(Element element, String description, String content) {
		Rolegroup rg = Repository.getInstance().getRolegroup(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (content != null) {
			if (rg != null) {
				div.appendElement("a").attr("href", rg.getId(synchronizer) + ".html").appendText(rg.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param element
	 *            the element
	 * @param description
	 *            the description
	 * @param content
	 *            the content
	 */
	public void addItem(Element element, String description, String content) {
		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (content != null) {
			div.appendText(content);
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the H 1.
	 *
	 * @param element
	 *            the element
	 * @param header
	 *            the header
	 */
	public void addH1(Element element, String header) {
		element.appendElement("h1").appendText(header);
	}

	/**
	 * Adds the H 2.
	 *
	 * @param element
	 *            the element
	 * @param header
	 *            the header
	 */
	public void addH2(Element element, String header) {
		element.appendElement("h2").appendText(header);
	}

	/**
	 * Adds the H 3.
	 *
	 * @param element
	 *            the element
	 * @param header
	 *            the header
	 */
	public void addH3(Element element, String header) {
		element.appendElement("h3").appendText(header);
	}
}
