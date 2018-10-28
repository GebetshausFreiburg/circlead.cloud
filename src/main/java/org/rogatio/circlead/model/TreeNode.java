package org.rogatio.circlead.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.rogatio.circlead.model.data.DefaultDataitem;
import org.rogatio.circlead.model.work.DefaultWorkitem;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.util.ObjectUtil;

public class TreeNode {

	private TreeNode parent;
	private IWorkitem workitem;
	private Color color;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	public TreeNode(String rootName) {
		this.workitem = new DefaultWorkitem(new DefaultDataitem());
		workitem.setTitle(rootName);
	}

	public TreeNode(IWorkitem workitem, TreeNode parentNode, Color color) {
		this.workitem = workitem;
		this.parent = parentNode;
		this.color = color;
		parentNode.addChild(this);
	}

	public TreeNode(IWorkitem workitem, TreeNode parentNode) {
		this.workitem = workitem;
		this.parent = parentNode;
		parentNode.addChild(this);
	}

	public String getHtmlColor() {
		try {
			return ObjectUtil.convertToHtmlColor(color);
		} catch (java.lang.NullPointerException e) {
			return "#000000";
		}
	}

	public Color getColor() {
		
		if (color==null) {
			return Color.BLACK;
		}
		
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public IWorkitem getWorkitem() {
		return workitem;
	}

	private void addChild(TreeNode child) {
		children.add(child);
	}

	public TreeNode getParent() {
		return parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public int getChildCount() {
		return children.size();
	}
}
