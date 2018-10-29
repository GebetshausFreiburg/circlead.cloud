package org.rogatio.circlead.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.rogatio.circlead.model.data.DefaultDataitem;
import org.rogatio.circlead.model.work.DefaultWorkitem;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.util.ObjectUtil;

/**
 * The Class TreeNode.
 */
public class TreeNode {

	/** The parent. */
	private TreeNode parent;
	
	/** The workitem. */
	private IWorkitem workitem;
	
	/** The color. */
	private Color color;
	
	/** The children. */
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	/**
	 * Instantiates a new tree node.
	 *
	 * @param rootName the root name
	 */
	public TreeNode(String rootName) {
		this.workitem = new DefaultWorkitem(new DefaultDataitem());
		workitem.setTitle(rootName);
	}

	/**
	 * Instantiates a new tree node.
	 *
	 * @param workitem the workitem
	 * @param parentNode the parent node
	 * @param color the color
	 */
	public TreeNode(IWorkitem workitem, TreeNode parentNode, Color color) {
		this.workitem = workitem;
		this.parent = parentNode;
		this.color = color;
		parentNode.addChild(this);
	}

	/**
	 * Instantiates a new tree node.
	 *
	 * @param workitem the workitem
	 * @param parentNode the parent node
	 */
	public TreeNode(IWorkitem workitem, TreeNode parentNode) {
		this.workitem = workitem;
		this.parent = parentNode;
		parentNode.addChild(this);
	}

	/**
	 * Gets the html color.
	 *
	 * @return the html color
	 */
	public String getHtmlColor() {
		try {
			return ObjectUtil.convertToHtmlColor(color);
		} catch (java.lang.NullPointerException e) {
			return "#000000";
		}
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		
		if (color==null) {
			return Color.BLACK;
		}
		
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the workitem.
	 *
	 * @return the workitem
	 */
	public IWorkitem getWorkitem() {
		return workitem;
	}

	/**
	 * Adds the child.
	 *
	 * @param child the child
	 */
	private void addChild(TreeNode child) {
		children.add(child);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public int getChildCount() {
		return children.size();
	}
}
