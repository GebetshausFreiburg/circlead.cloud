package org.rogatio.circlead.view.items.voronoi;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Stroke;

import org.apache.batik.svggen.DOMGroupManager;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.rogatio.circlead.control.synchronizer.SynchronizerFactory;
import org.rogatio.circlead.model.work.Activity;
import org.rogatio.circlead.model.work.Role;
import org.rogatio.circlead.view.items.CellType;
import org.rogatio.circlead.view.items.ICell;
import org.w3c.dom.Element;

/**
 * The Class SVGCircleadGraphics2D is used to map in the svg-export links to the
 * rendered workitems
 */
public class SVGCircleadGraphics2D extends SVGGraphics2D {

	/**
	 * Instantiates a new SVG Graphics2D for Circlead to paint the voronoi
	 *
	 * @param generatorCtx the generator context of the svg
	 * @param textAsShapes the text as shapes
	 */
	public SVGCircleadGraphics2D(SVGGeneratorContext generatorCtx, boolean textAsShapes) {
		super(generatorCtx, textAsShapes);
	}

	/**
	 * Adds the link.
	 *
	 * @param element the element
	 * @param url     the url
	 * @param tooltip the tooltip
	 * @return the element
	 */
	private Element addLink(Element element, String url, String tooltip) {
		Element ref = this.getGeneratorContext().getDOMFactory().createElement("a");
		ref.setAttribute("xlink:href", url);
		Element title = this.getGeneratorContext().getDOMFactory().createElement("title");
		title.setTextContent(tooltip);
		element.appendChild(title);
		ref.appendChild(element);
		return ref;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.batik.svggen.SVGGraphics2D#fill(java.awt.Shape)
	 */
	@Override
	public void fill(Shape s) {
		Element svgShape = shapeConverter.toSVG(s);
		if (svgShape != null) {
			if (s instanceof VoronoiCell) {
				VoronoiCell c = (VoronoiCell) s;
				ICell dataCell = c.getDataCell();

				Element link = null;
				if (dataCell != null) {
					if (dataCell.getType() == CellType.ROLE) {
						Object r = dataCell.getData("role");
						if (r != null) {
							Role role = (Role) r;
							String id = role.getId(SynchronizerFactory.getInstance().getActual());
							link = addLink(svgShape, id + ".html", "[Rolle]\n"+role.getTitle() + "");
						} else {
							Element title = this.getGeneratorContext().getDOMFactory().createElement("title");
							title.setTextContent("[Keine definierte Rolle]\n"+dataCell.getName() + "");
							svgShape.appendChild(title);
						}
					}
					if (dataCell.getType() == CellType.GATEWAY) {
						Activity a = (Activity) dataCell.getData("process");
						String role = (String)dataCell.getData("roletitle");
						if (a != null) {
							String id = a.getId(SynchronizerFactory.getInstance().getActual());
							link = addLink(svgShape, id + ".html", "[Entscheidung]\n"+dataCell.getName().replace("\n", " ")
									+ "\n"+"[Prozess]\n" + a.getTitle() + "\n[Rolle]\n"+role);
						}
					}
					if (dataCell.getType() == CellType.EVENT_START) {
						Activity a = (Activity) dataCell.getData("process");
						String role = (String)dataCell.getData("roletitle");
						if (a != null) {
							String id = a.getId(SynchronizerFactory.getInstance().getActual());
							link = addLink(svgShape, "../web/" + id + ".html", "[Startaktivität]\n"+dataCell.getName().replace("\n", " ")
									+ "\n"+"[Prozess]\n" + a.getTitle() + "\n[Rolle]\n"+role);
						}
					}
					if (dataCell.getType() == CellType.EVENT_END) {
						Activity a = (Activity) dataCell.getData("process");
						String role = (String)dataCell.getData("roletitle");
						if (a != null) {
							String id = a.getId(SynchronizerFactory.getInstance().getActual());
							link = addLink(svgShape, "../web/" + id + ".html", "[Endaktivität]\n"+dataCell.getName().replace("\n", " ")
									+ "\n"+"[Prozess]\n" + a.getTitle() + "\n[Rolle]\n"+role);
						}
					}
					if (dataCell.getType() == CellType.ACTIVITY) {
						Activity a = (Activity) dataCell.getData("process");
						String role = (String)dataCell.getData("roletitle");
						if (a != null) {
							String id = a.getId(SynchronizerFactory.getInstance().getActual());
							link = addLink(svgShape, "../web/" + id + ".html", "[Aktivität]\n"+dataCell.getName().replace("\n", " ")
									+ "\n"+"[Prozess]\n" + a.getTitle() + "\n[Rolle]\n"+role);
						}
					}
				}

				if (link != null) {
					domGroupManager.addElement(link, DOMGroupManager.FILL);
				} else {
					domGroupManager.addElement(svgShape, DOMGroupManager.FILL);
				}

			} else {
				domGroupManager.addElement(svgShape, DOMGroupManager.FILL);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.batik.svggen.SVGGraphics2D#draw(java.awt.Shape)
	 */
	@Override
	public void draw(Shape s) {
		// Only BasicStroke can be converted to an SVG attribute equivalent.
		// If the GraphicContext's Stroke is not an instance of BasicStroke,
		// then the stroked outline is filled.
		Stroke stroke = gc.getStroke();
		if (stroke instanceof BasicStroke) {
			Element svgShape = shapeConverter.toSVG(s);
			if (svgShape != null) {
				domGroupManager.addElement(svgShape, DOMGroupManager.DRAW);
			}
		} else {
			Shape strokedShape = stroke.createStrokedShape(s);
			fill(strokedShape);
		}
	}

}
