package org.rogatio.circlead.view.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IEdge;
import com.yworks.yfiles.graph.INode;

import de.alsclo.voronoi.graph.Point;

/**
 * The Class VoronoiGraph.
 */
public class VoronoiGraph extends Component {

	/** The Constant POINT_SIZE. */
	private static final double POINT_SIZE = 5.0;

	/** The diagram. */
	private final VoronoiExtended diagram;

	/** The process graph. */
	private ProcessGraph processGraph;

	/**
	 * Instantiates a new voronoi graph.
	 *
	 * @param processGraph the process graph
	 */
	public VoronoiGraph(ProcessGraph processGraph) {
		this.processGraph = processGraph;

		int offset = 50;

		List<Point> points = processGraph.getNodePoints(offset);

		RectD r = processGraph.getBounds();

		int w = (int) r.width + offset * 2;
		int h = (int) r.height + offset * 2;

		this.setBounds((int) r.x, (int) r.y, w, h);

		for (int i = 0; i < w; i += 50) {
			Point p = new Point(i, 0);
			points.add(p);
		}
		for (int i = 0; i < h; i += 50) {
			Point p = new Point(0, i);
			points.add(p);
		}
		for (int i = 0; i < h - 50; i += 50) {
			Point p = new Point(i, h - 50);
			points.add(p);
		}
		for (int i = 0; i < w - 50; i += 50) {
			Point p = new Point(w - 50, i);
			points.add(p);
		}

		this.diagram = new VoronoiExtended(points);
		diagram.setProcessGraph(processGraph);
		diagram.relax().relax();

	}

	/**
	 * Export png.
	 *
	 * @param filename the filename
	 */
	public void exportPng(String filename) {
		try {
			BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			this.paint(image.getGraphics());
			File outputfile = new File(filename);
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		ArrayList<VoronoiCell> cells = this.diagram.getCells();
		for (VoronoiCell cell : cells) {

			Color c = Color.BLACK;
			if (cell.getCenter() instanceof CellPoint) {
				c = processGraph.getColor(((CellPoint) cell.getCenter()).getNode());// graph.getColor(diagram.getNode(cell));
			}

			g2.setStroke(new BasicStroke());
			g2.setPaint(c);
			g2.fill(cell);

			/*
			 * List<Edge> edges = cell.getEdges(); for (Edge edge : edges) {
			 * g2.setPaint(Color.LIGHT_GRAY); g2.drawLine((int) edge.getSite1().x, (int)
			 * edge.getSite1().y, (int) edge.getSite2().x, (int) edge.getSite2().y ); }
			 */

		}

		for (Point site : diagram.getGraph().getSitePoints()) {
			g2.setStroke(new BasicStroke());

			if (site instanceof CellPoint) {
				g2.setPaint(Color.WHITE);
				if (processGraph.isRole(((CellPoint) site).getNode())) {
					int size = 3;
					g2.fillOval((int) Math.round(site.x - (int) (size * POINT_SIZE / 2)),
							(int) Math.round(site.y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
							(int) (size * POINT_SIZE));
				} else {
					g2.fillRect((int) Math.round(site.x - POINT_SIZE / 2), (int) Math.round(site.y - POINT_SIZE / 2),
							(int) POINT_SIZE, (int) POINT_SIZE);
				}
			} else {
				g2.setPaint(Color.BLACK);
				double size = 0.5;
				g2.fillOval((int) Math.round(site.x - (int) (size * POINT_SIZE / 2)),
						(int) Math.round(site.y - (int) (size * POINT_SIZE / 2)), (int) (size * POINT_SIZE),
						(int) (size * POINT_SIZE));
			}

		}

		g2.setStroke(new BasicStroke());
		g2.setPaint(Color.BLACK);

		diagram.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() != null).forEach(e -> {
			Point a = e.getA().getLocation();
			Point b = e.getB().getLocation();
			g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
		});

		g2.setPaint(Color.WHITE);
		for (Point site : diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				INode n = ((CellPoint) site).getNode();
				if (processGraph.isRole(n)) {
					String label = "";
					try {
						label = n.getLabels().first().getText();
					} catch (Exception e) {

					}
					g2.drawString(label, (int) site.x, (int) site.y + 20);
				}
			}
		}

		g2.setPaint(Color.WHITE);
		for (IEdge edge : this.processGraph.getActivityEdges()) {
			Point a = this.getStart(edge);
			Point b = this.getEnd(edge);
			if (!processGraph.isRole(edge.getSourceNode())) {
				g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
			}
		}

	}

	private Point getStart(IEdge edge) {
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
//				if (processGraph.isRole(p.getNode())) {
				if (p.getNode().equals(edge.getSourceNode())) {
					return site;
				}
//				}
			}
		}
		return null;
	}

	private Point getEnd(IEdge edge) {
		for (Point site : this.diagram.getGraph().getSitePoints()) {
			if (site instanceof CellPoint) {
				CellPoint p = (CellPoint) site;
//				if (processGraph.isRole(p.getNode())) {
				if (p.getNode().equals(edge.getTargetNode())) {
					return site;
				}
//				}
			}
		}
		return null;
	}

}
