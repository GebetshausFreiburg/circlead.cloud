package org.rogatio.circlead.view;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import org.jsoup.nodes.Element;
import org.rogatio.circlead.control.Repository;
import org.rogatio.circlead.model.WorkitemType;
import org.rogatio.circlead.model.work.IWorkitem;
import org.rogatio.circlead.model.work.Person;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.util.ObjectUtil;

/**
 * The Class SvgBuilder creates a XML-DOM for and SVG.
 */
public class SvgBuilder {

	/**
	 * Creates the svg element.
	 *
	 * @param size the size
	 * @return the element
	 */
	private static Element createSvgElement(int size) {
		Element element = new Element("svg");
		element.attr("id", UUID.randomUUID().toString());
		element.attr("width", "100%");
		element.attr("height", "25px");
		element.attr("viewPort", "0 0 " + (size + 4) + " 25");
		element.appendElement("rect").attr("x", "0").attr("y", "0").attr("width", "" + (size + 4) + "")
				.attr("height", "25").attr("fill", "gray");
		element.appendElement("rect").attr("x", "1").attr("y", "1").attr("width", "" + (size + 2) + "")
				.attr("height", "23").attr("fill", "white");
		return element;
	}

	/**
	 * Creates the person dna profile.
	 *
	 * @param person the person
	 * @param size the size
	 * @return the element
	 */
	public static Element createPersonDnaProfile(Person person, int size) {
		WorkitemTree tree = new WorkitemTree(WorkitemType.COMPETENCE);
		List<String> competencies = Repository.getInstance().getCompetenciesOfPerson(person);
		Element element = createSvgElement(size);

		for (String competence : competencies) {
			SvgBuilder.addSvgPersonCompetence(person, competence, size, element, tree);

			List<Role> implicitRoles = Repository.getInstance().getRolesWithCompetence(person, competence);
			for (Role r : implicitRoles) {
				List<Role> f = Repository.getInstance().getParentRoles(r);
				for (Role rx : f) {
					List<String> co = rx.getCompetences();
					for (String c : co) {
						SvgBuilder.addSvgPersonImplicitCompetence(person, c, size, element, tree);
					}
				}

			}

		}

		return element;
	}

	/**
	 * Creates the role dna profile.
	 *
	 * @param role the role
	 * @param size the size
	 * @return the element
	 */
	public static Element createRoleDnaProfile(Role role, int size) {
		WorkitemTree tree = new WorkitemTree(WorkitemType.COMPETENCE);
		List<String> competencies = role.getCompetences();
		Element element = createSvgElement(size);

		for (String competence : competencies) {
			SvgBuilder.addSvgCompetence(competence, size, element, tree);
			List<Role> f = Repository.getInstance().getParentRoles(role);
			for (Role rx : f) {
				List<String> co = rx.getCompetences();
				for (String c : co) {
					SvgBuilder.addSvgImplicitCompetence(rx, c, size, element, tree);
				}
			}

		}

		return element;
	}

	/**
	 * Adds the svg person implicit competence.
	 *
	 * @param person the person
	 * @param competence the competence
	 * @param size the size
	 * @param element the element
	 * @param tree the tree
	 */
	private static void addSvgPersonImplicitCompetence(Person person, String competence, int size, Element element,
			WorkitemTree tree) {
		Integer skill = Repository.getInstance().getSkillOfPersonCompetence(person, competence);

		double opacity = ((double) skill) / 100.0;

		TreeNode node = tree.getTreeNode(competence);
		if (node != null) {
			Color c = node.getColor();
			long idx = ColorPalette.getColorOrderIndex(c, size);

			Element line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "20").attr("x2", "" + (idx + 2))
					.attr("y2", "24").attr("stroke-width", "2").attr("stroke", "gray");

			Element title = line.appendElement("title");
			title.appendText(competence + " (" + skill + "% Implizit)");

			line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "1").attr("x2", "" + (idx + 2))
					.attr("y2", "19").attr("stroke-width", "2")
					.attr("stroke", ObjectUtil.convertToHtmlColor(node.getColor()));

			title = line.appendElement("title");
			title.appendText(competence + " (" + skill + "% Implizit)");
		}
	}

	/**
	 * Adds the svg implicit competence.
	 *
	 * @param rx the rx
	 * @param competence the competence
	 * @param size the size
	 * @param element the element
	 * @param tree the tree
	 */
	private static void addSvgImplicitCompetence(IWorkitem rx, String competence, int size, Element element, WorkitemTree tree) {

		double opacity = 1.0;

		TreeNode node = tree.getTreeNode(competence);
		if (node != null) {
			Color c = node.getColor();
			long idx = ColorPalette.getColorOrderIndex(c, size);

			Element line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "20").attr("x2", "" + (idx + 2))
					.attr("y2", "24").attr("stroke-width", "2").attr("stroke", "gray");

			Element title = line.appendElement("title");
			title.appendText(competence + " (Implizit "+rx.getTitle()+")");

			line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "1").attr("x2", "" + (idx + 2))
					.attr("y2", "19").attr("stroke-width", "2")
					.attr("stroke", ObjectUtil.convertToHtmlColor(node.getColor()));

			title = line.appendElement("title");
			title.appendText(competence + " (Implizit "+rx.getTitle()+")");
		}
	}

	/**
	 * Adds the svg competence.
	 *
	 * @param competence the competence
	 * @param size the size
	 * @param element the element
	 * @param tree the tree
	 */
	private static void addSvgCompetence(String competence, int size, Element element, WorkitemTree tree) {

		double opacity = 1.0;

		TreeNode node = tree.getTreeNode(competence);
		if (node != null) {
			Color c = node.getColor();
			long idx = ColorPalette.getColorOrderIndex(c, size);

			Element line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "1").attr("x2", "" + (idx + 2))
					.attr("y2", "24").attr("stroke-width", "2")
					.attr("stroke", ObjectUtil.convertToHtmlColor(node.getColor()));

			Element title = line.appendElement("title");
			title.appendText(competence + " (Explizit)");
		}
	}

	/**
	 * Adds the svg person competence.
	 *
	 * @param person the person
	 * @param competence the competence
	 * @param size the size
	 * @param element the element
	 * @param tree the tree
	 */
	private static void addSvgPersonCompetence(Person person, String competence, int size, Element element,
			WorkitemTree tree) {
		Integer skill = Repository.getInstance().getSkillOfPersonCompetence(person, competence);

		double opacity = ((double) skill) / 100.0;

		TreeNode node = tree.getTreeNode(competence);
		if (node != null) {
			Color c = node.getColor();
			long idx = ColorPalette.getColorOrderIndex(c, size);

			Element line = element.appendElement("line");
			line.attr("opacity", opacity + "").attr("x1", "" + (idx + 2)).attr("y1", "1").attr("x2", "" + (idx + 2))
					.attr("y2", "24").attr("stroke-width", "2")
					.attr("stroke", ObjectUtil.convertToHtmlColor(node.getColor()));

			Element title = line.appendElement("title");
			title.appendText(competence + " (" + skill + "% Explizit)");
		}
	}

}
