/**
 * Copyright (c) 2016 Dr. Matthias Wegner
 * 
 * Project Circlead (Circled Thread)
 * 
 * This project realizes a decentralized evolutionary model for
 * organic growing organisations. It can be used to organise and
 * steer the organisational communication and transparence.
 * 
 * DE: Ein Thread ist im englischen ein Gedankengang, ein durchgängiger Faden.
 * Dieser Faden ist kreisförmig aufgewickelt, wodurch die Rollen dargestellt werden. 
 * 
 * All rights reserved under the Creative Commons Licence BY-NC-SA 3.0.
 * You may obtain a copy of the License at
 * 
 *        https://creativecommons.org/licenses/by-nc-sa/3.0/
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rogatio.circlead.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.work.Competence;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.model.work.Rolegroup;
import org.rogatio.circlead.util.ColorPalette;

/**
 * The Class WorkitemTree.
 */
public class WorkitemTree {

	/**
	 * Instantiates a new workitem tree.
	 *
	 * @param type the type
	 */
	public WorkitemTree(WorkitemType type) {
		if (type == WorkitemType.ROLE) {
			createRoleTree(); 
		}
		if (type == WorkitemType.ROLEGROUP) {
			createRolegroupTree();
		}
		if (type == WorkitemType.COMPETENCE) {
			createCompetenceTree();
		}
	}

	/**
	 * Creates the competence tree.
	 */
	private void createCompetenceTree() {
		TreeNode root = new TreeNode(Parameter.ROOT.toString());
		setRoot(root);

		Competence rootCompetence = Repository.getInstance().getRootCompetence();

		for (Competence r : rootCompetence.getRootCompetencies()) {
			addChildNodeCompetence(root, r);
		}

		createLeafColorMap();
		for (Competence c : Repository.getInstance().getCompetencies()) {
			createTrunkColorMap(c);
		}
	}

	/**
	 * Creates the rolegroup tree.
	 */
	private void createRolegroupTree() {
		TreeNode root = new TreeNode(Parameter.ROOT.toString());
		setRoot(root);

		List<Rolegroup> roots = Repository.getInstance().getRootRolegroups();
		for (Rolegroup rolegroup : roots) {
			addChildNodeRolegroup(root, rolegroup);
		}

		createLeafColorMap();
		for (Role role : Repository.getInstance().getRoles()) {
			createTrunkColorMap(role);
		}
	}

	/**
	 * Creates the role tree.
	 */
	private void createRoleTree() {
		TreeNode root = new TreeNode(Parameter.ROOT.toString());
		setRoot(root);

		List<Role> roots = Repository.getInstance().getRootRoles();
		for (Role role : roots) {
			addChildNodeRole(root, role);
		}

		createLeafColorMap();
		for (Role role : Repository.getInstance().getRoles()) {
			createTrunkColorMap(role);
		}
	}

	/** The root. */
	private TreeNode root;

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Sets the root.
	 *
	 * @param root the new root
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * Gets the tree node.
	 *
	 * @param workitem the workitem
	 * @return the tree node
	 */
	public TreeNode getTreeNode(IWorkitem workitem) {
		return getTreeNode(workitem, root);
	}
	
	public TreeNode getTreeNode(String workitemTitle) {
		return getTreeNode(workitemTitle, root);
	}

	public TreeNode getTreeNode(String workitemTitle, TreeNode node) {
		List<TreeNode> children = node.getChildren();

		if (node.getWorkitem().getTitle().equals(workitemTitle)) {
			return node;
		}

		if (children.size() > 0) {
			for (TreeNode treeNode : children) {
				TreeNode x = getTreeNode(workitemTitle, treeNode);
				if (x != null) {
					return x;
				}
			}
		}

		return null;
	}
	
	/**
	 * Gets the tree node.
	 *
	 * @param workitem the workitem
	 * @param node the node
	 * @return the tree node
	 */
	public TreeNode getTreeNode(IWorkitem workitem, TreeNode node) {
		List<TreeNode> children = node.getChildren();

		if (node.getWorkitem().equals(workitem)) {
			return node;
		}

		if (children.size() > 0) {
			for (TreeNode treeNode : children) {
				TreeNode x = getTreeNode(workitem, treeNode);
				if (x != null) {
					return x;
				}
			}
		}

		return null;
	}

	/**
	 * Creates the leaf color map.
	 */
	private void createLeafColorMap() {
		List<IWorkitem> leafs = new ArrayList<IWorkitem>();
		getTreeLeafs(root, leafs);
		int colorPaletteSize = leafs.size();
		// Extend Color Palette with 1.25 to skip end of palette (and avoid same
		// colors)
		Color[] colors = ColorPalette.rainbow((int) (((double) colorPaletteSize) * ColorPalette.SCALEFACTOR));
		for (IWorkitem wi : leafs) {
			int idx = leafs.indexOf(wi);
			TreeNode tn = this.getTreeNode(wi, root);
			tn.setColor(colors[idx]);
		}
	}

	/**
	 * Creates the trunk color map.
	 *
	 * @param wi the wi
	 */
	private void createTrunkColorMap(IWorkitem wi) {
		TreeNode leafNode = getTreeNode(wi);
		if (leafNode != null) {
			TreeNode parentNode = leafNode.getParent();
			if (parentNode != null) {
				if (parentNode.getChildren().size() >= 1) {
					TreeNode firstNode = parentNode.getChildren().get(0);
					TreeNode lastNode = parentNode.getChildren().get(parentNode.getChildCount() - 1);
					if (firstNode != null) {
						if (firstNode.getWorkitem() != null) {
							if (lastNode != null) {
								if (lastNode.getWorkitem() != null) {
									Color firstColor = firstNode.getColor();
									if (firstColor == null) {
										firstColor = Color.black;
									}
									int r1 = firstColor.getRed();
									int g1 = firstColor.getGreen();
									int b1 = firstColor.getBlue();
									if (lastNode.getColor() != null) {
										int r2 = lastNode.getColor().getRed();
										int g2 = lastNode.getColor().getGreen();
										int b2 = lastNode.getColor().getBlue();
										int r = (r1 + r2) / 2;
										int g = (g1 + g2) / 2;
										int b = (b1 + b2) / 2;
										Color c = new Color(r, g, b);
										parentNode.setColor(c);
									} else {
										parentNode.setColor(firstNode.getColor());
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the tree leafs.
	 *
	 * @param treeNode the tree node
	 * @param leafs the leafs
	 * @return the tree leafs
	 */
	public void getTreeLeafs(TreeNode treeNode, List<IWorkitem> leafs) {
		if (treeNode.getChildCount() > 0) {
			for (TreeNode subTreeNode : treeNode.getChildren()) {
				getTreeLeafs(subTreeNode, leafs);
			}
		} else {
			leafs.add(treeNode.getWorkitem());
		}
	}

	/**
	 * Adds the child node competence.
	 *
	 * @param parent the parent
	 * @param competence the competence
	 */
	private void addChildNodeCompetence(TreeNode parent, Competence competence) {
		TreeNode node = new TreeNode(competence, parent);
		List<Competence> children = Repository.getInstance().getCompetenceChildren(competence.getTitle());
		if (children != null) {
			for (Competence c : children) {
				addChildNodeCompetence(node, c);
			}
		}
	}

	/**
	 * Adds the child node rolegroup.
	 *
	 * @param parent the parent
	 * @param rolegroup the rolegroup
	 */
	private void addChildNodeRolegroup(TreeNode parent, Rolegroup rolegroup) {
		TreeNode node = new TreeNode(rolegroup, parent);
		List<Rolegroup> children = Repository.getInstance().getRolegroupChildren(rolegroup.getTitle());
		for (Rolegroup r : children) {
			addChildNodeRolegroup(node, r);
		}
	}

	/**
	 * Adds the child node role.
	 *
	 * @param parent the parent
	 * @param role the role
	 */
	private void addChildNodeRole(TreeNode parent, Role role) {
		TreeNode node = new TreeNode(role, parent);
		List<Role> children = Repository.getInstance().getRoleChildren(role.getTitle());
		for (Role r : children) {
			addChildNodeRole(node, r);
		}
	}

	/** The Constant LOGGER. */
	final static Logger LOGGER = LogManager.getLogger(WorkitemTree.class);

	/**
	 * Prints the tree.
	 */
	public void printTree() {
		printNode("", root);
	}

	/**
	 * Prints the node.
	 *
	 * @param spacer the spacer
	 * @param node the node
	 */
	private void printNode(String spacer, TreeNode node) {
		LOGGER.info(
				spacer + "<font color=\"" + node.getHtmlColor() + "\">" + node.getWorkitem().getTitle() + "</font>");
		if (node.getChildCount() > 0) {
			for (TreeNode n : node.getChildren()) {
				printNode(spacer + "&nbsp;", n);
			}
		}
	}

}