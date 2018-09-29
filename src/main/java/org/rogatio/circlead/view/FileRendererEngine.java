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
import org.rogatio.circlead.control.validator.ValidationMessage;
//import org.rogatio.circlead.control.synchronizer.atlassian.parser.Parser;
import org.rogatio.circlead.model.WorkitemStatusParameter;
import org.rogatio.circlead.model.data.ActivityDataitem;
import org.rogatio.circlead.model.data.HowTo;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ObjectUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class RenderUtil.
 */
public class FileRendererEngine implements ISynchronizerRendererEngine {

	/** The synchronizer. */
	private ISynchronizer synchronizer;

	/**
	 * Instantiates a new file renderer.
	 *
	 * @param synchronizer the synchronizer
	 */
	public FileRendererEngine(ISynchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rogatio.circlead.view.ISynchronizerRenderer#getSynchronizer()
	 */
	public ISynchronizer getSynchronizer() {
		return synchronizer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addActivityList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	public void addActivityList(Element element, List<Activity> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Activity activity : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "../web/" + activity.getId(synchronizer) + ".html")
							.appendText(activity.getTitle());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.ISynchronizerRendererEngine#addWorkitemTable(org.jsoup.nodes.Element, java.util.List)
	 */
	public void addWorkitemTable(Element element, List<IWorkitem> workitem) {
		if (ObjectUtil.isListNotNullAndEmpty(workitem)) {
			Element table = element.appendElement("div").appendElement("table");

			Element tr = table.appendElement("tr");
			tr.appendElement("th").appendText("Title");
			tr.appendElement("th").appendText("Typ");
			tr.appendElement("th").appendText("Status");

			for (IWorkitem w : workitem) {
				tr = table.appendElement("tr");
				tr.appendElement("td").appendElement("a").attr("href", "../web/" + w.getId(synchronizer) + ".html")
						.appendText(w.getTitle());
				;
				tr.appendElement("td").appendText(w.getType());
				Element td = tr.appendElement("td");
				addStatus(td, w.getStatus());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addRolegroupList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	public void addRolegroupList(Element element, List<Rolegroup> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Rolegroup rolegroup : list) {
					Element li = ul.appendElement("li");
					li.appendElement("a").attr("href", "../web/" + rolegroup.getId(synchronizer) + ".html")
							.appendText(rolegroup.getTitle());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.ISynchronizerRendererEngine#addSubActivityList(org.jsoup.nodes.Element, java.util.List, org.rogatio.circlead.model.work.Activity, org.rogatio.circlead.model.work.Role)
	 */
	public void addSubActivityList(Element element, List<ActivityDataitem> list, Activity activity, Role role) {
		List<Activity> a = Repository.getInstance().getActivities(role.getTitle());

		if (ObjectUtil.isListNotNullAndEmpty(list)) {
			// Open html-list
			Element ul = element.appendElement("div").appendElement("ul");
			for (ActivityDataitem activitydataitem : list) {
				// Create html-List-item
				Element li = ul.appendElement("li");

				// Add subactivity-title to list with valid link

				boolean found = false;
				for (Activity act : a) {
					if (act.getTitle().equals(activitydataitem.getTitle())) {
						found = true;
					}
				}

				if (found) {
					for (Activity act : a) {
						if (act.getTitle().equals(activitydataitem.getTitle())) {
							li.appendText("").appendElement("a")
									.attr("href", "../web/" + activitydataitem.getId(synchronizer) + ".html")
									.appendText(activitydataitem.getTitle());
							li.appendText("");
						}
					}
				} else {
					li.appendText(activitydataitem.getTitle());
				}

				li.appendText(" (").appendElement("a").attr("href", "../web/" + activity.getId(synchronizer) + ".html")
						.appendText(activity.getTitle());
				li.appendText(")");

			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list    the list
	 */
	public void addRoleList(Element element, List<Role> list) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					@SuppressWarnings("unused")
					Role r = Repository.getInstance().getRole(role.getTitle());
					li.appendElement("a").attr("href", "../web/" + role.getId(synchronizer) + ".html")
							.appendText(role.getTitle());
				}
			}
		}
	}

	/**
	 * Adds the role list.
	 *
	 * @param element the element
	 * @param list    the list
	 * @param person  the person
	 */
	public void addRoleList(Element element, List<Role> list, Person person) {
		if (list != null) {
			if (list.size() > 0) {
				Element ul = element.appendElement("div").appendElement("ul");
				for (Role role : list) {
					Element li = ul.appendElement("li");
					Role r = Repository.getInstance().getRole(role.getTitle());
					if (r != null) {
						li.appendElement("a").attr("href", "../web/" + role.getId(synchronizer) + ".html")
								.appendText(role.getTitle());
					} else {
						li.appendText(role.getTitle());
					}

					if (role.getDataitem().hasRepresentation(person.getFullname())) {
						String representation = role.getDataitem().getRepresentation(person.getFullname());
						WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
						if (status != null) {
							li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
									.appendText(status.getName());
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
	 * @param key   the key
	 * @param value the value
	 * @param table the table
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
	 * @param element the element
	 * @param map     the map
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
	 * @param element the element
	 * @param list    the list
	 * @param role    the role
	 */
	public void addPersonList(Element element, List<String> list, Role role) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");
			if (person != null) {
				li.appendElement("a").attr("href", "../web/" + person.getId(synchronizer) + ".html")
						.appendText(person.getTitle());
			} else {
				li.appendText(identifier);
			}
			if (role.getDataitem().hasRepresentation(identifier)) {
				String representation = role.getDataitem().getRepresentation(identifier);
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
				}
			}
			if (role.getDataitem().hasSkill(identifier)) {
				String skill = role.getDataitem().getSkill(identifier);
				li.append("&nbsp;");
				li.appendText("" + skill + "%");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addStatus(org.jsoup.nodes.
	 * Element, java.lang.String)
	 */
	public void addStatus(Element element, String statusValue) {
		WorkitemStatusParameter status = WorkitemStatusParameter.get(statusValue);
		if (status != null) {
			element.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
					.appendText(status.getName());
		}
	}

	/**
	 * Adds the person list.
	 *
	 * @param element    the element
	 * @param list       the list
	 * @param role       the role
	 * @param leadPerson the lead person
	 */
	public void addPersonList(Element element, List<String> list, Role role, String leadPerson) {
		Element ul = element.appendElement("div").appendElement("ul");
		for (String identifier : list) {
			Person person = Repository.getInstance().getPerson(identifier);
			Element li = ul.appendElement("li");

			if (person != null) {
				if (identifier.equals(leadPerson)) {
					li.appendElement("u").appendElement("a")
							.attr("href", "../web/" + person.getId(synchronizer) + ".html")
							.appendText(person.getTitle());
				} else {
					li.appendElement("a").attr("href", "../web/" + person.getId(synchronizer) + ".html")
							.appendText(person.getTitle());
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
				WorkitemStatusParameter status = WorkitemStatusParameter.get(representation);
				if (status != null) {
					li.append("&nbsp;").appendElement("div").attr("id", "status" + status.getColor())
							.appendText(status.getName());
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
	 * @param element           the element
	 * @param list              the list
	 * @param underlinedElement the underlined element
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
	 * @param element the element
	 * @param list    the list
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
	 * @param element     the element
	 * @param description the description
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
	 * @param element     the element
	 * @param description the description
	 * @param list        the list
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addHowToItem(org.jsoup.nodes.
	 * Element, java.lang.String, java.lang.String)
	 */
	public void addHowToItem(Element element, String description, String content) {
		HowTo r = Repository.getInstance().getHowTo(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(":").append("&nbsp;");
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "../data/howtos/" + r.getUrl()).appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/* (non-Javadoc)
	 * @see org.rogatio.circlead.view.ISynchronizerRendererEngine#addActivityItem(org.jsoup.nodes.Element, java.lang.String, java.lang.String)
	 */
	public void addActivityItem(Element element, String description, String content) {
		Activity r = Repository.getInstance().getActivity(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "../web/" + r.getId(synchronizer) + ".html")
						.appendText(r.getTitle());
			} else {
				div.appendText(content);
			}
		} else {
			div.appendText("-");
		}
	}

	/**
	 * Adds the role item.
	 *
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
	 */
	public void addRoleItem(Element element, String description, String content) {
		Role r = Repository.getInstance().getRole(content);

		Element div = element.appendElement("div");
		if (description != null) {
			div.appendElement("b").appendText(description);
			div.appendText(":").append("&nbsp;");
		}
		if (content != null) {
			if (r != null) {
				div.appendElement("a").attr("href", "../web/" + r.getId(synchronizer) + ".html")
						.appendText(r.getTitle());
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
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
	 */
	public void addRolegroupItem(Element element, String description, String content) {
		Rolegroup rg = Repository.getInstance().getRolegroup(content);

		Element div = element.appendElement("div");
		div.appendElement("b").appendText(description);
		div.appendText(": ");
		if (content != null) {
			if (rg != null) {
				div.appendElement("a").attr("href", "../web/" + rg.getId(synchronizer) + ".html")
						.appendText(rg.getTitle());
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
	 * @param element     the element
	 * @param description the description
	 * @param content     the content
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
	 * @param element the element
	 * @param header  the header
	 */
	public void addH1(Element element, String header) {
		element.appendElement("h1").appendText(header);
	}

	/**
	 * Adds the H 2.
	 *
	 * @param element the element
	 * @param header  the header
	 */
	public void addH2(Element element, String header) {
		element.appendElement("h2").appendText(header);
	}

	/**
	 * Adds the H 3.
	 *
	 * @param element the element
	 * @param header  the header
	 */
	public void addH3(Element element, String header) {
		element.appendElement("h3").appendText(header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rogatio.circlead.view.ISynchronizerRenderer#addValidationList(org.jsoup.
	 * nodes.Element, java.util.List)
	 */
	@Override
	public void addValidationList(Element element, List<ValidationMessage> list) {
		Element table = element.appendElement("div").appendElement("table");
		for (ValidationMessage vm : list) {
			addDataPair(vm.getType().name(), vm.getMessage(), table);
		}
	}
}
