package org.rogatio.circlead.model.data;

import java.util.ArrayList;
import java.util.List;

import org.rogatio.circlead.control.Comparators;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.control.synchronizer.ISynchronizer;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;

/**
 * The Class WorkitemBreakdownElement is used for JSON-Export (for D3-Library)
 */
public class WorkitemBreakdownElement {

	/** The name. */
	private String name;

	/** The id. */
	private String id;

	/** The category. */
	private String category;

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/** The color. */
	private String color = "#FFFFFF";

	/** The children. */
	private List<WorkitemBreakdownElement> children = new ArrayList<WorkitemBreakdownElement>();

	/** The size. */
	private int size = 1;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<WorkitemBreakdownElement> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children the new children
	 */
	public void setChildren(List<WorkitemBreakdownElement> children) {
		this.children = children;
	}

	/**
	 * Adds the child.
	 *
	 * @param role the role
	 * @param synchronizer the synchronizer
	 */
	public void addChild(Role role, ISynchronizer synchronizer) {
		List<Role> children = Repository.getInstance().getRoleChildren(role.getTitle(), Comparators.REDUNDANCE);

		WorkitemBreakdownElement e = new WorkitemBreakdownElement();
		e.setName(role.getTitle());
		e.setCategory(role.getRolegroupIdentifier());
		e.setId(role.getId(synchronizer));
		e.setSize(role.getPersonIdentifiers().size() + Repository.getInstance().getTeamsWithRole(role).size());
//		e.setColor(ObjectUtil.createRandomHtmlColor());
		addChild(e);

		if (ObjectUtil.isListNotNullAndEmpty(children)) {
			for (Role r : children) {
				e.addChild(r, synchronizer);
			}
		}
	}

	/**
	 * Adds the child.
	 *
	 * @param child the child
	 */
	public void addChild(WorkitemBreakdownElement child) {
		this.children.add(child);
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		if (size >= 0) {
			this.size = size;
		}
	}

}
